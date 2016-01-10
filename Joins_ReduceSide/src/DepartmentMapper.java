import java.io.IOException;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper;
	
public class DepartmentMapper extends Mapper<LongWritable, Text, Text, BigramTextPair> {

	
	//private Text value_out=new Text();
	private BigramTextPair joinKey=new BigramTextPair();
	// private final static IntWritable one=new IntWritable(1);
	
	@Override
	protected void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		String EmpTableRow[] = value.toString().split("\t");
		
		// the table name and dept name in the text pair
		joinKey.set(new Text("DeptInfoDeptId"),new Text(EmpTableRow[1]));
		
		//key is the dept id
		context.write(new Text(EmpTableRow[0]),joinKey);
		
		
	}

	}
	

