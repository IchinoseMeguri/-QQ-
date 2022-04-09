/*
 * @Author: Meguri Ichinose
 * @Date: 2022-04-09 16:01:27
 * @Description: 注册GUI
 */
package gui;

import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JFrame;

public class Register extends JFrame {
    public static void testf() throws IOException {
        FileOutputStream out = new FileOutputStream("test");
        out.write("111".getBytes());
        out.close();
    }
}
