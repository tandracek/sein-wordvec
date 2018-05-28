package com.tim;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.word2vec.Word2Vec;
import org.deeplearning4j.text.sentenceiterator.BasicLineIterator;
import org.deeplearning4j.text.sentenceiterator.SentenceIterator;
import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.CommonPreprocessor;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;
import org.jsoup.UncheckedIOException;

import static com.tim.Paths.MERGED_PATH;
import static com.tim.Paths.VEC_PATH;

//TODO compare against glove
public class Word2VecProcess {
    private Word2Vec vec;

    private Word2VecProcess(Word2Vec vec) {
        this.vec = vec;
    }

    public void printClosest(String phrase, int numClosest) {
        Collection<String> lst = this.vec.wordsNearestSum(phrase, numClosest);
        System.out.println(String.format("%d Words closest to '%s': %s", numClosest, phrase, lst.toString()));
    }

    public void printFindRelatedWord(String word1, String word2, String compare) {
        Collection<String> nearest = this.vec.wordsNearestSum(Arrays.asList(compare, word2), Arrays.asList(word1), 1);
        System.out.println(String.format("%s is to %s, as %s is to %s", word1, word2, compare, nearest.toString()));
    }

    public static Word2VecProcess from(String modelPath) {
        Word2Vec model = WordVectorSerializer.readWord2VecModel(new File(modelPath)); 
        return new Word2VecProcess(model);
    }

    public static void trainAndWrite() {
        String filePath = MERGED_PATH;

        System.out.println("Load & Vectorize Sentences....");
        SentenceIterator iter = null;
        try {
            iter = new BasicLineIterator(filePath);
        } catch (IOException io) {
            throw new UncheckedIOException(io);
        }
        
        TokenizerFactory t = new DefaultTokenizerFactory();

        t.setTokenPreProcessor(new CommonPreprocessor());

        System.out.println("Building model....");
        Word2Vec vec = new Word2Vec.Builder()
                .minWordFrequency(5)
                .iterations(1)
                .layerSize(100)
                .seed(42)
                .windowSize(5)
                .iterate(iter)
                .tokenizerFactory(t)
                .build();

        System.out.println("Fitting Word2Vec model....");
        vec.fit();

        System.out.println("Writing word vectors to text file....");
        WordVectorSerializer.writeWord2VecModel(vec, new File(VEC_PATH));
    }
}