package com.tim;

import java.io.File;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import static com.tim.Paths.DOWNLOAD_PATH;
import static com.tim.Paths.PARSED_PATH;
import static com.tim.Paths.MERGED_PATH;

// @Ignore
public class Run {

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
    public void stripSingle() {
        System.out.println("Stripping single...");
        String parsed = Download.getAndStripDown(new File(DOWNLOAD_PATH + "/script-103"));
        System.out.println(parsed);
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
        IO.mergeText(new File(PARSED_PATH), MERGED_PATH);
    }
}