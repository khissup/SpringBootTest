package com.example.ksrdemo.service;

import com.example.ksrdemo.entity.LeaveHistory;
import com.example.ksrdemo.entity.Member;
import com.example.ksrdemo.entity.repository.LeaveHistoryRepository;
import com.example.ksrdemo.entity.repository.MemberRepository;
import com.example.ksrdemo.service.exception.EntityDoesNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;

@Service
public class LeaveHistoryService {

  private LeaveHistoryRepository leaveHistoryRepository;
  private MemberRepository memberRepository;

  @Autowired
  public LeaveHistoryService(
      LeaveHistoryRepository leaveHistoryRepository, MemberRepository memberRepository) {
    this.leaveHistoryRepository = leaveHistoryRepository;
    this.memberRepository = memberRepository;
  }

  public List<LeaveHistory> retrieveLeaveHistory(Long memberId) {
    return leaveHistoryRepository.findByMember_Id(memberId);
  }

  @Transactional
  public Float submitLeaveHistory(Long memberId, @Valid LeaveHistory leaveHistory) {
    Member member =
        memberRepository
            .findById(memberId)
            .orElseThrow(() -> new EntityDoesNotExistException(memberId, Member.class));

    member.deductLeaveDays(leaveHistory.getUsedLeaveDays());
    memberRepository.save(member);
    leaveHistory.setMember(member);
    leaveHistoryRepository.save(leaveHistory);

    return member.getCurrentLeaveDays();
  }

  @Transactional
  //@PreAuthorize("principal.")
  public Float cancelLeaveHistory(Long memberId, Long leaveHistoryId) {
    LeaveHistory leaveHistory =
        leaveHistoryRepository
            .findByIdAndMember_Id(leaveHistoryId, memberId)
            .orElseThrow(() -> new EntityDoesNotExistException(leaveHistoryId, LeaveHistory.class));

    Member member =
        memberRepository
            .findById(memberId)
            .orElseThrow(() -> new EntityDoesNotExistException(memberId, Member.class));

    leaveHistory.cancellableOrElseThrow();
    member.cancelLeaveDays(leaveHistory.getUsedLeaveDays());
    leaveHistoryRepository.save(leaveHistory);
    memberRepository.save(member);

    return member.getCurrentLeaveDays();
  }
}
