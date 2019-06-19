package covergae;/**
 * Created by didi on 2019/6/15.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @description:
 * @author: charlyne
 * @time: 2019/6/15 8:03 PM
 */
public class JDiffMethods {
    public  ArrayList<HashMap> getDiffMethods(String oldBranchPath,String newBranchPath,String pathname,String ChangeType){
        //just for test
        ArrayList<HashMap> ll=null;
        if(ChangeType=="ADD"){
            // TODO: 2019/6/15 do one thing
           // return new Parser().getMethods(newBranchPath+pathname,true);


        }else if(ChangeType=="MODIFY"){
            // TODO: 2019/6/15 do another thing

        }
    //just for test
return ll;
    }
    public static void main(String[] args){
        String pathname="/Users/didi/IdeaProjects/difffilelist.txt";
        String branch_a="/Users/didi/IdeaProjects/astonmartin";
        String branch_b="/Users/didi/IdeaProjects/nouse/astonmartin";
        try(FileReader reader=new FileReader(pathname);
            BufferedReader br=new BufferedReader(reader)
        ){
            String line;
            while((line=br.readLine())!=null){
                //
            }
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}