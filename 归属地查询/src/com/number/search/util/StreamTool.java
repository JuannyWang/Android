/**
 * 
 */
package com.number.search.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * @author 玄雨
 * @qq 821580467
 * @date 2013-5-23
 */
public class StreamTool {

	/**
	 * 从输入流读取数据
	 * 
	 * @param inStream
	 * @return
	 * @throws Exception
	 */
	public static byte[] readInputStream(InputStream inStream) throws Exception {
		ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inStream.read(buffer)) != -1) {
			outSteam.write(buffer, 0, len);
		}
		outSteam.close();
		inStream.close();
		return outSteam.toByteArray();
	}

}
