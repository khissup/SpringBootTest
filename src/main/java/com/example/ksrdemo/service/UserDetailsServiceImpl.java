package com.example.ksrdemo.service;
import com.example.demo.entity.Member;
import com.example.demo.entity.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    MemberRepository memberRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member user = memberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("해당 회원은 존재하지 않습니다: " + username));

        return user;
    }

}