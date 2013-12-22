import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.TreeMap;
import java.util.regex.*;
public class test {
	
	public static void main(String[] args)throws Exception
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String inp=br.readLine();
		String path1="/media/LENOVO/title_ind/";
		BufferedReader out5 = new BufferedReader(new FileReader(path1+"sec_ttl_phase2"));
		String temp1=bsearch(out5,inp);
		System.out.println(temp1);
		String s1[]=temp1.split(" : ");
		BufferedReader p5 = new BufferedReader(new FileReader(path1+s1[1]));
		System.out.println(s1[1]);
		String temp2=search(p5,inp);
		System.out.println(temp2);//19180141 19598227 19762439	20304984 12377724 18005220 19762676	19408837
	}
	static String bsearch( BufferedReader br1, String s)throws IOException{
		String temp=br1.readLine();
		String ans=temp;
        while(temp!=null)
        {
        	String[] lis=temp.split(" : "); 	
        	if(Long.parseLong(s)>Long.parseLong(lis[0]))
        	{	
        		ans=temp;
        	}
        	temp=br1.readLine();
        }     			
			return ans;
	     }
	static String search( BufferedReader br1, String s)throws IOException{
		String ans="";
		String temp=br1.readLine();
        while(temp!=null)
        {
        	String[] lis=temp.split(" :: ");
        	if(s.equals(lis[0]))
        	{
        		ans=temp;
        		break;
        	}
        	temp=br1.readLine();
        }     			
			return ans;
	    }
	
}
