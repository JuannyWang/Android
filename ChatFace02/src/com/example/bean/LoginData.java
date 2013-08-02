/**
 * 
 */
package com.example.bean;

import java.io.Serializable;

/**
 * @author 玄雨
 * @qq 821580467
 * @date 2013-7-13
 */
public class LoginData implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String password;
	
	public LoginData(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
