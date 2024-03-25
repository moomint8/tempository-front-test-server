package org.example.testserver.aggregate.vo.user;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ResponseUserInfoVO {
    private String message;
    private int id;
    private String name;
    private String nickname;
    private String email;
    private int follower;
    private int following;
}
