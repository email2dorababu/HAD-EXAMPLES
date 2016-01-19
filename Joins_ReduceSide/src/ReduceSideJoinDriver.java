import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


public class ReduceSideJoinDriver {

	public static void main(String[] args) throws IOException,  InterruptedException, ClassNotFoundException {
		
		Configuration conf =new Configuration();
		Job job=Job.getInstance(conf,"Reduce Side Join");
		 
		job.setJarByClass(ReduceSideJoinDriver.class);
	
		Path EmpInfoPath=new Path(args[0]);
		Path DeptPath=new Path(args[1]);
		Path OutPath=new Path(args[2]);
		 
		
		MultipleInputs.addInputPath(job, EmpInfoPath, TextInputFormat.class,EmpInfoMapper.class);
		MultipleInputs.addInputPath(job, DeptPath, TextInputFormat.class,DepartmentMapper.class);
		
		FileOutputFormat.setOutputPath(job, OutPath);
		
		job.setReducerClass(ReduceSideJoinReducer.class);
		
		job.setMapOutputKeyClass(BigramTextPair.class);
		job.setMapOutputValueClass(Text.class);
		
		job.setOutputKeyClass(Text.class);
		
		job.setOutputValueClass(Text.class);
		//job.setOutputValueClass(Text.class);
		
		//job.setOutputValueClass(Text.class);
		
		System.exit(job.waitForCompletion(true)? 0: 1);
		
	}
	
}
