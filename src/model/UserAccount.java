package model;

import java.math.BigDecimal;

public class UserAccount {
    private String name;
    private String lastName;
    private String cardName;
    private String pinCode;
    private BigDecimal balance;

    public UserAccount() {
    }

    public UserAccount(String name, String lastName, String cardName, String pinCode, BigDecimal balance) {
        this.name = name;
        this.lastName = lastName;
        this.cardName = cardName;
        this.pinCode = pinCode;
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }


    @Override
    public String toString() {
        return "UserAccount{" +
                "name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", cardName='" + cardName + '\'' +
                ", pinCode='" + pinCode + '\'' +
                ", balance=" + balance +
                '}';
    }
}
