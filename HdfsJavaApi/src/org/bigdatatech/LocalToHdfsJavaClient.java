package org.bigdatatech;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.Progressable;

public class LocalToHdfsJavaClient {

	// java client to copy file from Local file system to HDFS
	
	// Write file using FileSystem API
	public static void main(String[] args) throws IOException {
		
		//args[0] = "/home/ubuntu/workspace/InputData/ExampleFile.txt" ;
		//args[1] = "/user/LocalToHdfs/" ;
		
		
		if(args.length<2){
			System.err.println("LocalToHdfsJavaClient [Local input path] [HDFS output path] ");
			System.exit(-1);
			
		}
		Path hdfsOutputPath=new Path(args[1]);
		String localInputPath=args[0]; // local path is just a string
		Configuration conf=new Configuration();
		//conf.set("fs.default.name", "hdfs://localhost:50070");
		conf.addResource(new Path("/home/ubuntu/YARNBOX/hadoop-2.7.1/etc/hadoop/core-site.xml"));
		System.out.println("Configured FileSystem="+conf.toString());
		//FileSystem is from org.apache.hadoo.fs package
		FileSystem fs=FileSystem.get(conf);
		
		//Once you have the FileSystem instance, Open Output Stream 
		FSDataOutputStream os=fs.create(hdfsOutputPath);
		
		//with progressible
		/* 
		FSDataOutputStream os=fs.create(hdfsOutputPath, new Progressable(){
			public void progress(){
				System.out.println("xxxx->");
			}
		}); 
		*/
		
		InputStream is=new BufferedInputStream(new FileInputStream(localInputPath));
		// The above InputStream,BufferedInputStream and FileInputStream are from java.io
		IOUtils.copyBytes(is, os,4096,true);
		System.out.println("FileTransfer completed");
		

	}

}
