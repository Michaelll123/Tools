package util;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Tools {
	//工具类
	public static String javaversion = "java8";
//	public static String javaversion = "java7";
//	public static ArrayList<String> getallfilepaths()
//	{
//		ArrayList<String> filepaths = new ArrayList<String>();
////		for(int i=2;i<32307;i++)
//		for(int i=11;i<=20;i++)
//		{
//			String filepath= "D:\\BIT\\FlakyTest\\ACMdata\\codes\\"+i+".java";
//			filepaths.add(filepath);
//		}
//		return filepaths;
//	}
	//定位函数，对传入文件的每一行，判断是否满足pattern给出的正则表达式;
	//如果满足，返回一个类型为Map的数组，存储：行号，调用变量，返回变量，方法名称信息。
	public static  ArrayList<Map<String,String>> locate(String filepath,String pattern) throws IOException
	{
		ArrayList<Map<String,String>> maplist = new ArrayList<Map<String,String>>();
		ArrayList<String> lines = FileUtil.readFile(filepath);
		Pattern p;Matcher m;
		for(int i=0;i<lines.size();i++)
		{
//			System.out.println(line);
			p = Pattern.compile(pattern);
			m = p.matcher(lines.get(i));
			if(m.find())
			{
//				System.out.println(line);
				String[] splitarray = lines.get(i).split("[\\.\\s;\\{,]+");
				for (int j=0;j<splitarray.length;j++)
				{
					System.out.println(splitarray[j]);
					if(splitarray[j].equals("="))
					{
						HashMap<String,String> map = new HashMap<String,String>();
						map.put("listname", splitarray[j-1]);
						map.put("objectname",splitarray[j+1]);
						map.put("methodname", splitarray[splitarray.length-1]);
						map.put("Position", String.valueOf(i));
//						System.out.println(map);
						maplist.add(map);
						break;
					}
//					System.out.println(words);
				}
			}
//			System.out.println(line);
		}
		
//		System.out.println(maplist);
		return maplist;
	}
//	public static String findObjectClass(String filepath, String objectname) throws IOException
//	{
//		ArrayList<String> lines = FileUtil.readFile(filepath);
//		Pattern p;
//		Matcher m;
//		for(String line : lines)
//		{
////			System.out.println(line);
//			String pattern = "\\w*\\s*"+objectname+"\\s*\\w*"+"|\\w+\\s*"+objectname;
//			System.out.println(pattern);
//			p = Pattern.compile(pattern);
//			m = p.matcher(line);
//			if(m.lookingAt())
//			{
//				String[] splitarray = line.split("\\s");
//				return splitarray[0];
//			}
//			
//		
//	}
//		return null;
//	}
	//找到java文件的主类名
	public static String findMainClassName(String filename) throws IOException
	{
		Pattern p;
		Matcher m;
		String pattern = "\\s*public\\s+class\\s+\\w+";
		String mainclassname = "";
		p = Pattern.compile(pattern);
		ArrayList<String> lines = FileUtil.readFile(filename);
		for(int j=0;j<lines.size();j++)
		{
			String line = lines.get(j);
			m = p.matcher(line);
			if(m.find())
			{
//				System.out.println(line);
				String[] tmp = line.split("\\s|\\{");
//				System.out.println(tmp);
				for(int k=0;k<tmp.length;k++)
				{
					if(tmp[k].equals("class"))
					{
						mainclassname = tmp[tmp.length-1];
						return mainclassname;
					}
				}
				
			}
		}
		return mainclassname;
	}
	//找出java文件中自定义的所有类名称，用于判断是否是javaAPI的调用
	//（只要调用方法的对象的类型名字不在这个返回的列表中，则认为是JavaAPI调用）
	public static ArrayList<String> findClassNames(String filename) throws IOException
	{
		Pattern p;
		Matcher m;
		String pattern = "\\s*\\w*\\s*class\\s+\\w+";
		p = Pattern.compile(pattern);
		ArrayList<String> classlist= new ArrayList<String>();
//		ArrayList<String> filepaths = getallfilepaths();
		ArrayList<String> lines = FileUtil.readFile(filename);
			for(int j=0;j<lines.size();j++)
			{
				String line = lines.get(j);
				m = p.matcher(line);
				if(m.find())
				{
//					System.out.println(line);
					String[] tmp = line.split("\\s|\\{");
//					System.out.println(tmp);
					for(int k=0;k<tmp.length;k++)
					{
						if(tmp[k].equals("class"))
						{
							classlist.add(tmp[k+1]);
//							System.out.println(tmp[k+1]);
						}
					}
					
				}
//				String [] array = line.split("\\s|\\.");
//				if(array[0].equals("import"))
//				{
//					for(int i=1;i<array.length;i++)
//						if(array[i].equals("java"))
//				}
				
				
			}
			
		
		return classlist;
		
	}
	//区别直接通过类名调用的静态方法和通过对象调用的方法。
	public static boolean IsClass(String objectname)
	{
			
				System.out.println(objectname);
				if(objectname.length()==1||objectname.length()==0||objectname==null) return false;
				else
				{
					boolean firstletter = Character.isUpperCase(objectname.charAt(0));
					boolean otherletter = true;
					for(int i=1;i<objectname.length();i++)
						if(Character.isUpperCase(objectname.charAt(i)))
							otherletter = false;
					if(firstletter&&otherletter)
						return true;
					else 
						return false;
				}				
		
	}
//	//暂时筛除直接通过类名调用的静态方法。
//	public static  ArrayList<Map<String,String>> IsClass(ArrayList<Map<String,String>> maplist)
//	{
//		ArrayList<Map<String,String>> newlist = new ArrayList<Map<String,String>>();
//		//将map中直接通过类调用API的行删掉。
//		for(int i=0;i<maplist.size();i++)
//		{
//			Map<String,String>map = maplist.get(i);
//			String objectname = map.get("objectname");
//			if((objectname!="")&&(objectname!=null))
//			{
//				System.out.println(objectname);
//				System.out.println(map);
//				if (Character.isLowerCase(objectname.charAt(0))&&(!objectname.equals("this")))
//				{
//					
//					newlist.add(map);
//				}
//			}
//			
//		}
//		return newlist;
//		
//	}
	//判断是否是JavaAPI调用
	public static boolean IsJavaApI(String classname,ArrayList<String> classlist) throws IOException
	{
//		System.out.println(maplist.size());

			String[] tmp = classname.split("\\s");
			String realclassname = tmp[tmp.length-1];
			for(int j=0;j<classlist.size();j++)
			{				
				System.out.println("objectclassname:"+realclassname);
				System.out.println("classnames:"+classlist.get(j));
				if(realclassname.equals(classlist.get(j)))
					return false;		
			}
			return true;
		
		
//		System.out.println(maplist.size());
	}
//	public static void IsJavaApI(ArrayList<Map<String,String>> maplist,String classname,ArrayList<String> classlist) throws IOException
//	{
//		System.out.println(maplist.size());
//		for(int i=0;i<maplist.size();i++)
//		{
//			Map<String,String>map = maplist.get(i);
//			String[] tmp = classname.split("\\s");
//			String realclassname = tmp[tmp.length-1];
//			for(int j=0;j<classlist.size();j++)
//			{				
//				System.out.println("objectclassname:"+realclassname);
//				System.out.println("classnames:"+classlist.get(j));
//				if(realclassname.equals(classlist.get(j)))
//				System.out.println("It is not a java API invacation");
//				else
//					maplist.remove(map);
//			}
//			
//		}
//		
//		System.out.println(maplist.size());
//		
//		
//		
//		
//		
//		
////		for(Map<String,String> map : maplist)
////		{
////			String objectname = map.get("objectname");
//////			System.out.println(objectname);
////			String objectclass = findObjectClass(filepath,objectname);
////			System.out.println(objectclass);
//////			String[] classnames = findClassNames(filepath);
//////			for(String classname : classnames)
//////			{
//////				if(classname.equals(objectclass))
//////				{
//////					maplist.remove(map);
//////				}
//////			}		
////		}
//		
//	}
	//获取返回对象的所有内容
	public static String generateResult(Object obj)
	{
		//将返回值集合写入名字为Id_ClassName_MethodName_JavaVersion的文本文件中
		String result="";
		System.out.println(obj.toString());
		if(obj instanceof Collection)
		{
			Object[] list = ((Collection)obj).toArray();
			for (int i=0;i<list.length;i++)
			{
				result = result + String.valueOf(i+1) + "\t" + list[i] + "\n";
			}
//			System.out.println(result);
		}
		else
			result = result + obj.toString();
		
		return result;
	}
	//总调用函数
	public static void Run(String filepath,String writename,Object obj,String classname) throws IOException
	{
//function1:筛选出是JAVAAPI调用的那些map,把不是JAVAAPI调用的那些剔除掉
//function2:筛选出是返回值是集合的那些map,把返回值不是集合的那些剔除掉
//function3:将对象的结果写入文件。
		//正则表达式，筛选出形似 Set set = map1.values();的代码行
			String writefilename="";
			ArrayList<String> classlist = findClassNames(filepath);
//			System.out.println(classlist);
			//筛选出是JAVAAPI调用的那些map,把不是JAVAAPI调用的那些剔除掉
			boolean isjavaapi = Tools.IsJavaApI(classname,classlist);
			String[] tmp = classname.split("\\s");
			String realclassname = tmp[tmp.length-1];
			String []resultfilepaths = filepath.split("\\\\");
			String resultfilepath = "";
			for(int i=0;i<resultfilepaths.length-1;i++)
			{
				resultfilepath = resultfilepath + resultfilepaths[i] + "\\\\";
			}
			resultfilepath = resultfilepath + "results\\\\" + resultfilepaths[resultfilepaths.length-1];
			if(isjavaapi)
			{
				String result = generateResult(obj);
				writefilename=resultfilepath+"_"+writename.split("\\_")[0]+"_"+realclassname+"_"+writename.split("\\_")[1]+"_"+javaversion+".txt";
//				System.out.println(writefilename);
				FileUtil.writeFile(writefilename, result);
			}
				
		
//		String writefilepath = null;
//
//
//			String result = generateResult(obj);
//			FileUtil.writeFile(writefilepath, result);
	}
//	public static void main(String[] args) throws IOException
//	{
//		String pattern = "\\w*\\s*\\w+\\s*=\\s*\\w+\\.\\w+\\(";
//		ArrayList<String> filepaths = getallfilepaths();
//		ArrayList<Map<String,String>> maplist = Tools.locate(filepaths.get(i), pattern);
//		for(int i=0;i<filepaths.size();i++)
//		{
//			Tools.IsClass(maplist, classname, classlist);	
//		}
		 

//	}
	
}
