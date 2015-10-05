package org.tjuscs.sevenwonders.net;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
/**
 * 向指定的socket输出message
 * @author CSDN ID:zhouyuqwert
 *
 */
public class ServerOutputThread extends Thread
{
	private Socket socket;
	private Object message;
	public ServerOutputThread(Socket socket,Object message)
	{
		this.socket = socket;
		this.message = message;
	}
	
	@Override
	public void run()
	{
		try
		{
			OutputStream os = socket.getOutputStream();
			
			ObjectOutputStream oos = new ObjectOutputStream(os);
			
			oos.writeObject(message);
			
		}
		catch(SocketException e)
		{
			
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
