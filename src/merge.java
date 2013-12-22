
import java.io.*;
import java.util.*;

public class merge
{
	public void merge1(String t1,String t2,String t3)throws Exception
	{
		BufferedReader f1 = new BufferedReader(new FileReader(t1));
		BufferedReader f2 = new BufferedReader(new FileReader(t2));
		BufferedWriter f3 = new BufferedWriter(new FileWriter(t3));
		String s1="",s2="";
		if(f1.ready())
			s1=f1.readLine();
		if(f2.ready())
			s2=f2.readLine();
		while(s1!=null && s2!=null)
		{
			if(!s1.equals("\n") && !s2.equals("\n"))
			{
				String[] fs1=s1.split(":");
				String[] fs2=s2.split(":");
				if(fs1[0].compareTo(fs2[0])<0)
				{
					f3.write(s1+"\n");
					s1=f1.readLine();
				}
				else if(fs1[0].compareTo(fs2[0])>0)
				{
					f3.write(s2+"\n");
					s2=f2.readLine();
				}
				else if(fs1[0].equals(fs2[0]))
				{
					String com=s1.substring(0,s1.length()-1)+", "+fs2[1].substring(1,fs2[1].length());
					f3.write(com+"\n");
					s1=f1.readLine();
					s2=f2.readLine();
				}
			}
		}
		if(s1!=null)
		{
		f2.close();
			while(s1!=null)
			{
				f3.write(s1+"\n");
				s1=f1.readLine();
			}
		}
		else
		{
		f1.close();
			while(s2!=null)
			{
				f3.write(s2+"\n");
				s2=f2.readLine();
			}
		}
		f3.close();
	}
	public static void main(String args[])throws Exception
	{
		long x=System.currentTimeMillis();
		int lev=0;
		File f=new File("/media/LENOVO/Download_dc++/index/new_out");
		int len=f.list().length;
		int len1=len;
		while(true)
		{
			System.out.println(len1);
			if(len==1)
				break;		
			for(int i=1;i<=len1;i=i+2)
			{
				int p=i+1;
				int k=p/2;
				if(i==len1 && (i%2)==1)
				{
					File te=new File("/media/LENOVO/Download_dc++/index/new_out/l"+lev+"_"+i);		
					File te1=new File("/media/LENOVO/Download_dc++/index/new_out/l"+(lev+1)+"_"+k);
					if(te.exists())
					{						
						boolean b=te.renameTo(te1);
						if(b)
							System.out.println("l"+lev+"_"+i+"    renamed as   "+"l"+(lev+1)+"_"+k);
						else
							System.out.println("renaming failed ");
					}
				}
				else
				{
					String k1="l"+(lev+1)+"_"+k;
					String k2="l"+lev+"_"+i;
					String k3="l"+lev+"_"+p;
					String s1="/media/LENOVO/Download_dc++/index/new_out/"+k2;
					String s2="/media/LENOVO/Download_dc++/index/new_out/"+k3;
					String s3="/media/LENOVO/Download_dc++/index/new_out/"+k1;
					File f11=new File(s1);
					File f22=new File(s2);
					merge m=new merge();
					m.merge1(s1, s2, s3);
					len--;
					f11.delete();
					f22.delete();						
				}
			}
			if(len1%2==0)
				len1=len1/2;
			else
				len1=(len1/2)+1;
			lev++;
		}
		long y=System.currentTimeMillis();
		System.out.println(y-x+"msecs");
	}
}
