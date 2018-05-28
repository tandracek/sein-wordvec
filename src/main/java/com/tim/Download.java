package com.tim;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class Download {
    private static final List<Integer> DOUBLES = Arrays.asList(82, 100, 177, 179);

    public static void downloadSeinology(String downloadPath) {
        IntStream.range(1, 180).forEach(index -> {
            String scriptNumber = String.valueOf(index);
            if (DOUBLES.contains(index - 1)) {
                return;
            }
            if (DOUBLES.contains(index)) {
                scriptNumber = String.format("%dand%d", index, index + 1);
            } else if (index < 10) {
                scriptNumber = "0" + scriptNumber;
            }

            System.out.println("Getting number " + scriptNumber);
            String link = String.format("http://www.seinology.com/scripts/script-%s.shtml", scriptNumber);
            WebsiteDownload.downloadLink(3000, link, downloadPath);
        });
    }

    public static void parseScripts(String pathToFiles, String parsedPath) {
        if (!new File(pathToFiles).exists()) {
            throw new RuntimeException("Path " + pathToFiles + " does not exist.");
        }
        File directory = new File(pathToFiles);
        for (File file : directory.listFiles()) {
            if (!file.isDirectory()) {
                System.out.println("Parsing " + file.getName());    
                String parsed = getAndStripDown(file);
                IO.write(parsed, parsedPath + "/" + file.getName());
            }
        }
    }

    public static String getAndStripDown(File file) {
        String html = IO.readFile(file);
        String parsed = WebsiteParser.strip(html, ".spacer2 font", ele -> true);
        parsed = parsed.replaceAll("<br>", "\n");
        int index = parsed.lastIndexOf("=");
        if (index < 0) {
            throw new RuntimeException(String.format("Unable to parse %s. Expecting an '=' symbol in the html.", file.getName()));
        }
        parsed = parsed.substring(index + 1, parsed.length());
        parsed = parsed.replaceAll("&nbsp;", "");
        parsed = parsed.replaceAll("\\[.*\\]", "");
        parsed = parsed.replaceAll("(?m)^[ \t]*\r?\n", "");
        return parsed;
    }
}