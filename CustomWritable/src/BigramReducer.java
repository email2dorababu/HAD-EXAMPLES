import java.io.IOException;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Reducer;

public class BigramReducer extends Reducer<BigramTextPair, IntWritable, Text, IntWritable>{
	
	private static Text textPair=new Text(); // this is for reusing the variable
	
	public void reduce(BigramTextPair key,Iterable<IntWritable> values,Context context) throws IOException,InterruptedException
	{
		int count=0;
		for (IntWritable value:values){
			count+=value.get();
		}
		textPair.set(key.toString());
		context.write(textPair, new IntWritable(count));
	}

}
