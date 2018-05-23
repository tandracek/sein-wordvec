package com.tim;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.word2vec.Word2Vec;
import org.deeplearning4j.text.sentenceiterator.BasicLineIterator;
import org.deeplearning4j.text.sentenceiterator.SentenceIterator;
import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.CommonPreprocessor;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;
import org.jsoup.UncheckedIOException;

public class Process {
    public static final String HOME = System.getProperty("user.home");
    public static final String DOWNLOAD_PATH = HOME + "/seinfeld/original";
    public static final String PARSED_PATH = HOME + "/seinfeld/parsed";
    public static final String ALL_PATH = HOME + "/seinfeld/allScripts";
    public static final String VEC_PATH = HOME + "/seinfeld/wordVec.txt";

    public static void trainAndWrite() {
        String filePath = ALL_PATH;

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

    public static void runExistingModel() {
        Word2Vec vec = WordVectorSerializer.readWord2VecModel(new File(VEC_PATH)); 

        System.out.println("Closest Words:");
        Collection<String> lst = vec.wordsNearestSum("susan", 10);
        System.out.println(String.format("10 Words closest to 'susan': %s", lst.toString()));
    }
}