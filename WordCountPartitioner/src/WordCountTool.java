


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

/* 
// Map Reduce Driver program
// Without GenericOptionsParser, Tool and ToolRunner
 * Aim is to understand basic components of MR programming.
 * Mapper
 * Reducer
 * Driver
 * Driver Code
 * Paths
 * How to Set Input File Format and Outpu File Format
 * How To run the WordCount Job
 * 
 */

/* 
// To DO list
// 1. Run in eclipse
 * 2. Run in cluster
 * 3. See all the WebUI logs and stats
 * 
 */
public class WordCountTool extends Configured implements Tool{
	
	@Override
	public int run(String[] args) throws Exception {
		
		//Comment the below line
		// Configuration conf=new Configuration();
		
		Configuration conf=getConf();
		// Job job=new Job(); deprecated in Hadoop 2
		Job job=Job.getInstance(conf,"Word Count");  //Invoke the Static method.
		
		job.setJarByClass(WordCountTool.class); // set the driver class
		// Specify the Mapper and Reducer classes
		// Here you need to specify class extension because framework gets the code using 
		// Java reflection and substitutes the code
		job.setMapperClass(WordCountMapper.class);
		job.setReducerClass(WordCountReducer.class);
		
		// specify the reducer output key and value types
		
		job.setOutputKeyClass(IntWritable.class);
		job.setOutputValueClass(IntWritable.class);
		
		// Specify the Input file URI, in Hadoop we use the Path object 
		// Path is in hadoop.fs package
		// Note that both the FileInputFormat and FileOutputFormat are from mapreduce.lib package 
		FileInputFormat.addInputPath(job, new Path(args[0]));
		//Specify the output format
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		//Added here
		
		//job.setNumReduceTasks(2);
		
		
		
		// Run the job
		//Comment the below line
		// System.exit(job.waitForCompletion(true)? 0:1 );
		return job.waitForCompletion(true)?0:1;
	}
	
	public static void main(String[] args) throws Exception{
		
		int exitCode=ToolRunner.run(new WordCountTool(), args);
		System.exit(exitCode);
		
	}

}
