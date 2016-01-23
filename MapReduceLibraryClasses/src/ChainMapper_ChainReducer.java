import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.chain.ChainMapper;
import org.apache.hadoop.mapreduce.lib.chain.ChainReducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


public class ChainMapper_ChainReducer {
	// From Documentation
// Chain Mapper class Allows to use Multiple Mapper classes within  a single Map task
// Chain Reducer class Allows to chain multiple Mapper classes after a Reducer within the Reducer task.
// M+ R M* - is achieved using the chain Mapper and the Chain Reducer classes.
//IMP- No need to specify the output key/value classes for the chainReducer, this is done by setReducer or addMapper for the last element in the chain.
//Care has to be taken when creating  chains that the key/value output by a Mapper are valid for the following Mapper in the chain.

	//First Mapper - SplitMapper - splits the line
	//Second Mapper- LowerCaseMapper-convert all the words to lower case
	//Reducer - Reducer, word count
	//Mapper after reducer - reverses the string
	
	public static class SplitMapper extends Mapper<LongWritable, Text, Text, IntWritable>{
		private Text word=new Text();
		@Override
		protected void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException {
			StringTokenizer itr=new StringTokenizer(value.toString());
			while(itr.hasMoreTokens()){
				word.set(itr.nextToken());
				context.write(word, new IntWritable(1)); // every time invoking new is inefficent
			}
		}
	}
	public static class LowerCaseMapper extends Mapper<Text, IntWritable, Text, IntWritable>{
		//private Text word=new Text();
		@Override
		protected void map(Text key, IntWritable value, Context context)
				throws IOException, InterruptedException {
				context.write(new Text(key.toString().toLowerCase()), value); // every time invoking new is inefficent
			
		}
	}
	public static class WordCountReducer extends Reducer<Text, IntWritable, Text, IntWritable>{
		public IntWritable result=new IntWritable(); // to write the sum value to context
		@Override
		protected void reduce(Text key, Iterable<IntWritable> values,
				Context context) throws IOException, InterruptedException {
			int sum=0;
			for(IntWritable val: values){
				sum = sum+ val.get();
			}
			result.set(sum);
			context.write(key,result);  // finally emit the word and count
		}
	}

	public static class ReverseMapper extends Mapper<Text, IntWritable, Text, IntWritable>{
		//private Text word=new Text();
		@Override
		protected void map(Text key, IntWritable value, Context context)
				throws IOException, InterruptedException {
				
			String reverse=new StringBuffer(key.toString()).reverse().toString(); 
				context.write(new Text(reverse), value); // every time invoking new is inefficent
			
		}
	}
	
	
	public static void main(String[] args) throws Exception{
			Configuration conf=new Configuration();
			
			Job job=Job.getInstance(conf,"Word Count");  //Invoke the Static method.
			
			
			Configuration splitMapConfig=new Configuration(false);
			ChainMapper.addMapper(job, SplitMapper.class, LongWritable.class, Text.class, Text.class, IntWritable.class, splitMapConfig);
			
			Configuration LowerCaseMapConfig=new Configuration(false);
			ChainMapper.addMapper(job, LowerCaseMapper.class, Text.class, IntWritable.class, Text.class, IntWritable.class, LowerCaseMapConfig);
			
			Configuration WordCountReducerConfig=new Configuration(false);
			ChainReducer.setReducer(job, WordCountReducer.class, Text.class, IntWritable.class, Text.class, IntWritable.class, WordCountReducerConfig);
			
			
			Configuration ReverseMapperConfig=new Configuration(false);
			ChainReducer.addMapper(job, ReverseMapper.class, Text.class, IntWritable.class, Text.class, IntWritable.class, ReverseMapperConfig);
			
			job.setJarByClass(ChainMapper_ChainReducer.class); // set the driver class
			
			
			//job.setMapperClass(WordCountMapper.class);
			//job.setReducerClass(WordCountReducer.class);
			
			//job.setOutputKeyClass(Text.class);
			//job.setOutputValueClass(IntWritable.class);
			
			FileInputFormat.addInputPath(job, new Path(args[0]));
			FileOutputFormat.setOutputPath(job, new Path(args[1]));
			
			System.exit(job.waitForCompletion(true)? 0:1 );
			
			
		}

	
	
}
