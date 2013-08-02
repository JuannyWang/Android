/**
 * 
 */
package com.ghost.beans;

import java.io.Serializable;

/**
 * @author 玄雨
 * @qq 821580467
 * @date 2013-7-12
 */
public class PackageBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int type;

	private int fromUser;

	private int toUser;

	private Object data;

	public PackageBean(int type, int fromUser, int toUser, Object data) {
		this.type = type;
		this.fromUser = fromUser;
		this.toUser = toUser;
		this.data = data;
	}

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * @return the fromUser
	 */
	public int getFromUser() {
		return fromUser;
	}

	/**
	 * @param fromUser
	 *            the fromUser to set
	 */
	public void setFromUser(int fromUser) {
		this.fromUser = fromUser;
	}

	/**
	 * @return the toUser
	 */
	public int gettoUser() {
		return toUser;
	}

	/**
	 * @param toUser
	 *            the toUser to set
	 */
	public void settoUser(int toUser) {
		this.toUser = toUser;
	}

	/**
	 * @return the data
	 */
	public Object getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(Object data) {
		this.data = data;
	}

}
