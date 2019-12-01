package org.bit.unit;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;
public class FileUtil {

	public static String content=null;

	public static void generateFunction(String filepath,int id,String returnobj,String invokeobj,String methodname,String pos)
	{
		
		String []TMfilepath_ = filepath.split("\\\\");
		String TMfilepath="";
		for(int i=0;i<TMfilepath_.length-1;i++)
		{
			TMfilepath = TMfilepath + TMfilepath_[i]+"\\\\";
		}
		TMfilepath = TMfilepath + TMfilepath_[TMfilepath_.length-1];
//		System.out.println(TMfilepath);
		if(Tools.IsClass(invokeobj))
			content = "try{Tools.Run(\""+TMfilepath+"\",\"line"+pos +"_" +methodname+"_"+"\", "+returnobj +","+invokeobj+".class);}catch(IOException e){}";
		else
		content = "try{Tools.Run(\""+TMfilepath+"\",\"line"+pos +"_" +methodname+"_"+"\", "+returnobj+ ","+invokeobj+".getClass());}catch(IOException e){}";	
	}
	public static void insertUtilClass(String filepath) throws IOException
	{
		String content = "import util.Tools;\nimport util.FileUtil;\nimport java.io.IOException;\n";
		ArrayList<String> fileContent = FileUtil.readFile(filepath);
		int cnt =0;
		for(int i=0;i<fileContent.size();i++) 
		{
			if(fileContent.get(i).trim().startsWith("package"))
			{
				content = fileContent.get(i) + "\n" + content ;
				cnt = i;
				break;
			}
		}
		for(int i=cnt+1;i<fileContent.size();i++) content = content + fileContent.get(i)+ "\n";
		FileWriter fileWriter =new FileWriter(filepath);
        fileWriter.write("");
        fileWriter.flush();
        fileWriter.close();
		writeFile(filepath,content);
	}
	public static void pretreat(String filepath) throws IOException
	{
		String result = Mytrim(filepath);
		//empty the file before rewriting
		FileWriter fileWriter =new FileWriter(filepath);
        fileWriter.write("");
        fileWriter.flush();
        fileWriter.close();
		writeFile(filepath,result);
	}
	
	public static String Mytrim(String filepath) throws IOException
	{
		String result = ""; int cnt =0;
		String resultstr = "";
		ArrayList<String> trimedfileContent = new ArrayList<String>();
		ArrayList<String> fileContent = FileUtil.readFile(filepath);
		for(int i=0;i<fileContent.size();i++)
		{
			String str = fileContent.get(i);
			if(str.contains("//"))
			trimedfileContent.add(str.substring(0,str.indexOf("//")));
			else
				trimedfileContent.add(str);
		}
		for(int i=0;i<trimedfileContent.size();i++)
		{
//			for(int j=0;j<fileContent.get(i).length();j++)
//			{
//				System.out.println(fileContent.get(i).charAt(j));
//			}
			String tmp = trimedfileContent.get(i).trim(); 
			
			if(tmp.equals("")||tmp==null||tmp.startsWith("@")||tmp.startsWith("/*")||tmp.startsWith("*")) 
				{
				resultstr = resultstr + trimedfileContent.get(i) + "\n";
				 continue;
				}
			else{
				if(tmp.charAt(tmp.length()-1)!=';'||cnt!=0)
				{
					if((tmp.contains("{")||tmp.contains("}")||tmp.contains("public")||tmp.contains("class")||tmp.contains("for")||tmp.contains("if")||tmp.contains("else")||tmp.contains("while"))&&cnt==0)
					{
//						System.out.println(tmp);
						resultstr  = resultstr+trimedfileContent.get(i)+"\n";
						continue;
					}
					else
					{
						cnt++;
						if(tmp.charAt(tmp.length()-1)!=';')
						result =result + tmp;
						else 
						{
							result = result + tmp;
							resultstr  = resultstr+result+"\n";
							System.out.println(result);
							result = "";
							cnt=0;
						}
						
					}
						
				}
				else
				{
					resultstr  = resultstr+trimedfileContent.get(i)+"\n";
				}
//				System.out.println("line"+(i+1)+":"+tmp.charAt(tmp.length()-1));
			}
			
		}
		return resultstr;
	}
	public static void appendToFile(String writefilename,String filepath,String returnobj,String invokeobj,String methodname,int startpos,int endpos,String pos) throws IOException
	{
		Random random = new Random();
		int id = random.nextInt(Integer.MAX_VALUE-1);
		String appendContent="";
		generateFunction(filepath,id,returnobj,invokeobj,methodname,pos);
		if(content!=null)
		{
			ArrayList<String> fileContent = readFile(filepath);
//			System.out.println(endpos+" "+(fileContent.size()-1));
			if(endpos!=fileContent.size()-1)
			{
				for(int i=startpos;i<endpos+1;i++)
				{
					appendContent = appendContent + fileContent.get(i)+ "\n";
				}
				appendContent = appendContent + content + "\n";
			}
			else 
			{
//				System.out.println("1");
				for(int i=startpos+1;i<endpos+1;i++)
				{
					appendContent = appendContent + fileContent.get(i)+ "\n";
				}
			}
//			System.out.println(appendContent);
			
			
			writeFile(writefilename,appendContent);
		}
	
	}
	public static void rewritefile(String sourcefile,String targetfile) throws IOException
	{
		File file = new File(targetfile);
		
	 	   if (file.exists()) 
	 	   {
	  		   FileWriter fileWriter =new FileWriter(file);
	             fileWriter.write("");
	             fileWriter.flush();
	             fileWriter.close();
	 	   }
		ArrayList<String> sourcecontent = readFile(sourcefile);
		String lines="";
		for(int i=0;i<sourcecontent.size();i++) lines = lines + sourcecontent.get(i)+ "\n";
		writeFile(targetfile,lines);
		TwoFileIsSame.Deletefile(sourcefile);
	}
	public static void writeFile(String FileName,String Content)
	{
       try {
    	   {
    		   File file = new File(FileName);
    		   File fileParent = file.getParentFile(); 
    		   if(!fileParent.exists()){ 
    		    fileParent.mkdirs(); 
    		   } 
    		   if(!file.exists())
    		   file.createNewFile();
    		   
    		   BufferedWriter out = new BufferedWriter(new FileWriter(FileName,true));
	            out.append(Content);
	            out.close();
    	   }
		            
		        } catch (IOException e) {
		        }
	}
	public static ArrayList<String> readFile(String FileName) throws IOException
	{
		ArrayList<String> lines = new ArrayList<String>();

        FileInputStream fileInputStream = new FileInputStream(FileName);

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));

        String line = null;

        while ((line = bufferedReader.readLine()) != null) {
//            System.out.println(line);
            lines.add(line);
        }

        fileInputStream.close();
        bufferedReader.close();
		return lines;
		
	}
}

