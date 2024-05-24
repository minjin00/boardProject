package com.example.boardexam.controller;

import com.example.boardexam.domain.Board;
import com.example.boardexam.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    // list - 페이지 보여주기
    @GetMapping("/list")
    public String listBoard(Model model,
                            @RequestParam(defaultValue = "1") int page,
                            @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page -1, size);

        Page<Board> boards = boardService.findAllBoard(pageable);
        model.addAttribute("boards", boards);
        model.addAttribute("currentPage", page);

        return "board/list";
    }

    // view - 특정 게시글의 상세 내용
    //view/id
    @GetMapping("/view/{id}")
    public String viewBoardById(@PathVariable Long id, Model model) {
        Board board = boardService.findBoardById(id);

        model.addAttribute("board", board);
        return "board/view";
    }

    //게시판 삭제
    @GetMapping("/delete/{id}")
    public String deleteBoard(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        boardService.deleteBoard(id);
        return "redirect:/list";
    }


    // 게시판 등록 폼
    @GetMapping("/writeform")
    public String writeBoard(Model model) {
        model.addAttribute("board", new Board());
        return "board/form";
    }
    // 게시판 등록 처리
    @PostMapping("/writeform")
    public String writeBoard(@ModelAttribute Board board) {
        boardService.writeBoard(board);
        return "redirect:/list";
    }


    // 게시글 삭제
    @GetMapping("/deleteform/{id}")
    public String deleteBoardForm(@PathVariable Long id, Model model) {
        model.addAttribute("id", id);
        return "board/deleteform";
    }
    @PostMapping("/deleteform/{id}")
    public String deleteBoard(@RequestParam Long id, @RequestParam String password,
                              RedirectAttributes redirectAttributes) {
        Board board = boardService.findBoardById(id);
        if (board != null && board.getPassword().equals(password)) {
            boardService.deleteBoard(id);
            return "redirect:/list";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Password 가 일치하지 않습니다.");
            return "redirect:/deleteform/" + id;
        }
    }

    // 게시글 정보 수정
    @GetMapping("/updateform/{id}")
    public String editBoardForm(@PathVariable Long id, Model model) {
        model.addAttribute("board", boardService.findBoardById(id));
        return "board/edit";
    }
    @PostMapping("/updateform/{id}")
    public String editBoardById(@PathVariable Long id, @RequestParam String password,
                                @ModelAttribute Board board, RedirectAttributes redirectAttributes) {
        Board editBoard = boardService.findBoardById(id);
        if(editBoard!= null && editBoard.getPassword().equals(password)) {
            board.setId(id);
            boardService.writeBoard(board);
            return "redirect:/view/" + id;
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Password 가 일치하지 않습니다.");
            return "redirect:/updateform/" + id;
        }

    }
}
