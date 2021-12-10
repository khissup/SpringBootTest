package com.example.ksrdemo.entity.repository;

import com.example.ksrdemo.entity.LeaveHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LeaveHistoryRepository extends JpaRepository<LeaveHistory, Long> {
  Optional<LeaveHistory> findByIdAndMember_Id(@Param("id")Long id, @Param("memberID") Long memberId);

  List<LeaveHistory> findByMember_Id(@Param("memberID") Long memberId);
}
