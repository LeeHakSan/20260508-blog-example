package com.example.demo.board;

import com.example.demo.core.errors.Exception403;
import com.example.demo.core.errors.Exception404;
import com.example.demo.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {
    private final BoardRepository boardRepository;

    public List<BoardResponse.ListDTO> findAll() {
        List<Board> boardList = boardRepository.findAllJoinUser();
//        boardList.get(0).getUser();
        return boardList.stream().map(BoardResponse.ListDTO::new).collect(Collectors.toList());
    }

    public BoardResponse.DetailDTO findBoardById(Integer id) {
        Board boardEntity = boardRepository.findByIdJoinUser(id).orElseThrow(
                () -> new RuntimeException("게시글이 존재하지 않습니다."));
        return new BoardResponse.DetailDTO(boardEntity);
    }

    @Transactional
    public void save(BoardRequest.SaveDTO saveDTO, User sessionUser) {
        Board board = saveDTO.toEntity(sessionUser);
        boardRepository.save(board);
    }

    public BoardResponse.DetailDTO updateBoardCheck(Integer id, User sessionUser) {
        BoardResponse.DetailDTO detailDTO = findBoardById(id);
        if (!detailDTO.getUserId().equals(sessionUser.getId())) {
            throw new Exception403("게시글 수정 권한이 없습니다.");
        }
        return detailDTO;
    }

    @Transactional
    public void update(Integer id, BoardRequest.UpdateDTO updateDTO, User sessionUser) {
        Board boardEntity = boardRepository.findByIdJoinUser(id).orElseThrow(() -> {
           throw new Exception404("게시글이 존재하지 않습니다.");
        });
        boardEntity.isOwner(sessionUser.getId());
        boardEntity.update(updateDTO);
    }

    @Transactional
    public void delete(Integer id, User sessionUser) {
        Board boardEntity = boardRepository.findById(id).orElseThrow(() -> {
            throw new Exception404("게시글이 존재하지 않습니다.");
        });
        boardEntity.isOwner(sessionUser.getId());
        boardRepository.deleteById(id);
    }
}








