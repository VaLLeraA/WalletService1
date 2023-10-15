import appStart.in.WalletConsole;
import entities.User;

/*
 * главный класс программы
 */
public class Main {
    public static void main(String[] args) {

        WalletConsole walletConsole = new WalletConsole();
        User user = new User();

        walletConsole.registration(user);
        walletConsole.authorization(user);
    }
}