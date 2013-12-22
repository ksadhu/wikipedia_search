
	import java.io.*;
	import java.util.*;
	import java.util.regex.*;

	import org.xml.sax.XMLReader;
	import org.xml.sax.Attributes;
	import org.xml.sax.InputSource;
	import org.xml.sax.helpers.XMLReaderFactory;
import org.xml.sax.helpers.DefaultHandler;

	public class build_forward extends DefaultHandler
	{
	    public static void main (String args[])throws Exception{
	    	XMLReader xr = XMLReaderFactory.createXMLReader();
	    	build_forward handler = new build_forward();
	    	xr.setContentHandler(handler);
	    	xr.setErrorHandler(handler);
	    	for (int i = 0; i < args.length; i++) {
	    		FileReader r = new FileReader(args[i]);
	    		xr.parse(new InputSource(r));
	    	}		
	    }
	    public build_forward ()
	    {
	    	super();
	    }
	    public HashMap <String,Integer> hashMap = new HashMap<String,Integer>();
	    int flag1=0,flag2=0,temp=1,flag3=0,pf=0,rflg=0;
	    public static Stopwords sp=new Stopwords();
	    public static String[] stopwords = sp.stopwords;
	    String title="",id="",cont="";
	    String pid="";    
	    StringBuffer tx=new StringBuffer();
	    StringBuffer tx1=new StringBuffer();
	    String path="/media/LENOVO/temp/";
	    public TreeMap <Long, String> tMap1 = new TreeMap<Long , String>();
	    long start=0,end=0;
	    long docs=0,fnum=0;
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
	    			System.out.println(pid);
	    			boolean info=false;
	    			tx1.append(pid+" : ");
	    			String cont=tx.toString();
	        		cont=cont.replaceAll("==External links==.*|==See also==.*|==References==.*|==Bibliography==.*|===Further reading===.*","");
	        		cont=cont.replaceAll("<ref.*?</ref>","");
	        		Pattern p2=Pattern.compile("\\[\\[[a-zA-Z |]+\\]\\]");
	        		Matcher m2 = p2.matcher(cont);
	        		Pattern p = Pattern.compile("\\{\\{Infobox.*?(}}[\n ]*[A-Za-z0-9'\"\\[<])", Pattern.DOTALL|Pattern.MULTILINE);
	        		Matcher m = p.matcher(cont);
	        		Pattern p1 = Pattern.compile("\\|([a-z0-9_ ]+?)=");
	        		int gh=0;
	        		while(m.find())
	        		{
	        			gh++;
	        			String x=m.group();
	        			x=x.toLowerCase();
	        			Pattern p3=Pattern.compile("\\{\\{infobox[a-z0-9_\\. ]+");
		        		Matcher m3=p3.matcher(x);
		        		Matcher m1 = p1.matcher(x);
		        		if(gh<=1)
		        		{
		        			while(m3.find())
		        			{
		        				String z= m3.group();
		        				z=z.replaceAll("[^a-z0-9]+", " ");
		        				z=z.replaceAll("[\\s]+", " ");
		        				z=z.trim();
		        				z=callstem(z);
		        				tx1.append(z+" ; ");
		        			}	
		        			while(m1.find())
	        				{
	        					String y=m1.group();
	        					y=y.replaceAll("[\\s\\|=]+", " ");
	        					y=y.trim();
	        					tx1.append(y+", ");
	        				}
		        			tx1.append(" ; ");
	        			}
		        		else
		        			break;
	        		}
	        		
	        		while(m2.find()){
	    	        		String d=m2.group();
	    	        		d=d.toLowerCase();
	    	        		if(!d.startsWith("image:"))
	    	        		{
	    	        			if(!d.contains("|"))
	    	        			{
	    	        				d=d.replaceAll("[^a-zA-Z0-9\\.,:' ]+","");	
	    	        				d=d.trim();
	    	        				String f="";
	    	        				f=callstem(d);
	    	        				tx1.append(f+", ");
	    	        			}
	    	        			else
	    	        			{
	    	        				String[] ol=d.split("\\|");
	    	        				String s1=ol[0];	
	    	        				String s2=ol[1];
	    	        				s1=s1.replaceAll("[^a-zA-Z0-9\\.,:' ]+","");
	    	        				s1=s1.trim();
	    	        				s2=s2.replaceAll("[^a-zA-Z0-9\\.,:' ]+","");	
	    	        				s2=s2.trim();
	    	        				String f="";
	    	        				f=callstem(s1);
	    	        				tx1.append(f+", ");	
	    	        				f=callstem(s2);
	    	        				tx1.append(f+", ");		
	    	        			}
	    	        		}
	        			}
	        		tx1.append(" ; \n");
	        		if(!(tx1.toString().matches("[\\s]+")))
	        		{       			
	        			tMap1.put(Long.parseLong(pid), tx1.toString());
	        		}
	        		tx1.delete(0, tx1.length());
	    		}	
	    		tx.delete(0, tx.length());
	    		flag3=0;
	    		rflg=0;
	    		
	    		if(docs%5000==0)
    			{
    				index(fnum);
    				fnum++;
    			}
	    		docs++;
	    	}
	    }
	    public String callstem(String s)
	    {
	        Stemmer st = new Stemmer();
	    	for(int i=0;i<s.length();i++)
	    	{
	                st.add(s.charAt(i));
	    	}           
	    	st.stem();                 
	        String u;
	        u = st.toString();
	        return u;                	
	     }

	    public void characters (char ch[], int start, int length)
	    {
	    	String temp1=new String(ch,start,length);
	    	if( flag3==1 && (temp1.startsWith("#REDIRECT")||temp1.startsWith("#redirect")))
	    	{
	    		rflg=1;
	    	}  	
	    	if(flag3==1 && rflg!=1)
	    	{
	    		tx.append(temp1);
	    		
	    	}
	    	
	    	if(flag2==1 && temp==0)
	    	{
	    		pid=new String(ch,start,length);
	    	}
	    }
	    public void index(long z)
	    {
	    	
	    	try{
	    	BufferedWriter out1 = new BufferedWriter(new FileWriter(path+z));
	        BufferedWriter tempt= new BufferedWriter(new FileWriter(path+"sec_fwd", true));   
	    	Set st1= tMap1.entrySet();
	    	Iterator it1= st1.iterator();
	    	int tr=0;
	    	while (it1.hasNext()){
	    		Map.Entry me = (Map.Entry)it1.next();
	    		if(tr==0){
    				tempt.write(me.getKey().toString() +" : "+z+"\n");
	    		}
	    		tr++;
    			out1.write(me.getValue().toString()+"\n");	    						    		
    		}
	    	out1.close();
	    	tempt.close();
	    	tMap1.clear();
	    	}
	    	catch (IOException e) {
	    	    {
	    	    	System.err.println("Caught IOException: " 
	                        +  e.getMessage());
	    	    }
	    	    }
	    }
	    	
}

