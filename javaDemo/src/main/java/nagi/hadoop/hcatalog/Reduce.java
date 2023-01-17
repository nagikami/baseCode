package nagi.hadoop.hcatalog;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hive.hcatalog.data.DefaultHCatRecord;
import org.apache.hive.hcatalog.data.HCatRecord;

import java.io.IOException;
import java.util.Iterator;

public class Reduce extends Reducer<IntWritable, IntWritable, WritableComparable, HCatRecord> {
    @Override
    protected void reduce(IntWritable key, Iterable<IntWritable> values, Reducer<IntWritable, IntWritable, WritableComparable, HCatRecord>.Context context) throws IOException, InterruptedException {
        int sum = 0;
        Iterator<IntWritable> iterator = values.iterator();
        while (iterator.hasNext()) {
            sum++;
            iterator.next();
        }
        DefaultHCatRecord record = new DefaultHCatRecord(2);
        record.set(0, key.get());
        record.set(1, sum);
        context.write(null, record);
    }
}
