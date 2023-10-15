package entities;

import lombok.*;

/*
 * класс описывает аминистратора
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Admin {

    private String name;
    private String password;
    private boolean isRegistered = false;
    private boolean isAuthorized = false;

}
