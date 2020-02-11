package covergae;/**
 * Created by didi on 2019/6/12.
 */

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.comments.Comment;
import com.github.javaparser.ast.comments.LineComment;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @description:获取一个类的方法,存储方法名和方法md5值,但是有一个缺陷,如果newpath新增了2个同名方法不同参数呢?
 * 怎么倒退出是哪一个方法,使用md5解析吗?
 * @author: charlyne
 * @time: 2019/6/12 8:11 PM
 */


public  class CMethodsParser {
    final String strfield=new String("99");
    final int a=0;
    protected HashMap<String,String> MethodsMD5List=new HashMap<String,String>();

    public HashMap<String,String> MethodsMD5List(String file) throws FileNotFoundException {
        FileInputStream in=new FileInputStream(file);
        CompilationUnit cu=JavaParser.parse(in);
        //remove comments
        List<Comment> comments = cu.getAllContainedComments();
        List<Comment> unwantedComments = comments
                .stream()
                .filter(p -> !p.getCommentedNode().isPresent() || p instanceof LineComment)
                .collect(Collectors.toList());
        unwantedComments.forEach(Node::remove);
        cu.accept(new MethodVisitor(), null);

        return MethodsMD5List;
    }

    private  class MethodVisitor extends VoidVisitorAdapter<Void> {
        @Override
        public void visit(MethodDeclaration n, Void arg) {
            /* here you can access the attributes of the method.
             this method will be called for all methods in this
             CompilationUnit, including inner class methods */

            CMethodsParser.this.MethodsMD5List.put(n.getName().toString(),CMethodsParser.this.getMD5Value(n.toString()));
            super.visit(n, arg);
        }
    }
    public static String getMD5Value(String dataStr) {
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(dataStr.getBytes("UTF8"));
            byte s[] = m.digest();
            String result = "";
            for (int i = 0; i < s.length; i++) {
                result += Integer.toHexString((0x000000FF & s[i]) | 0xFFFFFF00).substring(6);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    public static String getMD5Value(String dataStr,int j) {
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(dataStr.getBytes("UTF8"));
            byte s[] = m.digest();
            String result = "";
            for (int i = 0; i < s.length; i++) {
                result += Integer.toHexString((0x000000FF & s[i]) | 0xFFFFFF00).substring(6);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    public static String getMD5Value(int j,String dataStr) {
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(dataStr.getBytes("UTF8"));
            byte s[] = m.digest();
            String result = "";
            for (int i = 0; i < s.length; i++) {
                result += Integer.toHexString((0x000000FF & s[i]) | 0xFFFFFF00).substring(6);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void main(String[] args) throws FileNotFoundException {
        String file="/Users/didi/IdeaProjects/rollsroyce/rollsroyce-resource/src/main/java/com/xiaoju/automarket/energy/rollsroyce/resource/transfer/FuelTransfer.java";
        CMethodsParser cMethodsParser=new CMethodsParser();
        HashMap map=cMethodsParser.MethodsMD5List(file);
        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();

        while(it.hasNext()){

            Map.Entry<String, String> entry = it.next();

            System.out.println(entry.getKey()+" : "+entry.getValue());

        }
        System.out.println("**************");
        String file2="/Users/didi/IdeaProjects/astonmartin/astonmartin-api/src/main/java/com/xiaoju/automarket/energy/astonmartin/api/covergae/JgitTest32.java";
        CMethodsParser cMethodsParser2=new CMethodsParser();
        HashMap map2=cMethodsParser.MethodsMD5List(file2);
        Iterator<Map.Entry<String, String>> it2 = map2.entrySet().iterator();

        while(it2.hasNext()){

            Map.Entry<String, String> entry = it2.next();

            System.out.println(entry.getKey()+" : "+entry.getValue());

        }

    }
}