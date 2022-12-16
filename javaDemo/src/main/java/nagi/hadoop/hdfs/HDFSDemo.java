package nagi.hadoop.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class HDFSDemo {
    public static void main(String[] args) {
        FileSystem fileSystem = null;
        try {
            Configuration configuration = new Configuration();
            configuration.set("fs.defaultFS", "hdfs://192.168.56.101:9000");
            fileSystem = FileSystem.get(configuration);
            boolean exists = fileSystem.exists(new Path("/tmp/reg"));
            if (exists) {
                FSDataInputStream inputStream = fileSystem.open(new Path("/tmp/reg/000000_0"));
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                int read = inputStream.read(buffer);
                while (read > 0) {
                    buffer.flip();
                    System.out.println(Charset.forName(StandardCharsets.UTF_8.toString()).decode(buffer));
                    read = inputStream.read(buffer);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileSystem != null) {
                try {
                    fileSystem.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
