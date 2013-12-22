import java.io.*;

public class secondary {
	public static void main(String[] args) throws Exception{
		String path="/media/LENOVO/Download_dc++/index/new_out/";
		BufferedReader out1 = new BufferedReader(new FileReader(path+"new_out"));
    	
    	BufferedWriter tempt= new BufferedWriter(new FileWriter(path+"sec_out"));
    	
    	String s1=out1.readLine();
    	
    	long t1=0,t2=0,t3=0,t4=0,t5=0;
    	int f1=0,f2=0,f3=0,f4=0,f5=0;
    	BufferedWriter tem1= new BufferedWriter(new FileWriter(path+f1));
    	while(s1!=null)
    	{
    		String[] sub1=s1.split(":");
    		if(t1%5000==0)
    		{
    			f1++;
    			tem1.flush();
    			tem1=new BufferedWriter(new FileWriter(path+f1));
    			tempt.write(sub1[0]+":"+f1+"\n");
    		}
    		tem1.write(s1+"\n");
    		s1=out1.readLine();
    		t1++;
    	}
    	tem1.close();
    	tempt.close();
    	out1.close();
    	System.out.println("------------Secondary index for forward index is done -----------");
	}
}
