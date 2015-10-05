package org.tjuscs.sevenwonders.net;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import org.tjuscs.sevenwonders.Manager;
import org.tjuscs.sevenwonders.gui.GUIManager;
import org.tjuscs.sevenwonders.kernel.RecManager.GameInfo;

public class NetManager {
	private static NetManager nm = null;
	private static String localName, playerName;
	private static String localIP, connectIP, wonderType;
	private static int port, playerNum;
	public static boolean isHost;
	
	private NetManager(){
		try {
			InetAddress addr = InetAddress.getLocalHost();  
			localName = addr.getHostName();  
			localIP = addr.getHostAddress();
			NetManager.connectIP = "127.0.0.1";
			NetManager.port = 7777;
			NetManager.playerNum = 7;
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}
	
	public static NetManager getManager() {
		if(nm==null){
			nm = new NetManager();
		}
		return nm;
	}

	public static String getLocalName(){
		return localName;
	} 
	
	public static String getPlayerName(){
		return playerName;
	} 
	
	public static String getLanIP() {
		return localIP;
	}
	
	public static String getConnectIP() {
		return connectIP;
	}
	
	public static int getPort(){
		return port;
	}
	
	public static void startClient(){
		NetManager.playerName = "Player" + (int)(Math.random()*100);//For Test
		System.out.println("Starting Client...");
		Client.start();
	}
	
	public static void startServer(){
		new Thread(){
			public void run(){
		NetManager.playerName = "Host";//For Test
		System.out.println("Starting Host...");
		Server.start();
			}
		}.start();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		startClient();
	}
	
	public static void start(){
		try {
			if(isHost){
				startServer();
				while(ServerUI.getServerUIInstance().isWaiting()){
					Thread.sleep(1000);
				}
				init();
			}else{
				startClient();
				while(ClientLogin.chat.isEmpty()){
					Thread.sleep(1000);
				}
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		NetManager.connectIP = "127.0.0.1";
		NetManager.port = 7777;
		NetManager.playerNum = 7;
		int choice = JOptionPane.showConfirmDialog(null, "Server or client?","Please choose start type",JOptionPane.YES_NO_CANCEL_OPTION);
		if(choice == JOptionPane.YES_OPTION)
			isHost = true;
		else if(choice == JOptionPane.NO_OPTION)
			isHost = false;
		start();
	}
	
	public static void init(){
		Manager.getKernel().setNumOfPlayers(playerNum);
		Manager.getKernel().setBoards(wonderType);
		GameInfo info = Manager.getKernel().setNetPlayers((String[]) ServerUI.getServerUIInstance().getUsers());
		Manager.getKernel().initializeGame();
		Client.send(info.toString());
	}

	public static int getPlayerNum() {
		// TODO Auto-generated method stub
		return playerNum;
	}
}
