package nagi.hadoop.mapred.advanced;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class TokenizerMapper extends Mapper<Object, Text, Text, IntWritable> {

    enum CounterEnum { INPUT_WORDS }

    private static final IntWritable one = new IntWritable(1);
    private Text word = new Text();

    private boolean caseSensitive;
    private Set<String> patternToSkip = new HashSet<>();

    private Configuration conf;
    private BufferedReader reader;

    @Override
    protected void setup(Mapper<Object, Text, Text, IntWritable>.Context context) throws IOException, InterruptedException {
        conf = context.getConfiguration();
        conf.getBoolean("WordCount.case.sensitive", true);

    }

    @Override
    protected void map(Object key, Text value, Mapper<Object, Text, Text, IntWritable>.Context context) throws IOException, InterruptedException {
        super.map(key, value, context);
    }
}
