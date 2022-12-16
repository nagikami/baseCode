package nagi.hadoop.mapred;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.StringTokenizer;

// 继承Mapper，通过泛型制定输入输出的<K,V>类型
public class TokenizerMapper extends Mapper<Object, Text, Text, IntWritable> {
    // key和value需要在Mapper输出时需要序列化，因此需要实现Writable接口
    private final static IntWritable one = new IntWritable(1);
    private Text word = new Text();

    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        // 使用分词类（已过时，建议使用split）将字符串通过delimiter分词
        StringTokenizer itr = new StringTokenizer(value.toString());
        // 遍历word，生成<K,V>对
        while (itr.hasMoreTokens()) {
            word.set(itr.nextToken());
            // 写入<K,V>对，<word,1>
            context.write(word, one);
        }
    }
}
