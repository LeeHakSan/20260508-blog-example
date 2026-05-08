package com.example.demo.board;

import com.example.demo.core.errors.Exception403;
import com.example.demo.user.User;
import com.example.demo.util.MyDataUtil;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "board_tb")
@Builder
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @CreationTimestamp
    private Timestamp createdAt;

    public String getTime() {
        return MyDataUtil.timestampFormat(this.createdAt);
    }

    public void update(BoardRequest.UpdateDTO updateDTO) {
        this.title = updateDTO.getTitle();
        this.content = updateDTO.getContent();
    }

    public boolean isOwner(Integer sessionUserId) {
        if (!this.user.getId().equals(sessionUserId)) {
            throw new Exception403("본인이 작성한 게시글이 아닙니다.");
        }
        return true;
    }



}














