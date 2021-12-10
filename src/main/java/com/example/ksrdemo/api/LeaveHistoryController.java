package com.example.ksrdemo.api;

import com.example.demo.api.schema.CommonResponse;
import com.example.demo.api.schema.leave.LeaveRequestBody;
import com.example.demo.entity.LeaveHistory;
import com.example.demo.service.LeaveHistoryService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController("/members")
@RequiredArgsConstructor
public class LeaveHistoryController {

  private final LeaveHistoryService leaveHistoryService;

  @ApiOperation(value = "휴가(연차) 사용이력 조회")
  @GetMapping("/{memberId}/leave-histories")
  public ResponseEntity<List<LeaveHistory>> retrieveUserLeave(@PathVariable Long memberId) {

    return ResponseEntity.ok(leaveHistoryService.retrieveLeaveHistory(memberId));
  }

  @ApiOperation(value = "휴가(연차) 사용")
  @PostMapping("/{memberId}/leave-histories")
  public ResponseEntity<CommonResponse> submitLeaveHistory(
      @PathVariable Long memberId, @RequestBody @Valid LeaveRequestBody leaveRequestBody) {

    leaveRequestBody.calculateUsedLeaveDays();

    float remainedDays =
        leaveHistoryService.submitLeaveHistory(memberId, leaveRequestBody.convertToEntity());

    return ResponseEntity.ok(
        new CommonResponse(String.format("이제 연차가 %.2f일 남았습니다.", remainedDays)));
  }

  @ApiOperation(value = "상신한 휴가(연차) 취소")
  @DeleteMapping("/{memberId}/leave-histories/{leaveHistoryId}")
  public ResponseEntity<CommonResponse> cancelLeaveHistory(
      @PathVariable Long memberId,
      @PathVariable Long leaveHistoryId) {

    float remainedDays =
        leaveHistoryService.cancelLeaveHistory(memberId, leaveHistoryId);

    return ResponseEntity.ok(
        new CommonResponse(String.format("상신된 연차가 취소되었습니다. 이제 연차가 %.2f일 남았습니다.", remainedDays)));
  }
}
