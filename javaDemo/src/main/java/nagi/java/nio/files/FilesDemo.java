package nagi.java.nio.files;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FilesDemo {
    public static void main(String[] args) throws IOException {
        Path sourcePath = Paths.get("E:\\testData\\01.txt");
        Path destPath = Paths.get("E:\\testData\\001.txt");

        Files.copy(sourcePath, destPath, StandardCopyOption.REPLACE_EXISTING);
    }
}
