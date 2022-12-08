package kfq.springcoco.dto;

import lombok.*;

import java.util.Set;


@AllArgsConstructor
@Getter
@Setter
@RequiredArgsConstructor
public class SignUpDto {

    private String email;
    private String nickname;
    private String password;
    private Set<String> role;

}
