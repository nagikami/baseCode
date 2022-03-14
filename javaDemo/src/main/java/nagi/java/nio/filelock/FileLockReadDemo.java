package nagi.java.nio.filelock;

import java.io.BufferedReader;
import java.io.FileReader;

public class FileLockReadDemo {
    public static void main(String[] args) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("E:\\testData\\01.txt"))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
