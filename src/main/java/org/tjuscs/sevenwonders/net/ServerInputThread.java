package org.tjuscs.sevenwonders.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;


/**
 * 
 * @author CSDN ID:zhouyuqwert
 *
 */
public class ServerInputThread extends Thread
{
	private Socket socket;

	private Object rec;// 接收到当前连接的用户名

	public ServerInputThread(Socket socket)
	{
		this.socket = socket;
	}

	@Override
	public void run()
	{
		try
		{
			InputStream is = socket.getInputStream();
			// 此处是否是用ObjectInputStream读取判断是User还是String,广播同样
			ObjectInputStream ois = new ObjectInputStream(is);

			rec = ois.readObject(); // 每开启一个InputThread，第一个读入的必然是用户名（因为最开始需要进行登陆）
			ServerUI serverui = ServerUI.getServerUIInstance();
			if (!(rec instanceof User)
					|| !serverui.addUser(((User) rec).getUserName()))
			{
				new ServerOutputThread(socket, "用户名已存在！").start();
				return;
			}
			// 如果运行至此处则说明该客户端可以正常登陆，需要广播在线列表Object[]类型
			mutiCast(ServerUI.getServerUIInstance().getUsers());

			while (true)
			{
				// 如果运行到此处则说明该用户已成功进入聊天室并等待发送消息与接收消息
				Object message = ois.readObject();// 接收到得消息
				// 向所有的socket广播
				if (message instanceof String)
				{
					mutiCast(message);
				}

			}
		}
		catch (SocketException e)
		{
			// 通信关闭
			if (rec != null){
				ServerUI.getServerUIInstance().removeUser(
						((User) rec).getUserName());
				mutiCast(ServerUI.getServerUIInstance().getUsers());
			}
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 广播消息
	 * 
	 * @param message
	 */
	private void mutiCast(Object message)
	{
		ArrayList<Socket> sockets = ServerConnect.getClientScokets();

		for (Socket socket : sockets)
		{
			new ServerOutputThread(socket, message).start();
		}
	}
}
