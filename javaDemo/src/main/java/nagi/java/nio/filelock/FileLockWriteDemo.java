package nagi.java.nio.filelock;

import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileLockWriteDemo {
    //文件锁为进程锁
    public static void main(String[] args) throws Exception {
        FileChannel fileChannel = FileChannel.open(Paths.get("E:\\testData\\01.txt"), StandardOpenOption.WRITE);
        ByteBuffer byteBuffer = ByteBuffer.wrap("hello".getBytes());

        //write from end
        fileChannel.position(fileChannel.size() - 1);

        //acquire lock
        FileLock lock = fileChannel.lock();
        System.out.println("Is shared " + lock.isShared());

        Thread.sleep(10000);

        fileChannel.write(byteBuffer);
        fileChannel.close();
    }
}
