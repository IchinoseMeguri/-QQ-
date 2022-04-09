/*
 * @Author: Meguri Ichinose
 * @Date: 2022-04-09 15:55:23
 * @Description: 登录窗口GUI
 */
package gui;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.GridLayout;

public class Login extends JFrame {
    private JTextField name;
    private JPasswordField passwd;
    private JPanel _name;
    private JPanel _passwd;

    private JButton register;
    private JButton find;

    private JCheckBox remember;
    private JCheckBox autologin;
    private JPanel section;

    private JButton login;

    private JTextField ip;
    private JTextField port;

    private JPanel up;
    private JPanel down;

    public Login() {
        setTitle("登录");

        name = new JTextField("", 15);
        passwd = new JPasswordField("", 15);
        passwd.setEchoChar('*');
        up = new JPanel();
        _name = new JPanel();
        _passwd = new JPanel();
        up.setLayout(new GridLayout(4, 1));
        _name.add(new JLabel("账号"));
        _name.add(name);
        _passwd.add(new JLabel("密码"));
        _passwd.add(passwd);
        up.add(_name);
        up.add(_passwd);

        remember = new JCheckBox("记住密码");
        autologin = new JCheckBox("自动登录");
        section = new JPanel();
        section.add(remember);
        section.add(autologin);
        up.add(section);

        register = new JButton("注册账号");
        find = new JButton("找回密码");
        find.setEnabled(false);
        _name.add(register);
        _passwd.add(find);

        login = new JButton("登录");
        up.add(login);

        ip = new JTextField("127.0.0.1", 15);
        port = new JTextField("50000", 15);

        down = new JPanel();
        add(up, BorderLayout.NORTH);
        add(down, BorderLayout.SOUTH);
        down.add(new JLabel("服务器主机号"));
        down.add(ip);
        down.add(new JLabel("端口号"));
        down.add(port);

        // 窗口自适应
        pack();
        // 固定可视化界面窗口大小
        setResizable(false);
        // 窗口居中
        setLocationRelativeTo(null);
    }
}
