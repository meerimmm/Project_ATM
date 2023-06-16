package dao;

import model.UserAccount;

import java.util.ArrayList;
import java.util.List;

public class AccountDao {
    private List<UserAccount> userAccounts = new ArrayList<>();

    public List<UserAccount> getUserAccounts() {
        return userAccounts;
    }

    public void setUserAccounts(List<UserAccount> userAccounts) {
        this.userAccounts = userAccounts;
    }

    public UserAccount getUserByCardNumber(String cardNumber){
        return userAccounts.stream()
                .filter(s->s.getCardName().equals(cardNumber)).findFirst().orElse(null);
    }
}
