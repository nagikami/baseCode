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
        DecompressGzip.decompressWithNIO(src, dest);
        decompressTar(dest, unTar);
    }

    public static void decompressTar(Path src, String dest) {
        try (TarArchiveInputStream tarArchiveInputStream = new TarArchiveInputStream(new FileInputStream(src.toFile()))) {
            TarArchiveEntry tarArchiveEntry;
            while ((tarArchiveEntry = tarArchiveInputStream.getNextTarEntry()) != null) {
                File file = new File(dest + File.separator + tarArchiveEntry.getName());
                if (tarArchiveEntry.isDirectory()) {
                    file.mkdirs();
                } else {
                    file.getParentFile().mkdirs();
                    Files.copy(tarArchiveInputStream, file.toPath());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
