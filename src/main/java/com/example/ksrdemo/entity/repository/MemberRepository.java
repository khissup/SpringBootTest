package com.example.ksrdemo.entity.repository;


import com.example.demo.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(String username);

    Boolean existsByUsernameOrEmail(String username,String email);

}
