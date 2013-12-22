
	import java.io.*;
	import java.util.*;
	import java.util.regex.*;

	import org.xml.sax.XMLReader;
	import org.xml.sax.Attributes;
	import org.xml.sax.InputSource;
	import org.xml.sax.helpers.XMLReaderFactory;
	import org.xml.sax.helpers.DefaultHandler;

	public class untoken_info_out extends DefaultHandler
	{
	    public static void main (String args[])
		throws Exception
	    {
		XMLReader xr = XMLReaderFactory.createXMLReader();
		untoken_info_out handler = new untoken_info_out();
		xr.setContentHandler(handler);
		xr.setErrorHandler(handler);
		for (int i = 0; i < args.length; i++) {
		    FileReader r = new FileReader(args[i]);
		    xr.parse(new InputSource(r));
		}
	    }

	    public untoken_info_out ()
	    {
	    	super();
	    }
	    public HashMap <String,Integer> hashMap = new HashMap<String,Integer>();
	    public TreeMap <String, TreeMap <String, Integer>> tMap3 = new TreeMap<String, TreeMap <String, Integer>>();
	    public TreeMap <String, TreeMap <String, Integer>> tMap4 = new TreeMap<String, TreeMap <String, Integer>>();
	    int flag1=0,flag2=0,temp=1,flag3=0,pf=0,rflg=0;
	    public static Stopwords sp=new Stopwords();
	    public static String[] stopwords = sp.stopwords;
	    String title="",id="",cont="";
	    String pid="";    
	    StringBuffer tx=new StringBuffer();
	    long start=0,end=0;
	    long docs=0,fnum=1;
	    public void startDocument ()
	    {
	    	System.out.println("Start document");
	    	start=System.currentTimeMillis();
	    	for(int i=0; i<stopwords.length ; i++)
	    	{
	    		hashMap.put(stopwords[i],1);
	    	}
	    }

	    public void endDocument ()
	    {
	    index(fnum);
	    end=System.currentTimeMillis();	
		System.out.println("End document "+(end-start));
		System.out.println(docs);
	    }

	    public void startElement (String uri, String name,
				      String qName, Attributes atts)
	    {
	    	if ("title".equals (name))
	    	{
	    		flag1=1;
	    		temp=0;
	    	}
	    	if ("id".equals (name) )
	    	{
	    		flag2=1;
	    	}
	    	if ("text".equals (name) )
	    	{
	    		flag3=1;
	    		cont="";
	    	}
	    }

	    public void endElement (String uri, String name, String qName)
	    {
	    	if ("id".equals (name))
	    	{
					temp=1;
					flag2=0;
	    	}
	    	if ("title".equals(name))
	    	{
	    		flag1=0;
	    	}
	    	if ("text".equals(name))
	    	{
	    		if(flag3==1 && rflg!=1)
	    		{
	    			String cont=tx.toString();
	    			cont=cont.replaceAll("==External links==.*|==See also==.*|==References==.*|==Bibliography==.*|===Further reading===.*","");
	        		cont=cont.replaceAll("<ref.*?</ref>","");
	        		Pattern p1=Pattern.compile("\\{\\{[Ii]+nfobox[a-zA-Z0-9_\\. ]+");
	        		Pattern p2=Pattern.compile("\\[\\[[a-zA-Z |]+\\]\\]");
	        		Matcher m1 = p1.matcher(cont);
	        		Matcher m2 = p2.matcher(cont);
	        			while(m1.find()){
	        		String d=m1.group();
	        		d=d.toLowerCase();
	        		d=d.replaceAll("[^a-zA-Z0-9\\. ]+"," ");
	        		d=d.substring(9);
	        		d=d.trim();
	        		callstem(d,3);
	        		}
	        			while(m2.find()){
	    	        		String d=m2.group();
	    	        		d=d.toLowerCase();
	    	        		if(!d.startsWith("image:"))
	    	        		{
	    	        			if(!d.contains("|"))
	    	        			{
	    	        				d=d.replaceAll("[^a-zA-Z0-9\\.,:' ]+"," ");
	    	        				d=d.trim();
	    	        				callstem(d,4);
	    	        			}
	    	        			else
	    	        			{
	    	        				String[] ol=d.split("\\|");
	    	        				String s1=ol[0];
	    	        				String s2=ol[1];
	    	        				s1=s1.replaceAll("[^a-zA-Z0-9\\.,:' ]+"," ");
	    	        				s1=s1.trim();
	    	        				s2=s2.replaceAll("[^a-zA-Z0-9\\.,:' ]+"," ");	
	    	        				s2=s2.trim();
	    	        				callstem(s1,4);
	    	        				callstem(s2,4);
	    	        			}
	    	        		}
	    	        	}
	    		}	
	    		tx.delete(0, tx.length());
	    		flag3=0;
	    		rflg=0;
	    		if(docs%10000==0)
	    		{
	    			index(fnum);
	    			fnum++;
	    		}
	    	}
	    }
	    public void callstem(String s, Integer fl)
	    {
	        Stemmer st = new Stemmer();
	    	for(int i=0;i<s.length();i++)
	    	{
	                st.add(s.charAt(i));
	    	}           
	    					st.stem();                 
	                    	String u;
	                        u = st.toString();
	                       // u=u.trim();
	    						//String u=s;
	                        	if(fl==3)
	                        	{
	                        		if(!hashMap.containsKey(u))        									
	    							{
	    								if(tMap3.containsKey(u))
	    								{
	    									if(tMap3.get(u).containsKey(pid))
	    									{
	    										int l= tMap3.get(u).get(pid)+1;
	    										tMap3.get(u).put(pid, l);
	    									}
	    									else
	    									{
	    										tMap3.get(u).put(pid, 1);
	    									}
	    								}
	    								else
	    								{
	    									TreeMap<String,Integer> tm1= new TreeMap<String,Integer>();
	    									tm1.put(pid, 1);
	    									tMap3.put(u, tm1);
	    								}
	    							}
	                        	}
	                        	if(fl==4)
	                        	{
	                        		if(!hashMap.containsKey(u))        									
	    							{
	    								if(tMap4.containsKey(u))
	    								{
	    									if(tMap4.get(u).containsKey(pid))
	    									{
	    										int l= tMap4.get(u).get(pid)+1;
	    										tMap4.get(u).put(pid, l);
	    									}
	    									else
	    									{
	    										tMap4.get(u).put(pid, 1);
	    									}
	    								}
	    								else
	    								{
	    									TreeMap<String,Integer> tm1= new TreeMap<String,Integer>();
	    									tm1.put(pid, 1);
	    									tMap4.put(u, tm1);
	    								}
	    							}
	                        	}
	     }

	    public void characters (char ch[], int start, int length)
	    {
	    	String temp1=new String(ch,start,length);
	    	if(temp1.startsWith("#REDIRECT") && flag3==1)
	    	{
	    		rflg=1;
	    	}	    	
	    	if(flag3==1 && rflg!=1)
	    	{
	    		tx.append(temp1);
	    		//cont=cont+temp1; 		
	    	}
	    	
	    	if(flag2==1 && temp==0)
	    	{
	    		docs++;
	    		pid=new String(ch,start,length);
	    		System.out.println(pid);
	    	}
	    }
	    
	    public void index(long z)
	    {
	    	String path3="/media/LENOVO/Download_dc++/index/new_info/";
	    	String path4="/media/LENOVO/Download_dc++/index/new_out/";
	    	try{
	    	BufferedWriter out3 = new BufferedWriter(new FileWriter(path3+"l0_"+z));
	    	BufferedWriter out4 = new BufferedWriter(new FileWriter(path4+"l0_"+z));
	    	Set st3= tMap3.entrySet();
	    	Set st4= tMap4.entrySet();
	    	Iterator it3= st3.iterator();
	    	Iterator it4= st4.iterator();
	    		while (it3.hasNext()){
	    			Map.Entry me = (Map.Entry)it3.next();
	    			out3.write(me.getKey().toString()+":"+ me.getValue().toString()+"\n");	    						    		
	    		}
	    		out3.close();	
	    		tMap3.clear();
	    		while (it4.hasNext()){
	    			Map.Entry me = (Map.Entry)it4.next();
	    			out4.write(me.getKey().toString()+":"+ me.getValue().toString()+"\n");	    						    		
	    		}
	    		out4.close();
	    		tMap4.clear();
	    	}
	    catch (IOException e) {
	    {
	    	System.err.println("Caught IOException: " 
                    +  e.getMessage());
	    }
	    }
}}
