package online.fadai.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    private String username;
    private String password;
    private String phone;
    private String auth;
    private String photo;
    private Integer sex;
    private Integer age;
    private String cardId;
}
