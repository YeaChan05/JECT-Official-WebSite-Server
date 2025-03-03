package org.ject.support.domain.recruit.dto;

import org.ject.support.domain.recruit.domain.Question;

public record QuestionResponse(Long id, String title, String body) {
    public static QuestionResponse from(final Question question) {
        return new QuestionResponse(question.getId(), question.getTitle(), question.getBody());
    }
}
