package org.example.testserver.aggregate.vo.user;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RequestSignInVO {
    private String email;
    private String password;
}
