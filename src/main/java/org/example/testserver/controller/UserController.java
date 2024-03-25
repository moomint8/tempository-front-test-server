package org.example.testserver.controller;

import org.example.testserver.aggregate.entity.User;
import org.example.testserver.aggregate.vo.ResponseMessageVO;
import org.example.testserver.aggregate.vo.user.*;
import org.example.testserver.service.SessionService;
import org.example.testserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final SessionService sessionService;

    @Autowired
    public UserController(UserService userService, SessionService sessionService) {
        this.userService = userService;
        this.sessionService = sessionService;
    }

    @PostMapping("/exist")
    public ResponseEntity<ResponseMessageVO> existNickname(@RequestBody RequestExistVO request) {

        if (request.getNickname() != null && userService.existNickname(request.getNickname())) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessageVO("OK"));
        }
        if (request.getEmail() != null && userService.existEmail(request.getEmail())) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessageVO("OK"));
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessageVO("중복"));
    }

    @PostMapping("/signup")
    public ResponseEntity<ResponseMessageVO> signup(@RequestBody RequestSignupVO request) {
        try {
            User user = new User(request.getName(), request.getNickname(), request.getEmail(), request.getPassword());

            userService.signUp(user);

            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessageVO("회원가입 성공"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessageVO("회원가입 실패"));
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<ResponseMessageVO> signIn(@RequestBody RequestSignInVO request) {
        try {
            userService.signIn(request.getEmail(), request.getPassword());

            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessageVO("로그인 성공"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessageVO("회원 정보가 일치하지 않습니다."));
        }
    }

    @GetMapping("/who-am-i")
    public ResponseEntity<ResponseWhoAmIVO> whoAmI() {
        ResponseWhoAmIVO response = new ResponseWhoAmIVO();
        User user = userService.whoAmI();

        if (user != null) {
            response.setId(user.getId());
            response.setName(user.getName());
            response.setNickname(user.getNickname());
            response.setEmail(user.getEmail());
            response.setFollower(user.getFollower());
            response.setFollowing(user.getFollowing());

            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        response.setMessage("로그인 이후 사용해주세요.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/logout")
    public ResponseEntity<ResponseMessageVO> logout() {
        if (userService.whoAmI() != null) {
            userService.logout();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessageVO("로그아웃 성공"));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessageVO("로그인 이후 사용해주세요."));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<ResponseUserInfoVO> findUserByEmail(@PathVariable("email") String email) {
        ResponseUserInfoVO response = new ResponseUserInfoVO();

        if (sessionService.whoAmI() == null) {
            response.setMessage("로그인 이후 사용해주세요.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        try {
            User user = userService.findUserByEmail(email);
            response.setId(user.getId());
            response.setName(user.getName());
            response.setNickname(user.getNickname());
            response.setEmail(user.getEmail());
            response.setFollower(user.getFollower());
            response.setFollowing(user.getFollowing());

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            response.setMessage("존재하지 않는 회원입니다.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("id/{id}")
    public ResponseEntity<ResponseUserInfoVO> findUserById(@PathVariable("id") String id) {
        ResponseUserInfoVO response = new ResponseUserInfoVO();

        if (sessionService.whoAmI() == null) {
            response.setMessage("로그인 이후 사용해주세요.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        try {
            User user = userService.findUserById(Integer.parseInt(id));
            response.setId(user.getId());
            response.setName(user.getName());
            response.setNickname(user.getNickname());
            response.setEmail(user.getEmail());
            response.setFollower(user.getFollower());
            response.setFollowing(user.getFollowing());

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            response.setMessage("존재하지 않는 회원입니다.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PutMapping("/update-info")
    public ResponseEntity<ResponseUserInfoVO> updateInfo(@RequestBody RequestUpdateInfoVO request) {
        ResponseUserInfoVO response = new ResponseUserInfoVO();

        if (sessionService.whoAmI() == null) {
            response.setMessage("로그인 이후 사용해주세요.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        try {
            User user = userService.updateUserInfo(request.getNickname(), request.getName());
            response.setMessage("업데이트 성공");
            response.setId(user.getId());
            response.setName(user.getName());
            response.setNickname(user.getNickname());
            response.setEmail(user.getEmail());
            response.setFollower(user.getFollower());
            response.setFollowing(user.getFollowing());

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            response.setMessage("업데이트 실패");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

    }

    @PutMapping("/update-password")
    public ResponseEntity<ResponseUserInfoVO> updatePassword(@RequestBody RequestUpdateInfoVO request) {
        ResponseUserInfoVO response = new ResponseUserInfoVO();

        if (sessionService.whoAmI() == null) {
            response.setMessage("로그인 이후 사용해주세요.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        try {
            User user = userService.updatePassword(request.getOldPassword(), request.getNewPassword());
            response.setMessage("업데이트 성공");
            response.setId(user.getId());
            response.setName(user.getName());
            response.setNickname(user.getNickname());
            response.setEmail(user.getEmail());
            response.setFollower(user.getFollower());
            response.setFollowing(user.getFollowing());

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            response.setMessage("업데이트 실패");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
