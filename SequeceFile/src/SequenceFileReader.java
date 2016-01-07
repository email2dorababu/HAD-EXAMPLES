import java.io.IOException;
import java.net.URI;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;
import org.apache.zookeeper.common.IOUtils;

public class SequenceFileReader {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws IOException {
		String uri_in=args[0];
		Configuration conf=new Configuration();
		conf.addResource(new Path("/home/ubuntu/YARNBOX/hadoop-2.7.1/etc/hadoop/core-site.xml"));
		FileSystem fs=FileSystem.get(URI.create(uri_in),conf);
		Path path=new Path(uri_in);
		
		//Writer for converting the byte stream to sequence file stream
		//Reader is to read from a sequence file.
		SequenceFile.Reader reader=null;
		
		try {
			reader=new SequenceFile.Reader(fs,path,conf);
			
			//To get the key class and value class use ReflectionUtils.
			
			IntWritable key=new IntWritable(); //using Text will give problem
			Text value=new Text();
			
			//To find the key and value Writable types
			// Writable key=(Writable)ReflectionUtils.newInstance(reader.getKeyClass(),conf);
			//// Writable value=(Writable)ReflectionUtils.newInstance(reader.getValueClass(),conf);
			
			
			while (reader.next(key,value)){
			
			String syncPoint=reader.syncSeen()? "****" : "";
			long position=reader.getPosition();
					
					//you cannot use if inside syso, use Ternary operator
			//System.out.println("syncPointFoundAt="+if(syncPoint=="****",position,"")+"\t"+"key="+key+"\t"+"Value="+value);
			System.out.println("syncPointFoundAt="+((syncPoint=="****")? position : "no"  )+"\t"+"key="+key+"\t"+"Value="+value);
			
			}
			
		}
		finally{
			IOUtils.closeStream(reader);
			System.out.println("completed Processing");
		}
	}
	
}
