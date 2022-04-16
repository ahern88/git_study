package io.github.ahern88.git;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.security.MessageDigest;

public class GitUtils {

    public static String getCurrentPath() {
        try {
            String currentPath = new java.io.File(".").getCanonicalPath();
            return currentPath;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) throws Exception{
        File file = new File("/Users/shzto/git/demo05/LocationPosition.java");
        byte[] bytes = FileUtils.readFileToByteArray(file);
        String content = new String(bytes);
        String type = "blob";
        int len = bytes.length;
        String temp = "%s %d\0%s";
        String str = String.format(temp, type, len, content);
        System.out.println(sha1(str));
    }

    public static String sha1(String original) {
        String mySignature;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA1");
            md.update(original.getBytes());
            byte[] digest = md.digest();

            StringBuffer hexstr = new StringBuffer();
            String shaHex = "";
            for (int i = 0; i < digest.length; i++) {
                shaHex = Integer.toHexString(digest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexstr.append(0);
                }
                hexstr.append(shaHex);
            }
            mySignature = hexstr.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return mySignature;
    }

}
