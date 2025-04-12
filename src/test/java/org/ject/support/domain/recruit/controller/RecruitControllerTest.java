package org.ject.support.domain.recruit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.ject.support.domain.recruit.domain.Recruit;
import org.ject.support.domain.recruit.domain.Semester;
import org.ject.support.domain.recruit.dto.RecruitRegisterRequest;
import org.ject.support.domain.recruit.repository.RecruitRepository;
import org.ject.support.domain.recruit.repository.SemesterRepository;
import org.ject.support.testconfig.AuthenticatedUser;
import org.ject.support.testconfig.IntegrationTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.ject.support.domain.member.JobFamily.BE;
import static org.ject.support.domain.member.JobFamily.FE;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@IntegrationTest
@AutoConfigureMockMvc
@AuthenticatedUser(isAdmin = true)
class RecruitControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    RecruitRepository recruitRepository;

    @Autowired
    SemesterRepository semesterRepository;

    @BeforeEach
    void setUp() {
        semesterRepository.save(Semester.builder().name("3기").isRecruiting(true).build());
    }

    @AfterEach
    void tearDown() {
        semesterRepository.deleteAll();
        recruitRepository.deleteAll();
    }

    @Test
    @DisplayName("모집 정보를 등록합니다.")
    void register_recruits() throws Exception {
        // given
        List<RecruitRegisterRequest> requests = List.of(
                new RecruitRegisterRequest(
                        BE,
                        LocalDateTime.now().minusDays(1),
                        LocalDateTime.now().plusDays(1)),
                new RecruitRegisterRequest(
                        FE,
                        LocalDateTime.now().minusDays(1),
                        LocalDateTime.now().plusDays(1))
        );

        String reqJson = objectMapper.writeValueAsString(requests);

        // when
        mockMvc.perform(MockMvcRequestBuilders.post("/recruits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reqJson))
                .andExpect(content().string(containsString("SUCCESS")));

        // then
        Assertions.assertThat(recruitRepository.findActiveRecruits(LocalDateTime.now())).hasSize(2);
    }

    @Test
    @DisplayName("이미 모집중인 직군에 대한 모집 등록 시 실패합니다.")
    void register_recruit_fail_by_duplicated_job_family() throws Exception {
        // given
        Long semesterId = semesterRepository.findOngoingSemesterId().orElse(1L);
        recruitRepository.save(Recruit.builder()
                .semesterId(semesterId)
                .startDate(LocalDateTime.now().minusDays(1))
                .endDate(LocalDateTime.now().plusDays(1))
                .jobFamily(BE)
                .build());

        List<RecruitRegisterRequest> requests = List.of(
                new RecruitRegisterRequest(BE, LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(1)),
                new RecruitRegisterRequest(FE, LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(1))
        );

        String reqJson = objectMapper.writeValueAsString(requests);

        // when, then
        mockMvc.perform(MockMvcRequestBuilders.post("/recruits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reqJson))
                .andExpect(content().string(containsString("DUPLICATED_JOB_FAMILY")));
    }
}