package org.bit.unit;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Tools {
	public static String javaversion = System.getProperty("java.version");
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
	public static  ArrayList<Map<String,String>> locate(String filepath,String pattern) throws IOException
	{
		ArrayList<Map<String,String>> maplist = new ArrayList<Map<String,String>>();
		ArrayList<String> lines = FileUtil.readFile(filepath);
		Pattern p;Matcher m;
		
		for(int i=0;i<lines.size();i++)
		{
			//ignore the comments and annotations
			if(lines.get(i).trim().startsWith("//")||lines.get(i).trim().startsWith("@")||lines.get(i).trim().startsWith("/*")||lines.get(i).trim().startsWith("*"))
				continue;
			String tmp="";
//			System.out.println(line);
			p = Pattern.compile(pattern);
			m = p.matcher(lines.get(i));
			if(m.find())
			{
//				System.out.println(line);
				String[] splitarray = lines.get(i).split("[\\.\\s;\\{]+");
				for(int r=0;r<splitarray.length;r++)
					System.out.println(splitarray[r]);
				for (int j=0;j<splitarray.length;j++)
				{
					
					if(splitarray[j].equals("="))
					{
						HashMap<String,String> map = new HashMap<String,String>();
						map.put("listname", splitarray[j-1]);
						map.put("objectname",splitarray[j+1]);
						for(int q=j+2;q<splitarray.length;q++)
							tmp = tmp + splitarray[q];
						map.put("methodname", tmp);
						map.put("Position", String.valueOf(i));
						System.out.println(map);
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

//	public static String findMainClassName(String filename) throws IOException
//	{
//		Pattern p;
//		Matcher m;
//		String pattern = "\\s*public\\s+class\\s+\\w+";
//		String mainclassname = "";
//		p = Pattern.compile(pattern);
//		ArrayList<String> lines = FileUtil.readFile(filename);
//		for(int j=0;j<lines.size();j++)
//		{
//			String line = lines.get(j);
//			m = p.matcher(line);
//			if(m.find())
//			{
////				System.out.println(line);
//				String[] tmp = line.split("\\s|\\{");
////				System.out.println(tmp);
//				for(int k=0;k<tmp.length;k++)
//				{
//					if(tmp[k].equals("class"))
//					{
//						mainclassname = tmp[tmp.length-1];
//						return mainclassname;
//					}
//				}
//				
//			}
//		}
//		return mainclassname;
//	}
//	public static ArrayList<String> findClassNames(String filename) throws IOException
//	{
//		Pattern p;
//		Matcher m;
//		String pattern = "\\s*\\w*\\s*class\\s+\\w+";
//		p = Pattern.compile(pattern);
//		ArrayList<String> classlist= new ArrayList<String>();
////		ArrayList<String> filepaths = getallfilepaths();
//		ArrayList<String> lines = FileUtil.readFile(filename);
//			for(int j=0;j<lines.size();j++)
//			{
//				String line = lines.get(j);
//				m = p.matcher(line);
//				if(m.find())
//				{
////					System.out.println(line);
//					String[] tmp = line.split("\\s|\\{");
////					System.out.println(tmp);
//					for(int k=0;k<tmp.length;k++)
//					{
//						if(tmp[k].equals("class"))
//						{
//							classlist.add(tmp[k+1]);
////							System.out.println(tmp[k+1]);
//						}
//					}
//					
//				}
////				String [] array = line.split("\\s|\\.");
////				if(array[0].equals("import"))
////				{
////					for(int i=1;i<array.length;i++)
////						if(array[i].equals("java"))
////				}
//				
//				
//			}
//			
//		
//		return classlist;
//		
//	}
	public static boolean IsClass(String objectname)
	{
				
				System.out.println(objectname);
				if(objectname.length()==0||objectname==null) return false;
				else
				{
					if(Character.isUpperCase(objectname.charAt(0)))
						return true;
					else
						return false;
//					for(int i=0;i<objectname.length();i++)
//					{
//						if(Character.isUpperCase(objectname.charAt(i)))
//						{
//							return true;
//						}						
//					}
//					return false;
					
				}				
		
	}
//	public static  ArrayList<Map<String,String>> IsClass(ArrayList<Map<String,String>> maplist)
//	{
//		ArrayList<Map<String,String>> newlist = new ArrayList<Map<String,String>>();
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
	public static boolean IsJavaApI(Class<?>classname) throws IOException
	{
//		System.out.println(maplist.size());
		if(classname.getClassLoader()==null)
			return true;
		else
			return false;
		
		
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

	public static String generateResult(Object obj)
	{

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
	
	public static void Run(String filepath,String writename,Object obj,Class<?> classname) throws IOException
	{
			String writefilename="";
//			ArrayList<String> classlist = findClassNames(filepath);
//			System.out.println(classlist);
		
//			boolean isjavaapi = Tools.IsJavaApI(classname);
			String[] tmp = classname.toString().split("\\s");
			String realclassname = tmp[tmp.length-1];
			String []resultfilepaths = filepath.split("\\\\");
			String resultfilepath = "";
			for(int i=0;i<resultfilepaths.length-1;i++)
			{
				resultfilepath = resultfilepath + resultfilepaths[i] + "\\\\";
			}
			resultfilepath = resultfilepath + "results\\\\" + resultfilepaths[resultfilepaths.length-1];
//			if(isjavaapi)
//			{
				String result = generateResult(obj);
				writefilename=resultfilepath+"_"+writename.split("\\_")[0]+"_"+realclassname+"_"+writename.split("\\_")[1]+"_"+javaversion+".txt";
//				System.out.println(writefilename);
				FileUtil.writeFile(writefilename, result);
//			}
				
		
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
