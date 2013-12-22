	import java.io.*;
	import java.util.*;
	import java.util.regex.*;

	import org.xml.sax.XMLReader;
	import org.xml.sax.Attributes;
	import org.xml.sax.InputSource;
	import org.xml.sax.helpers.XMLReaderFactory;
import org.xml.sax.helpers.DefaultHandler;

	public class new_title extends DefaultHandler
	{
	    public static void main (String args[])
		throws Exception
	    {
		XMLReader xr = XMLReaderFactory.createXMLReader();
		new_title handler = new new_title();
		xr.setContentHandler(handler);
		xr.setErrorHandler(handler);
		for (int i = 0; i < args.length; i++) {
		    FileReader r = new FileReader(args[i]);
		    xr.parse(new InputSource(r));
		}
	    }

	    public new_title ()
	    {
	    	super();
	    }
	    public HashMap <String,Integer> hashMap = new HashMap<String,Integer>();
	    public TreeMap <String, TreeMap <String, Integer>> tMap1 = new TreeMap<String, TreeMap <String, Integer>>();
	    int flag1=0,flag2=0,temp=1,flag3=0,pf=0,rflg=0;
	    public static Stopwords sp=new Stopwords();
	    public static String[] stopwords = sp.stopwords;
	    String title1="",id="",cont="";
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
	    	else if ("text".equals(name))
	    	{
	    		if(flag3==1 && rflg!=1)
	    		{
	    			//System.out.println(title1);
	    			title1=title1.toLowerCase();
		    		title1=title1.replaceAll("[^a-zA-Z0-9\\. ]+"," ");
		    		String[] lis=title1.split("[\" \"/]");
		    		for(int g=0;g<lis.length;g++)
		    		{
		    			callstem(lis[g],1);
		    		}
		    		flag3=0;
		    	//	rflg=0;
		    		if(docs%10000==0)
		    		{
		    			index(fnum);
		    			fnum++;
		    		}
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
	     }

	    public void characters (char ch[], int start, int length)
	    {
	    	String temp1=new String(ch,start,length);
	    	if(flag1==1)
	    	{
	    		title1=temp1;
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
	    	String path1="/media/LENOVO/Download_dc++/index/title/";
	    	try{
	    	BufferedWriter out1 = new BufferedWriter(new FileWriter(path1+"l0_"+z));
	    	Set st1= tMap1.entrySet();
	    	Iterator it1= st1.iterator();
	    		while (it1.hasNext()){
	    			Map.Entry me = (Map.Entry)it1.next();
	    			out1.write(me.getKey().toString()+":"+ me.getValue().toString()+"\n");	    						    		
	    		}
	    		out1.close();
	    		tMap1.clear();		
	    	}
	    	catch (IOException e) {
	    		System.err.println("Caught IOException: "+  e.getMessage());
	    	}	
	
	    }
	}
