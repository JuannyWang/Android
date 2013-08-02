/**
 * 
 */
package com.ghost;

import com.ghost.server.Server;

/**
 * @author 玄雨
 * @qq 821580467
 * @date 2013-7-12
 */
public class StartServer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Server server = new Server();
		server.startServer();
	}

}
