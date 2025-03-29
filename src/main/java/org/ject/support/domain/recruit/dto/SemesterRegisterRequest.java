package org.ject.support.domain.recruit.dto;

import java.time.LocalDate;
import java.util.List;

public record SemesterRegisterRequest(String name,
                                      LocalDate startDate,
                                      LocalDate endDate,
                                      List<RecruitRegisterRequest> recruitRegisterRequests) {
}
