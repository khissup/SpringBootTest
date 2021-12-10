package com.example.ksrdemo.api.schema.leave;

import com.example.ksrdemo.entity.LeaveHistory;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Setter
@ApiModel(description = "시작날짜~종료날짜 또는 usedLeaveDays가 채워져야합니다.")
public class LeaveRequestBody {

  @ApiModelProperty(example = "2021-06-25", dataType = "java.time.LocalDate")
  @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
  private LocalDate startAt;

  @ApiModelProperty(example = "2021-06-26", dataType = "java.time.LocalDate")
  @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
  private LocalDate endAt;

  @ApiModelProperty(example = "0.0", notes = "반차: 0.5, 반반차: 0.25, 연차: 1.0")
  private float usedLeaveDays;

  @ApiModelProperty(example = "여수 밤바다 보러 휴가 다녀오겠습니다.")
  @NotNull
  private String comment;

  public void calculateUsedLeaveDays() {
    if (this.usedLeaveDays != 0) {
      return;
    }
    Long days = ChronoUnit.DAYS.between(startAt, endAt) + 1L;
    this.usedLeaveDays = days.floatValue();
  }

  public String getComment() {
    return comment;
  }

  public LeaveHistory convertToEntity() {
    return new LeaveHistory(null, null, comment, usedLeaveDays, false, startAt, endAt, null, null);
  }
}
