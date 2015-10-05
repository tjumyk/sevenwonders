package org.tjuscs.sevenwonders.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.UnknownHostException;


/**
 * @author CSDN ID:zhouyuqwert
 */
public class ClientConnect {
    private String ip;

    private int port;

    private Socket socket;

    public ClientConnect(String ip, int port) throws UnknownHostException,
            IOException {
        this.ip = ip;
        this.port = port;
        socket = new Socket(ip, port);
    }

    /**
     * 与服务器新的端口建立连接
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void setNewConnect() throws IOException, ClassNotFoundException {

        new Thread() {
            @Override
            public void run() {
                InputStream is;
                try {
                    is = socket.getInputStream();

                    ObjectInputStream ois = new ObjectInputStream(is);

                    Object message = ois.readObject();// 读取一个port值

                    if (message instanceof String) {
                        port = Integer.parseInt((String) message);
                    }

                    socket = new Socket(ip, port);// 建立新的连接
                    new ClientInputThread(socket).start();
                    String userName = ClientLogin.getUserName();
                    User user = new User(userName);
                    new ClientOutputThread(socket, user).start();//向服务器发送用户名

                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }.start();

    }

}
