package nagi.lucene;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;

import java.nio.file.Paths;

public class SearchDemo {
    public static void main(String[] args) {
        //create indices dir
        try (DirectoryReader reader = DirectoryReader.open(FSDirectory.open(Paths.get("E:\\testData\\lucene\\indices")))) {
            //create searcher
            IndexSearcher searcher = new IndexSearcher(reader);
            StandardAnalyzer analyzer = new StandardAnalyzer();
            //create query parser and set default field and analyzer
            QueryParser parser = new QueryParser("content", analyzer);
            //parse query string
            Query query = parser.parse("陈");
            //search and return top 10
            TopDocs topDocs = searcher.search(query, 10);
            System.out.println(topDocs.totalHits);
            ScoreDoc[] scoreDocs = topDocs.scoreDocs;
            for (ScoreDoc scoreDoc : scoreDocs) {
                //unique id of lucene
                int docId = scoreDoc.doc;
                //read doc from fs by doc id
                Document doc = searcher.doc(docId);
                System.out.println(doc.getField("id"));
                System.out.println(doc.getField("content"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
