
import java.io.*;
import java.util.*;
import java.lang.*;
public class search {
	public static String query = null;
	public static void main(String[] args) throws IOException{ 
	System.out.println("Enter query ");
	String path="/media/LENOVO/Download_dc++/index/";
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
	try {
        query = br.readLine();
        query=query.toLowerCase();
        String[] qlis = query.split(" ");
        String[] res= new String[qlis.length];
        String[] fin= new String[qlis.length];
        for(int p=0;p<qlis.length;p++)
        {
        	res[p]="";
        	fin[p]="";
        }
        for(int h=0;h<qlis.length;h++)
        {
        	String[] sub=qlis[h].split(":");
        	Stemmer stm= new Stemmer();
        	for(int k=0;k<sub[1].length();k++)
        		stm.add(sub[1].charAt(k));
        	stm.stem();
        	String u=stm.toString();
        	System.out.println(u);
        	if(sub[0].startsWith("co"))
        	{
        		BufferedReader out1 = new BufferedReader(new FileReader(path+"content/sec_con"));
        		res[h]=bsearch(out1,u);
        		String s1[]=res[h].split(":");
        		BufferedReader p1 = new BufferedReader(new FileReader(path+"content/"+s1[1]));
        		fin[h]=search(p1,u);
        		out1.close();
        		p1.close();
        	}
        	if(sub[0].startsWith("ca"))
        	{
        		BufferedReader out2 = new BufferedReader(new FileReader(path+"category/sec_cat"));
        		res[h]=bsearch(out2,u);
        		String s1[]=res[h].split(":");
        		BufferedReader p2 = new BufferedReader(new FileReader(path+"category/"+s1[1]));
        		fin[h]=search(p2,u);
        		out2.close();
        		p2.close();
        	}
        	if(sub[0].startsWith("in"))
        	{
        		BufferedReader out3 = new BufferedReader(new FileReader(path+"infobox/sec_inf"));
        		res[h]=bsearch(out3,u);
        		String s1[]=res[h].split(":");
        		BufferedReader p3 = new BufferedReader(new FileReader(path+"infobox/"+s1[1]));
        		fin[h]=search(p3,u);
        		out3.close();
        		p3.close();
        	}
        	if(sub[0].startsWith("ou"))
        	{
        		BufferedReader out4 = new BufferedReader(new FileReader(path+"outlink/sec_out"));
        		res[h]=bsearch(out4,u);
        		String s1[]=res[h].split(":");
        		BufferedReader p4 = new BufferedReader(new FileReader(path+"outlink/"+s1[1]));
        		fin[h]=search(p4,u);
        		out4.close();
        		p4.close();
        	}
        	if(sub[0].startsWith("ti"))
        	{
        		BufferedReader out5 = new BufferedReader(new FileReader(path+"title/sec_ttl"));
        		res[h]=bsearch(out5,u);
        		String s1[]=res[h].split(":");
        		BufferedReader p5 = new BufferedReader(new FileReader(path+"title/"+s1[1]));
        		fin[h]=search(p5,u);
        		out5.close();
        		p5.close();
        	}     
        }
        for(int i=0;i<fin.length;i++)
        	System.out.println(fin[i]);
        System.out.println("Results are of the type(freq: PageIds with that frequency).This is Boolean OR Search\n");
        TreeMap <String, Integer> tMap = new TreeMap<String, Integer>();
        for(int i=0;i<fin.length;i++)
        {
        	fin[i]=fin[i].replaceAll("[{},]", "");
        	String[] w=fin[i].split(":");
        	String[] li=w[1].split(" ");
        	for(int j=0;j<li.length;j++)
        	{
        		String pair[]=li[j].split("=");
        		//int n=Integer.parseInt(pair[1]);
        		if(tMap.containsKey(pair[0]))
        		{
        			int tp=tMap.get(pair[0]);
        			tMap.put(pair[0], tp+1);
        		}
        		else
        		{
        			tMap.put(pair[0], Integer.parseInt(pair[1]));
        		}
        	}
        }
        
        Set st1= tMap.entrySet();
        Iterator it1= st1.iterator();
        TreeMap <Integer, String> tMap1 = new TreeMap<Integer, String>();
        while (it1.hasNext()){
			Map.Entry me = (Map.Entry)it1.next();
			int n=Integer.parseInt(me.getValue().toString());
			if(tMap1.containsKey(n))
			{
				String tp=tMap1.get(n);
				tMap1.put(n, tp+", "+me.getKey().toString());
			}
			else
			{
				tMap1.put(n, me.getKey().toString());
			}
		}
        String def=tMap1.lastKey().toString();
        System.out.println(def);
        Set st2= tMap1.entrySet();
        Iterator it2= st2.iterator();
        int g=0;
        while (!(tMap1.isEmpty()))
        {	
        	Map.Entry tr= tMap1.lastEntry();
        	if(g<10)
        	{
        		if(tr!=null)
        		{
        			System.out.println(tr.getValue().toString());
        			tMap1.remove(tr.getKey());
        			g++;
        		}
        	}
        	tMap1.remove(tr.getKey());
		}
        if(g==0)
        	System.out.println("No results matching your query with frequency greater than 1!");
      
     } catch (IOException ioe) {
        System.out.println("IO error trying to search query!");
        System.exit(1);
     }
	}
	static String bsearch( BufferedReader br1, String s)throws IOException{
		String ans;
		String temp=br1.readLine();
		ans=temp;
        while(temp!=null)
        {
        	if((s.compareTo(temp))>0)
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
        	if(temp.startsWith(s))
        	{
        		ans=temp;
        		break;
        	}
        	temp=br1.readLine();
        }     			
			return ans;
	     }
	public void input(String s)
	{
		query=s;
	}
}
