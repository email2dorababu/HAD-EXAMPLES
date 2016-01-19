import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;


// added commons/lib, commons, mapreduce and yarn jar files

public class BigramTextPair implements WritableComparable<BigramTextPair> {

	 // Source -> implement unimplemented methods
	// implement Object methods also. 
	
	// Text pair have two text variables, first and second
	
	private Text first;
	private Text second;

	public Text getFirst() {
		return first;
	 }

	public void setFirst(Text first) {
		this.first = first;
	}

	public Text getSecond() {
		return second;
	}

	public void setSecond(Text second) {
		this.second = second;
	}

	// Set method for both the texts

	public void set(Text first,Text second) {
		this.first = first;
		this.second = second;
	}
	
	// Three Constructors
	
	public BigramTextPair(Text first,Text second){
		set(first,second);
	}
	
	public BigramTextPair(){
		set(new Text(),new Text());
	}
	
	public BigramTextPair(String first,String Second){
		set(new Text(first),new Text(second));
	}
	
	// implement the below methods in Object and WritableComparable
	
	
	@Override
	public void readFields(DataInput in) throws IOException {
		// TODO Auto-generated method stub
		first.readFields(in);  
		// This calls the Text() readFields() method,  because deserialization in Text is already optimized
		second.readFields(in); 
	}

	@Override
	public void write(DataOutput out) throws IOException {
		// TODO Auto-generated method stub
		//use Text() serialization method 
		first.write(out);
		second.write(out);
		
	}

	@Override
	public int compareTo(BigramTextPair tp) {
		//TODO Auto-generated method stub
		int cmp=first.compareTo(tp.first);
		
		if(cmp !=0){
			return cmp;
		}
		return second.compareTo(tp.second);
		
		
	} 
	

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return first.hashCode()*163+second.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(obj  instanceof BigramTextPair){
			BigramTextPair tp=(BigramTextPair) obj;
			return first.equals(tp.first)&& second.equals(tp.second);
		}
		return false;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return first+ " " +second; 
		// this calls toString() method in Text() 
	}

	
	
}
