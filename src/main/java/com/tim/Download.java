package com.tim;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Download {
    
    public static void downloadScripts(String downloadPath) {
        WebsiteDownload download = new WebsiteDownload();
        Document root = get("http://www.seinfeldscripts.com/seinfeld-scripts.html");
        List<String> links = WebsiteParser.getLinksByElement(root, "table")
                                .stream()
                                .map(link -> "http://www.seinfeldscripts.com/" + link.replaceAll(" +", ""))
                                .collect(Collectors.toList());
        download.downloadAll(1500, links, downloadPath);
    }

    public static void parseScripts(String pathToFiles, String parsedPath) {
        if (!new File(pathToFiles).exists()) {
            throw new RuntimeException("Path " + pathToFiles + " does not exist.");
        }
        File directory = new File(pathToFiles);
        for (File file : directory.listFiles()) {
            if (!file.isDirectory()) {
                try {
                    System.out.println("Parsing " + file.getName());
                    Document script = Jsoup.parse(file, "UTF-8");
                    String parsed = WebsiteParser.strip(script, "p", element -> {
                        return element.children().isEmpty() && element.hasText();
                    });
                    parsed = furtherParse(parsed);
                    IO.write(parsed, parsedPath + "/" + file.getName());
                } catch (IOException io) {
                    System.out.println("Error occured while parsing file: " + file);
                    io.printStackTrace();
                }
            }
        }
    }

    public static String furtherParse(String contents) {
        return contents.replaceAll("\\[.*\\]", "").replaceAll("&nbsp;", "").replaceAll(" {2,}", " ");
    }

    public static Document get(String url) {
        try {
            return Jsoup.connect(url).get();
        } catch (IOException io) {
            throw new UncheckedIOException(io);
        }
    }
}