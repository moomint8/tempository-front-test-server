package org.example.testserver.service;

import org.example.testserver.aggregate.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTests {

    @Autowired
    UserService userService;
    @Autowired
    SessionService sessionService;

    @Test
    @DisplayName("회원가입 테스트")
    void sighUpSuccessTest() throws Exception {
        User user = new User();
        user.setName("새로운회원");
        user.setNickname("새로운닉네임");
        user.setEmail("새로운이메일@gmail.com");
        user.setPassword("1234");

        User response = userService.signUp(user);

        assertEquals(user.getName(), response.getName());
        assertEquals(user.getNickname(), response.getNickname());
        assertEquals(user.getEmail(), response.getEmail());
        assertEquals(user.getPassword(), response.getPassword());
        assertEquals(31, response.getId());
    }

    @Test
    @DisplayName("닉네임 중복 테스트")
    void existNickname() {
        assertTrue(userService.existNickname("없는닉네임"));
        assertFalse(userService.existNickname("RedLoad"));
    }

    @Test
    @DisplayName("닉네임 중복 테스트")
    void existEmail() {
        assertTrue(userService.existEmail("없는이메일@없어용.com"));
        assertFalse(userService.existEmail("RedLoad@gmail.com"));
    }

    @Test
    @DisplayName("로그인 성공 테스트")
    void signInSuccessTest() throws Exception {

        String email = "RedLoad@gmail.com";
        String password = "1234";

        User result = userService.signIn(email, password);

        assertEquals(result.getNickname(), "RedLoad");
        assertEquals(result, sessionService.whoAmI());
    }

    @Test
    @DisplayName("로그인 실패 테스트")
    void signInFailTest() {
        String email = "RedLoad@gmail.com";
        String password = "wrong password";

        assertThatThrownBy(() -> userService.signIn(email, password))
                .isInstanceOf(Exception.class);
    }

    @Test
    @DisplayName("로그아웃 테스트")
    void logoutTest() throws Exception {
        // given
        String email = "RedLoad@gmail.com";
        String password = "1234";

        User result;

        result = userService.signIn(email, password);
        assertEquals(result, sessionService.whoAmI());

        // when
        userService.logout();

        // then
        assertNull(sessionService.whoAmI());
    }

}