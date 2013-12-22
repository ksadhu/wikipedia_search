	import java.io.*;
	import java.util.*;
	import java.util.regex.*;

	import org.xml.sax.XMLReader;
	import org.xml.sax.Attributes;
	import org.xml.sax.InputSource;
	import org.xml.sax.helpers.XMLReaderFactory;
import org.xml.sax.helpers.DefaultHandler;

	public class title_index extends DefaultHandler
	{
	    public static void main (String args[])
		throws Exception
	    {
		XMLReader xr = XMLReaderFactory.createXMLReader();
		title_index handler = new title_index();
		xr.setContentHandler(handler);
		xr.setErrorHandler(handler);
		for (int i = 0; i < args.length; i++) {
		    FileReader r = new FileReader(args[i]);
		    xr.parse(new InputSource(r));
		}	
	    }

	    public title_index ()
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
	    String path="/media/LENOVO/title_ind/";
	    public TreeMap <Long, String> tMap1 = new TreeMap<Long , String>();
	    long start=0,end=0;
	    long docs=1,fnum=1;
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
	    		//System.out.print(tx.toString()+" --- ");  	
	    		tx.append(" :: ");
	    		flag1=0;
	    	}
	    	if ("text".equals(name))
	    	{
	    		String[] spl=tx.toString().split(" :: ");
	    		tMap1.put(Long.parseLong(spl[1]), spl[0]);
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
	    	
	    	if(flag2==1 && temp==0)
	    	{
	    		pid=new String(ch,start,length);
	    		System.out.println(pid);
	    		tx.append(pid);
	    	}
	    	if(flag1==1)
	    	{
	    		temp1=temp1.toLowerCase();
	    		String stm=callstem(temp1);
	    		tx.append(temp1);	
	    	}
	    }
	    public void index(long z)
	    {
	    	
	    	try{
	    	BufferedWriter out1 = new BufferedWriter(new FileWriter(path+z));
	        BufferedWriter tempt= new BufferedWriter(new FileWriter(path+"sec_ttl_phase2", true));   
	    	Set st1= tMap1.entrySet();
	    	Iterator it1= st1.iterator();
	    	int tr=0;
	    	while (it1.hasNext()){
	    		Map.Entry me = (Map.Entry)it1.next();
	    		if(tr==0){
    				tempt.write(me.getKey().toString() +" : "+z+"\n");
	    		}
	    		tr++;
    			out1.write(me.getKey().toString()+" :: "+me.getValue().toString()+"\n");	    						    		
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
