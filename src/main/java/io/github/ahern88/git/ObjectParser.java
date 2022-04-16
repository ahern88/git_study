package io.github.ahern88.git;

import java.io.*;
import java.util.zip.InflaterInputStream;


/**
 * zlib压缩工具类
 */
public class ObjectParser {

	public static void main(String[] args) throws Exception{
		printObjectInfo();
	}

	/**
	 * 参考：https://www.bianchengquan.com/article/320620.html
	 * @throws Exception
	 */
	public static void printObjectInfo() throws Exception{
		InputStream is = new InflaterInputStream(new FileInputStream(
				new File("/Users/shzto/git/git/.git/objects/ba/af27759304a6bd325e87d7eb19d4fc864f547d")
		));

		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		int b = 0;
		while ((b = is.read()) != -1) {
			baos.write(b);
		}

		byte[] res = baos.toByteArray();
		System.out.println(new String(res));

		is.close();
		baos.close();
	}

}
