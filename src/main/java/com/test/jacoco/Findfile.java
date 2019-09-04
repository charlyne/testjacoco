package com.test.jacoco;

import java.io.File;

/**
 * @description:
 * @author: charlyne
 * @time: 2019/6/24 5:26 PM
 */
public class Findfile {
    public static void main(String[] args){
        Findfile findfile=new Findfile();
        System.out.println(findfile.findFile("/Users/didi/Downloads/testjacoco","index.html","output.txt"));
    }

    /**
     * 寻找pom.xml和jacoco.exec和difffile.txt,默认这三个文件应该在项目的根目录,三个文件同时存在才能继续进行
     * @param projectDir
     * @param execfilename
     * @param difffilename
     * @return
     */
    public  boolean findFile(String projectDir,String execfilename,String difffilename){
        int cout=0;
        File dir=new File(projectDir);
        if(dir.exists()){
            File[] files=dir.listFiles();
            for(File file:files){
                if(file.isDirectory()){
                    continue;
                }
                // TODO: 2019/6/24  difffilename和execfilename是不是要写成路径啊,绝对路径?
                String filename=file.getName();
               if(filename.equals("pom.xml")||filename.equals(execfilename)||filename.equals(difffilename)){
                   cout=cout+1;
               }
            }
        }

        if(cout==3)return true;
        return false;
    }

    public  boolean findFile1(String projectDir,String execfilename,String difffilename){
        int cout=0;
        File dir=new File(projectDir);
        if(dir.exists()){
            File[] files=dir.listFiles();
            for(File file:files){
                if(file.isDirectory()){
                    continue;
                }
                // TODO: 2019/6/24  difffilename和execfilename是不是要写成路径啊,绝对路径?
                String filename=file.getName();
                if(filename.equals("pom.xml")||filename.equals(execfilename)||filename.equals(difffilename)){
                    cout=cout+1;
                }
            }
        }
        if(cout==3)return true;
        return false;
    }
}