package com.mmpj.mmpj.controller;

import com.mmpj.mmpj.repository.Membersrepository;
import com.mmpj.mmpj.service.MemberSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.mmpj.mmpj.entity.Member;

import java.util.Objects;

@Controller
public class Membercontroller {


    public Member nowmember;
    public Boolean islogin = false;

    @Autowired
    MemberSerivce memberSerivce;

    @Autowired
    Membersrepository membersrepository;

    @GetMapping("/")
    public String mainview() {
        if (islogin)
            return "return";
        return "mainview";
    }

    @GetMapping("/join")
    public String join() {
        return "join";
    }

    @PostMapping("/join/new")
    public String newjoin(Member member, Model model) {
        String save = nullcheck(member);

        if(save != null)
        {
            model.addAttribute("message", save);
            model.addAttribute("searchUrl", "/join");
            return "message";
        }
        else
        {
            memberSerivce.memberjoin(member);
            return "redirect:/";
        }

    }

    public String nullcheck(Member member) {
        if (Objects.equals(member.getId(), ""))
            return "아이디가 비어있습니다";
        if (Objects.equals(member.getPassword(), ""))
            return "비밀번호가 비어있습니다";
        if (Objects.equals(member.getE_mail(), ""))
            return "이메일이 비어있습니다";
        if (Objects.equals(member.getNickname(), ""))
            return "닉네임이 비어있습니다";
        if (memberSerivce.idDupCheck(member.getId()))
            return "이미 사용중인 아이디 입니다";
        return null;
    }

    @PostMapping("login/check")
    public String logincheck(String id, String password, Model model) {
        String check = memberSerivce.logindatafind(id, password);
        if (check == null) {
            nowmember = memberSerivce.findmember(id);
            islogin = true;
            return "redirect:/board/list";
        }
        else {
            model.addAttribute("message", check);
            model.addAttribute("searchUrl", "/");
            return "message";
        }
    }
}
