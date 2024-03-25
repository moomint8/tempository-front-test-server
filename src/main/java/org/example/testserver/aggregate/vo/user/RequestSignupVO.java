package org.example.testserver.aggregate.vo.user;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RequestSignupVO {
    private String name;
    private String nickname;
    private String email;
    private String password;
}
