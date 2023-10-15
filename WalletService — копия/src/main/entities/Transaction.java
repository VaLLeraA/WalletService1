package entities;

import lombok.*;

/*
 * класс описывает транзакции пользователя
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Transaction {

    private long id;
    private String transactionType;

}
