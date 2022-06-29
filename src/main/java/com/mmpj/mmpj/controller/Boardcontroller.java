package com.mmpj.mmpj.controller;

import com.mmpj.mmpj.entity.Board;
import com.mmpj.mmpj.service.BoardService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Controller
public class Boardcontroller {

    Membercontroller membercontroller;

    public Boardcontroller(Membercontroller membercontroller) {
        this.membercontroller = membercontroller;
    }

    @Autowired
    BoardService boardService;

    @GetMapping("board/list")
    public String boardlist(Model model) {
        List<Board> list;
        list = boardService.allboardlist();
        model.addAttribute("list", list);
        return "boardlist";
    }

    @PostMapping("/logout")
    public String logout(Model model) {
        membercontroller.islogin = false;
        membercontroller.nowmember = null;

        model.addAttribute("message", "로그아웃이 되었습니다");
        model.addAttribute("searchUrl", "/");

        return "message";
    }

    @GetMapping("/board/write")
    public String write() {
        return "boardwrite";
    }

    @PostMapping("/board/writepro")
    public String writepro(Board board, Model model) {
        //if (Objects.equals(board.getTitle(), ""))
        board.setUser(membercontroller.nowmember.getNickname());
        board.setDate(nowtime());
        board.setView(0);
        boardService.boarddatasave(board);
        return "return";
    }

    public String nowtime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm(E)"));
    }

    @GetMapping("/board/view")
    public String boardview(Model model, Integer pk) {
        model.addAttribute("board", boardService.findboardpk(pk));
        return "boardview";
    }

    @GetMapping("/board/delete")
    public String boarddelete (Integer pk, Model model) {

        String user = boardService.findboardpk(pk).getUser();
        if (!Objects.equals(membercontroller.nowmember.getNickname(), user)) {
            model.addAttribute("message", "삭제 권한이 없습니다.");
            model.addAttribute("searchUrl", "/board/view?pk=" + pk);
            return "message";
        }

        boardService.boarddelete(pk);
        model.addAttribute("message", "게시글이 삭제 되었습니다.");
        model.addAttribute("searchUrl", "/board/list");

        return "message";
    }

    @GetMapping("/board/modify")
    public String boardmodify(Integer pk, Model model) {
        String user = boardService.findboardpk(pk).getUser();
        if (!Objects.equals(membercontroller.nowmember.getNickname(), user)) {
            model.addAttribute("message", "수정 권한이 없습니다.");
            model.addAttribute("searchUrl", "/board/view?pk=" + pk);
            return "message";
        }
        Board board = boardService.findboardpk(pk);
        model.addAttribute("board", board);
        return "boardmodify";
    }

    @PostMapping("/board/update/{pk}")
    public String boardupdate(@PathVariable("pk") Integer pk, String title, String content, Model model) {
        System.out.println(pk);
        Board board = boardService.findboardpk(pk);
        board.setTitle(title);
        board.setContent(content);
        board.setDate(nowtime());
        boardService.boarddatasave(board);
        model.addAttribute("message", "수정이 완료되었습니다.");
        model.addAttribute("searchUrl", "/board/view?pk=" + pk);
        return "message";
    }
}
