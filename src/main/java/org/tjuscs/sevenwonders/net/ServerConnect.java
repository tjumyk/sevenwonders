package org.tjuscs.sevenwonders.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;


/**
 * 每一个Server都仅有一个Connect等待在指定的端口号
 * 所有客户端登陆都需要尝试连接上该端口号
 *
 * @author CSDN ID:zhouyuqwert
 */
public class ServerConnect {

    private int port;

    private int maxClientNum;

    private ServerSocket serverSocket;

    public boolean waiting = true;


    private static ArrayList<Socket> clientSockets = new ArrayList<Socket>();//所有已连接上的与客户端交互的线程

    public ServerConnect(int port, int maxClientNum) throws IOException {
        this.port = port;
        this.maxClientNum = maxClientNum;
        serverSocket = new ServerSocket(this.port);
    }

    public static ArrayList<Socket> getClientScokets() {
        return clientSockets;
    }

    /**
     * 开启线程在port上等待客户端连接
     * 连接上后为该客户端建立新的端口连接以及输入流线程，输出流线程在接收到数据需要广播时（数据或者在线用户）开启
     *
     * @throws IOException
     */
    public void waitForClient() {
        new Thread() {                 //等待客户端连接的线程


            @Override
            public void run() {
                while (waiting) {

                    try {
                        Socket socket = serverSocket.accept();
                        /*
						 *与某一客户端连接上之后建立线程开启单独的与该客户端连接的通道 
						 */
                        ConnectClientThread thread = new ConnectClientThread(socket);
                        thread.start();
                    } catch (SocketException e) {
                        //关闭服务器，清空在线列表

                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        break;
                    }
                    if (clientSockets.size() >= maxClientNum - 1) {
                        waiting = false;
                        //JOptionPane.showMessageDialog(null, "All "+clientSockets.size()+"players have been connected!");
                        NetManager.init();
                    }
                }
            }
        }.start();
    }

    /**
     * 关闭服务器，停止监听
     *
     * @throws IOException
     */
    public void stopServer() throws IOException {
        ServerUI.getServerUIInstance().clearUsers();
        for (Socket s : clientSockets) {
            try {
                s.close();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
        serverSocket.close();//此处断开了监听用户连接的socket，有可能其他已经连接上的socket未断开？！
    }

    /**
     * 通过随机获取1000到65539端口号，并判断是否可用
     * 该端口号为新的客户端连接所用
     * @return 可用的端口号
     */
//	private int getPort()
//	{
//		return -1;
//	}

}

/**
 * 针对每一个已建立连接的客户端新建端口与之交互
 *
 * @author Administrator
 */
class ConnectClientThread extends Thread {
    private Socket socket;//等待客户端连接的端口，需要从这个端口向客户端输出新的端口号
    private Socket new_socket;

    public ConnectClientThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        ServerSocket clientServerSocket;
        try {
            clientServerSocket = new ServerSocket();
            clientServerSocket.bind(null);//创建一个可用端口监听
            int port = clientServerSocket.getLocalPort();
            new ServerOutputThread(socket, String.valueOf(port)).start();//向客户端输出端口号
            new_socket = clientServerSocket.accept();//等待该客户端在此端口号上建立连接
            ServerConnect.getClientScokets().add(new_socket);
            new ServerInputThread(new_socket).start();//等待输入
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public Socket getNewSocket() {
        return this.new_socket;
    }
}
