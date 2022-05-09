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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import util.ClientThread;
import util.Message;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

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

    private ClientThread clientthread;

    public Login() {
        setTitle("登录");

        File settings = new File(".\\Settings.json");
        if (!settings.exists()) {
            try {
                settings.createNewFile();
                String s = "{\n\t\"RememberPassword\":false,\n\t\"Username\":\"\",\n\t\"Password\":\"\",\n\t\"AutoLogin\":false,"
                        + "\n\t\"Ip\":\"127.0.0.1\",\n\t\"Port\":50000\n}";
                Files.write(Paths.get(".\\Settings.json"), s.getBytes());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

        ArrayList<String> filelines = new ArrayList<String>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(settings));
            String line = "";
            try {
                while ((line = reader.readLine()) != null) {
                    filelines.add(line);
                }
                reader.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        boolean RememberPassword = filelines.get(1).contains("true");
        boolean AutoLogin = filelines.get(4).contains("true");
        String Username = filelines.get(2).split("\"")[3];
        String Password = filelines.get(3).split("\"")[3];
        String Ip = filelines.get(5).split("\"")[3];
        String Port = filelines.get(6).split(":")[1];

        name = new JTextField(Username, 15);
        passwd = new JPasswordField(Password, 15);
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
        remember.setSelected(RememberPassword);
        autologin.setSelected(AutoLogin);
        section = new JPanel();
        section.add(remember);
        section.add(autologin);
        up.add(section);

        register = new JButton("注册账号");
        find = new JButton("找回密码");
        _name.add(register);
        _passwd.add(find);

        login = new JButton("登录");
        up.add(login);

        ip = new JTextField(Ip, 10);
        port = new JTextField(Port, 10);

        down = new JPanel();
        add(up, BorderLayout.NORTH);
        add(down, BorderLayout.SOUTH);
        down.add(new JLabel("服务器主机号"));
        down.add(ip);
        down.add(new JLabel("端口号"));
        down.add(port);

        login.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Message message = new Message();
                message.setType(11);
                message.setUsername(name.getText());
                message.setPasswd(String.valueOf(passwd.getPassword()));
                clientthread.SendToServer(message);
            }
        });
        register.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Register reg = new Register(clientthread);
                reg.setVisible(true);
            }
        });
        find.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                FindPasswd find = new FindPasswd(clientthread, name.getText());
                find.setVisible(true);
            }
        });

        clientthread = new ClientThread(ip.getText(), Integer.parseInt(port.getText()));
        clientthread.setLoginframe(this);

        if (AutoLogin)
            login.doClick();

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

    /**
     * @description: 客户端向服务器发出验证请求后，服务器向客户端返回一个结果，线程解析这条消息后调用此方法。
     * @param b
     * @param online
     * @return
     */
    public void LoginJudge(boolean b, ArrayList<String> online) {
        if (b == true) {
            Chat chat = new Chat(this.clientthread, this.name.getText(), online);
            chat.setVisible(true);
            File settings = new File(".\\Settings.json");

            try {
                settings.createNewFile();
                String s = "{\n\t\"RememberPassword\":" + remember.isSelected() + ",\n\t\"Username\":\""
                        + name.getText() + "\",\n\t\"Password\":\"";
                if (remember.isSelected())
                    s += String.valueOf(passwd.getPassword());
                s += "\",\n\t\"AutoLogin\":" + autologin.isSelected() + ","
                        + "\n\t\"Ip\":\"" + ip.getText() + "\",\n\t\"Port\":" + port.getText() + "\n}";
                Files.write(Paths.get(".\\Settings.json"), s.getBytes());
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            setVisible(false);
        } else {
            JOptionPane.showMessageDialog(null, "账号或密码错误");
        }
    }

    @Override
    protected void processWindowEvent(WindowEvent e) {
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            clientthread.CloseClient();
            System.exit(0);
        }
    }
}
