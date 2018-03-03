package com.tim;

import java.io.File;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class DownloadTest {

    @Test
    public void download() {
        System.out.println("Downloading");
        Download.downloadScripts(Locations.DOWNLOAD_PATH);
    }

    @Test
    public void parse() {
        System.out.println("Parsing...");
        Download.parseScripts(Locations.DOWNLOAD_PATH, Locations.PARSED_PATH);
    }
}