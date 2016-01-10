import java.io.IOException;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

public class DCReducer extends Reducer<Text, Text, Text, NullWritable>{
	
	
	public void reduce(Text key,Text value, Context context) throws IOException, InterruptedException{
		context.write(value,NullWritable.get());
	}
	
	
}