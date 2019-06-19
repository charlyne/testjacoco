package covergae;

//from https://blog.csdn.net/u012621115/article/details/52171679

import junit.framework.TestCase;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.diff.*;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.patch.FileHeader;
import org.eclipse.jgit.patch.HunkHeader;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href= "mailto:" style="color:##E0E;">牵手无奈</a>
 * @date 2016年8月9日 下午3:08:35
 * @version V1.0
 */
public class JgitTest extends TestCase {

    String gitFilePath = "/Users/didi/IdeaProjects/astonmartin";
    File root = new File(gitFilePath);
    Git git;
    Repository repository;

    @Before
    public void init() {
        try {
            git = Git.open(root);
            repository = git.getRepository();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test3() throws Exception {
        init();
        RevWalk walk = new RevWalk(repository);
        List<RevCommit> commitList = new ArrayList<>();
        //获取最近提交的两次记录
        Iterable<RevCommit> commits = git.log().setMaxCount(2).call();
        for (RevCommit commit : commits) {
            commitList.add(commit);
            System.out.println(commit.getFullMessage());
            System.out.println(commit.getAuthorIdent().getWhen());
        }
        if (commitList.size() == 2) {
            AbstractTreeIterator newTree = prepareTreeParser(commitList.get(0));
            AbstractTreeIterator oldTree = prepareTreeParser(commitList.get(1));
            //这里获取的是diffclass
           //List<DiffEntry> diff = git.diff().setOldTree(oldTree).setNewTree(newTree).setShowNameAndStatusOnly(true).call();
            List<DiffEntry> diff = git.diff()
                    .setOldTree(oldTree)
                    .setNewTree(newTree)
                    .setShowNameAndStatusOnly(true)
                    //.setPathFilter(PathSuffixFilter.create(".java"))
                    //这个后缀好像没有生效,因为有这一句之后,debug模式里面还是有pom.xml文件
                    .call();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            DiffFormatter df = new DiffFormatter(out);//df.out=out
            //设置比较器为忽略空白字符对比（Ignores all whitespace）
            df.setDiffComparator(RawTextComparator.WS_IGNORE_ALL);
            df.setRepository(git.getRepository());
            System.out.println("------------------------------start-----------------------------");
            //每一个diffEntry都是第个文件版本之间的变动差异
            for (DiffEntry diffEntry : diff) {
                //打印文件差异具体内容
                df.format(diffEntry);
                String diffText = out.toString("UTF-8");
                System.out.println("&&&&&");
                System.out.println(diffEntry.getChangeType());
                System.out.println(diffText);
                System.out.println("&&&&&&&&&&&");
                //获取文件差异位置，从而统计差异的行数，如增加行数，减少行数
                FileHeader fileHeader = df.toFileHeader(diffEntry);
                List<HunkHeader> hunks = (List<HunkHeader>) fileHeader.getHunks();
                int addSize = 0;
                int subSize = 0;
                for (HunkHeader hunkHeader : hunks) {
                    EditList editList = hunkHeader.toEditList();
                    for (Edit edit : editList) {
                        subSize += edit.getEndA() - edit.getBeginA();
                        addSize += edit.getEndB() - edit.getBeginB();

                    }
                }
                System.out.println("addSize=" + addSize);
                System.out.println("subSize=" + subSize);
                System.out.println("------------------------------end-----------------------------");
                out.reset();
            }

        }

    }

    public AbstractTreeIterator prepareTreeParser(RevCommit commit) {
        System.out.println(commit.getId());
        RevWalk walk = new RevWalk(repository);
        try  {

            System.out.println(commit.getTree().getId());
            RevTree tree = walk.parseTree(commit.getTree().getId());
            CanonicalTreeParser oldTreeParser = new CanonicalTreeParser();
            ObjectReader oldReader = repository.newObjectReader()
            try {
                oldTreeParser.reset(oldReader, tree.getId());
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                oldReader.close();
            }
            walk.dispose();
            return oldTreeParser;
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        }finally {
            walk.close();
        }
        return null;
    }

}
