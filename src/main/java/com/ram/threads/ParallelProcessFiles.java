package com.ram.threads;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Thread.sleep;

public class ParallelProcessFiles {
    /*
     1. Scan src main resources for files.
     2. Once a file is found, create a thread
     3. Calculate the hash of each line and output to _output suffix
     4. Write to output /src/main/resources/output
     5. Throw an exception if a line is empty
   */

    static List<String> processedFiles;

    public static void main(String[] args) {
        processedFiles = new ArrayList<>();
        Thread checkFileThread = new Thread(new CheckFileThread());
        checkFileThread.start();
    }

    static class CheckFileThread implements Runnable {

        @Override
        public void run() {
            try {
                while (true) {
                    Thread.sleep(1000);
                    Path inputResourcePath = Paths.get("src", "main", "resources", "input");
                    List<String> filesToProcess = getFileToProcess(inputResourcePath);
                    System.out.println("Files to process: " + filesToProcess.size());
                    if(filesToProcess.isEmpty()) {
                        System.out.println("No file to process");
                        break;
                    } else {
                        filesToProcess.forEach(filePath -> new Thread(new ProcessFileThread(filePath)).start());
                    }
                }
            } catch(InterruptedException interruptedException) {
                System.out.println(Thread.currentThread().getName() + " interrupted");
            }
        }
    }

    static class ProcessFileThread implements Runnable {
        Path path;
        public ProcessFileThread(String filePath) {
            path = Paths.get(filePath);
        }

        @Override
        public void run() {
            try {
                System.out.println("Processing file : " + path.toString() + " with thread " + Thread.currentThread().getName());
                List<String> fileLines = Files.lines(path).collect(Collectors.toList());
                fileLines.forEach(System.out::println);
                processedFiles.add(path.toString());
                System.out.println("Processed files size: " + processedFiles.size());
            }  catch (IOException e) {
                System.out.println("Error processing file");
                throw new RuntimeException(e);
            }
        }

    }
    static List<String> getFileToProcess(Path inputfilePath) {
        List<String> filesToProcess = new ArrayList<>();
        List<String> filesInCurrentDir;
        try {
            filesInCurrentDir = Files.list(inputfilePath).map(Path::toString).collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("Error with getting files to process.");
        }

        for(String filePath: filesInCurrentDir) {
            if(!processedFiles.contains(filePath)) {
                filesToProcess.add(String.valueOf(Paths.get(filePath)));
            }
        }

        return filesToProcess;
    }
}
