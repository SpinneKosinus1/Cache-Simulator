package org.example.Model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileLoader {
    private FileLoader() {

    }

    public static List<String> GetFileContent(String FilePath)  {
        try {
            List<String> TraceFile = Files.readAllLines(Path.of(FilePath));
            return TraceFile;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
