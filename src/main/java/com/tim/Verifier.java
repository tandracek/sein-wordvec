package com.tim;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Verifier {
    private String path;

    public Verifier(String path) {
        this.path = path;
    }

    public List<File> getIncorrectDownloads() {
        return checkEachFile(contents -> {
            return contents.chars().filter(ch -> (char)ch == '\n').count() <= 300;
        });
    }

    private List<File> checkEachFile(Predicate<String> filter) {
        File pathToFiles = new File(this.path);
        return Arrays.stream(pathToFiles.listFiles())
                     .filter(file -> {
                         System.out.println("File: " + file.getAbsolutePath());
                         return filter.test(IO.readFile(file, "ISO-8859-1"));
                     })
                     .collect(Collectors.toList());
    }
}