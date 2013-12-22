	

import java.io.*;
import java.util.*;
import java.lang.*;
public class search1 {
	public static String query = null;
	public static void main(String[] args) throws IOException{ 
	System.out.println("Enter query ");
	String path="/media/LENOVO/Download_dc++/index/";
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
	try {
        query = br.readLine();
        query=query.toLowerCase();
        build_forward bfd=new build_forward();
        query=bfd.callstem(query);
        
        String[] qlis = query.split(" , ");
        System.out.println(qlis.length);
        System.out.println(qlis[0]);
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
        	if(sub[0].startsWith("ti"))
        	{
        		Set set1= new HashSet();
                Set set2= new HashSet();
        		String[] mult=sub[1].split(" ");
        		String[] res1= new String[mult.length];
                String[] fin1= new String[mult.length];
                for(int p=0;p<mult.length;p++)
                {
                	res1[p]="";
                	fin1[p]="";
                }
        		for(int i=0; i<mult.length; i++)
        		{
        			BufferedReader out3 = new BufferedReader(new FileReader(path+"title/sec_ttl"));
        		//	System.out.println(mult[i]);
        			res1[h]=bsearch(out3,mult[i]);
        		//	System.out.println(res[h]);
        			String s1[]=res1[h].split(":");
        			BufferedReader p3 = new BufferedReader(new FileReader(path+"title/"+s1[1]));
        			fin1[h]=search(p3,mult[i]);
        			p3.close();
        			out3.close();
        		}
        		fin1[0]=fin1[0].replaceAll("[a-z{}:]+", "");
                String[] po=fin1[0].split(", ");
                for(int h1=0;h1<po.length;h1++)
                {
                	set2.add(po[h1]);
                }
                Set inter=new TreeSet(set2);
                for(int i=0;i<fin1.length;i++)
                {
                	fin1[i]=fin1[i].replaceAll("[a-z{}:]+", "");
                	String[] pol=fin1[i].split(", ");
                    for(int h2=0;h2<pol.length;h2++)
                    {
                    	set1.add(pol[h2]);
                    }
                	inter.retainAll(set1);
                }

                    System.out.println("--------------" + "intersection" + "--------------");

                    Iterator it = inter.iterator();
                    while (it.hasNext()) {
                    	System.out.print(it.next()+", ");
                    }
                    System.out.println();
	
        	}
        	if(sub[0].startsWith("ou"))
        	{
        		Set set1= new HashSet();
                Set set2= new HashSet();
        		String[] mult=sub[1].split(" ");
        		String[] res1= new String[mult.length];
                String[] fin1= new String[mult.length];
                for(int p=0;p<mult.length;p++)
                {
                	res1[p]="";
                	fin1[p]="";
                }
        		for(int i=0; i<mult.length; i++)
        		{
        			BufferedReader out3 = new BufferedReader(new FileReader(path+"outlink/sec_out"));
        		//	System.out.println(mult[i]);
        			res1[h]=bsearch(out3,mult[i]);
        		//	System.out.println(res[h]);
        			String s1[]=res1[h].split(":");
        			BufferedReader p3 = new BufferedReader(new FileReader(path+"outlink/"+s1[1]));
        			fin1[h]=search(p3,mult[i]);
        			p3.close();
        			out3.close();
        		}
        		fin1[0]=fin1[0].replaceAll("[a-z{}:]+", "");
                String[] po=fin1[0].split(", ");
                for(int h1=0;h1<po.length;h1++)
                {
                	set2.add(po[h1]);
                }
                Set inter=new TreeSet(set2);
                for(int i=0;i<fin1.length;i++)
                {
                	fin1[i]=fin1[i].replaceAll("[a-z{}:]+", "");
                	String[] pol=fin1[i].split(", ");
                    for(int h2=0;h2<pol.length;h2++)
                    {
                    	set1.add(pol[h2]);
                    }
                	inter.retainAll(set1);
                }

                    System.out.println("--------------" + "intersection" + "--------------");

                    Iterator it = inter.iterator();
                    while (it.hasNext()) {
                    	System.out.print(it.next()+", ");
                    }
                    System.out.println();
        	}
        	if(sub[0].startsWith("in"))
        	{
        		Set set1= new HashSet();
                Set set2= new HashSet();
        		String[] mult=sub[1].split(" ");
        		String[] res1= new String[mult.length];
                String[] fin1= new String[mult.length];
                for(int p=0;p<mult.length;p++)
                {
                	res1[p]="";
                	fin1[p]="";
                }
        		for(int i=0; i<mult.length; i++)
        		{
        			BufferedReader out3 = new BufferedReader(new FileReader(path+"infobox/sec_inf"));
        		//	System.out.println(mult[i]);
        			res1[h]=bsearch(out3,mult[i]);
        		//	System.out.println(res[h]);
        			String s1[]=res1[h].split(":");
        			BufferedReader p3 = new BufferedReader(new FileReader(path+"infobox/"+s1[1]));
        			fin1[h]=search(p3,mult[i]);
        			p3.close();
        			out3.close();
        		}
        		fin1[0]=fin1[0].replaceAll("[a-z{}:]+", "");
                String[] po=fin1[0].split(", ");
                for(int h1=0;h1<po.length;h1++)
                {
                	set2.add(po[h1]);
                }
                Set inter=new TreeSet(set2);
                for(int i=0;i<fin1.length;i++)
                {
                	fin1[i]=fin1[i].replaceAll("[a-z{}:]+", "");
                	String[] pol=fin1[i].split(", ");
                    for(int h2=0;h2<pol.length;h2++)
                    {
                    	set1.add(pol[h2]);
                    }
                	inter.retainAll(set1);
                }

                    System.out.println("--------------" + "intersection" + "--------------");

                    Iterator it = inter.iterator();
                    while (it.hasNext()) {
                      System.out.print(it.next()+", ");
                    }
                    System.out.println();
        	}     
        }
        /*
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
        
        Set st2= tMap1.entrySet();
        Iterator it2= st2.iterator();
        int g=0;
        while (!(tMap1.isEmpty()))
        {	
        	Map.Entry tr= tMap1.lastEntry();
        	if(g<10)
        	{
        		if(tr!=null && !(tr.getKey().toString().equals("1")))
        		{
        			System.out.println(tr.getKey().toString()+":"+ tr.getValue().toString());
        			tMap1.remove(tr.getKey());
        			g++;
        		}
        	}
        	tMap1.remove(tr.getKey());
		}
        if(g==0)
        	System.out.println("No results matching your query with frequency greater than 1!");
  */    
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
}

