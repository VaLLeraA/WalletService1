package repository;

import entities.Admin;
import entities.User;

/*
 * интерфейс с методами администратора
 */
public interface AdminRepository {
    public void registration(Admin admin);

    public void authorization(Admin admin);
    public void checkActionsHistory(Admin admin, User user);
}
