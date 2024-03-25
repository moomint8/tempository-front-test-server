package org.example.testserver.aggregate.vo.user;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RequestUpdateInfoVO {
    String nickname;
    String name;
    String oldPassword;
    String newPassword;
}
