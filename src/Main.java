
import service.impl.AccountServiceImpl;

public class Main {
    public static void main(String[] args) {

        AccountServiceImpl accountService = new AccountServiceImpl();
        accountService.singUp("Meerim", "Ismanalieva");
        while (true) {
            accountService.singIn("Meerim", "Ismanalieva");
        }
    }
}