import Control.LoginControl;
import Control.Session;

public class main {

    public static void main(String[] args) {
        Session.startServer();
        new LoginControl().show();
    }
}
