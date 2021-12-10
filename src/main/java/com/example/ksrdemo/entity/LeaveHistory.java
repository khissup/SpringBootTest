package com.example.ksrdemo.entity;

import com.example.ksrdemo.entity.exception.DomainException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "leave_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LeaveHistory {
  /*
   * 연차 사용 이력
   * */
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id", nullable = false)
  @JsonIgnore
  private Member member;

  @NotBlank
  @Column(nullable = false)
  private String comment;

  @Column(name = "used_leave_days", nullable = false)
  @DecimalMin(value = "0.25", message = "최소 0.25이상부터 연차를 사용할수 있습니다.")
  private Float usedLeaveDays;

  @Column(name = "is_canceled", nullable = false)
  @ColumnDefault("false")
  private Boolean isCanceled;

  @Column(nullable = false)
  private LocalDate startAt;

  @Column(nullable = false)
  private LocalDate endAt;

  @CreationTimestamp
  @Column(updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp private LocalDateTime modifiedAt;

  public void cancellableOrElseThrow() {
    if (this.startAt.isBefore(LocalDate.now())) {
      throw new DomainException("이미 사용한 연차라서 취소할수 없습니다.");
    }
    this.isCanceled = Boolean.TRUE;
  }
}
