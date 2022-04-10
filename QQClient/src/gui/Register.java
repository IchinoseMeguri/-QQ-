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

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Register extends JFrame {
    private JTextField name;
    private JPasswordField psw;
    private JPasswordField psw2;
    private JTextField question;
    private JTextField answer;

    private JButton register;

    private JPanel left;
    private JPanel right;

    public Register() {
        setTitle("注册");

        left = new JPanel();
        left.setLayout(new GridLayout(5, 1));
        right = new JPanel();
        right.setLayout(new GridLayout(5, 1));
        register = new JButton("注册");

        name = new JTextField("", 15);
        psw = new JPasswordField("", 15);
        psw.setEchoChar('*');
        psw2 = new JPasswordField("", 15);
        psw2.setEchoChar('*');
        question = new JTextField("", 15);
        answer = new JTextField("", 15);

        left.add(new JLabel("账号", SwingConstants.RIGHT));
        left.add(new JLabel("密码", SwingConstants.RIGHT));
        left.add(new JLabel("确认密码", SwingConstants.RIGHT));
        left.add(new JLabel("密保问题（用于找回，请牢记）", SwingConstants.RIGHT));
        left.add(new JLabel("密保问题答案", SwingConstants.RIGHT));
        right.add(name);
        right.add(psw);
        right.add(psw2);
        right.add(question);
        right.add(answer);

        add(left, BorderLayout.WEST);
        add(right, BorderLayout.EAST);
        add(register, BorderLayout.SOUTH);

        register.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 向服务器通信，对数据库进行操作
                // 重名判断
                if (true) {
                    JOptionPane.showMessageDialog(null, "该账号不可用");
                } else {
                    // 判断密码与确认密码是否一致
                    if (psw.toString() != psw2.toString()) {
                        JOptionPane.showMessageDialog(null, "密码与确认密码不一致");
                    } else {
                        // 写入数据库
                        JOptionPane.showMessageDialog(null, "注册成功");
                    }
                }
            }
        });

        // 窗口自适应
        pack();
        // 固定可视化界面窗口大小
        setResizable(false);
        // 窗口居中
        setLocationRelativeTo(null);
    }
}
