package covergae;

import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @description:
 * @author: charlyne
 * @time: 2019/6/28 7:21 PM
 */
public class Nouse {
    public static List<List<Integer>> threeSum(int[] nums) {
        //去重复，要先排序
        Arrays.sort(nums);
        System.out.println(Arrays.toString(nums));
        List<List<Integer>> list=new ArrayList();
        for(int i=0;i<nums.length-2&&nums[i]<=0;i++){
            if(i>=1){
                if(nums[i]==nums[i-1]){
                    continue;
                }
            }
            for(int j=i+1;j<nums.length-1;j++){
                if(j-1>i){
                    if(nums[j]==nums[j-1])
                        continue;
                }
                for(int k=j+1;k<nums.length;k++){
                    if(k-1>j){
                        if(nums[k]==nums[k-1]){
                            continue;
                        }
                    }
                    if(nums[i]+nums[j]+nums[k]==0){
                        //输入值,如何去重
                        List<Integer> list1=new ArrayList();
                        list1.add(nums[i]);
                        list1.add(nums[j]);
                        list1.add(nums[k]);
                        list.add(list1);
                        System.out.println(StringUtils.join(list1.toArray(), ","));
                    }
                }
            }

        }

        return list;

    }
    public static int[] sort(int[] nums){
        for(int i=0;i<nums.length-1;i++){
            int min=i;
            for(int j=i+1;j<nums.length;j++){
                if(nums[min]>nums[j]){
                    min=j;
                }
                int tmp=nums[i];
                nums[i]=nums[min];
                nums[min]=tmp;


            }
        }
        return nums;

    }
    public static boolean isPalindrome(int x) {
        String str=Integer.toString(x);
        for(int i=0,j=str.length()-1;i<j;i++,j--){
            if(str.charAt(i)==(str.charAt(j))){
                return false;
            }

        }
        return true;
    }
    public static void main(String[] args) throws FileNotFoundException,IOException {

        Nouse nouse=new Nouse();
        long starttime=System.currentTimeMillis();
        nouse.copyDir("/Users/didi/IdeaProjects/rollsroyce","/Users/didi/IdeaProjects/rollsroyce99");
        long endtime=System.currentTimeMillis();
        System.out.println(endtime-starttime);


    }
    public  void copyDir(String oldPath, String newPath) throws IOException {
        File file = new File(oldPath);
        //文件名称列表
        String[] filePath = file.list();

        if (!(new File(newPath)).exists()) {
            (new File(newPath)).mkdir();
        }

        for (int i = 0; i < filePath.length; i++) {
            if ((new File(oldPath + file.separator + filePath[i])).isDirectory()) {
                copyDir(oldPath  + file.separator  + filePath[i], newPath  + file.separator + filePath[i]);
            }

            if (new File(oldPath  + file.separator + filePath[i]).isFile()) {
                copyFile(oldPath + file.separator + filePath[i], newPath + file.separator + filePath[i]);
            }

        }
    }
    public  void copyFile(String oldPath, String newPath) throws IOException {
        File oldFile = new File(oldPath);
        File file = new File(newPath);
        FileInputStream in = new FileInputStream(oldFile);
        FileOutputStream out = new FileOutputStream(file);;

        byte[] buffer=new byte[2097152];

        while((in.read(buffer)) != -1){
            out.write(buffer);
        }
    }
}