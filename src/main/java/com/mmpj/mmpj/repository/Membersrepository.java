package com.mmpj.mmpj.repository;


import com.mmpj.mmpj.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface Membersrepository extends JpaRepository<Member, Integer>  {

    Optional<Member> findByid(String id);
}
