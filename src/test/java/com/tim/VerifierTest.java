package com.tim;

import java.io.File;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class VerifierTest {

    @Before
    public void before() {
        IO.deleteFolder("src/test/resources/verifier");
    }

    @Test
    public void countNewline() {
        IO.write(createLargeString(200), "src/test/resources/verifier/testNewLineBad.txt");
        IO.write(createLargeString(400), "src/test/resources/verifier/testNewLineGood.txt");
        Verifier verifier = new Verifier("src/test/resources/verifier");
        List<File> files = verifier.getIncorrectDownloads();
        assertEquals(1, files.size());
    }

    @Test
    public void verifyFiles() {
        Verifier verifier = new Verifier(Locations.DOWNLOAD_PATH);
        List<File> files = verifier.getIncorrectDownloads();
        System.out.println("Got the following incorrect: \n" + files.toString());
    }

    private static String createLargeString(int count) {
        StringBuilder builder = new StringBuilder();
        IntStream.range(0, count).forEach(i -> {
            builder.append("something\n");
        });
        return builder.toString();
    }
}