package org.koreait.restcontroller;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RequestLogin {
    @NotBlank(message = "아이디를 입력하세요ㅡㅡ;")
    private String userId;

    @NotBlank(message = "비밀번호를 입력하세요ㅡㅡ;")
    private String userPw;
}
