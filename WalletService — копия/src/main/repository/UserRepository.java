package repository;

import entities.User;

/*
 * интерфейс с методами пользователя
 */
public interface UserRepository {

    public void registration(User user);

    public void authorization(User user);

    public void chooseTransaction(User user);
    public void continueOrExit(User user);
    public void checkUserTransactions(User user);
    public  void debit(User user);
    public void credit (User user);
    public void checkBalance(User user);
}
