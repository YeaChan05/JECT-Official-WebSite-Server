package org.ject.support.domain.recruit.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.List;
import org.ject.support.domain.member.JobFamily;
import org.ject.support.domain.recruit.dto.RecruitRegisterRequest;
import org.ject.support.domain.recruit.dto.SemesterRegisterRequest;
import org.ject.support.domain.recruit.repository.RecruitRepository;
import org.ject.support.domain.recruit.repository.SemesterRepository;
import org.ject.support.testconfig.AuthenticatedUser;
import org.ject.support.testconfig.IntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@IntegrationTest
@AutoConfigureMockMvc
@Transactional
class SemesterControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    RecruitRepository recruitRepository;

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private SemesterRepository semesterRepository;

    @Test
    @AuthenticatedUser(isAdmin = true)
    @Rollback
    @DisplayName("register semester with recruitment")
    void register_semester() throws Exception {
        // given
        SemesterRegisterRequest request = new SemesterRegisterRequest("2023-2학기",
                LocalDate.now(),
                LocalDate.now().plusDays(1),
                List.of(
                        new RecruitRegisterRequest(JobFamily.BE),
                        new RecruitRegisterRequest(JobFamily.FE)
                ));

        String reqJson = objectMapper.writeValueAsString(request);
        // when
        mockMvc.perform(post("/semesters/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reqJson))
                .andExpect(status().isOk())
                .andExpect(
                        content().string(containsString("SUCCESS"))
                );
        TestTransaction.flagForCommit();
        TestTransaction.end();

        // then
        assertThat(semesterRepository.findAll()).hasSize(1);
        assertThat(recruitRepository.findAll()).hasSize(2);

    }

    @Test
    @AuthenticatedUser(isAdmin = true)
    @Rollback
    @DisplayName("register semester recruitment with duplicate jobFamily")
    void register_semester_with_duplicate_jobFamily() throws Exception {
        // given
        SemesterRegisterRequest request = new SemesterRegisterRequest("2023-2학기",
                LocalDate.now(),
                LocalDate.now().plusDays(1),
                List.of(
                        new RecruitRegisterRequest(JobFamily.BE),
                        new RecruitRegisterRequest(JobFamily.BE)// 중복된 jobFamily
                ));

        String reqJson = objectMapper.writeValueAsString(request);
        // when
        mockMvc.perform(post("/semesters/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reqJson))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(
                        content().string(containsString("DUPLICATED_JOB_FAMILY"))
                );

        // then
        assertThat(semesterRepository.findAll()).hasSize(0);
        assertThat(recruitRepository.findAll()).hasSize(0);
    }
}
