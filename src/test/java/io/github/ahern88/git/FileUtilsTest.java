package io.github.ahern88.git;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.net.URL;

public class FileUtilsTest {
    @Test
    public void copyPath() throws Exception{
        File gitPathFile = new File(GitUtils.getCurrentPath(), ".git");
        try {
            if (!gitPathFile.exists()) {
                gitPathFile.mkdir();
            } else {
                System.err.println("This directory is realy a git repo");
                return;
            }
            URL gitURL = this.getClass().getClassLoader().getResource("git_template");
            File sourceFile = new File(gitURL.getFile());
            FileUtils.copyDirectory(sourceFile, gitPathFile);
        } catch (Exception e) {
            gitPathFile.deleteOnExit();
            e.printStackTrace();
        }
    }
}
