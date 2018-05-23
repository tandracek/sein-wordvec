package com.tim;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Collectors;

public class IO {

    public static void mergeText(File directory, String toFilePath) {
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getName() + " is not a directory.");
        }

        for (File file : directory.listFiles()) {
            String contents = readFile(file);
            write(contents, toFilePath, true);
        }
    }

    public static String readFile(File file) {
        return readFile(file, "UTF-8");
    }

    public static String readFile(File file, String charset) {
        return read(file.getPath(), charset);
    }

    public static String read(String path) {
        return read(path, "UTF-8");
    }

    public static String read(String path, String charset) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(path), Charset.forName(charset))) {
            return reader.lines().collect(Collectors.joining("\n"));
        } catch (IOException io) {
            throw new UncheckedIOException(io);
        }
    }

    public static void write(String content, String path, boolean append) {
        try (Writer writer = new FileWriter(new File(path), append)) {
            writer.write(content);
        } catch (IOException io) {
            System.out.println("Unable to write file to path: " + path);
            io.printStackTrace();
        }
    }

    public static void write(String content, String path) {
        write(content, path, false);
    }

    public static void deleteFolder(String folder) {
        File pathToDelete = new File(folder);
        Arrays.stream(pathToDelete.listFiles()).forEach(File::delete);
    }
}