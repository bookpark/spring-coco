package kfq.springcoco.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UpdateRequest {
    @NotBlank
    @Size(min = 1, max = 20)
    private String nickname;
}
