import java.io.IOException;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Reducer;

public class ReduceSideJoinReducer 
	extends Reducer< Text,BigramTextPair, Text, NullWritable>{

	
	private Text val_out=new Text();
	private String empRec="";
	
	@Override
	protected void reduce(Text key, Iterable<BigramTextPair> values,
			Context context) throws IOException, InterruptedException {
		
		for(BigramTextPair val:values){
			
			if(val.getSecond().equals("EmpInfoDeptId"){
				
				val_out=val.getSecond()
						
				context.write(new Textval.getSecond().toString()
			}
		}
		
		
	}
	
	

}
