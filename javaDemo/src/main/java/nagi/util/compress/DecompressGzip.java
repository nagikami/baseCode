package nagi.util.compress;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.GZIPInputStream;

public class DecompressGzip {
    public static void main(String[] args) {
        Path src = Paths.get("E:\\data\\compress\\text.txt.gz");
        Path dest = Paths.get("E:\\data\\compress\\text.txt");
        if (Files.notExists(src)) {
            System.out.println("Source file does not exist!");
            return;
        }
        //deCompressWithIO(src, target);
        deCompressWithNIO(src, dest);
    }

    public static void deCompressWithIO(Path src, Path dest) {
        try (GZIPInputStream gzipInputStream = new GZIPInputStream(new FileInputStream(src.toFile()));
             FileOutputStream fileOutputStream = new FileOutputStream(dest.toFile())) {
            byte[] buffer = new byte[1024];
            while (gzipInputStream.read(buffer) > 0) {
                fileOutputStream.write(buffer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deCompressWithNIO(Path src, Path dest) {
        try (GZIPInputStream gzipInputStream = new GZIPInputStream(new FileInputStream(src.toFile()))) {
            Files.copy(gzipInputStream, dest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
