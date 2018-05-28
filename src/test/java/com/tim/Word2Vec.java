package com.tim;

import org.junit.Test;

import static com.tim.Paths.WORDVEC_MODEL;

public class Word2Vec {

    @Test
    public void train() {
        try {
            Word2VecProcess.trainAndWrite();
        } catch (Exception e) {
            System.out.println("Got exception");
            e.printStackTrace();
        }
    }

    @Test
    public void runExisting() {
        Word2VecProcess.from(WORDVEC_MODEL).printClosest("costanza", 5);
    }

    @Test
    public void isTo() {
        Word2VecProcess.from(WORDVEC_MODEL).printFindRelatedWord("george", "susan", "elaine");
    }
}