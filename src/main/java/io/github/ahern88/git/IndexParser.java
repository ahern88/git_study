package io.github.ahern88.git;

import org.apache.commons.io.FileUtils;

import java.io.File;

/**
 * 参考：https://git-scm.com/docs/index-format
 */
public class IndexParser {

    private static final String index_file = "/Users/shzto/git/git/.git/index";

    /**
     * header + entries + extensions
     */
    public static void main(String[] args) throws Exception{
        byte[] bytes = FileUtils.readFileToByteArray(new File(index_file));

        // =========================header========================
        // 4-byte signature:
        //  The signature is { 'D', 'I', 'R', 'C' } (stands for "dircache")
        byte byte0 = bytes[0];
        byte byte1 = bytes[1];
        byte byte2 = bytes[2];
        byte byte3 = bytes[3];
        if (!('D' == byte0 && 'I' == byte1 && 'R' == byte2 && 'C' == byte3)) {
            System.err.println("signature error");
            return;
        }

        // 4-byte version number:
        //  The current supported versions are 2, 3 and 4.
        int version = bytesToInt(bytes, 4);
//        System.out.println("version:" + version);

        // 32-bit number of index entries.
        int entryLen = bytesToInt(bytes, 8);
        System.out.println("=========== entries:" + entryLen + " ==========");

        // =========================entries========================
        int index = 12;
        for (int i = 0; i < entryLen; i++) {
            // 前28位先忽略
            // 4bit object type
            // 3bit unused
            // 9bit 权限
            // 32bit uid
            // 32bit gid
            // 32bit file size
            // 16bit flags
            // 1bit valid flag
            // 1bit extended flag (must be zero in version 2)
            // 2bit stage (during merge)
            // 12bit name length if the length is less than 0xFFF; otherwise 0xFF is stored in this field.
            // 1bit reserved for future
            // 1bit skip-worktree flag (used by sparse checkout)
            // 1bit intent-to-add flag (used by "git add -N")
            // 13bit unused, must be zero

            // 40byte后的结构：
            // 20byte sha1
            // 16bit 长度
            // filename名称
            // padding
            int entryByteLen = 0;
            index += 60;
            entryByteLen += 60;
            byte abyte1 = bytes[index];
            byte abyte2 = bytes[index+1];
            int fileNameLen = (int)((abyte1 & 0X0F)<<8 | (abyte2 & 0XFF));
            index += 2;
            String filename = getFileName(bytes, index, fileNameLen);
            System.out.println(filename);
            entryByteLen += (2 + fileNameLen);
            index += fileNameLen;
            int padding = (8 - entryByteLen % 8);
            index += padding;
        }
        System.out.println("================================");

        // =========================extensions========================

    }

    public static String getFileName(byte[] bytes, int index, int len) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < len; i++) {
            sb.append((char)bytes[index + i]);
        }
        return sb.toString();
    }

    public static int bytesToInt(byte[] src, int offset) {
        int value;
        value = (int) ((src[offset+3] & 0xFF)
                | ((src[offset+2] & 0xFF)<<8)
                | ((src[offset+1] & 0xFF)<<16)
                | ((src[offset] & 0xFF)<<24));
        return value;
    }

}
