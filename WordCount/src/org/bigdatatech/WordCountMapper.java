package org.bigdatatech;



import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;


// Mapper and reducer are in Package org.apache.hadoop.mapreduce
//Hence we need to add mapreduce client core jar file 
// The jar file is in share/hadoop/mapreduce/hadoop-mapreduce-client-core-2.7.1.jar

// decide the input and output types for the mapper

// Now the data types are present in Hadoop Common Core jar file, these are heart of Hadoop
public class WordCountMapper extends Mapper<LongWritable, Text, Text, IntWritable>{


	// Mapper class contains map method, which we override and implement out code
	// map method runs for every input record

	private Text word=new Text();

	@Override
	protected void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		// we get byte offset as key and line as value.
		// we convert Text into String since there are not much methods for String manupulation present in Text 
		// and we split the string based on some delimiter, here it is space.
		StringTokenizer itr=new StringTokenizer(value.toString());
		// We read each and every word and emit the word as key and value as 1.
		while(itr.hasMoreTokens()){
			word.set(itr.nextToken());
			context.write(word, new IntWritable(1));
		}

	}

}
