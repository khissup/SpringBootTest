package com.example.ksrdemo.service;

import com.example.ksrdemo.entity.Member;
import com.example.ksrdemo.entity.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  MemberRepository memberRepository;

  RedisTemplate redisTemplate;

  @Autowired
  public UserDetailsServiceImpl(MemberRepository memberRepository, RedisTemplate redisTemplate) {
    this.memberRepository = memberRepository;
    this.redisTemplate = redisTemplate;
  }

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
    valueOperations.set("foo", "bar");

    System.out.println(valueOperations.get("foo"));

    Member user =
        memberRepository
            .findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("해당 회원은 존재하지 않습니다: " + username));

    return user;
  }
}
