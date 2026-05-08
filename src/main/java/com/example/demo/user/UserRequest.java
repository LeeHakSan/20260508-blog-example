package com.example.demo.user;

import com.example.demo.core.errors.Exception400;
import lombok.Data;

public class UserRequest {
    @Data
    public static class LoginDTO {
        private String username;
        private String password;

        public void validate() {
            if (username.trim().isEmpty()) {
                throw new Exception400("사용자명을 입력하세요.");
            }
            if (password.trim().isEmpty() || password.length() <= 3) {
                throw new Exception400("비밀번호는 공백이 될 수 없으며, 3자 이상이어야 합니다.");
            }
        }
    }

    @Data
    public static class JoinDTO {
        private String username;
        private String password;
        private String email;

        public User toEntity() {
            return User.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .build();
        }

        public void validate() {
            if (username.trim().isEmpty()) {
                throw new Exception400("사용자명을 입력하세요.");
            }
            if (password.trim().isEmpty() || password.length() < 4) {
                throw new Exception400("비밀번호는 공백이 될 수 없으며, 4자 이상이어야 합니다.");
            }
            if (email.trim().isEmpty()) {
                throw new Exception400("이메일은 필수 입력입니다.");
            }
            if (!email.contains("@")) {
                throw new Exception400("올바른 이메일 주소를 입력하세요.");
            }
        }
    }

    @Data
    public static class UpdateDTO {
        private String password;

        public void validate() {
            if (password.trim().isEmpty()) {
                throw new Exception400("비밀번호는 필수 입니다.");
            }
            if (password.length() < 4) {
                throw new Exception400("비밀번호는 4자 이상이어야 합니다.");
            }
        }
    }
}
