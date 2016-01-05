import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.CompressionCodecFactory;

public class DeCompressionHDFSFile {

	public static void main(String[] args) throws Exception {
		// String hdfsFileUri=args[0];
		//String hdfsFileUri=new String("hdfs://localhost:50070/user/LocalToHdfs/passwdinput.tar.gz");
		String hdfsFileUri=new String("/user/LocalToHdfs/passwdinput.tar.gz");
		Configuration conf=new Configuration();
		conf.addResource(new Path("/home/ubuntu/YARNBOX/hadoop-2.7.1/etc/hadoop/core-site.xml"));
		System.out.println("Configured FileSystem="+conf.toString());
		// Get a file system instance, to access the HDFS
		FileSystem fs =FileSystem.get(URI.create(hdfsFileUri),conf);
		
		Path HdfsFilePath=new Path(hdfsFileUri);
		
		
		// get codec from the input file(using signature of file)/extension
		CompressionCodecFactory factory=new CompressionCodecFactory(conf);
		CompressionCodec codec=factory.getCodec(HdfsFilePath);
		
		if(codec==null)
		{
			System.out.println("No Codec Found for the input path"+ hdfsFileUri);
			System.exit(1);
		}
		
		String hdfsOutputURI = CompressionCodecFactory.removeSuffix(hdfsFileUri, codec.getDefaultExtension());
		
		InputStream in= null;
		OutputStream out =null;
		
		try{
				in=codec.createInputStream(fs.open(HdfsFilePath));
				out=fs.create(new Path(hdfsOutputURI));
				IOUtils.copyBytes(in,out,conf);
				
		} finally {
			IOUtils.closeStream(in);
			IOUtils.closeStream(out);
			System.out.println("Processing Completed");
		}

	}

}
