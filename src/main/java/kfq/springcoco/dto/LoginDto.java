package kfq.springcoco.dto;

import lombok.Data;

@Data
public class LoginDto {

    private String nicknameOrEmail;
    private String password;

}
