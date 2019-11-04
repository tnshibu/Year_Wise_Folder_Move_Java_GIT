package com.vypeensoft;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//----------------------------------------------------------------------------------------------------------------------------- 
public class Year_Wise_Folder_Move {
	private static List<String> sourceFileList = new ArrayList<String>(1000);
//----------------------------------------------------------------------------------------------------------------------------- 
	public static void main(String[] args) {
		try	{
			doIt(args);
		}catch (Throwable t){
			t.printStackTrace();
		}
	}
//----------------------------------------------------------------------------------------------------------------------------- 
	public static void doIt(String[] args) throws FileNotFoundException, IOException {
		String userCurrentDir = System.getProperty("user.dir");
		System.out.println("REM - userCurrentDir = "+userCurrentDir);
		sourceFileList = getFileListFromFolder(userCurrentDir);
		Pattern pattern = Pattern.compile("^(.*)(\\d{4})(.*)");
		for(int i=0;i<sourceFileList.size();i++) {
		  try {
			  String sourceFileFullPath = sourceFileList.get(i);
			  File sourceFile = new File(sourceFileFullPath);
			  File parentFolder = sourceFile.getParentFile();
			  String parentFolderName = parentFolder.getName();
			  String name = sourceFile.getName();

				Matcher matcher = pattern.matcher(name);
				if (matcher.find()) {
					int year = Integer.parseInt(matcher.group(2));
					if(parentFolderName.contains(year+"")) {
						//file is already in year named folder
					} else {
						System.out.println("name = "+name );
						System.out.println("year="+year);
						File newFolder = new File(parentFolder.getAbsolutePath() + "\\" + year);
						//System.out.println("newFolder="+newFolder);
						newFolder.mkdirs();
						File destFile = new File(newFolder.getAbsolutePath() + "\\" + name);
						System.out.println("destFile="+destFile);
						sourceFile.renameTo(destFile);
					}
				}
			  System.out.println("--------------------------------------------------");
		  } catch (Exception e) {
			  e.printStackTrace();
		  }
        }
	}
//----------------------------------------------------------------------------------------------------------------------------- 
  /******************************************************************************************/
    public static List<String> getFileListFromFolder(String sourcePath) {
        //System.out.println(sourcePath);
        File dir = new File(sourcePath);
        if(!dir.exists()) {
            return new ArrayList<String>();
        }
        List<String> fileTree = new ArrayList<String>(100);
        for (File entry : dir.listFiles()) {
            if (entry.isFile()) {
                //System.out.println(entry);
                fileTree.add(entry.getAbsolutePath());
            } else {
                try {
                    fileTree.addAll(getFileListFromFolder(entry.getAbsolutePath()));
                } catch (Exception e) {
                }
            }
        }
        return fileTree;
    }
  /******************************************************************************************/
}
