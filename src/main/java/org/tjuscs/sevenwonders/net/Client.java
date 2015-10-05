package org.tjuscs.sevenwonders.net;
/**
 * 
 * @author CSDN ID:zhouyuqwert
 *
 */
public class Client
{

	private static ClientLogin loginForm = new ClientLogin();
	/**
	 * @param args
	 */
	public static void start()
	{
		//loginForm.setSize(300,300);
		//loginForm.setVisible(true);
	}
	
	public static ClientLogin getLoginForm()
	{
		return loginForm;
	}
	
	public static void send(String msg){
		loginForm.chat.send(msg);
	}

}
