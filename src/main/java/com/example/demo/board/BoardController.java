package com.example.demo.board;

import com.example.demo.user.User;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/board/save-form")
    public String saveForm(HttpSession httpSession) {
        return "board/save-form";
    }

    @PostMapping("/board/save")
    public String saveProc(BoardRequest.SaveDTO saveDTO, HttpSession httpSession) {
        User sessionUser = (User) httpSession.getAttribute("sessionUser");
        saveDTO.validate();
        boardService.save(saveDTO, sessionUser);
        return "redirect:/";
    }

    @GetMapping({"/", "index"})
    public String list(Model model) {
        List<BoardResponse.ListDTO> boardList = boardService.findAll();
        model.addAttribute("boardList", boardList);
        return "board/list";
    }

    @GetMapping("/board/{id}")
    public String detailPage(@PathVariable(name = "id") Integer id, Model model) {
        BoardResponse.DetailDTO detailDTO = boardService.findBoardById(id);
        model.addAttribute("board", detailDTO);
        return "board/detail";
    }

    @GetMapping("/board/{id}/update-form")
    public String updateFormPage(@PathVariable(name = "id") Integer id, HttpSession httpSession, Model model) {
        User sessionUser = (User) httpSession.getAttribute("sessionUser");
        BoardResponse.DetailDTO detailDTO = boardService.updateBoardCheck(id, sessionUser);
        model.addAttribute("board", detailDTO);
        return "board/update-form";
    }

    @PostMapping("/board/{id}/delete")
    public String deleteProc(@PathVariable(name = "id") Integer id, HttpSession httpSession) {
        User sessionUser = (User) httpSession.getAttribute("sessionUser");
        boardService.delete(id, sessionUser);
        return "redirect:/";
    }

    @PostMapping("/board/{id}/update")
    public String updateProc(@PathVariable(name = "id") Integer id, BoardRequest.UpdateDTO updateDTO, HttpSession httpSession) {
        User sessionUser = (User) httpSession.getAttribute("sessionUser");
        updateDTO.validate();
        boardService.update(id, updateDTO, sessionUser);
        return "redirect:/board/" + id;
    }
}