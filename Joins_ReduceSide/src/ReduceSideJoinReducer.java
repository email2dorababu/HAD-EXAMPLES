import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Reducer;

public class ReduceSideJoinReducer 
	extends Reducer< BigramTextPair,Text, Text, Text>{

	
	//private Text val_out=new Text();
//	private String str="";
	private Text DeptName=new Text();
	 

	protected void setup(Context context) throws IOException, InterruptedException{
		context.write(new Text("EMPNO_EMPNAME_SALARY_DEPTID"),new Text("DEPTID_DEPTNAME"));
	}
	 
	
	protected void reduce(BigramTextPair key, Iterable<Text> values,
			Context context) throws IOException, InterruptedException {
		
		// show the output with the below code
		
	/* for(Text val:values){
					
			context.write(new Text(key.getFirst().toString()+"****"+key.getSecond().toString()),val);
		
					
		} */
	Iterator<Text> itr=values.iterator();
	if(key.getSecond().toString().equals("DeptInfoDeptId")){
		DeptName= new Text(itr.next());
	}
	while(itr.hasNext()){
		Text empRec=itr.next();
		//THe below line prints all content.
		//Text outVal=new Text(empRec.toString()+"\t"+DeptName.toString());
		context.write(empRec, DeptName);
	}
		
}
	
}
