import java.util.Map.Entry;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class ToolDemo2 extends Configured implements Tool{

		
	@Override
	public int run(String[] arg0) throws Exception {
		
		Configuration conf=getConf(); // this is from COnfigured ..
		
		
		
		conf.addResource(new Path("/home/ubuntu/YARNBOX/hadoop-2.7.1/etc/hadoop/core-site.xml"));
		
		
		
		for (Entry<String, String> entry: conf){
			System.out.println(entry.getKey()+"\t"+entry.getValue());
						
		}
		
		
		
		
		return 0;

		
	}
	
	public static void main(String[] args) throws Exception{
		// int exitCode=ToolRunner.run(tool, args)\
		
		// in the above statement tool is the above class ToolDemo which 
		//implements Tool.
		int exitCode=ToolRunner.run(new ToolDemo2(), args);
		System.exit(exitCode);
		
		
	}

}
