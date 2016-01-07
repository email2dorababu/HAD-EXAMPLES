import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper;

public class BigramMapper extends Mapper<LongWritable,Text,BigramTextPair,IntWritable> {

	private static Text word1=new Text();
	private static Text word2=new Text();
	private static BigramTextPair textPair=new BigramTextPair();
	
	@Override
	protected void map(LongWritable key, Text value,
			Context context)
					throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		String line=value.toString();
		line.replace(",", "");
		line.replace(".", "");
		
		StringTokenizer itr=new StringTokenizer(value.toString());
		// We read each and every word and emit the word as key and value as 1.
		while(itr.hasMoreTokens()){
			
			word1.set(itr.nextToken());
			
			//In case of odd no of words in the line, the second word will be null
			if(itr.hasMoreTokens() == true){
				word2.set(itr.nextToken());
			}
					
			textPair.set(word1,word2);
			
			context.write(textPair, new IntWritable(1)); // every time invoking new is inefficent
		// hence declare a IntWritable object and reuse it.	
			// context.write(word, one);
		}

	}
}
	
	
	

