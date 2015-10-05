package org.tjuscs.sevenwonders.net;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
/**
 * 
 * @author CSDN ID:zhouyuqwert
 *
 */
public class ClientOutputThread extends Thread
{
	private static Socket socket;
	private Object message;
	private static OutputStream os;
	private static ObjectOutputStream oos;
	public ClientOutputThread(Socket socket,Object message) throws IOException
	{
		Socket oldSocket = ClientOutputThread.socket;
		if(oldSocket!=socket)
		{
			ClientOutputThread.socket = socket;
			ClientOutputThread.os = socket.getOutputStream();
			ClientOutputThread.oos = new ObjectOutputStream(ClientOutputThread.os);
		}
		
		this.message = message;
	}
	
	@Override
	public void run()
	{
		try
		{
			
			
			oos.writeObject(message);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
