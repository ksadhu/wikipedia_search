	import java.io.*;
	import java.util.*;
	import java.util.regex.*;

	import org.xml.sax.XMLReader;
	import org.xml.sax.Attributes;
	import org.xml.sax.InputSource;
	import org.xml.sax.helpers.XMLReaderFactory;
	import org.xml.sax.helpers.DefaultHandler;

	public class se_ind extends DefaultHandler
	{
	    public static void main (String args[])
		throws Exception
	    {
		XMLReader xr = XMLReaderFactory.createXMLReader();
		se_ind handler = new se_ind();
		xr.setContentHandler(handler);
		xr.setErrorHandler(handler);
		for (int i = 0; i < args.length; i++) {
		    FileReader r = new FileReader(args[i]);
		    xr.parse(new InputSource(r));
		}
	    }

	    public se_ind ()
	    {
	    	super();
	    }
	    public HashMap <String,Integer> hashMap = new HashMap<String,Integer>();
	    public TreeMap <String, TreeMap <String, Integer>> tMap1 = new TreeMap<String, TreeMap <String, Integer>>();
	    public TreeMap <String, TreeMap <String, Integer>> tMap2 = new TreeMap<String, TreeMap <String, Integer>>();
	    public TreeMap <String, TreeMap <String, Integer>> tMap3 = new TreeMap<String, TreeMap <String, Integer>>();
	    public TreeMap <String, TreeMap <String, Integer>> tMap4 = new TreeMap<String, TreeMap <String, Integer>>();
	    public TreeMap <String, TreeMap <String, Integer>> tMap5 = new TreeMap<String, TreeMap <String, Integer>>();
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
	        		Pattern p=Pattern.compile("\\[\\[Category:[a-zA-Z |,()]+\\]\\]");
	        		Pattern p1=Pattern.compile("\\{\\{Infobox [a-zA-Z ]+");
	        		Pattern p2=Pattern.compile("\\[\\[[a-zA-Z0-9|]+\\]\\]");
	        		Matcher m = p.matcher(cont);
	        		Matcher m1 = p1.matcher(cont);
	        		Matcher m2 = p2.matcher(cont);
	        			while(m.find()){
	        		String d=m.group();
	        		d=d.toLowerCase();
	        		d=d.replaceAll("[^a-zA-Z0-9\\. ]+"," ");
	        		String[] lis=d.split(" ");
	    				for(int g=0;g<lis.length;g++)
	    				{
	    					if(lis[g]!="" && !lis[g].startsWith("0"))
	    						callstem(lis[g],2);
	    				}
	        		}
	        			while(m1.find()){
	        		String d=m1.group();
	        		d=d.toLowerCase();
	        		d=d.replaceAll("[^a-zA-Z0-9\\. ]+"," ");
	        		String[] lis=d.split(" ");
	    				for(int g=0;g<lis.length;g++)
	    				{
	    					if(lis[g]!="" && !lis[g].startsWith("0"))
	    						callstem(lis[g],3);
	    				}
	        		}
	        			while(m2.find()){
	    	        		String d=m2.group();
	    	        		if(!d.startsWith("Image:"))
	    	        		{
	    	        			d=d.toLowerCase();
	    	        			if(!d.matches("[a-zA-Z0-9 ]+|[a-zA-Z0-9 ]+"))
	    	        			{
	    	        				d=d.replaceAll("[^a-zA-Z0-9\\.,:' ]+"," ");
	    	        				String[] lis=d.split(" ");
	    	        				for(int g=0;g<lis.length;g++)
	    	        				{
	    	        					if(lis[g]!="" && !lis[g].startsWith("0")){
	    	        						callstem(lis[g],4);
	    	        						callstem(lis[g],5);
	    	        					}
	    	        				}
	    	        			}
	    	        			else
	    	        			{
	    	        				String[] ol=d.split("|");
	    	        				String s1=ol[0];
	    	        				String s2=ol[1];
	    	        				s1=s1.replaceAll("[^a-zA-Z0-9\\.,:' ]+"," ");
	    	        				s2=s2.replaceAll("[^a-zA-Z0-9\\.,:' ]+"," ");	
	    	        				String[] lis1=d.split(" ");
	    	        				String[] lis2=d.split(" ");
	    	        				for(int g=0;g<lis1.length;g++)
	    	        				{
	    	        					if(lis1[g]!="" && !lis1[g].startsWith("0")){
	    	        						callstem(lis1[g],4);
	    	        					}
	    	        				}	
	    	        				for(int g=0;g<lis2.length;g++)
	    	        				{
	    	        					if(lis2[g]!="" && !lis2[g].startsWith("0")){
	    	        						callstem(lis2[g],5);
	    	        					}
	    	        				}	
	    	        				
	    	        			}
	    	        		}
	    	        	}
	        		cont=cont.replaceAll("\\s"," ");
	        		cont=cont.replaceAll("==External links==.*|==See also==.*|==References==.*|==Bibliography==.*|===Further reading===.*"," ");
	        		cont=cont.replaceAll("\\[\\[.*?\\]\\]|\\[.*?\\]|\\{\\{.*?\\}\\}"," ");
	        		cont=cont.replaceAll("<ref.*?</ref>"," ");
	        		cont=cont.replaceAll("\".*?\"", " ");
	        		cont=cont.replaceAll("[^a-zA-Z0-9]"," ");
	        		cont=cont.toLowerCase();
	        		String[] lis=cont.split(" ");
    				for(int g=0;g<lis.length;g++)
    				{
    					if(lis[g]!="" && !lis[g].startsWith("0"))
    						callstem(lis[g],5);
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
	                        	if(fl==1)
	                        	{
	                        		if(!hashMap.containsKey(u))        									
	    							{
	    								if(tMap1.containsKey(u))
	    								{
	    									if(tMap1.get(u).containsKey(pid))
	    									{
	    										int l= tMap1.get(u).get(pid)+1;
	    										tMap1.get(u).put(pid, l);
	    									}
	    									else
	    									{
	    										tMap1.get(u).put(pid, 1);
	    									}
	    								}
	    								else
	    								{
	    									TreeMap<String,Integer> tm1= new TreeMap<String,Integer>();
	    									tm1.put(pid, 1);
	    									tMap1.put(u, tm1);
	    								}
	    							}
	                        	}
	                        	if(fl==2)
	                        	{
	                        		if(!hashMap.containsKey(u))        									
	    							{
	    								if(tMap2.containsKey(u))
	    								{
	    									if(tMap2.get(u).containsKey(pid))
	    									{
	    										int l= tMap2.get(u).get(pid)+1;
	    										tMap2.get(u).put(pid, l);
	    									}
	    									else
	    									{
	    										tMap2.get(u).put(pid, 1);
	    									}
	    								}
	    								else
	    								{
	    									TreeMap<String,Integer> tm1= new TreeMap<String,Integer>();
	    									tm1.put(pid, 1);
	    									tMap2.put(u, tm1);
	    								}
	    							}
	                        	}
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

	                        	if(fl==5)
	                        	{
	                        		if(!hashMap.containsKey(u))        									
	    							{
	    								if(tMap5.containsKey(u))
	    								{
	    									if(tMap5.get(u).containsKey(pid))
	    									{
	    										int l= tMap5.get(u).get(pid)+1;
	    										tMap5.get(u).put(pid, l);
	    									}
	    									else
	    									{
	    										tMap5.get(u).put(pid, 1);
	    									}
	    								}
	    								else
	    								{
	    									TreeMap<String,Integer> tm1= new TreeMap<String,Integer>();
	    									tm1.put(pid, 1);
	    									tMap5.put(u, tm1);
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
	    	if(flag1==1 && flag2==0 && rflg!=1)
	    	{
	    		title=new String(ch,start,length);
	    		title=title.toLowerCase();
	    		title=title.replaceAll("[^a-zA-Z0-9\\. ]+"," ");
	    		String[] lis=title.split("[\" \"/]");
	    		for(int g=0;g<lis.length;g++)
	    		{
	    			callstem(lis[g],1);
	    		}
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
	    	String path1="/media/LENOVO/Download_dc++/test/title/";
	    	String path2="/media/LENOVO/Download_dc++/test/category/";
	    	String path3="/media/LENOVO/Download_dc++/test/infobox/";
	    	String path4="/media/LENOVO/Download_dc++/test/outlink/";
	    	String path5="/media/LENOVO/Download_dc++/test/content/";
	    	try{
	    	BufferedWriter out1 = new BufferedWriter(new FileWriter(path1+"l0_"+z));
	    	BufferedWriter out2 = new BufferedWriter(new FileWriter(path2+"l0_"+z));
	    	BufferedWriter out3 = new BufferedWriter(new FileWriter(path3+"l0_"+z));
	    	BufferedWriter out4 = new BufferedWriter(new FileWriter(path4+"l0_"+z));
	    	BufferedWriter out5 = new BufferedWriter(new FileWriter(path5+"l0_"+z));
	    	Set st1= tMap1.entrySet();
	    	Set st2= tMap2.entrySet();
	    	Set st3= tMap3.entrySet();
	    	Set st4= tMap4.entrySet();
	    	Set st5= tMap5.entrySet();
	  
	    	Iterator it1= st1.iterator();
	    	Iterator it2= st2.iterator();
	    	Iterator it3= st3.iterator();
	    	Iterator it4= st4.iterator();
	    	Iterator it5= st5.iterator();
	    		while (it1.hasNext()){
	    			Map.Entry me = (Map.Entry)it1.next();
	    			out1.write(me.getKey().toString()+":"+ me.getValue().toString()+"\n");	    						    		
	    		}
	    		out1.close();
	    		tMap1.clear();
	    		while (it2.hasNext()){
	    			Map.Entry me = (Map.Entry)it2.next();
	    			out2.write(me.getKey().toString()+":"+ me.getValue().toString()+"\n");	    						    		
	    		}
	    		out2.close();	
	    		tMap2.clear();
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
	    		while (it5.hasNext()){
	    			Map.Entry me = (Map.Entry)it5.next();
	    			out5.write(me.getKey().toString()+":"+ me.getValue().toString()+"\n");	    						    		
	    		}
	    		out5.close();
	    		tMap5.clear();
	    	}
	    catch (IOException e) {
	    {
	    	System.err.println("Caught IOException: " 
                    +  e.getMessage());
	    }
	    }
}}
