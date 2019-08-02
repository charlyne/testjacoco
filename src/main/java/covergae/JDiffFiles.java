package covergae;

/**
 * @description:
 * @author: charlyne
 * @time: 2019/6/13 10:44 AM
 */

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JDiffFiles {
    private String gitFilePath;
    private Git git;
    private Repository repository;
    public JDiffFiles(String gitFilePath){
        this.gitFilePath=gitFilePath;
        try {
            this.git=Git.open(new File(gitFilePath+"/.git"));
            this.repository=git.getRepository();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // TODO 后面要实现只找出diffjava文件(要过滤掉非java文件,和测试代码)
    //a代表本地分支,b代表本地git的远程master分支,commit版本的比较,不是暂存区的比较
    public  ArrayList diffFiles(String oldBranch,String newBranch) throws IOException {
        ArrayList fileList=new ArrayList();
        ObjectId oldObjId=repository.resolve(oldBranch);
        ObjectId newObjId=repository.resolve(newBranch);
        AbstractTreeIterator oldTree=prepareTreeParser(oldObjId);
        AbstractTreeIterator newTree=prepareTreeParser(newObjId);
        try {
            List<DiffEntry> diff=git.diff()
                    .setNewTree(newTree)
                    .setOldTree(oldTree)
                    .setShowNameAndStatusOnly(true)
                    .call();
            for (DiffEntry diffEntry : diff) {
                if(diffEntry.getChangeType()== DiffEntry.ChangeType.DELETE)
                    continue;
                if(diffEntry.getChangeType()== DiffEntry.ChangeType.ADD||diffEntry.getChangeType()== DiffEntry.ChangeType.MODIFY){
                    if(diffEntry.getNewPath().endsWith(".java")) {
                        fileList.add(diffEntry.getNewPath());
                        //System.out.println(diffEntry.getNewPath());
                    }
                }
            }
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
        return fileList;
    }


    public HashMap<String,ArrayList> diffMethodsList(String newBranch) throws IOException {
        return diffMethodsList(newBranch,"origin/master");
    }

    public HashMap<String,ArrayList> diffMethodsList(String oldBranch,String newBranch)throws IOException{
        HashMap<String,ArrayList> map=new HashMap<String,ArrayList>();
        ObjectId oldObjId=repository.resolve(oldBranch);
        ObjectId newObjId=repository.resolve(newBranch);
        AbstractTreeIterator oldTree=prepareTreeParser(oldObjId);
        AbstractTreeIterator newTree=prepareTreeParser(newObjId);
        try {
            List<DiffEntry> diff=git.diff()
                    .setNewTree(newTree)
                    .setOldTree(oldTree)
                    .setShowNameAndStatusOnly(true)
                    .call();
            for (DiffEntry diffEntry : diff) {
                if(diffEntry.getChangeType()== DiffEntry.ChangeType.DELETE)
                    continue;
                if(diffEntry.getNewPath().endsWith(".java")) {
                    if(diffEntry.getChangeType()== DiffEntry.ChangeType.ADD){

                        // TODO: 2019/6/15 do one method 先看能不能跑通,先使用diffEntry.getNewPath()得到类文件,后面肯定要改
                        // TODO: 2019/6/15 感觉这里要存储两个oldGitPath和newGitPath
                        String newclassFile=diffEntry.getNewPath();
                        System.out.println(gitFilePath+"/"+diffEntry.getNewPath());
                        try {
                            Parser classParser=new Parser();
                            classParser.getMethods(gitFilePath+"/"+newclassFile);
                            if(!classParser.methodList.isEmpty()){
                                map.put(newclassFile,classParser.methodList);
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }else if(diffEntry.getChangeType()== DiffEntry.ChangeType.MODIFY){
                        // TODO: 2019/6/15 do another method 先看能不能跑通,先使用diffEntry.getNewPath()得到类文件,后面肯定要改
                        // TODO: 2019/6/15 感觉这里要存储两个oldGitPath和newGitPath

                        String newclassFile=diffEntry.getNewPath();
                        String oldclassFile="/Users/didi/IdeaProjects/nouse/testjacocofirstcommit/";
                        try {
                            Parser classParser1=new Parser();
                            classParser1.getMthodsMd5(gitFilePath+"/"+newclassFile);
                            HashMap<String,String> newMethodMd5=classParser1.methodMd5Map;
                            // TODO: 2019/6/15  因为我不清楚如果继续使用classParser会不会是的newMethodMd5也变了(变成了oldMethodMd5),引用赋值不太清楚
                            Parser p=new Parser();
                            p.getMthodsMd5(oldclassFile+diffEntry.getNewPath());
                            HashMap<String,String> oldMethodMd5=p.methodMd5Map;
                            ArrayList<String> tmplist=diffmethods(newMethodMd5,oldMethodMd5);
                            if(!tmplist.isEmpty()) {
                                map.put(newclassFile, tmplist);
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                    //fileList.add(diffEntry.getNewPath());
                   // System.out.println(diffEntry.getNewPath());
                }
            }
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
        //TODO
        System.out.println("");
        return map;
    }
    public ArrayList<String> diffmethods(HashMap<String,String> newMap,HashMap<String,String> oldMap){
        ArrayList<String> methodList=new ArrayList<>();
        if(newMap.isEmpty()||oldMap.isEmpty()){
            return null;
        }
        for(String key:newMap.keySet()){
            if(oldMap.containsKey(key)){
                continue;
            }
            methodList.add(newMap.get(key));
        }
       return methodList;
    }

    public  AbstractTreeIterator prepareTreeParser(AnyObjectId objectId) throws IOException {
        //看来walk是重点啊
        try{
            RevWalk walk=new RevWalk(repository);
           // RevCommit commit=walk.parseCommit(objectId);
           // RevTree tree=walk.parseTree(commit.getTree().getId());
            RevTree tree=walk.parseTree(objectId);
            CanonicalTreeParser oldTreeParser = new CanonicalTreeParser();
            try (ObjectReader oldReader = repository.newObjectReader()) {
                oldTreeParser.reset(oldReader, tree.getId());
            }
            walk.dispose();
            return oldTreeParser;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws IOException {
        /**
        String gitFilePath="/Users/didi/IdeaProjects/astonmartin";
        JDiffFiles jDiffFiles=new JDiffFiles(gitFilePath+"/.git");
        String oldBranch="origin/master";
        String newBranch="HEAD";
        // ArrayList list=jDiffFiles.diffFiles(oldBranch,newBranch);
        HashMap<String,ArrayList> diffClass=jDiffFiles.diffMethodsList(oldBranch,newBranch);
        System.out.println("ddd");

        */
        String gitFilePath="/Users/didi/Downloads/testjacoco";
        JDiffFiles jDiffFiles=new JDiffFiles(gitFilePath);
        String oldBranch="c9b3e9fb1ac44389b54d6894373164309acdc8de";

        String newBranch="2a13647c8a3e283ebc07cf283a2b53f497dc8c59";
        // ArrayList list=jDiffFiles.diffFiles(oldBranch,newBranch);
        HashMap<String,ArrayList> diffClass=jDiffFiles.diffMethodsList(oldBranch,newBranch);
        try {
            File writeName = new File("output.txt"); // 相对路径，如果没有则要建立一个新的output.txt文件
            writeName.createNewFile(); // 创建新文件,有同名的文件的话直接覆盖
            try (FileWriter writer = new FileWriter(writeName);
                 BufferedWriter out = new BufferedWriter(writer)
            ) {
                for(String classname:diffClass.keySet()){
                    out.write(classname+"="+diffClass.get(classname).toString().replace(" ","git")+"\r\n");

                }
                out.flush(); // 把缓存区内容压入文件
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("ddd");
    }

}


