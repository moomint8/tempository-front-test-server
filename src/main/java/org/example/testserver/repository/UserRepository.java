package org.example.testserver.repository;

import org.example.testserver.aggregate.entity.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class UserRepository {
    private final ArrayList<User> userList = new ArrayList<User>();

    public UserRepository() {
        userList.add(new User(1, "홍길동", "RedLoad", "RedLoad@gmail.com", "1234", 0, 0));
        userList.add(new User(2, "김철수", "BlueWave", "BlueWave@gmail.com", "5678", 0, 0));
        userList.add(new User(3, "이영희", "GreenFire", "GreenFire@gmail.com", "abcd", 0, 0));
        userList.add(new User(4, "박영수", "YellowSun", "YellowSun@gmail.com", "efgh", 0, 0));
        userList.add(new User(5, "정미희", "PurpleRain", "PurpleRain@gmail.com", "ijkl", 0, 0));
        userList.add(new User(6, "최영호", "OrangeSky", "OrangeSky@gmail.com", "mnop", 0, 0));
        userList.add(new User(7, "이지수", "BlackStone", "BlackStone@gmail.com", "qrst", 0, 0));
        userList.add(new User(8, "박준호", "WhiteCloud", "WhiteCloud@gmail.com", "uvwx", 0, 0));
        userList.add(new User(9, "김영철", "SilverFish", "SilverFish@gmail.com", "yzab", 0, 0));
        userList.add(new User(10, "나혜미", "GoldMoon", "GoldMoon@gmail.com", "cdef", 0, 0));
        userList.add(new User(11, "송민지", "PinkStar", "PinkStar@gmail.com", "ghij", 0, 0));
        userList.add(new User(12, "황진우", "BrownEarth", "BrownEarth@gmail.com", "klmn", 0, 0));
        userList.add(new User(13, "김지현", "CyanSea", "CyanSea@gmail.com", "opqr", 0, 0));
        userList.add(new User(14, "정진우", "MagentaSky", "MagentaSky@gmail.com", "stuv", 0, 0));
        userList.add(new User(15, "박지영", "TurquoiseOcean", "TurquoiseOcean@gmail.com", "wxyz", 0, 0));
        userList.add(new User(16, "김영호", "VioletFlower", "VioletFlower@gmail.com", "123456", 0, 0));
        userList.add(new User(17, "이지민", "LavenderField", "LavenderField@gmail.com", "abcdef", 0, 0));
        userList.add(new User(18, "최지우", "LimeTree", "LimeTree@gmail.com", "ghijkl", 0, 0));
        userList.add(new User(19, "장혜진", "RubyGem", "RubyGem@gmail.com", "mnopqr", 0, 0));
        userList.add(new User(20, "강민수", "SapphireStone", "SapphireStone@gmail.com", "stuvwx", 0, 0));
        userList.add(new User(21, "홍지원", "EmeraldLeaf", "EmeraldLeaf@gmail.com", "yzabcd", 0, 0));
        userList.add(new User(22, "이준우", "AmberSky", "AmberSky@gmail.com", "efghij", 0, 0));
        userList.add(new User(23, "김지민", "DiamondStar", "DiamondStar@gmail.com", "klmnop", 0, 0));
        userList.add(new User(24, "정민지", "TopazSun", "TopazSun@gmail.com", "qrstuv", 0, 0));
        userList.add(new User(25, "박진우", "PearlMoon", "PearlMoon@gmail.com", "wxyzab", 0, 0));
        userList.add(new User(26, "이지현", "OpalLake", "OpalLake@gmail.com", "cdefgh", 0, 0));
        userList.add(new User(27, "김민지", "AquamarineSea", "AquamarineSea@gmail.com", "ijklmn", 0, 0));
        userList.add(new User(28, "황지민", "GarnetSky", "GarnetSky@gmail.com", "opqrstu", 0, 0));
        userList.add(new User(29, "장지원", "PeridotField", "PeridotField@gmail.com", "vwxyzab", 0, 0));
        userList.add(new User(30, "강민지", "TourmalineMountain", "TourmalineMountain@gmail.com", "cdefghi", 0, 0));
    }

    public boolean existNickname(String nickname) {
        for (User user : userList) {
            if (user.getNickname().equals(nickname)) {
                return false;
            }
        }
        return true;
    }

    public boolean existEmail(String email) {
        for (User user : userList) {
            if (user.getEmail().equals(email)) {
                return false;
            }
        }
        return true;
    }

    public User signUp(User user) {
        User newUser = new User(userList.size() + 1, user.getName(), user.getNickname(), user.getEmail(), user.getPassword(), 0, 0);
        userList.add(newUser);

        return newUser;
    }

    public User signIn(String email, String password) throws Exception {
        for (User user : userList) {
            if (user.getEmail().equals(email)) {
                if (user.getPassword().equals(password)) {
                    return user;
                }
            }
        }
        throw new Exception("회원 정보가 일치하지 않습니다.");
    }
}
