package nagi.util.compress;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DecompressTar {
    public static void main(String[] args) {
        Path src = Paths.get("E:\\data\\compress\\tar.tar.gz");
        Path dest = Paths.get("E:\\data\\compress\\tar.tar");
        String unTar = "E:\\data\\compress\\unTar";
        DecompressGzip.deCompressWithNIO(src, dest);
        deCompressTar(dest, unTar);
    }

    public static void deCompressTar(Path src, String dest) {
        try (TarArchiveInputStream tarArchiveInputStream = new TarArchiveInputStream(new FileInputStream(src.toFile()))) {
            TarArchiveEntry tarArchiveEntry;
            while ((tarArchiveEntry = tarArchiveInputStream.getNextTarEntry()) != null) {
                Path path = Paths.get(dest + File.separator + tarArchiveEntry.getName());
                if (tarArchiveEntry.isDirectory()) {
                    path.toFile().mkdirs();
                } else {
                    path.getParent().toFile().mkdirs();
                    Files.copy(tarArchiveInputStream, path);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
