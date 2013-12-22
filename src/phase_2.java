import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import edu.stanford.nlp.ie.crf.*;
import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.CoreAnnotations.AnswerAnnotation;
import edu.stanford.nlp.util.StringUtils;

//import java.util.List;
//import java.io.IOException;

public class phase_2 {
	public static TreeMap <String,  Integer> tMap2 = new TreeMap<String, Integer>();
	public static TreeMap <String,  Integer> infoid = new TreeMap<String, Integer>();
	public static TreeMap <Long,  Integer> outlnk = new TreeMap<Long, Integer>();
	public static TreeMap <String,  Integer> attr = new TreeMap<String, Integer>();
	public static TreeMap <String,  Integer> infty = new TreeMap<String, Integer>();
	public static TreeMap <String,  TreeMap <String, Integer>> tmp1 = new TreeMap<String, TreeMap <String, Integer>>();
	public static TreeMap <String,  TreeMap <String, Integer>> tmp2 = new TreeMap<String, TreeMap <String, Integer>>();
	public static TreeMap <String,  TreeMap <String, Integer>> tmp3 = new TreeMap<String, TreeMap <String, Integer>>();
	
	public static void main (String args[])throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String path="/media/LENOVO/temp/";
		String path1="/media/LENOVO/Download_dc++/index/";
		String s=br.readLine();
		String[] pd=s.split(" ");
		String ans="";
		String inftype="";
		String attrs="";
		String currpid="";
		for(int i=0;i<pd.length;i++)
		{
			BufferedReader out5 = new BufferedReader(new FileReader(path1+"title/sec_ttl"));
			pd[i]=callstem(pd[i]);
			String temp1=bsearch1(out5,pd[i]);
			String s1[]=temp1.split(":");
			BufferedReader p5 = new BufferedReader(new FileReader(path1+"title/"+s1[1]));
			String temp2=searchttl(p5,pd[i]);
			temp2=temp2.replaceAll("=[0-9]+[,}]", " ");
			temp2=temp2.replaceAll("[^0-9]+", " ");
			temp2=temp2.replaceAll("[\\s]+", " ");
			String[] ids=temp2.split(" ");
			for(int k=0;k<ids.length;k++){
				if(i==0){
					if(!ids[k].equals(""))
						tMap2.put(ids[k], 1);
				}
				else{
					if(tMap2.containsKey(ids[k])){
						tMap2.put(ids[k], tMap2.get(ids[k])+1);
					}					
				}		
			}	
			out5.close();
			p5.close();
		}
		Set st1= tMap2.entrySet();
        Iterator it1= st1.iterator();
        while (it1.hasNext()){
			Map.Entry me = (Map.Entry)it1.next();
			int l=Integer.parseInt(me.getValue().toString());
			if(pd.length==l){
				System.out.println(me.getKey().toString()+" bool AND success ");
				String path2="/media/LENOVO/title_ind/";
				BufferedReader out5 = new BufferedReader(new FileReader(path2+"sec_ttl_phase2"));
				String temp1=bsearch(out5,me.getKey().toString(),0);
				//System.out.println(temp1);
				String sl1[]=temp1.split(" : ");
				BufferedReader p5 = new BufferedReader(new FileReader(path2+sl1[1]));
				String temp2=search(p5,me.getKey().toString(),1);
				System.out.println(temp2);
				out5.close();
				p5.close();
				if(!temp2.equals("")){
					String[] x1=temp2.split(" :: ");
					//x1[1]=callstem(x1[1]);
					//System.out.println(x1[1]);
					if(s.equals(x1[1])){
						System.out.println("page found "+x1[0]);
						currpid=x1[0];
						BufferedReader out1 = new BufferedReader(new FileReader(path+"sec_fwd"));
						String tp1=bsearch(out1,me.getKey().toString(),0);
						String s1[]=tp1.split(" : ");
						BufferedReader p1 = new BufferedReader(new FileReader(path+s1[1]));
						String tp2=search(p1,me.getKey().toString(),0);
						out1.close();
						p1.close();
						if(tp2!=""){
							String[] sp=tp2.split(" : ");
							String[] sp1=sp[1].split(" ; ");			
							if(sp1.length==3){
								if(sp1[0].startsWith("infobox")){
									String info=sp1[0].substring(8);
									inftype=info;
									System.out.println(info+" infobox type ");
									String res=boolAnd(info,1);
									//System.out.println("infobox search results "+res);
									String[] infopids=res.split(" ");
									sp1[1]=sp1[1].replaceAll(" , ", "");
									String[] att=sp1[1].split(" ");
									for(int y=0;y<att.length;y++){
										if(attr.containsKey(att[y])){
											attr.put(att[y], Integer.parseInt(attr.get(att[y]).toString())+1);
										}
										else
											attr.put(att[y], 1);
									}
									for(int t=0;t<infopids.length;t++)
									{
										if(t>40)
											break;
										if(!infopids.equals("")){
										BufferedReader ou1 = new BufferedReader(new FileReader(path+"sec_fwd"));
										String tp11=bsearch(ou1,infopids[t],0);
										String s11[]=tp11.split(" : ");
										BufferedReader p11 = new BufferedReader(new FileReader(path+s11[1]));
										String tp22=search(p11,infopids[t],0);
										if(tp22!=""){
											String[] sp0=tp22.split(" : ");
											String[] sp11=sp0[1].split(" ; ");			
											if(sp11.length==3){
												if(sp11[0].startsWith("infobox")){
													//String info1=sp11[0].substring(8);
													sp11[1]=sp11[1].replaceAll(" , ", "");
													String[] at=sp11[1].split(" ");
													for(int y=0;y<at.length;y++){
														//System.out.println(at[y]);
														if(attr.containsKey(at[y])){
															attr.put(at[y], Integer.parseInt(attr.get(at[y]).toString())+1);
														}
														else
															attr.put(at[y], 1);
													}
												}
											}
										}
										ou1.close();
										p11.close();
										}		
										
									}
								}
							}
							else{
								System.out.println("in else! ");
								System.out.println(sp1[0]);
								String[] out=sp1[0].split(", ");
								for(int i=0;i<out.length;i++){
									if(i>40)
										break;
									String ans1=boolAnd(out[i],0);
									String[] finout=ans1.split(" ");
									for(int j=0;j<finout.length;j++)
									{
										if(j>5)
											break;
										long tem=Long.parseLong(finout[j]);
										System.out.print(finout[j]+" ");
										if(outlnk.containsKey(tem)){
											outlnk.put(tem, outlnk.get(tem)+1);
										}
										else
											outlnk.put(tem, 1);
									}
									System.out.println();
								}
							}
								
						}
					}
				}	
			}
		}
        
        Set set1= outlnk.entrySet();
        Iterator itr1= set1.iterator();
        String valu="";
        while (itr1.hasNext()){
        	Map.Entry me = (Map.Entry)itr1.next();
        	valu=valu+me.getKey().toString()+" ";
        	//valu=valu+me.getKey().toString()+"="+me.getValue().toString()+", ";
        	//System.out.println(me.getKey()+" "+me.getValue());
        }
        
        valu=valu.trim();
        //valu=valu+"}";
        //valu=rank(valu);
        System.out.println(valu);
        String[] fin=valu.split(" ");
        for(int p=0;p<fin.length;p++)
        {
        	if(!fin[p].equals("")){
        	BufferedReader out1 = new BufferedReader(new FileReader(path+"sec_fwd"));
        	System.out.println(fin[p]);
			String tp1=bsearch(out1,fin[p],0);
			String s1[]=tp1.split(" : ");
			BufferedReader p1 = new BufferedReader(new FileReader(path+s1[1]));
			String tp2=search(p1,fin[p],0);
			out1.close();
			p1.close();
			if(tp2!=""){
				String[] sp=tp2.split(" : ");
				String[] sp1=sp[1].split(" ; ");			
				if(sp1.length==3){
					if(sp1[0].startsWith("infobox")){
						String info=sp1[0].substring(8);
						System.out.println(info);
						if(infty.containsKey(info))
							infty.put(info, infty.get(info)+1);
						else
							infty.put(info, 1);
					//	String res=boolAnd(info,1);
						sp1[1]=sp1[1].replaceAll(" , ", "");
						String[] att=sp1[1].split(" ");
						for(int y=0;y<att.length;y++){
							if(attr.containsKey(att[y])){
								attr.put(att[y], Integer.parseInt(attr.get(att[y]).toString())+1);
							}
							else
								attr.put(att[y], 1);
						}
					}
				}
			}}		
		}
        Set st2= attr.entrySet();
        Iterator it2= st2.iterator();
        String atts="";
        TreeMap <Integer,  String> ttt = new TreeMap<Integer, String>();
        while (it2.hasNext()){
        	Map.Entry me = (Map.Entry)it2.next();
        	atts=atts+me.getKey().toString()+" + ";
        	//System.out.println(me.getKey().toString()+" --- "+me.getValue().toString());
        	if(ttt.containsKey(Integer.parseInt(me.getValue().toString()))){
        		String et=ttt.get(Integer.parseInt(me.getValue().toString()));
        		ttt.put(Integer.parseInt(me.getValue().toString()), et+" | "+me.getKey().toString());
        	}
        	else
        		ttt.put(Integer.parseInt(me.getValue().toString()), me.getKey().toString());
        }
        Set st3= infty.entrySet();
        Iterator it3= st3.iterator();
        TreeMap <Integer,  String> inft = new TreeMap<Integer, String>();
        while (it3.hasNext()){
        	Map.Entry me = (Map.Entry)it3.next();
        	System.out.println(me.getKey().toString()+" --- "+me.getValue().toString());
        	if(inft.containsKey(Integer.parseInt(me.getValue().toString()))){
        		String et=inft.get(Integer.parseInt(me.getValue().toString()));
        		inft.put(Integer.parseInt(me.getValue().toString()), et+" | "+me.getKey().toString());
        	}
        	else
        		inft.put(Integer.parseInt(me.getValue().toString()), me.getKey().toString());
        }
        /*Set st4=inft.entrySet();
        Iterator it4=st4.iterator();
        while(it4.hasNext()){
        	Map.Entry me= (Map.Entry)it4.next();
        	
        }*/
        int tp=0;
        while(!inft.isEmpty()){
        	tp++;
        	Map.Entry tr=inft.lastEntry();
        	inftype=inftype+tr.getValue()+" + ";
        	System.out.println(tr.toString());
        	inft.remove(tr.getKey());
        	if(tp>2)
        		break;
        }
        int tp1=0;
        while(!ttt.isEmpty()){
        	tp1++;
        	Map.Entry tr=ttt.lastEntry();
        	//inftype=inftype+tr.getValue()+" + ";
        	attrs=attrs+tr.getValue()+" + ";
        	System.out.println(tr.toString());
        	ttt.remove(tr.getKey());
        	if(tp1>10)
        		break;
        }
        attrs=attrs.replaceAll(" \\+ ", " | ");
        String[] finatt=attrs.split(" \\| ");
        System.out.println(inftype);
        inftype=inftype.replaceAll(" \\+ ", " | ");
        String[] fininf=inftype.split(" \\| ");
        String infids="";
        for(int h=0;h<fininf.length;h++){
        	String fr=boolAnd(fininf[h],1);
        	String[] ep=fr.split(" ");
        	String ne="";
        	for(int u=0;u<ep.length;u++){
        		ne=ne+" "+ep[u];
        		if(u>3)
        			break;
        	}
        	ne.trim();
        	infids=infids+" "+ne;
        	//System.out.println(fininf[h]);
        }
        infids=infids.trim();
        System.out.println(infids);
        atts=atts.replaceAll(",", "");
        //String[] finatt=atts.split(" \\+ ");
        String[] pids=infids.split("[\\s]+");
        System.out.println(pids.length);
        String pt="/media/disk/Users/harshika/Desktop/stanford-ner-2009-01-16/stanford-ner-2009-01-16/";
        AbstractSequenceClassifier classifier = CRFClassifier.getClassifierNoExceptions(pt+"classifiers/ner-eng-ie.crf-3-all2008.ser.gz");
        for(int i=0;i<finatt.length;i++)
        {
        	TreeMap <String, Integer> tm1 = new TreeMap<String, Integer>();
        	TreeMap <String, Integer> tm2 = new TreeMap<String, Integer>();
        	for(int j=0;j<pids.length;j++)
        	{
        		if(j>20)
        			break;
        		BufferedReader cfi = new BufferedReader(new FileReader("/media/LENOVO/Download_dc++/CFInd/SecInd"));
        		String fdi=bsearch(cfi,pids[j],1);
        		String[] fds=fdi.split("=");
        		BufferedReader cfi1 = new BufferedReader(new FileReader("/media/LENOVO/Download_dc++/CFInd/OffInd/"+fds[1]));
        		String fdl=search(cfi1,pids[j],2);
        		String[] fds1=fdl.split("=");
        		//System.out.println(fds1[1]);
        		BufferedReader cfi2 = new BufferedReader(new FileReader("/media/LENOVO/Download_dc++/CFInd/PrInd/"+fds[1]));
        		if(fds1.length>1)
        			cfi2.skip(Long.parseLong(fds1[1]));
        		String fs=cfi2.readLine();
        		Pattern p = Pattern.compile("\\{\\{[Ii]nfobox.*?(}}[\n ]*[A-Za-z0-9'\"\\[<])", Pattern.DOTALL|Pattern.MULTILINE);
        		Matcher m = p.matcher(fs);
        		fs=fs.replaceAll("==External links==.*|==See also==.*|==References==.*|==Bibliography==.*|===Further reading===.*"," ");
		//fs=fs.replaceAll("\\[\\[.*?\\]\\]|\\[.*?\\]|\\{\\{.*?\\}\\}"," ");
        		fs=fs.replaceAll("<ref.*?</ref>"," ");
        		fs=fs.replaceAll("<!--.*-->", " ");
        		fs=fs.replaceAll("\".*?\"", " ");
        		fs=fs.replaceAll("[\\[\\]\\{\\}]+", "");
        		fs=fs.replaceAll("[\\s]+"," ");
        		fs=fs.trim();
        		while(m.find()){
        			Pattern attval = Pattern.compile("([A-Za-z0-9_ ]+?)=([^\\|]+)");
        			Matcher at= attval.matcher(m.group());
        			while(at.find()){
        				//System.out.println(at.group());
        				String xi=at.group();
        				//xi=xi.trim();
        				String[] gh=xi.split("=");
        				//gh[1]=gh[1].replaceAll("[^a-z0-9 ,]+", "");
        				gh[1]=gh[1].trim();
        				gh[0]=gh[0].trim();
        				finatt[i]=finatt[i].replaceAll(",", "");
        				finatt[i]=finatt[i].trim();
        				//System.out.println(finatt[i]);
        				//System.out.println(gh[0]);
        				String[] cls={"PERSON","ORGANIZATION","LOCATION"};
        				if(finatt[i].equals(gh[0])){
        					gh[1]=gh[1].replaceAll("[^A-Za-z0-9 ,]+", " ");
        					gh[1]=gh[1].trim();
        					String ex=classifier.classifyToString(gh[1]);
        					if(!ex.equals("")){
        						
        						if(ex.contains(cls[0])){
        							if(tm1.containsKey(cls[0])){
        								int ht=tm1.get(cls[0]);
        								tm1.put(cls[0], ht+1);
        							}
        							else
        								tm1.put(cls[0], 1);
        						}
        						if(ex.contains(cls[1])){
        							if(tm1.containsKey(cls[1])){
        								int ht=tm1.get(cls[1]);
        								tm1.put(cls[1], ht+1);
        							}
        							else
        								tm1.put(cls[1], 1);
        						}
        						if(ex.contains(cls[2])){
        							if(tm1.containsKey(cls[2])){
        								int ht=tm1.get(cls[2]);
        								tm1.put(cls[2], ht+1);
        							}
        							else
        								tm1.put(cls[2], 1);
        						}
        						System.out.println(ex);
        						String[] sent=fs.split("\\. ");
        		        		for(int po=0;po<sent.length;po++){
        		        			sent[po]=sent[po].trim();
        		        			sent[po]=sent[po].replaceAll("[^A-Za-z0-9 ,]+", " ");
        		        			sent[po]=sent[po].replaceAll("[\\s]+", " ");
        		        			String stmt=classifier.classifyToString(sent[po]);
        		        			String ptt=pattern(stmt);
        		        		//	System.out.println(stmt);
        		        		//	System.out.println(ptt);
        		        			if(!ptt.equals("")){
        		        				if(tm2.containsKey(ptt)){
        		        					int ty=tm2.get(ptt);
        		        					tm2.put(ptt, ty+1);
        		        				}
        		        				else
        		        					tm2.put(ptt, 1);
        		        			}
        		        		}
        					}
        					
        				}
        				//System.out.println(gh[0].trim()+" -- "+gh[1]);
        				//System.out.println(at.group());
        			}
        		}
        		
        	} 	
        	tmp1.put(finatt[i], tm1);
    		tmp2.put(finatt[i], tm2);
        }
        Set seet1= tmp1.entrySet();
        Iterator itrr1= seet1.iterator();
        while(itrr1.hasNext()){
        	Map.Entry me = (Map.Entry)itrr1.next();
        	System.out.println(me.getKey()+"------"+me.getValue());
        }
        Set seet2= tmp2.entrySet();
        Iterator itrr2= seet2.iterator();
        while(itrr2.hasNext()){
        	Map.Entry me = (Map.Entry)itrr2.next();
        	System.out.println(me.getKey()+"------"+me.getValue());
        }
        BufferedReader cfi = new BufferedReader(new FileReader("/media/LENOVO/Download_dc++/CFInd/SecInd"));
		String fdi=bsearch(cfi,currpid,1);
		String[] fds=fdi.split("=");
		BufferedReader cfi1 = new BufferedReader(new FileReader("/media/LENOVO/Download_dc++/CFInd/OffInd/"+fds[1]));
		String fdl=search(cfi1,currpid,2);
		String[] fds1=fdl.split("=");
		//System.out.println(fds1[1]);
		BufferedReader cfi2 = new BufferedReader(new FileReader("/media/LENOVO/Download_dc++/CFInd/PrInd/"+fds[1]));
		if(fds1.length>1)
			cfi2.skip(Long.parseLong(fds1[1]));
		String fs=cfi2.readLine();
		fs=fs.replaceAll("==External links==.*|==See also==.*|==References==.*|==Bibliography==.*|===Further reading===.*"," ");
		//fs=fs.replaceAll("\\[\\[.*?\\]\\]|\\[.*?\\]|\\{\\{.*?\\}\\}"," ");
        fs=fs.replaceAll("<ref.*?</ref>"," ");
        fs=fs.replaceAll("<!--.*-->", " ");
        fs=fs.replaceAll("\".*?\"", " ");
        fs=fs.replaceAll("[\\[\\]\\{\\}]+", "");
        fs=fs.replaceAll("[\\s]+"," ");
        fs=fs.trim();
        String[] cursent=fs.split("\\. ");
        for(int lp=0;lp<finatt.length;lp++){
        	//System.out.println(finatt[lp]);
        	if(tmp1.containsKey(finatt[lp])){
        		String ttyp=mostfreq(tmp1.get(finatt[lp]).toString());
        		String patt=mostfreq(tmp2.get(finatt[lp]).toString());
        		if(ttyp.equals("PERSON"))
        			ttyp="p";
        		if(ttyp.equals("ORGANIZATION"))
        			ttyp="o";
        		if(ttyp.equals("LOCATION"))
        			ttyp="l";
        		if(!ttyp.equals("")){
        			System.out.println(finatt[lp]+"---"+ttyp);
        		for(int k=0;k<cursent.length;k++){
        			cursent[k]=cursent[k].replaceAll("[^A-Za-z0-9 ,]+", " ");
        			cursent[k]=cursent[k].replaceAll("[\\s]+", " ");
        			cursent[k]=cursent[k].trim();
        			String stmt=classifier.classifyToString(cursent[k]);
        			stmt=pattern(stmt);
        			if(!stmt.equals("")&& stmt.contains(patt)){
        				System.out.println(cursent[k]);
        				System.out.println(stmt);
        			}
        		}}
        	}
        }
        //System.out.println(fs);
        //System.out.println(tmp1.keySet());
      //  System.out.println(tmp1.values());
	}
	static String mostfreq(String inp){
		inp=inp.replaceAll("[\\{\\}]", "");
		String[] tags=inp.split(", ");
		String ans="";
		int cnt=0;
		for(int l=0;l<tags.length;l++){
			String[] temp=tags[l].split("=");
			if(temp.length>1){
				if(Integer.parseInt(temp[1])>cnt){
					cnt=Integer.parseInt(temp[1]);
					ans=temp[0];
				}
			}
		}
		return ans;
	}
	static String pattern(String s){
		String ans="";
		String[] cls={"PERSON","ORGANIZATION","LOCATION"};
		String[] fil=s.split("[\\s]+");
		for(int j=0;j<fil.length;j++){
			if(fil[j].contains(cls[0])){
				ans=ans+"p";
			}
			if(fil[j].contains(cls[1])){
				ans=ans+"o";
			}
			if(fil[j].contains(cls[2])){
				ans=ans+"l";
			}
		}
		ans=ans.trim();
		return ans;
	}
	static String callstem(String s)
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
	static String boolAnd(String s,int lol)throws IOException
	{
		String ans="";
		String path1="/media/LENOVO/Download_dc++/index/";
		String finpath="";
		String finpath1="";
		if(lol==0){
			finpath=path1+"new_out/sec_out";
			finpath1=path1+"new_out/";
		}
		else{
			finpath=path1+"new_info/sec_inf";
			finpath1=path1+"new_info/";
		}
		TreeMap <String,  Integer> tMap = new TreeMap<String, Integer>();
			BufferedReader out5 = new BufferedReader(new FileReader(finpath));
			String temp1=bsearch1(out5,s);
			String s1[]=temp1.split(":");
			BufferedReader p5 = new BufferedReader(new FileReader(finpath1+s1[1]));
			String temp2=search1(p5,s);
			temp2=temp2.replaceAll("[\\s]+", " ");
			temp2=temp2.trim();
			ans=temp2;
			out5.close();
			p5.close();
			return ans;
	}
	
	
	
	static String rank(String resl){
		String ans="";
		TreeMap <String, Integer> tMap = new TreeMap<String, Integer>();
        resl=resl.replaceAll("[{},]", "");
        String[] w=resl.split(":");
        if(w.length>1){
        	String[] li=w[1].split(" ");
        	for(int j=0;j<li.length;j++)
        	{
        		String pair[]=li[j].split("=");
        		if(tMap.containsKey(pair[0])){
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
        if(tMap1.size()>0){
        String def=tMap1.lastKey().toString();
        //System.out.println(def);
        Set st2= tMap1.entrySet();
        Iterator it2= st2.iterator();
        int g=0;
        while (!(tMap1.isEmpty()))
        {	
        	Map.Entry tr= tMap1.lastEntry();
        	if(g<5)
        	{
        		if(tr!=null)
        		{
        			ans=ans+" "+tr.getValue().toString();
        			tMap1.remove(tr.getKey());
        			g++;
        		}
        	}
        	tMap1.remove(tr.getKey());
		}
        }
        ans=ans.replaceAll("[^0-9 ]+", "");
        return ans;
	}
	
	
	static String bsearch1( BufferedReader br1, String s)throws IOException{
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
	static String search1( BufferedReader br1, String s)throws IOException{
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
			return rank(ans);
        	//return ans;
	     }	
	static String searchttl( BufferedReader br1, String s)throws IOException{
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
			//return rank(ans);
        	return ans;
	     }	
	
	static String bsearch( BufferedReader br1, String s, int fl)throws IOException{
		String temp=br1.readLine();
		String ans=temp;
        while(temp!=null)
        {
        	String[] lis={};
        	if(fl==0)
        		lis=temp.split(" : ");
        	else
        		lis=temp.split("=");
        	if(Long.parseLong(s)>Long.parseLong(lis[0]))
        	{	
        		ans=temp;
        	}
        	temp=br1.readLine();
        }     			
			return ans;
	     }
	static String search( BufferedReader br1, String s, int fl)throws IOException{
		String ans="";
		String temp=br1.readLine();
        while(temp!=null)
        {
        	String[] lis;
        	if(fl==1)
        		lis=temp.split(" :: ");
        	else if(fl==0)
        		lis=temp.split(" : ");
        	else
        		lis=temp.split("=");
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
