package nagi.lucene;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;

public class IndexFilesDemo {
    public static void main(String[] args) {
        try {
            RandomAccessFile accessFile = new RandomAccessFile("E:\\testData\\lucene\\docs\\delegate.txt", "rw");
            FileChannel channel = accessFile.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            int read = channel.read(byteBuffer);
            StringBuilder content = new StringBuilder();
            while (read > 0) {
                byteBuffer.flip();
                content.append(new String(byteBuffer.array(), 0, byteBuffer.limit()));
                byteBuffer.clear();
                read = channel.read(byteBuffer);
            }
            //create document
            Document document = new Document();
            //set filed
            document.add(new TextField("id", "1", Field.Store.YES));
            document.add(new TextField("content", content.toString(), Field.Store.YES));
            //create indices dir
            FSDirectory indicesDir = FSDirectory.open(Paths.get("E:\\testData\\lucene\\indices"));
            StandardAnalyzer analyzer = new StandardAnalyzer();
            //create iwc and set analyzer
            IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
            //create index writer
            try (IndexWriter indexWriter = new IndexWriter(indicesDir, indexWriterConfig)) {
                indexWriter.addDocument(document);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
