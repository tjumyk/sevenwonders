package org.tjuscs.sevenwonders.net;

import javax.swing.*;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;


/**
 * @author CSDN ID:zhouyuqwert
 */
public class ClientLogin {
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = 1L;
//
//	private JPanel panel;// 最外层的面板
//
//	private JTextField txt_userName;
//
//	private JTextField txt_ip;
//
//	private JTextField txt_port;
//
//	private JButton btn_confirm;

    private static String userName;

    private ClientConnect connect;

    public static ClientChat chat;

    public ClientLogin() {
//		super("客户端");
//		panel = new JPanel(new GridLayout(4, 2));
//
//		JPanel p = new JPanel();// 在每一个grid cell中 保持flow Layout
//
//		JLabel lb_pre_userName = new JLabel("用户名：");
//		p.add(lb_pre_userName, JPanel.RIGHT_ALIGNMENT);
//		panel.add(p);
//
//		txt_userName = new JTextField(10);
//		p = new JPanel();
//		p.add(txt_userName);
//		panel.add(p);
//
//		JLabel lb_pre_ip = new JLabel("服务器地址：");
//		p = new JPanel();
//		p.add(lb_pre_ip, JPanel.RIGHT_ALIGNMENT);
//		panel.add(p);
//
//		txt_ip = new JTextField(10);
//		p = new JPanel();
//		p.add(txt_ip);
//		panel.add(p);
//
//		JLabel lb_pre_port = new JLabel("端口号：");
//		p = new JPanel();
//		p.add(lb_pre_port, JPanel.RIGHT_ALIGNMENT);
//		panel.add(p);
//
//		txt_port = new JTextField(10);
//		p = new JPanel();
//		p.add(txt_port);
//		panel.add(p);
        /*
		 * 验证是否填入端口或者是否是数字
		 */
//		txt_port.setInputVerifier(new InputVerifier() // 应该是使用了观察者模式
//		{
//
//			@Override
//			public boolean verify(JComponent input)
//			{
//				JTextField txt_com = (JTextField) input;
//				String text = txt_com.getText();
//				return isNum(text);
//			}
//
//			private boolean isNum(String text)
//			{
//				for (int i = 0; i < text.length(); i++)
//				{
//					char c = text.charAt(i);
//
//					if (!Character.isDigit(c))
//					{
//						JOptionPane.showMessageDialog(null, "请输入1024到65535的数字！");
//
//						return false;
//					}
//				}
//				int port = Integer.parseInt(text);
//				if(port<1024||port>=65535)
//				{
//					JOptionPane.showMessageDialog(null, "请输入1024到65535的数字！");
//
//					return false;
//				}
//				return true;
//			}
//
//		});
//
//		panel.add(new JPanel());
//
//		btn_confirm = new JButton("确认");
//		btn_confirm.addActionListener(new ActionListener()
//		{
//
//			@Override
//			public void actionPerformed(ActionEvent e)
//			{
        userName = NetManager.getPlayerName();
        String ip = NetManager.getConnectIP();
//				String port_str = String.valueOf(NetManager.getPort());
//
//				if (port_str.length() <= 0)
//				{
//					JOptionPane.showMessageDialog(null, "请输入1024到65535的数字！");
//					return;
//				}
//				int port = Integer.parseInt(port_str);
        int port = NetManager.getPort();
        try {
            connect = new ClientConnect(ip, port);
            connect.setNewConnect();
        } catch (ConnectException e1) {
            JOptionPane.showMessageDialog(null, "未能找到服务器！");
        } catch (UnknownHostException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (ClassNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

    }
    //});
//		p = new JPanel();
//		p.add(btn_confirm, JPanel.LEFT_ALIGNMENT);
//		panel.add(p);
//
//		
//
//		add(panel, BorderLayout.CENTER);
//
//		addWindowListener(new WindowAdapter()
//		{
//			@Override
//			public void windowClosing(WindowEvent e)
//			{
//				System.exit(0);
//			}
//
//		});


    public static String getUserName() {
        return userName;
    }

    /**
     * 在socket连接上登陆成功，并更新当前在线列表
     *
     * @param socket 登陆成功的连接
     * @param list   当前在线列表
     */
    public ClientChat loginSuc(Socket socket, Object[] list) {
        //this.setVisible(false);
        chat = new ClientChat(socket, list);
        //chart.setVisible(true);
        return chat;
    }

}
