package com.example.ksrdemo.entity;

import com.example.ksrdemo.entity.exception.DomainException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "member")
@NoArgsConstructor
public class Member implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name = "username", length = 50, unique = true)
  @NotNull
  @Size(min = 4, max = 50)
  private String username;

  @Column(name = "password", length = 100)
  @NotNull
  @Size(min = 4, max = 100)
  private String password;

  @Column(name = "email", length = 50, unique = true)
  @NotNull
  private String email;

  @Column(name = "current_leave_days")
  @ColumnDefault("0")
  @NotNull
  private Float currentLeaveDays;

  @Column(name = "ROLE", length = 50)
  @NotNull
  private String role = "ADMIN";

  public Member(String username, String email, String password, Float currentLeaveDays) {
    this.username = username;
    this.email = email;
    this.password = password;
    this.currentLeaveDays = currentLeaveDays;
  }
  public Member(Long memberId,String username, String email, String password, Float currentLeaveDays) {
    this.id = memberId;
    this.username = username;
    this.email = email;
    this.password = password;
    this.currentLeaveDays = currentLeaveDays;
  }

  public void deductLeaveDays(Float usedDays) {
    if (currentLeaveDays - usedDays < 0) {
      throw new DomainException("남은연차가 부족합니다.");
    }
    this.currentLeaveDays -= usedDays;
  }

  public void cancelLeaveDays(Float cancelDays){
    this.currentLeaveDays +=cancelDays;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    // 인가 로직은 구현 생략한다.
    return List.of(new SimpleGrantedAuthority("ADMIN"));
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Member user = (Member) o;
    return Objects.equals(id, user.id);
  }
}
