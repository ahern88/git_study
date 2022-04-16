package io.github.ahern88.git;

import org.apache.commons.io.FileUtils;

import java.io.File;

public class Git {

    public static void main(String[] args) throws Exception{
        String currentPath = GitUtils.getCurrentPath();
        if (args == null || args.length == 0) {
            System.err.println("using init or clone command init your repo");
        } else {
            String command = args[0];
            if ("init".equals(command)) {
                if (args.length == 1) {
                    init(null);
                } else if (args.length == 2) {
                    String arg = args[1];
                    init(arg);
                } else {
                    System.err.println("no support more than 2 args");
                }
            }
            if ("status".equals(command)) {

            }
        }
    }

    public static void init(String path) {
        File gitPathFile = null;
        if (path == null) {
            gitPathFile = new File(GitUtils.getCurrentPath(), ".git");
        } else {
            gitPathFile = new File(GitUtils.getCurrentPath(), path + "/.git");
        }
        try {
            if (!gitPathFile.exists()) {
                gitPathFile.mkdir();
            } else {
                System.err.println("This directory is really a git repo");
                return;
            }
            FileUtils.copyDirectory(new File("/tmp/git_template"), gitPathFile);
            System.out.println("已初始化空Git仓库，位于 " + gitPathFile.getPath());
        } catch (Exception e) {
            gitPathFile.deleteOnExit();
            e.printStackTrace();
        }
    }


}
