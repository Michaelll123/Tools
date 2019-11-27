package util;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;
public class FileUtil {
	//文件工具类
	public static String content=null;

	//生成插装语句的函数
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
			content = "try{ Tools.Run(\""+TMfilepath+"\",\"line"+pos +"_" +methodname+"_"+"\", "+returnobj+ invokeobj +");}catch(IOException e){}";
		else
		content = "try{String classname"+id+" ="+ invokeobj+".getClass().toString(); Tools.Run(\""+TMfilepath+"\",\"line"+pos +"_" +methodname+"_"+"\", "+returnobj+ ",classname"+id+");}catch(IOException e){}";	
	}
	//在源文件中插入必要的import语句
	public static void insertUtilClass(String filepath) throws IOException
	{
		String content = "import util.Tools;\nimport util.FileUtil;\nimport java.io.IOException;\n";
		ArrayList<String> fileContent = FileUtil.readFile(filepath);
		int cnt =0;
		for(int i=0;i<fileContent.size();i++) 
		{
			String [] array  = fileContent.get(i).split("\\s");
			if(array[0].equals("package"))
				{
					content = fileContent.get(i) + "\n" + content ;
					cnt = i;
				}		
		}
		for(int i=cnt+1;i<fileContent.size();i++) content = content + fileContent.get(i)+ "\n";
		FileWriter fileWriter =new FileWriter(filepath);
        fileWriter.write("");
        fileWriter.flush();
        fileWriter.close();
		writeFile(filepath,content);
	}
	//插装函数
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
	//将临时文件的内容重写到源文件中。
	public static void rewritefile(String sourcefile,String targetfile) throws IOException
	{
		File file = new File(targetfile);
		
	 	   if (file.exists()) 
	 	   {
//	 		   System.out.println("文件存在！");
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
	//写文件
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
//	            System.out.println("文件创建成功！");
    	   }
		            
		        } catch (IOException e) {
		        }
	}
	//读文件
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

