package nagi.lucene;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.FSDirectory;

import java.nio.file.Paths;

public class BooleanQueryDemo {
    public static void main(String[] args) {
        //create indices dir
        try (DirectoryReader reader = DirectoryReader.open(FSDirectory.open(Paths.get("E:\\testData\\lucene\\indices")))) {
            //create searcher
            IndexSearcher searcher = new IndexSearcher(reader);
            //create analyzer, must same as analyzer of indexing files
            StandardAnalyzer analyzer = new StandardAnalyzer();
            //create query parser and set default field and analyzer
            QueryParser parser = new QueryParser("content", analyzer);
            //parse query string
            Query query1 = parser.parse("陈 AND 梦");
            Query query2 = IntPoint.newRangeQuery("price", 1, 101);
            BooleanQuery.Builder builder = new BooleanQuery.Builder();
            builder.add(query1, BooleanClause.Occur.MUST);
            builder.add(query2, BooleanClause.Occur.MUST);
            BooleanQuery query = builder.build();
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
