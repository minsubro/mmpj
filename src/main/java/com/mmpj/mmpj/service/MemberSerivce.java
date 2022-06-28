package com.mmpj.mmpj.service;

import com.mmpj.mmpj.entity.Member;
import com.mmpj.mmpj.repository.Membersrepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class MemberSerivce {


    @Autowired
    private Membersrepository membersrepository;

    public void memberjoin(Member member) {
        membersrepository.save(member);
    }

    public boolean  idDupCheck(String id) {
        if(membersrepository.findByid(id) == null)
            return false;
        else
            return true;
    }

    public String logindatafind(String id, String pass) {
        Optional<Member> member = membersrepository.findByid(id);
        if (!member.isPresent())
            return "존재하지 않는 아이디 입니다.";

        System.out.println(pass + " " + member.get().getPassword());
        if (Objects.equals(pass, member.get().getPassword()))
            return null;
        else
            return "패스워드가 틀렸습니다";
    }
}
