


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

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
public class BigramWC {
	
	public static void main(String[] args) throws Exception{
		Configuration conf=new Configuration();
		// Job job=new Job(); deprecated in Hadoop 2
		Job job=Job.getInstance(conf,"Word Count");  //Invoke the Static method.
		
		job.setJarByClass(BigramWC.class); // set the driver class
		// Specify the Mapper and Reducer classes
		// Here you need to specify class extension because framework gets the code using 
		// Java reflection and substitutes the code
		job.setMapperClass(BigramMapper.class);
		job.setReducerClass(BigramReducer.class);
		
		// specify the reducer output key and value types
		
		
		// Below two lines are mandatory
		job.setMapOutputKeyClass(BigramTextPair.class);
		job.setMapOutputValueClass(IntWritable.class);
		
		
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		
		// Specify the Input file URI, in Hadoop we use the Path object 
		// Path is in hadoop.fs package
		// Note that both the FileInputFormat and FileOutputFormat are from mapreduce.lib package 
		FileInputFormat.addInputPath(job, new Path(args[0]));
		//Specify the output format
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		// Run the job
		System.exit(job.waitForCompletion(true)? 0:1 );
		
		
	}

}
