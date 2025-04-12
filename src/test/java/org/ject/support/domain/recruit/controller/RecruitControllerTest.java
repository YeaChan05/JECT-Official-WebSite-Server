package org.ject.support.domain.recruit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.ject.support.domain.member.JobFamily;
import org.ject.support.domain.recruit.domain.Semester;
import org.ject.support.domain.recruit.dto.RecruitRegisterRequest;
import org.ject.support.domain.recruit.repository.RecruitRepository;
import org.ject.support.domain.recruit.repository.SemesterRepository;
import org.ject.support.testconfig.AuthenticatedUser;
import org.ject.support.testconfig.IntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@IntegrationTest
@AutoConfigureMockMvc
@Transactional
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

    @Test
    @DisplayName("모집 정보를 등록합니다.")
    @AuthenticatedUser(isAdmin = true)
    void register_recruits() throws Exception {
        // given
        List<RecruitRegisterRequest> requests = List.of(
                new RecruitRegisterRequest(
                        JobFamily.BE,
                        LocalDateTime.now().minusDays(1),
                        LocalDateTime.now().plusDays(1)),
                new RecruitRegisterRequest(
                        JobFamily.FE,
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
}