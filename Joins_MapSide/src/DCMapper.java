import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper;

public class DCMapper extends Mapper<LongWritable, Text, Text, Text>{
	
	
	
	// map function runs for each input row.
	// we need a function that runs only for once per map(i.e split) is setup() and cleanup()
	
	
	//here declare a HashMap , this is used in the while loop.
	private static HashMap<String,String> DeptMap=new HashMap<String,String>();
	
	protected void setup(Context context ) throws IOException{
	// Distributed Cache is deprecated , use Path[] localPaths=context.getLocalCacheFiles();
		// Path[] localPaths=context.getLocalCacheFiles();   this is also deprecated
		URI[] localPath=context.getCacheFiles(); 
		// the same method can be used on Job.
		
		//System.out.println("******************localPath******"+localPath);
;		
		File file=new File("Dept.txt");
		// BufferedReader reader=new BufferedReader(new FileReader(localPath[0].toString()));
		BufferedReader reader=new BufferedReader(new FileReader(file));
		
		//Split each line and Add it to a Hash Map so that the smaller file is fully loaded on memory.
		String lineString="";
		
		try{
		while((lineString = reader.readLine())!=null){
			String deptTable[]=lineString.split("\t");
			
			// put it into hash Map
			DeptMap.put(deptTable[0].trim(), deptTable[1].trim());
			
		}
		} finally{
			if(reader!=null){
				reader.close();
			}
		}
		
		
	}
	
	String dept_name="";
	Text key_out=new Text("");
	Text value_out=new Text("");
	public void map(LongWritable key,Text value,Context context) throws IOException,InterruptedException{
		String EmpTableRow[] = value.toString().split("\t");
		try{
			dept_name=DeptMap.get(EmpTableRow[3]);
			}finally{
				dept_name=(dept_name.equals(null)|| dept_name.equals(""))? "Not-Found" : dept_name ;
			}
		
		key_out.set(EmpTableRow[0]);
		
		value_out.set(EmpTableRow[0]+"\t"+EmpTableRow[1]+"\t"+EmpTableRow[2]+"\t"+EmpTableRow[3]+"\t"+dept_name);
		
		context.write(key_out, value_out);
	}
	
	
	
}