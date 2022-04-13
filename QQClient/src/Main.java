import java.io.File;

import javax.swing.JFileChooser;

/*
 * @Author: Meguri Ichinose
 * @Date: 2022-04-09 16:26:52
 * @Description: 
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        // new gui.Login().setVisible(true);

        // new gui.Register().setVisible(true);
        // new gui.FindPasswd("n").setVisible(true);
        JFileChooser save = new JFileChooser(".");
        save.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        save.setMultiSelectionEnabled(false);
        if (save.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            String path = save.getSelectedFile().getPath();
            System.out.println(path);
            File f = new File(path + "\\" + new File(".\\test").getName());
            System.out.println(f);
        }
    }

}
