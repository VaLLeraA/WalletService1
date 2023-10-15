package entities;

import lombok.*;

import java.util.List;

/*
 * класс описывает пользователя
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {
    private String name;
    private String password;
    private long balance;
    private List<Transaction> transactionsHistory;
    private List<ActionOfUser> userActions;
    private boolean isRegistered = false;

}
