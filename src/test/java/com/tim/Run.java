package com.tim;

import java.io.File;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class Run {
    public static final String HOME = System.getProperty("user.home");
    public static final String DOWNLOAD_PATH = HOME + "/seinfeld/original";
    public static final String PARSED_PATH = HOME + "/seinfeld/parsed";

    @Test
    public void downloadSeinology() {
        System.out.println("Downloading seinology");
        Download.downloadSeinology(DOWNLOAD_PATH);
    }

    @Test
    public void parse() {
        System.out.println("Parsing...");
        Download.parseScripts(DOWNLOAD_PATH, PARSED_PATH);
    }

    @Test
    public void verifyFiles() {
        Verifier verifier = new Verifier(DOWNLOAD_PATH);
        List<File> files = verifier.getIncorrectDownloads();
        System.out.println("Got the following incorrect: \n" + files.toString());
    }

    @Test
    public void merge() {
        System.out.println("Merging...");
        IO.mergeText(new File(PARSED_PATH), HOME + "/seinfeld/allScripts");
    }

    @Test
    public void train() {
        try {
            Process.trainAndWrite();
        } catch (Exception e) {
            System.out.println("Got exception");
            e.printStackTrace();
        }
    }

    @Test
    public void runExisting() {
        Process.runExistingModel();
    }
}