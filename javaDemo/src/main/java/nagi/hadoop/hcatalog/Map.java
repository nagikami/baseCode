package nagi.hadoop.hcatalog;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hive.hcatalog.data.HCatRecord;

import java.io.IOException;

public class Map extends Mapper<WritableComparable, HCatRecord, IntWritable, IntWritable> {
    int age;

    @Override
    protected void map(WritableComparable key, HCatRecord value, Mapper<WritableComparable, HCatRecord, IntWritable, IntWritable>.Context context) throws IOException, InterruptedException {
        age = (Integer) value.get(1);
        context.write(new IntWritable(age), new IntWritable(1));
    }
}
