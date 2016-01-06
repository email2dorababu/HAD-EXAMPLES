

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class WordCountReducer extends Reducer<Text, IntWritable, Text, IntWritable>{

	// The Reducer class input types should match with the Mapper output types
	// And output types are , we write word and its count.
	
	// For reduce method will run for every key once, the key is the word and 
	// values are List of values for that key
	public IntWritable result=new IntWritable(); // to write the sum value to context
	@Override
	protected void reduce(Text key, Iterable<IntWritable> values,
			Context context) throws IOException, InterruptedException {
		
		// we calculate sum of values , here it is 1+1+1 and so on..
		int sum=0;
		for(IntWritable val: values){
			sum = sum+ val.get();
		}
		result.set(sum);
		context.write(key,result);  // finally emit the word and count
	}
	
	
}
