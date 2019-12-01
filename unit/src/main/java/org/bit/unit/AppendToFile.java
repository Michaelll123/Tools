package org.bit.unit;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;


public class AppendToFile {

	public static void appendToFile(String fileDir) throws IOException
	{
	

		String pattern = "[\\w\\s]*\\w+\\s*=\\s*\\w+(\\.\\w+\\([\\w\\s\\(\\),:]*\\))+";
		ArrayList<String> filenames =new ArrayList<String>();
		TwoFileIsSame.getAllJavaFileName(fileDir, filenames);
		ArrayList<String> filepaths = filenames;
//		String filepath = args[0];
//		for(int i=0;i<filenames.size();i++)
//			filepaths.add(fileDir + "\\\\" + filenames.get(i));
		for (int i = 0; i < filepaths.size(); i++) {
			int startpos = 0;
			
//			
//			
			
			ArrayList<Map<String, String>> maplist = Tools.locate(filepaths.get(i), pattern);
			
			String initialpath = filepaths.get(i).split("\\.")[0];
			String path = "";
			for(int k=0;k<initialpath.split("\\\\").length-1;k++)
				path= path + initialpath.split("\\\\")[k] + "\\";
			path = path + initialpath.split("\\\\")[initialpath.split("\\\\").length-1];
			String writefilename = path + "edited.java";
			FileUtil.pretreat(filepaths.get(i));
			System.out.println(writefilename);
			ArrayList<String> fileContent = FileUtil.readFile(filepaths.get(i));
			int size = fileContent.size();
			System.out.println(size-1);
//			File file = new File(writefilename);
			
//	    	   if (file.exists()) 
//	    	   {
//	     		   FileWriter fileWriter =new FileWriter(file);
//	                fileWriter.write("");
//	                fileWriter.flush();
//	                fileWriter.close();
//	    	   }
//	    	   System.out.println(newlist.size());
//	    	   ArrayList<Map<String, String>> newlist = Tools.IsClass(maplist);
			for (int j = 0; j < maplist.size(); j++) {	
//				System.out.println("1");
				Map<String, String> map = maplist.get(j);
//				System.out.println(map);
				
//					System.out.println("j:"+j+"\t"+ "mapsize:"+newlist.size());
					
					if (j == maplist.size()-1)
					{	FileUtil.appendToFile(writefilename,filepaths.get(i), map.get("listname"), map.get("objectname"),
								map.get("methodname"),startpos, Integer.valueOf(map.get("Position")),map.get("Position"));
//					System.out.println(111);
					FileUtil.appendToFile(writefilename,filepaths.get(i), map.get("listname"), map.get("objectname"),
							map.get("methodname"),Integer.valueOf(map.get("Position")),size-1,map.get("Position"));
					}
					else {
						FileUtil.appendToFile(writefilename,filepaths.get(i), map.get("listname"), map.get("objectname"),
								map.get("methodname"), startpos, Integer.valueOf(map.get("Position")),map.get("Position"));
					}
						startpos = Integer.valueOf(map.get("Position")) + 1;
					
				}
			File file1 = new File(writefilename);
			if(file1.exists()){
				FileUtil.insertUtilClass(writefilename);
				FileUtil.rewritefile(writefilename,filepaths.get(i));
			}
			if(i == filepaths.size()-1) System.out.println("Insert Function complete！");
			
			
	}
	}
		public static void main(String[] args) throws IOException {
	
			String fileDir = "D:\\WorkSpace_Eclipse1\\Testmaven1";
//			String fileDir = "D:\\BIT\\FlakyTest\\ACMdata\\codes\\a";
			appendToFile(fileDir);

			}

		}
	
	

