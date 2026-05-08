package com.example.demo.board;

import com.example.demo.core.errors.Exception400;
import com.example.demo.user.User;
import lombok.Builder;
import lombok.Data;

public class BoardRequest {

    @Data
    @Builder
    public static class SaveDTO {
        private String title;
        private String content;

        // DTO 에서 Entity로 변환하는 메서드
        public Board toEntity(User user) {
            return Board.builder()
                    .title(title)
                    .user(user)
                    .content(content)
                    .build();
        }

        // 유효성 검사 메서드
        public void validate() {
            if (this.title.trim().isEmpty()) {
                throw new Exception400("제목은 공백이 될 수 없습니다.");
            }
            if (this.content.trim().isEmpty() || this.content.length() <= 3) {
                throw new Exception400("내용은 공백이 될 수 없으며, 3자 이상이어야 합니다.");
            }
        }
    }

    @Data
    @Builder
    public static class UpdateDTO {
        private String username;
        private String title;
        private String content;

        public void validate() {
            if (this.title.trim().isEmpty()) {
                throw new Exception400("제목은 공백이 될 수 없습니다.");
            }
            if (this.content.trim().isEmpty() || this.content.length() <= 3) {
                throw new Exception400("내용은 공백이 될 수 없으며, 3자 이상이어야 합니다.");
            }
        }
    }

}
