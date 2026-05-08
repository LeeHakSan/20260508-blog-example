package com.example.demo.user;

import lombok.Data;

public class UserResponse {
    @Data
    public static class JoinDTO {
        private Integer id;
        private String username;
        private String email;

        public JoinDTO(User user) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.email = user.getEmail();
        }
    }

    @Data
    public static class SessionDTO extends User{
        public SessionDTO(User user) {
            super.setId(user.getId());
            super.setUsername(user.getUsername());
            super.setEmail(user.getEmail());
        }
    }
}
