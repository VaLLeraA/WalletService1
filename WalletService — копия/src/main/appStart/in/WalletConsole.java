package appStart.in;

import entities.ActionOfUser;
import entities.Admin;
import entities.Transaction;
import entities.User;
import services.AdminService;
import repository.UserRepository;
import services.UserService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * класс-консоль для взаимодействия пользователей/администраторов с приложением
 */
public class WalletConsole {

    UserRepository userService = new UserService();
    AdminService adminServiceReady = new AdminService();

    /*
     * регистрация администратора
     * @param admin объект пользователя
     */
        public void registration(Admin admin) {
            if(!admin.isRegistered()) {

                System.out.println("Регистрация администратора");
                Scanner scanner = new Scanner(System.in);

                System.out.print("Введите ваше имя: ");
                admin.setName(scanner.nextLine());
                System.out.print("Введите ваш пароль: ");
                admin.setPassword(scanner.nextLine());
                System.out.println("Регистрация прошла успешно!");
            } else {
                System.out.println("Администратор уже зарегестрирован.");
            }
        }

    /*
     * регистрация администратора
     * @param admin объект администратора
     */
        public void authorization(Admin admin) {
            Scanner scanner = new Scanner(System.in);

            System.out.print("Введите ваше имя: ");
            while (!admin.getName().equals(scanner.nextLine())) {
                System.out.print("Вы ввели неверное имя. Повторите попытку: ");
            }
            System.out.print("Введите ваш пароль: ");
            while (!admin.getPassword().equals(scanner.nextLine())) {
                System.out.print("Вы ввели неверный пароль. Повторите попытку: ");
            }
            System.out.println("Авторизация прошла успешно!");
            admin.setAuthorized(true);
        }

    /*
     * просмотр действий пользователя
     * @param admin объект администратора
     * @param user объект пользователя
     */
        public void checkActionsHistory(Admin admin, User user) {
            if(admin.isAuthorized()) {
                System.out.println("История действий пользователя " + user.getName() + " :");
                for (int i = 0; i < user.getUserActions().size(); i++) {
                    System.out.println(user.getUserActions().get(i));
                }
            } else {
                System.out.println("Для просмотра действий пользователя администратор должен быть авторизован.");
            }
        }

    /*
     * регистрация пользователя
     * @param user объект пользователя
     */
    public void registration(User user){
        if(!user.isRegistered()) {
            System.out.println("Регистрация пользователя");
            Scanner scanner = new Scanner(System.in);

            System.out.print("Введите ваше имя: ");
            user.setName(scanner.nextLine());
            System.out.print("Введите ваш пароль: ");
            user.setPassword(scanner.nextLine());

            user.setTransactionsHistory(new ArrayList<Transaction>());
            user.setUserActions(new ArrayList<ActionOfUser>());
            user.setRegistered(true);

            System.out.println("Пользователь успешно зарегестрирован!");
            user.getUserActions().add(new ActionOfUser("регистрация"));
        } else {
            System.out.println("Пользователь уже зарегестрирован");
        }
    }

    /*
     * авторизация пользователя
     * @param user объект пользователя
     */
    public void authorization(User user){
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите ваше имя: ");
        while(!user.getName().equals(scanner.nextLine())){
            System.out.print("Вы ввели неверное имя. Повторите попытку: ");
        }
        System.out.print("Введите ваш пароль: ");
        while (!user.getPassword().equals(scanner.nextLine())){
            System.out.print("Вы ввели неверный пароль. Повторите попытку: ");
        }
        System.out.println("Авторизация прошла успешно!");
        chooseTransaction(user);
        user.getUserActions().add(new ActionOfUser("авторизация"));
    }

    /*
     * выбор типа транзакции
     * @param user объект пользователя
     */
    public void chooseTransaction(User user){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Выберете услугу:  \n '1' - снять наличные \n '2' - пополнить баланс \n '3' - проверить баланс \n '4' - посмотреть историю транзакций");
        switch (scanner.nextLine()) {
            case ("1") -> debit(user);
            case ("2") -> credit(user);
            case ("3") -> checkBalance(user);
            case ("4") -> checkUserTransactions(user);
            default -> {
                System.out.println("Такую услугу мы не предоставляем");
                continueOrExit(user);
            }
        }
    }

    /*
     * выбор другого типа транзакции или выход из системы
     * @param user объект пользователя
     */
    public void continueOrExit(User user){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите: \n '1' - если хотите воспользоваться другой услугой \n '2' - если хотите выйти из системы");
        switch (scanner.nextLine()){
            case ("1") -> chooseTransaction(user);
            case ("2") ->{
                System.out.println("Вы вышли из системы.");
            }
            default -> {
                System.out.println("Варианта с таким номером не сущетвует");
                continueOrExit(user);
            }
        }
    }

    /*
     * просмотр истории транзакций игрока
     * @param user объект пользователя
     */
    public void checkUserTransactions(User user){
        for (int i = 0; i < user.getTransactionsHistory().size(); i++) {
            System.out.println(user.getTransactionsHistory().get(i));
        }
        user.getUserActions().add(new ActionOfUser("просмотр истории транзакций"));
        continueOrExit(user);
    }

    /*
     * снятие среств со счета
     * @param user объект пользователя
     */
    public void debit(User user) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите числовой идентификатор транзакции: ");
        int idOfTransaction = scanner.nextInt();

        try {
            for (int i = 0; i < user.getTransactionsHistory().size(); i++) {
                if (idOfTransaction == user.getTransactionsHistory().get(i).getId()) {
                    throw new IOException();
                }
            }

            System.out.print("Введите сумму, которую хотите снять: ");
            long amountOfCash = scanner.nextLong();

            if (user.getBalance() > user.getBalance() - amountOfCash) {
                user.setBalance(user.getBalance() - amountOfCash);
                user.getTransactionsHistory().add(new Transaction(idOfTransaction, "снятие средств"));
                System.out.println("Транзакция прошла успешно!");
                continueOrExit(user);
            } else {
                System.out.println("На вашем счете недостаточно средств");
                continueOrExit(user);
            }
        } catch (IOException e) {
            System.out.println("Транзакция с таким идентификатором уже существует. Повторите попытку позже. ");
            continueOrExit(user);
        }
        user.getUserActions().add(new ActionOfUser("снятие средств"));
    }

    /*
     * пополнение счета
     * @param user объект пользователя
     */
    public void credit (User user) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите числовой идентификатор транзакции: ");
        int idOfTransaction = scanner.nextInt();

        try {
            for (int i = 0; i < user.getTransactionsHistory().size(); i++) {
                if (idOfTransaction == user.getTransactionsHistory().get(i).getId()) {
                    throw new IOException();
                }
            }

            System.out.print("Введите сумму пополнения: ");
            user.setBalance(user.getBalance() + scanner.nextLong());
            user.getTransactionsHistory().add(new Transaction(idOfTransaction, "пополнение баланса"));
            System.out.println("Транзакция прошла успешно!");
            continueOrExit(user);

        } catch (IOException e) {
            System.out.println("Транзакция с таким идентификатором уже существует. Повторите попытку позже. ");
            continueOrExit(user);
        }
        user.getUserActions().add(new ActionOfUser("пополнение счета"));
    }

    /*
     * просмотр баланса
     * @param user объект пользователя
     */
    public void checkBalance(User user){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите числовой идентификатор транзакции: ");
        int idOfTransaction = scanner.nextInt();

        try {
            for (int i = 0; i < user.getTransactionsHistory().size(); i++) {
                if (idOfTransaction == user.getTransactionsHistory().get(i).getId()) {
                    throw new IOException();
                }
            }

            user.getTransactionsHistory().add(new Transaction(idOfTransaction, "проверка баланса"));
            System.out.println("Ваш баланс: " + user.getBalance());
            continueOrExit(user);

        } catch (IOException e) {
            System.out.println("Транзакция с таким идентификатором уже существует. Повторите попытку позже. ");
            continueOrExit(user);
        }
        user.getUserActions().add(new ActionOfUser("проверка баланса"));
    }
}
