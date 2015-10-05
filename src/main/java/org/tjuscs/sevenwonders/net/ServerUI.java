package org.tjuscs.sevenwonders.net;

import java.io.IOException;
import java.util.ArrayList;


/**
 * @author CSDN ID:zhouyuqwert
 */
public class ServerUI// extends JFrame
{
    private static ServerUI serverUI = new ServerUI();

    private static ArrayList<String> list = new ArrayList<String>();// 在线列表

    public static ServerUI getServerUIInstance() {
        return serverUI;
    }

    public boolean isWaiting() {
        return connect.waiting;
    }

    /**
     *
     */
    private static final long serialVersionUID = 1L;// 序列化编号

//	private JPanel panel;// 仅次于Frame一层包裹其他层次控件
//
//	private JSplitPane splitPane1;// 分割状态和下面层次
//
//	private JSplitPane splitPane2;// 位于splitPane1,分割端口和在线用户列表
//
//	private JPanel panel_status;
//
//	private JPanel panel_port;
//
//	private JPanel panel_listbox;
//
//	private JLabel lb_status;// 状态变更
//
    //private String status;
//	private JTextField txt_port;// 端口
//
//	private JButton btn_start;// 启动服务器
//
//	private JButton btn_stop;// 停止服务器
//
//	private JList list_users;// 在线列表

    private ServerConnect connect;// 监听连接

    /**
     * 初始化界面
     */
    private ServerUI() {
//		super("服务器");
//		panel = new JPanel(new BorderLayout());
//
//		/*
//		 * 初始化下半部分的splitPane
//		 */
//		panel_port = new JPanel();
//
//		/*
//		 * 初始化端口号面板
//		 */
//
//		JLabel lb_pre_port = new JLabel("端口号：");
//		txt_port = new JTextField(10);
//		/*
//		 * 验证是否填入端口或者是否是数字
//		 */
//		txt_port.setInputVerifier(new InputVerifier() // 应该是使用了观察者模式
//		{
//
//			@Override
//			public boolean verify(JComponent input)
//			{
//				JTextField txt_com = (JTextField) input;
//				String text = txt_com.getText();
//				return text.length() > 0 && isNum(text);
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
//		btn_start = new JButton("启动服务器");
//
//		/**
//		 * 开启服务器监听事件 循环监听特定端口，连接上一个客户端后新建端口与客户端相连接
//		 * 将端口号输入框disabled,将start按钮隐藏，将stop按钮显示,改变服务器状态
//		 * 
//		 * @author Administrator
//		 * 
//		 */
//		btn_start.addActionListener(new ActionListener()
//		{
//
//			@Override
//			public void actionPerformed(ActionEvent e)
//			{
        //String text =;
        int port = NetManager.getPort();
        int maxClient = NetManager.getPlayerNum();
//				if (text.length() <= 0)
//				{
//					JOptionPane.showMessageDialog(null, "请输入1024到65535的数字！");
//					return;
//				}
        try {
            connect = new ServerConnect(port, maxClient);
        } catch (NumberFormatException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        connect.waitForClient();
    }// 开启线程等待在port上
//				btn_start.setVisible(false);
//				btn_stop.setVisible(true);
//				txt_port.setEditable(false);
//				lb_status.setText("正在运行");
//				lb_status.setForeground(Color.green);

//			}
//		});
//
//		btn_stop = new JButton("停止服务器");
//		btn_stop.setVisible(false);
//
//		/**
//		 * 停止服务器事件 客户端断开socket会抛出异常，捕获后提示并关闭程序
//		 * 将端口号输入框enable,将stop按钮隐藏，将start按钮显示，改变服务器状态
//		 * 
//		 * @author Administrator
//		 * 
//		 */
//		btn_stop.addActionListener(new ActionListener()
//		{
//
//			@Override
//			public void actionPerformed(ActionEvent e)
//			{
//				try
//				{
//					connect.stopServer();
//				}
//				catch (IOException e1)
//				{
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//				btn_start.setVisible(true);
//				btn_stop.setVisible(false);
//				txt_port.setEditable(true);
//				lb_status.setText("停止");
//				lb_status.setForeground(Color.red);
//			}
//		});
//
//		panel_port.add(lb_pre_port);
//		panel_port.add(txt_port);
//		panel_port.add(btn_start);
//		panel_port.add(btn_stop);
//		JTextField t1 = new JTextField(10);
//		panel_port.add(t1);
//		/*
//		 * 初始化在线列表面板
//		 */
//		panel_listbox = new JPanel(new BorderLayout());

//		DefaultListModel listModel = new DefaultListModel();// JList数据模型，必须显示构造
//		list_users = new JList(listModel);
//		JScrollPane scrollPane = new JScrollPane(list_users);
//		panel_listbox.add(scrollPane);
//
//		splitPane2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, panel_port,
//				panel_listbox);
//
//		splitPane2.setEnabled(false);
//		/*
//		 * 上半部分
//		 */
//
//		panel_status = new JPanel();
//
//		/*
//		 * 初始化状态面板
//		 */
//		JLabel lb_pre_status = new JLabel("服务器状态：");
//
//		lb_status = new JLabel("停止");
//
//		lb_status.setForeground(Color.red);
//
//		panel_status.add(lb_pre_status);
//		panel_status.add(lb_status);
//
//		/*
//		 * 加入到最外层的面板中
//		 */
//		splitPane1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, panel_status,
//				splitPane2);
//
//		panel.add(splitPane1, BorderLayout.CENTER);
//
//		splitPane1.setEnabled(false);
//
//		this.add(panel);
//
//		/*
//		 * 关闭程序
//		 */
//		this.addWindowListener(new WindowAdapter()
//		{
//			@Override
//			public void windowClosing(WindowEvent e)
//			{
//				System.exit(0);
//			}
//		});
//	}

    /**
     * 向在线列表添加
     *
     * @param name
     * @return
     */
    public boolean addUser(String name) {
        //DefaultListModel lm = (DefaultListModel) list_users.getModel();

        if (list.contains(name)) {
            // JOptionPane.showMessageDialog(null, "该用户已存在！");

            // 在客户端弹出消息
            return false;
        }

        list.add(name);
        return true;
    }

    public void removeUser(String name) {
        //DefaultListModel lm = (DefaultListModel) list_users.getModel();
        list.remove(name);
    }

    public void clearUsers() {
        //DefaultListModel lm = (DefaultListModel) list_users.getModel();
        list.clear();
    }

    public Object[] getUsers() {
        //DefaultListModel lm = (DefaultListModel) list_users.getModel();

        return list.toArray();
    }

    public void stopServer() {
        try {
            connect.stopServer();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }
}
