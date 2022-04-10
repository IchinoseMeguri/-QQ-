
/*
 * @Author: Meguri Ichinose
 * @Date: 2022-04-09 16:26:52
 * @Description: 
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        // new gui.Login().setVisible(true);
        gui.Chat chat = new gui.Chat("1");
        chat.setVisible(true);
        chat.NewLogin("user");
        Thread.sleep(5000);
        chat.NewLogout("user");

        // new gui.Register().setVisible(true);
        // new gui.FindPasswd("n").setVisible(true);
    }
}
