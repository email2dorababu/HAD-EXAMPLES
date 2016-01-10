import java.io.IOException;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper;
	
public class EmpInfoMapper extends Mapper<LongWritable, Text,  BigramTextPair,Text> {

	
	//private Text value_out=new Text();
	private BigramTextPair joinKey=new BigramTextPair();
	// private final static IntWritable one=new IntWritable(1);
	
	@Override
	protected void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		String EmpTableRow[] = value.toString().split("\t");
		
		
		// joinKey.set(new Text("EmpInfoDeptId"),value);
		//value_out.set(EmpTableRow[0]+"\t"+EmpTableRow[1]+"\t"+EmpTableRow[2]+"\t"+EmpTableRow[3]);
		joinKey.set(new Text(EmpTableRow[3]),new Text("EmpInfoDeptId"));
		
		context.write(joinKey,value);
		
		
	}

	}
	

