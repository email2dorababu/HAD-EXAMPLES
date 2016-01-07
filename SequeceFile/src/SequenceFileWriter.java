import java.io.IOException;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;



public class SequenceFileWriter {
	
	//Program to convert a text file into sequence file
	
	private static final String[] data_file={
			"ABCDE",
			"FGHIJ",
			"KLMNO",
			"PQRST",
			"UVWXYZ"
	};
	
	
	
	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws IOException{
		//String uri_in=args[0];
		//String uri_out=args[1];
		
		String uri=args[0];
		Configuration conf=new Configuration();
		
		
		// FileSystem fs_in=FileSystem.get(URI.create(uri_in),conf);
		
		//FileSystem fs_out=FileSystem.get(URI.create(uri_out),conf);
		
		FileSystem fs=FileSystem.get(URI.create(uri),conf);
		
		Path path=new Path(uri);
		IntWritable key=new IntWritable();
		Text value=new Text();
		
		SequenceFile.Writer writer=null;
		
		try{
			writer=SequenceFile.createWriter(fs,conf,path,key.getClass(),value.getClass());
			for(int i=0;i<100;i++){
				key.set(i); //because index of s
				value.set(data_file[i% (data_file.length) ]);
				System.out.printf("%s \t %s \n", key,value);
				writer.append(key,value);
			}
			} finally{
					IOUtils.closeStream(writer);;
			
		}
		
		
		
	}
	

}
