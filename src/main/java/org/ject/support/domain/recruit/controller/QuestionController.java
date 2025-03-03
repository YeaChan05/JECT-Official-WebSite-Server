package org.ject.support.domain.recruit.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.ject.support.domain.member.JobFamily;
import org.ject.support.domain.recruit.dto.QuestionResponse;
import org.ject.support.domain.recruit.service.QuestionUsecase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/question")
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionUsecase questionUsecase;

    @GetMapping
    public List<QuestionResponse> getQuestions(@RequestParam JobFamily jobFamily) {
        return questionUsecase.getQuestions(jobFamily);
    }
}
