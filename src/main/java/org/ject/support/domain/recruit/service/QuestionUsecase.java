package org.ject.support.domain.recruit.service;

import java.util.List;
import org.ject.support.domain.member.JobFamily;
import org.ject.support.domain.recruit.dto.QuestionResponse;

public interface QuestionUsecase {
    List<QuestionResponse> getQuestions(JobFamily jobFamily);
}
