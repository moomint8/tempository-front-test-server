package org.example.testserver.service;

import org.example.testserver.aggregate.entity.User;
import org.springframework.stereotype.Service;

@Service
public class SessionService {
    private static User session = null;

    public SessionService() {}

    public void signUp(User user) {
        session = user;
    }

    public void logout() {
        session = null;
    }

    public User whoAmI() {
        return session;
    }
}
