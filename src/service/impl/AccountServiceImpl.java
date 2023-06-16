package service.impl;

import dao.AccountDao;
import enums.AtmServices;
import model.UserAccount;
import service.AccountService;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.Scanner;

public class AccountServiceImpl implements AccountService {
    final AccountDao accountDao = new AccountDao();

    @Override
    public void singUp(String name, String lastName) {
        SecureRandom random=new SecureRandom();
        UserAccount userAccount = new UserAccount();

        userAccount.setName(name);
        userAccount.setLastName(lastName);

        int cardNumber = random.nextInt(10_000_000, 99_999_999);
        int pinCode = random.nextInt(1_000, 9_999);

        userAccount.setCardName(String.valueOf(cardNumber));
        userAccount.setPinCode(String.valueOf(pinCode));

        accountDao.getUserAccounts().add(userAccount);
        System.out.println(userAccount);

    }

    @Override
    public void singIn(String name, String lastName) {
        Scanner scanner=new Scanner(System.in);
        int choose = 0;
        try {
            ho:
            for (int i = 0; i < accountDao.getUserAccounts().size(); i++) {
                UserAccount user = accountDao.getUserAccounts().get(i);
                if (user.getName().equals(name) && user.getLastName().equals(lastName)) {
                    System.out.println("Выберите услугу : ");
                    String chooseStr ;
                    System.out.print(AtmServices.BAlANCE + " нажмите на 1 "
                            + "\n" + AtmServices.DEPOSIT + "нажмите на 2 "
                            + "\n" + AtmServices.SEND_MONEY_TO_A_FRIEND + " нажмите на 3"
                            + "\n" + AtmServices.WITHDRAW_MONEY + "  нажмите на 4 ");

                    while (true) {

                        chooseStr = scanner.nextLine();
                        if ("1234".contains(chooseStr) && !chooseStr.equals("")) {
                            choose = Integer.parseInt(chooseStr);
                            break ho;
                        } else if (!chooseStr.equals(""))
                            System.out.println("выберите услугу ! ");
                    }

                }
            }
            switch (choose) {
                case 1 -> {
                    System.out.println("Введите номер  своей карты");
                    String cardNumber = scanner.nextLine();

                    System.out.println("Введите пин код");
                    String pinCode = scanner.nextLine();
                    balance(cardNumber, pinCode);
                }
                case 2 -> {
                    System.out.println("Введите номер  своей карты ");
                    String cardNumber = scanner.nextLine();

                    System.out.println("Введите пин код");
                    String pinCode = scanner.nextLine();
                    deposit(cardNumber, pinCode);
                }
                case 3 -> {
                    System.out.println("введите номер карты друга ");
                    String friendCardNumber = scanner.nextLine();
                    sendToFriend(friendCardNumber);
                }
                case 4 -> {
                    System.out.println("Сколько денег хотите снять?");
                    int amount = scanner.nextInt();

                    withdrawMoney(amount);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
            }

    @Override
    public void balance(String cardNumber, String pinCode) {
        for (int i = 0; i < accountDao.getUserAccounts().size(); i++) {
            UserAccount user = accountDao.getUserAccounts().get(i);
            if (user.getCardName().equals(cardNumber) && user.getPinCode().equals(pinCode)) {
                System.out.println("Ваш баланс ");
                System.out.println(user.getBalance());
            } else {
                System.out.println("Не правильные данные : ");
            }
        }
    }

    @Override
    public void deposit(String cardNumber, String pinCode) {

        Scanner scanner = new Scanner(System.in);
        UserAccount user = accountDao.getUserByCardNumber(cardNumber);

        if (user != null && user.getPinCode().equals(pinCode)) {
            System.out.println("введите сумму : ");
            BigDecimal summa = scanner.nextBigDecimal();
            user.setBalance(summa);
            accountDao.getUserAccounts().add(user);
            System.out.println(" сумма пополнена  -> " + user);
        }else {
            System.out.println("не правильные данные ! ");
        }
    }
    @Override
    public void sendToFriend(String friendCardNumber) {
        UserAccount friendAccount = new UserAccount("Meerimm", "Ismanalievaa", "1234", "4321", new BigDecimal(10000));
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите номер карты ");
        String cardNumber = scanner.nextLine();

        System.out.println("Введите свой  пин код");
        String pinCode = scanner.nextLine();

        System.out.println("Сколько денег хотите перевести?");
        int amount = scanner.nextInt();
        for (int i = 0; i < accountDao.getUserAccounts().size(); i++) {
            UserAccount user = accountDao.getUserAccounts().get(i);

            if (user.getCardName().equals(friendCardNumber)) {
                friendAccount = user;
            }
        }
        for (int i = 0; i < accountDao.getUserAccounts().size(); i++) {
            UserAccount user = accountDao.getUserAccounts().get(i);
            if (user.getCardName().equals(cardNumber) && user.getPinCode().equals(pinCode)) {
                user.setBalance(user.getBalance().subtract(BigDecimal.valueOf(amount)));
                accountDao.getUserAccounts().add(user);

                friendAccount.setBalance(friendAccount.getBalance().add(BigDecimal.valueOf(amount)));

                accountDao.getUserAccounts().add(friendAccount);

                System.out.println(" Ваши данные :  " + user);
                System.out.println("Данные друга  : " + friendAccount);
            } else {
                System.out.println("Логин или пароль неверный");
            }

        }
    }

    @Override
    public void withdrawMoney(int amount) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Варианты обналички  : ");
        int[] denominations = {1000, 500, 200, 100, 50};
        int remainingAmount = amount;
        for (int denomination : denominations) {
            int count = amount / denomination;
            if (count > 0) {
                System.out.println(denomination + "->" + count + " купюр ");
                remainingAmount %= denomination;
            }
        }
        if (remainingAmount > 0) {
            System.out.println("Монетами 10р " + remainingAmount * 100 + "штук");
        }
        System.out.println("Выберите варинат обналички : ");
        int number = scanner.nextInt();
        switch (number) {
            case 1 -> System.out.println("1000->6 купюр снято ");
            case 2 -> System.out.println("500->12 купюр снято ");
            case 3 -> System.out.println("200->30 купюр снято ");
            case 4 -> System.out.println("100->60 купюр снято ");
            case 5 -> System.out.println(" 50->120 купюр снято ");
            case 6 -> System.out.println("Монетами 10р " + remainingAmount * 100 + "штук" + " снято ");
            default -> System.out.println("таких вариантов обналички нет ");
        }
        for (UserAccount userAccount1 : accountDao.getUserAccounts()) {
            int remains = (amount - remainingAmount);
            System.out.println("Сумма " + (amount - remainingAmount) + " успешно списана со счета ");
            System.out.println(" ваш текущий баланс - "+userAccount1.getBalance().subtract(BigDecimal.valueOf(remains)));

        }
    }
    }

