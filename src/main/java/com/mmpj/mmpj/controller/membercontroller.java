package com.mmpj.mmpj.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.mmpj.mmpj.entity.Member;

@Controller
public class membercontroller {


    @GetMapping("/")
    public String mainview() {
        return "mainview";
    }

    @GetMapping("/join")
    public String join() {
        return "join";
    }

    @PostMapping("/join/new")
    public String newjoin(Member member, Model model) {
        if(nullcheck(member) != null)
        {
            System.out.println(nullcheck(member) + "null");
            model.addAttribute("searchUrl", "redirect:/");
            return "message";
        }
        else
            return "redirect:/";
    }

    public String nullcheck(Member member) {
        if (member.getId() == "")
            return "Id";
        if (member.getPassword() == "")
            return "Password";
        if (member.getBirth() == "")
            return "Birth";
        if (member.getE_mail() == "")
            return "e-mail";
        if (member.getNickname() == "")
            return "nickname";
        return null;

    }


}
