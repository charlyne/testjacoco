package covergae;

import java.security.MessageDigest;
//from https://blog.csdn.net/junmoxi/article/details/80841555

/**
 * MD5加密工具类
 *
 */
public class MyMD5Util {
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
    public static void main(String[] args){
        MyMD5Util myMD5Util=new MyMD5Util();
        System.out.println(myMD5Util.getMD5Value("hhhhhh"));
        System.out.println(myMD5Util.getMD5Value("hhhhhh"));
    }

}
