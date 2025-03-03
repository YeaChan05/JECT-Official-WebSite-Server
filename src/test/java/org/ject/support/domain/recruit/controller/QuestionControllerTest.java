package org.ject.support.domain.recruit.controller;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import org.ject.support.domain.member.JobFamily;
import org.ject.support.domain.recruit.domain.Question;
import org.ject.support.domain.recruit.domain.Recruit;
import org.ject.support.domain.recruit.repository.RecruitRepository;
import org.ject.support.testconfig.IntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

@IntegrationTest
@AutoConfigureMockMvc
class QuestionControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    RecruitRepository recruitRepository;

    @Test
    @DisplayName("inquire questions")
    void inquire_questions() throws Exception {
        // given
        recruitRepository.save(RecruitFixture.getRecruit());
        // when & then
        mockMvc.perform(get("/question")
                        .param("jobFamily", JobFamily.BE.name()))
                .andExpectAll(
                        content().string(containsString("question1")),
                        content().string(containsString("question2")),
                        content().string(containsString("question3")),
                        content().string(containsString("answer1")),
                        content().string(containsString("answer2")),
                        content().string(containsString("answer3"))
                )
                .andExpect(status().isOk())
                .andDo(print());

    }

    private static class RecruitFixture {
        public static Recruit getRecruit() {
            Recruit recruit = Recruit.builder()
                    .jobFamily(JobFamily.BE)
                    .semester("2025-1")
                    .startDate(LocalDate.of(2025, 2, 28))
                    .endDate(LocalDate.of(2025, 3, 7))
                    .build();
            recruit.addQuestion(Question.builder()
                    .title("question1")
                    .body("answer1")
                    .build());

            recruit.addQuestion(Question.builder()
                    .title("question2")
                    .body("answer2")
                    .build());

            recruit.addQuestion(Question.builder()
                    .title("question3")
                    .body("answer3")
                    .build());
            return recruit;
        }
    }
}
