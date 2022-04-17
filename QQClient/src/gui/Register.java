/*
 * @Author: Meguri Ichinose
 * @Date: 2022-04-09 16:01:27
 * @Description: 注册GUI
 */
package gui;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import util.ClientThread;
import util.Message;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;

public class Register extends JFrame {
    private JTextField name;
    private JPasswordField psw;
    private JPasswordField psw2;
    private JTextField phone;

    private JButton register;

    private JPanel left;
    private JPanel right;

    private ClientThread clientthread;

    public Register(ClientThread clientthread) {
        setTitle("注册");
        this.clientthread = clientthread;
        this.clientthread.setRegisterframe(this);

        left = new JPanel();
        left.setLayout(new GridLayout(4, 1));
        right = new JPanel();
        right.setLayout(new GridLayout(4, 1));
        register = new JButton("注册");

        name = new JTextField("", 15);
        psw = new JPasswordField("", 15);
        psw.setEchoChar('*');
        psw2 = new JPasswordField("", 15);
        psw2.setEchoChar('*');
        phone = new JTextField("", 15);

        left.add(new JLabel("账号", SwingConstants.RIGHT));
        left.add(new JLabel("密码", SwingConstants.RIGHT));
        left.add(new JLabel("确认密码", SwingConstants.RIGHT));
        left.add(new JLabel("手机号", SwingConstants.RIGHT));
        right.add(name);
        right.add(psw);
        right.add(psw2);
        right.add(phone);

        add(left, BorderLayout.WEST);
        add(right, BorderLayout.EAST);
        add(register, BorderLayout.SOUTH);

        register.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                Message message = new Message();
                message.setType(1);
                message.setUsername(name.getText());
                message.setPasswd(psw.toString());
                message.setPhone(phone.getText());
                clientthread.SendToServer(message);

            }
        });

        // 窗口自适应
        pack();
        // 固定可视化界面窗口大小
        setResizable(false);
        // 窗口居中
        setLocationRelativeTo(null);
    }

    public ClientThread getClientthread() {
        return clientthread;
    }

    public void setClientthread(ClientThread clientthread) {
        this.clientthread = clientthread;
    }

    @Override
    protected void processWindowEvent(WindowEvent e) {
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            clientthread.CloseClient();
        }
    }

    /**
     * @description: 客户端向服务器发出验证请求后，服务器向客户端返回一个结果，线程解析这条消息后调用此方法。
     * @param b
     * @return
     */
    public void RegisterOK(boolean b) {
        if (b == true) {
            JOptionPane.showMessageDialog(null, "注册成功");
        } else {
            JOptionPane.showMessageDialog(null, "注册失败");
        }
    }
}
