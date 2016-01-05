import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.CompressionCodecFactory;
import org.apache.hadoop.io.compress.CompressionOutputStream;
import org.apache.hadoop.io.compress.GzipCodec;
import org.apache.hadoop.io.compress.SnappyCodec;
import org.apache.hadoop.util.ReflectionUtils;

public class CompressionHDFSFile {

	public static void main(String[] args) throws Exception {
		// String hdfsFileUri=args[0];
		//String hdfsFileUri=new String("hdfs://localhost:50070/user/LocalToHdfs/passwdinput.tar.gz");
		String hdfsFileUri=new String("/user/LocalToHdfs/passwdinput");
		Configuration conf=new Configuration();
		conf.addResource(new Path("/home/ubuntu/YARNBOX/hadoop-2.7.1/etc/hadoop/core-site.xml"));
		System.out.println("Configured FileSystem="+conf.toString());
		// Get a file system instance, to access the HDFS
		//FileSystem fs =FileSystem.get(URI.create(hdfsFileUri),conf);
		
		//Path HdfsFilePath=new Path(hdfsFileUri);
		
		/*
		 * Deflate o.a.h.io.compress.DefaultCodec
		 * gzip	o.a.h.io.compress.GzipCodec
		 * bzip2 o.a.h.io.compress.BZip2Codec
		 * LZO	com.hadoop.compression.lzo.LzopCodec
		 * LZ4 o.a.h.io.compress.Lz4Codec
		 * Snappy	o.a.h.io.compress.SnappyCodec
		 */
		
		CompressionCodec codec=(CompressionCodec) ReflectionUtils.newInstance(GzipCodec.class, conf);
		String hdfsOutputURI = hdfsFileUri+codec.getDefaultExtension();
		System.out.println(hdfsOutputURI);
	
		//InputStream in= null;
		//OutputStream out =null;
		Path hdfsOutputPath=new Path(hdfsOutputURI);
		FileSystem fs=FileSystem.get(conf);
		System.out.println("Opened the input file");
		FSDataInputStream in=fs.open(new Path(hdfsFileUri));
		FSDataOutputStream os=fs.create(hdfsOutputPath);
		System.out.println("Opened the output file");
		CompressionOutputStream out=codec.createOutputStream(os);
		System.out.println("Created the stream the output file");
		IOUtils.copyBytes(in, out, conf,true);
		System.out.println("Completed writing compressed Output File");
		
		
		/*
		try{
				in=codec.createInputStream(fs.open(HdfsFilePath));
				out=fs.create(new Path(hdfsOutputURI));
				IOUtils.copyBytes(in,out,conf);
				
		} finally {
			IOUtils.closeStream(in);
			IOUtils.closeStream(out);
			System.out.println("Processing Completed");
		}
				*/
	}

}
