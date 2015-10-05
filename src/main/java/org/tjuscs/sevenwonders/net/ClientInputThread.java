package org.tjuscs.sevenwonders.net;

import javax.swing.*;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketException;


/**
 * @author CSDN ID:zhouyuqwert
 */
public class ClientInputThread extends Thread {
    private Socket socket;

    public ClientInputThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        /**
         *
         * 监听输入，在客户端向服务器发送用户名成功后， 服务器会返回一个是否登陆成功的信息，
         * 如果成功则是Object[]类型的广播，如果失败则是String类型的错误提示
         *
         */
        try {
            InputStream is = socket.getInputStream();

            ObjectInputStream ois = new ObjectInputStream(is);

            Object rec = ois.readObject();

            if (rec == null || rec instanceof String) {
                // 错误提示
                JOptionPane.showMessageDialog(null, rec == null ? "服务器验证失败，请稍后重试！" : rec);
                return;
            }

            Object[] list = (Object[]) rec;// 会传给Chart Form,加入到列表中

			/*
             * 登陆成功，隐藏掉login Form,显示Chart Form
			 */
            ClientLogin loginForm = Client.getLoginForm();
            ClientChat chart = loginForm.loginSuc(socket, list);
            chart.setList(list);
			/*
			 * 继续监听inputStream,接收在线列表的更新（Object[]类型）和消息广播（String类型）
			 */
            while (true) {
                InputStream inputStream = socket.getInputStream();
                ObjectInputStream objectInputStream = new ObjectInputStream(
                        inputStream);
                Object message = objectInputStream.readObject();
                if (message instanceof String) {
                    // 将该String显示到Chart界面
                    String m = (String) message;
                    chart.setMessage(m);
                } else {
                    // 更新在线列表
                    Object[] l = (Object[]) message;
                    chart.setList(l);
                }

            }

        } catch (SocketException e) {
            //提示并关闭
            JOptionPane.showMessageDialog(null, "与服务器断开连接！", "错误提示", JOptionPane.OK_OPTION);
            System.exit(0);
        } catch (EOFException e) {
            //提示并关闭
            JOptionPane.showMessageDialog(null, "与服务器断开连接！", "错误提示", JOptionPane.OK_OPTION);
            System.exit(0);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
