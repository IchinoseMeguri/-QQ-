/*
 * @Author: Meguri Ichinose
 * @Date: 2022-04-09 16:02:02
 * @Description: 找回密码GUI
 */
package gui;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import util.ClientThread;
import util.Message;

import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;

public class FindPasswd extends JFrame {
    private JTextField name;
    private JPasswordField newpsw;
    private JTextField phone;
    private JButton ok;

    private JPanel _name;
    private JPanel _question;
    private JPanel _answer;

    private ClientThread clientthread;

    public FindPasswd(String name) {
        setTitle("找回密码");
        clientthread.setFindframe(this);

        this.name = new JTextField(name);
        this.newpsw = new JPasswordField("", 20);
        newpsw.setEchoChar('*');
        this.phone = new JTextField("", 20);
        this.ok = new JButton("找回密码");

        setLayout(new GridLayout(4, 1));
        _name = new JPanel();
        _name.add(new JLabel("账号："));
        _name.add(this.name);
        _question = new JPanel();
        _question.add(new JLabel("请输入新密码："));
        _question.add(newpsw);
        _answer = new JPanel();
        _answer.add(new JLabel("请输入手机号"));
        _answer.add(phone);
        ok = new JButton("确认");
        add(_name);
        add(_question);
        add(_answer);
        add(ok);

        ok.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Message message = new Message();
                message.setType(31);
                message.setUsername(name);
                message.setPasswd(newpsw.toString());
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

    @Override
    protected void processWindowEvent(WindowEvent e) {
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            clientthread.CloseClient();
        }
    }

    public ClientThread getClientthread() {
        return clientthread;
    }

    public void setClientthread(ClientThread clientthread) {
        this.clientthread = clientthread;
    }

    /**
     * @description: 客户端向服务器发出验证请求后，服务器向客户端返回一个结果，线程解析这条消息后调用此方法。
     * @param b
     * @param passwd
     * @return
     */
    public void Judge(boolean b, String passwd) {
        if (b == true) {
            JOptionPane.showMessageDialog(null, "请牢记新的密码");
        } else {
            JOptionPane.showMessageDialog(null, "验证信息错误");
        }
    }
}
