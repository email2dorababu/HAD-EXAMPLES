import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;


public class DistributedCacheDemo extends Configured implements Tool{

	public int run(String[] args) throws Exception{
		//Job job=new Job(getConf());
		Configuration conf=getConf();
		Job job=Job.getInstance(conf,"Map side join with Distributed Cache");
		
		//Add the DC file using -D option or using below class
		// DistributedCache from filecache is deprecated
		job.addCacheFile(URI.create("/user/Joins/Input/Dept.txt"));
		
		
		URI[] cacheFiles=job.getCacheFiles();
		if(cacheFiles != null){
			for (URI cacheFile:cacheFiles){
			System.out.println("Cache Files are :"+ cacheFile);
			
		}
		}
		
	job.setJarByClass(DistributedCacheDemo.class);
	FileInputFormat.addInputPath(job, new Path(args[0]));
	FileOutputFormat.setOutputPath(job, new Path(args[1]));
	
	job.setMapperClass(DCMapper.class);
	job.setReducerClass(DCReducer.class);
		
	job.setMapOutputKeyClass(Text.class);
	job.setMapOutputValueClass(Text.class);
	
	
	job.setOutputKeyClass(Text.class);
	job.setOutputValueClass(NullWritable.class);
		
		
		
		return(job.waitForCompletion(true)?0:1);
	}
	
	public static void main(String[] args) throws Exception {
		int exitCode=ToolRunner.run(new DistributedCacheDemo(), args);
		System.exit(exitCode);
	}
	
	
}
