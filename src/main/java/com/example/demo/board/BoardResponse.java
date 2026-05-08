package com.example.demo.board;

import com.example.demo.util.MyDataUtil;
import lombok.Data;

public class BoardResponse {
    @Data
    public static class ListDTO {
        private Integer id;
        private String title;
        private String username;
        private String createdAt;

        public ListDTO(Board board) {
            this.id = board.getId();
            this.title = board.getTitle();

            if (board.getUser() != null) {
                this.username = board.getUser().getUsername();
            }
            if (board.getCreatedAt() != null) {
                this.createdAt = MyDataUtil.timestampFormat(board.getCreatedAt());
            }
        }
    }

    @Data
    public static class DetailDTO {
        private Integer id;
        private String title;
        private String content;
        private String username;
        private Integer userId;

        public DetailDTO(Board board) {
            this.id = board.getId();
            this.title = board.getTitle();
            this.content = board.getContent();

            if (board.getUser() != null) {
                this.username = board.getUser().getUsername();
                this.userId = board.getUser().getId();
            }
        }
    }

}








