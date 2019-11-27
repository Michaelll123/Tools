package util;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
public class TwoFileIsSame {
	//�ж�Java7&8�����ļ��������Ƿ�һ�£����һ����ɾ���ļ�
	public static void main(String [] args) throws IOException
	{
		ArrayList<String> filenamelist = new ArrayList<String>();
		String fileDir = "D:\\WorkSpace_Eclipse1";
		getAllFileName(fileDir,filenamelist);
		
//		DeleteSameFile(fileDir,filenamelist);
		
	}
	//ɾ���ļ�
	public static boolean Deletefile(String filename)
	{
		boolean del = false;
		File file = new File(filename);
		if(file.exists())
			 del = file.delete();
		else 
			System.out.println("�ļ�������");
		
		return del;
	}
	//��java7&8������ͬ���ļ�ɾ��
	public static void DeleteSameFile(String fileDir,ArrayList<String> filenamelist) throws IOException
	{
		HashMap<String,String> map = new HashMap<String,String>();
		for(int i=0;i<filenamelist.size();i++)
		{
			String value="";
			String filenameprefixs[] = filenamelist.get(i).split("\\_");
			String key ="";
			for(int k=0;k<filenameprefixs.length-1;k++)
			{
				key = key + filenameprefixs[k]+"_";
			}
			ArrayList<String> filecontent = readFile(fileDir + "\\\\" + filenamelist.get(i));
			for(int j=0;j<filecontent.size();j++)value = value + filecontent.get(j);
			System.out.println("key:" + key +"\nvalue:"+value);
			
			if( !map.containsKey(key))
			{
				map.put(key, value);
				System.out.println("Insert");
			}				
			else
			{
				System.out.println(map.get(key).toString()+"\n"+value.toString());
				if((map.get(key)).equals(value))
				{
					File file7 = new File(fileDir+"\\\\"+key+"java7.txt");
					File file8 = new File(fileDir+"\\\\"+key+"java8.txt");
//					System.out.println(file7.getName());
					if(file7.delete()) System.out.println("ɾ��"+file7.getName());
					if(file8.delete()) System.out.println("ɾ��"+file8.getName());	
				}
				
			}
		}
	}
	//���ļ�
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
	//�õ�Ŀ¼�����е�java�ļ��ľ���·��
	public static void getAllJavaFileName(String path,ArrayList<String> fileNameList) {
        //ArrayList<String> files = new ArrayList<String>();
//        boolean flag = false;
        File file = new File(path);
        File[] tempList = file.listFiles();

        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
//              System.out.println("��     ����" + tempList[i]);
                //fileNameList.add(tempList[i].toString());
              String []array = tempList[i].getAbsolutePath().split("\\.");
              String suffix = array[array.length-1];
//              System.out.println(suffix);
              if(suffix.equals("java"))
                {fileNameList.add(tempList[i].getAbsolutePath());
              System.out.println(tempList[i].getAbsolutePath());
                }
              }
            if (tempList[i].isDirectory()) {
//              System.out.println("�ļ��У�" + tempList[i]);
            	if(!tempList[i].getName().equals("test"))
                getAllJavaFileName(tempList[i].getAbsolutePath(),fileNameList);
            }
        }

     
    }
	//�õ�Ŀ¼�������ļ�����
    public static void getAllFileName(String path,ArrayList<String> fileNameList) {
        //ArrayList<String> files = new ArrayList<String>();
//        boolean flag = false;
        File file = new File(path);
        File[] tempList = file.listFiles();

        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
//              System.out.println("��     ����" + tempList[i]);
                //fileNameList.add(tempList[i].toString());
                fileNameList.add(tempList[i].getName());
            }
            if (tempList[i].isDirectory()) {
//              System.out.println("�ļ��У�" + tempList[i]);
                getAllFileName(tempList[i].getAbsolutePath(),fileNameList);
            }
        }
     
    }

}
