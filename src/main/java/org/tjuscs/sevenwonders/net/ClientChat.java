package org.tjuscs.sevenwonders.net;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;


/**
 * 
 * @author CSDN ID:zhouyuqwert
 * 
 */
public class ClientChat
{

	// private static final long serialVersionUID = 1L;

	private Socket socket;// 当前与服务器的连接

	private String message;// 新消息

	private ArrayList<String> msgList = new ArrayList<String>();

	private Object[] list;// 在线列表

	// private JTextArea txtarea_messages;// 当前所有的消息
	//
	// private JList list_users;// 当前在线用户列表
	//
	// private JTextField txt_messageToSend;// 发送消息文本框
	//
	// private JButton btn_send;// 发送按钮

	private ClientChat() {
		// super("聊天窗口");
		// JPanel panel = new JPanel(new BorderLayout());// 仅次于Frame的面板
		//
		// /*
		// * 初始化左上消息窗口
		// */
		// JPanel panel_message = new JPanel(new BorderLayout());
		// panel_message.setBorder(BorderFactory.createTitledBorder("消息窗口"));
		// txtarea_messages = new JTextArea(30, 10);
		// txtarea_messages.setEditable(false);
		// panel_message.add(txtarea_messages, BorderLayout.CENTER);
		//
		// /*
		// * 初始化右上在线列表
		// */
		// JPanel panel_list = new JPanel(new BorderLayout());
		// panel_list.setBorder(BorderFactory.createTitledBorder("在线列表"));
		// DefaultListModel dlm = new DefaultListModel();
		// list_users = new JList(dlm);
		// panel_list.add(list_users);
		//
		// JSplitPane split1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
		// panel_message, panel_list);
		// split1.setDividerLocation(600);
		// split1.setEnabled(false);
		//
		// /*
		// * 初始化下方发送消息面板
		// */
		// JPanel panel_send = new JPanel();
		// panel_send.setBorder(BorderFactory.createTitledBorder("发送消息"));
		// txt_messageToSend = new JTextField(30);
		//
		// txt_messageToSend.addKeyListener(new KeyAdapter()
		// {
		// @Override
		// public void keyPressed(KeyEvent e)
		// {
		// if (e.getKeyCode() == KeyEvent.VK_ENTER)
		// {
		// String message = txt_messageToSend.getText();
		// // 需要转换成用户名：消息\n的形式
		// @SuppressWarnings("static-access")
		// String messageToSend = Client.getLoginForm().getUserName()
		// + "：" + message + "\n";
		// try
		// {
		// new OutputThread(socket, messageToSend).start();
		// }
		// catch (IOException e1)
		// {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// }
		// txt_messageToSend.setText("");
		// }
		// }
		//
		// });
		//
		// btn_send = new JButton("发送");
		//
		// btn_send.addActionListener(new ActionListener()
		// {
		//
		// @Override
		// public void actionPerformed(ActionEvent e)
		// {
		// String message = txt_messageToSend.getText();
		// // 需要转换成用户名：消息\n的形式
		// @SuppressWarnings("static-access")
		// String messageToSend = Client.getLoginForm().getUserName()
		// + "：" + message + "\n";
		// try
		// {
		// new OutputThread(socket, messageToSend).start();
		// }
		// catch (IOException e1)
		// {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// }
		// txt_messageToSend.setText("");
		// }
		// });

		// panel_send.add(txt_messageToSend);
		// panel_send.add(btn_send);
		//
		// JSplitPane split2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, split1,
		// panel_send);
		// split2.setDividerLocation(700);
		// split2.setEnabled(false);
		//
		// panel.add(split2);
		// add(panel);
		// setSize(800, 800);
		//
		// addWindowListener(new WindowAdapter()
		// {
		// @Override
		// public void windowClosing(WindowEvent e)
		// {
		// System.exit(0);
		// }
		// });
	}

	public void send(String msg) {
		String message = msg;
		// 需要转换成用户名：消息\n的形式
		// @SuppressWarnings("static-access")
		String messageToSend = Client.getLoginForm().getUserName() + "：" + message + "\n";
		try {
			new ClientOutputThread(socket, messageToSend).start();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public ClientChat(Socket socket, Object[] list) {
		this();
		this.socket = socket;
		setList(list);
		
		//Following is for test!
//		final Chat self = this;
//		new Thread() {
//			@Override
//			public void run() {
//				while (true) {
//					try {
//						Thread.sleep(5000);
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					self.send("Hello!");
//				}
//			}
//		}.start();
	}

	public synchronized void setMessage(String message) {
		this.message = message;

		// 向消息列表添加
		// txtarea_messages.append(this.message);
		msgList.add(this.message);
		System.out.println(ClientLogin.getUserName()+" get: ["+message+"]");
	}

	public synchronized void setList(Object[] list) {
		this.list = list;

		// 向在线列表更新
		// list_users.setListData(this.list);
	}
	
	public boolean isEmpty(){
		return msgList.isEmpty();
	}

}
