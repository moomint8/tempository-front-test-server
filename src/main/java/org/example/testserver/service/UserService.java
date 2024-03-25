package org.example.testserver.service;

import org.example.testserver.aggregate.entity.User;
import org.example.testserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final SessionService sessionService;

    @Autowired
    public UserService(UserRepository userRepository, SessionService sessionService) {
        this.userRepository = userRepository;
        this.sessionService = sessionService;
    }

    public boolean existNickname(String nickname) {
        return userRepository.existNickname(nickname);
    }

    public boolean existEmail(String email) {
        return userRepository.existEmail(email);
    }

    public User signUp(User user) throws Exception {
        if (!userRepository.existEmail(user.getEmail())) {
            throw new Exception("중복된 이메일입니다.");
        }
        if (!userRepository.existNickname(user.getNickname())) {
            throw new Exception("중복된 닉네임입니다.");
        }

        return userRepository.signUp(user);
    }

    public User signIn(String email, String password) throws Exception {
        try {
            User result = userRepository.signIn(email, password);
            sessionService.signUp(result);

            return result;
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    public User whoAmI() {
        return sessionService.whoAmI();
    }

    public void logout() {
        sessionService.logout();
    }

    public User findUserByEmail(String email) throws Exception {

        return userRepository.selectUserByEmail(email);
    }

    public User updateUserInfo(String nickname, String name) throws Exception {
        User user = sessionService.whoAmI();

        if (nickname != null) {
            if (!userRepository.existNickname(nickname)) {
                throw new Exception("중복된 닉네임입니다.");
            }
            user.setNickname(nickname);
        }
        if (name != null) {
            user.setName(name);
        }

        return userRepository.updateUser(user);
    }

    public User updatePassword(String oldPassword, String newPassword) throws Exception {
        User user = sessionService.whoAmI();
        if (user.getPassword().equals(oldPassword)) {
            user.setPassword(newPassword);

            return userRepository.updateUser(user);
        }
        throw new Exception();
    }
}
