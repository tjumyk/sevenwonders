package org.tjuscs.sevenwonders.net;

import java.util.Arrays;

/**
 * 
 * @author CSDN ID:zhouyuqwert
 *
 */
public class Server
{

	/**
	 * @param args
	 */
	public static void start()
	{
		ServerUI serverUI = ServerUI.getServerUIInstance();
		
		//serverUI.setSize(300, 300);
		
		//serverUI.setVisible(true);
		
		//Following is for test!
		while(true){
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(Arrays.toString(serverUI.getUsers()));
		}
	}

}






