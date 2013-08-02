/**
 * 
 */
package com.ghost;

import java.io.IOException;
import java.net.UnknownHostException;
import java.security.MessageDigest;

import com.ghost.beans.PackageBean;
import com.ghost.beans.PackageType;
import com.ghost.connection.ServerConnection;

/**
 * @author 玄雨
 * @qq 821580467
 * @date 2013-7-23
 */
public class MainTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(MD5("12121212"));
		try {
			ServerConnection connection = ServerConnection.getInstance();
			connection.sendPackageBean(new PackageBean(PackageType.REGIST,
					"111111111111", "000000000000",
					MD5("12121212")));
			PackageBean bean = connection.readPackageBean();
			System.out.println(bean);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public final static String MD5(String s) {
        char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};       
        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
