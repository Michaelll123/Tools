package util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class AppendToFile {
	//插装函数：实现的功能，读取用户输入目录中所有java文件并在其下一行进行插装。（注意 修改main函数中的文件夹目录）
	public static void appendToFile(String fileDir) throws IOException
	{
		// 正则表达式，筛选出形似 Set set = map1.values();的代码行
//		String pattern = "\\w*\\s*\\w+\\s*=\\s*\\w+\\.\\w+\\(\\w*\\)";
		String pattern = "[\\w\\s]*\\w+\\s*=\\s*\\w+\\.(\\w+\\([\\w\\s\\(\\),:]*\\))+";
		ArrayList<String> filenames =new ArrayList<String>();
		TwoFileIsSame.getAllJavaFileName(fileDir, filenames);
		ArrayList<String> filepaths = filenames;
//		String filepath = args[0];
//		for(int i=0;i<filenames.size();i++)
//			filepaths.add(fileDir + "\\\\" + filenames.get(i));
		for (int i = 0; i < filepaths.size(); i++) {
			int startpos = 0;
			ArrayList<String> fileContent = FileUtil.readFile(filepaths.get(i));
			int size = fileContent.size();
//			System.out.println(size-1);
			// 对每个java文件定位到有函数调用且有返回值接受的那些代码行，拿到该行中接受返回值的对象，调用API的对象，行数以及调用的函数名称
			ArrayList<Map<String, String>> maplist = Tools.locate(filepaths.get(i), pattern);
			
			String initialpath = filepaths.get(i).split("\\.")[0];
			String path = "";
			for(int k=0;k<initialpath.split("\\\\").length-1;k++)
				path= path + initialpath.split("\\\\")[k] + "\\";
			path = path + initialpath.split("\\\\")[initialpath.split("\\\\").length-1];
			String writefilename = path + "edited.java";
			System.out.println(writefilename);
			File file = new File(writefilename);
			
	    	   if (file.exists()) 
	    	   {
//	    		   System.out.println("文件存在！");
	     		   FileWriter fileWriter =new FileWriter(file);
	                fileWriter.write("");
	                fileWriter.flush();
	                fileWriter.close();
	    	   }
//	    	   System.out.println(newlist.size());
//	    	   ArrayList<Map<String, String>> newlist = Tools.IsClass(maplist);
			// 对每个map对应的代码行进行代码插装
			for (int j = 0; j < maplist.size(); j++) {	
//				System.out.println("1");
				Map<String, String> map = maplist.get(j);
//				System.out.println(map);
				// 在每个符合正则表达式并且是JAVAAPI调用的代码行之后的一行写入通用函数
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
			
			
	}
	}
		public static void main(String[] args) throws IOException {
			//输入需要插装的项目所在上一级路径。
			String fileDir = "D:\\WorkSpace_Eclipse1\\TestMaven";
//			String fileDir = "D:\\WorkSpace_Eclipse1\\TestMaven\\calcite\\core\\src\\main\\java\\org\\apache\\calcite\\tools";
			appendToFile(fileDir);

			}

		}
	
	

