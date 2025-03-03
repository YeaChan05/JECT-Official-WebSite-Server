package org.ject.support.domain.recruit.service;

import java.util.Map;
import org.ject.support.domain.member.JobFamily;

public interface ApplyUsecase {
    Map<String, String> inquireApplication(JobFamily jobFamily, Long memberId);

    void applyTemporary(JobFamily jobFamily, Long memberId, Map<String, String> answers);
}
