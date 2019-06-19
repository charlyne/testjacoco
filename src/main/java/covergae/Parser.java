package covergae;

//from https://blog.csdn.net/c5113620/article/details/80479859


import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.comments.Comment;
import com.github.javaparser.ast.comments.LineComment;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Parser {
    public   ArrayList<String> methodList;
    public HashMap<String,String> methodMd5Map;


    public static void main(String[] args) throws Exception {
        String file="/Users/didi/IdeaProjects/astonmartin/astonmartin-api/src/main/java/com/xiaoju/automarket/energy/astonmartin/api/covergae/CMethodsParser.java";
        Parser parser=new Parser();
        parser.getMethods(file);
        System.out.println(parser.methodList);
        System.out.println();
    }


    public  void getMethods(String classFile) throws Exception{
        methodList=new ArrayList<>();
        FileInputStream in = new FileInputStream(classFile);
        // parse the file
        CompilationUnit cu = JavaParser.parse(in);
        //去掉注释,from https://github.com/javaparser/javaparser-visited/blob/master/src/main/java/org/javaparser/samples/CommentRemover.java
        //另外好像不需要去掉空行,从打印出来的方法来看已经去掉了空行
        List<Comment> comments = cu.getAllContainedComments();
        List<Comment> unwantedComments = comments
                .stream()
                .filter(p -> !p.getCommentedNode().isPresent() || p instanceof LineComment)
                .collect(Collectors.toList());
        unwantedComments.forEach(Node::remove);
        // prints the resulting compilation unit to default system output
//        System.out.println(cu.toString());

        // 这一句好像等价于了MethodVisitor.visit(cu,null)
        cu.accept(new MethodVisitor(), null);

        //下面这一句用来返回类的方法集合
        tt();

    }
    public void getMthodsMd5(String classFile)throws Exception{
        methodMd5Map=new HashMap<String,String>();
        FileInputStream in = new FileInputStream(classFile);
        CompilationUnit cu = JavaParser.parse(in);
        List<Comment> comments = cu.getAllContainedComments();
        List<Comment> unwantedComments = comments
                .stream()
                .filter(p -> !p.getCommentedNode().isPresent() || p instanceof LineComment)
                .collect(Collectors.toList());
        unwantedComments.forEach(Node::remove);
        cu.accept(new MethodMd5Visitor(), null);
        //下面这一句用来返回类的方法集合带md5
        mm();

    }


    private  class MethodVisitor extends VoidVisitorAdapter<Void> {
        // VoidVisitorAdapter本身就有visit(MethodDeclaration,A)或者(ClassOrInterfaceDeclaration n, Void arg),这里
        //是为了实现本例(打印方法等),所以才override了一些方法
        @Override
        public void visit(MethodDeclaration n, Void arg) {
            /* here you can access the attributes of the method.
             this method will be called for all methods in this
             CompilationUnit, including inner class methods */
            methodList.add(n.getName().toString());
            super.visit(n, arg);
        }
/**
        @Override
        public void visit(ClassOrInterfaceDeclaration n, Void arg) {
            System.out.println("class:"+n.getName());
            System.out.println("extends:"+n.getExtendedTypes());
            System.out.println("implements:"+n.getImplementedTypes());
            super.visit(n, arg);
        }

        @Override
        public void visit(PackageDeclaration n, Void arg) {
            System.out.println("package:"+n.getName());
            super.visit(n, arg);
        }
        */
    }
    private  class MethodMd5Visitor extends VoidVisitorAdapter<Void> {
        @Override
        public void visit(MethodDeclaration n, Void arg) {
            System.out.println("method:"+n.getName());
            System.out.println(n.toString());
            String md5="";
            //n.toString是方法体
            md5=MyMD5Util.getMD5Value(n.toString());
            //以md5作为存储key,方法名字作为value,万一类包含多个重名函数呢?
            methodMd5Map.put(md5,n.getName().toString());
            super.visit(n, arg);
        }

    }


    private ArrayList<String> tt(){return methodList;}
    private HashMap<String,String> mm(){return methodMd5Map;}

}
