package org.ject.support.domain.recruit.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.ject.support.domain.member.JobFamily;
import org.ject.support.domain.recruit.domain.Semester;
import org.ject.support.domain.recruit.dto.RecruitRegisterRequest;
import org.ject.support.domain.recruit.dto.SemesterRegisterRequest;
import org.ject.support.domain.recruit.repository.RecruitRepository;
import org.ject.support.domain.recruit.repository.SemesterRepository;
import org.ject.support.testconfig.AuthenticatedUser;
import org.ject.support.testconfig.IntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
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

    @Nested
    @DisplayName("기수 등록 API 테스트")
    class SemesterRegisterTests {
        @Test
        @AuthenticatedUser(isAdmin = true)
        @Rollback
        @DisplayName("학기와 모집 포지션을 등록한다")
        void register_semester() throws Exception {
            // given
            SemesterRegisterRequest request = new SemesterRegisterRequest("2023-2학기",
                    LocalDate.now(),
                    LocalDate.now().plusDays(1),
                    List.of(
                            new RecruitRegisterRequest(JobFamily.BE,
                                    LocalDateTime.of(2025, 4, 1, 0, 0),
                                    LocalDateTime.of(2025, 4, 2, 0, 0)),
                            new RecruitRegisterRequest(JobFamily.FE,
                                    LocalDateTime.of(2025, 4, 1, 0, 0),
                                    LocalDateTime.of(2025, 4, 2, 0, 0)
                            )));

            String reqJson = objectMapper.writeValueAsString(request);
            // when
            mockMvc.perform(post("/semesters")
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
        @DisplayName("중복된 JobFamily로 학기를 등록할 수 없다")
        void register_semester_with_duplicate_jobFamily() throws Exception {
            // given
            SemesterRegisterRequest request = new SemesterRegisterRequest("2023-2학기",
                    LocalDate.now(),
                    LocalDate.now().plusDays(1),
                    List.of(
                            new RecruitRegisterRequest(JobFamily.BE,
                                    LocalDateTime.of(2025, 4, 1, 0, 0),
                                    LocalDateTime.of(2025, 4, 2, 0, 0)),
                            new RecruitRegisterRequest(JobFamily.BE,
                                    LocalDateTime.of(2025, 4, 1, 0, 0),
                                    LocalDateTime.of(2025, 4, 2, 0, 0))// 중복된 jobFamily
                    ));

            String reqJson = objectMapper.writeValueAsString(request);
            // when
            mockMvc.perform(post("/semesters")
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

    @Nested
    @DisplayName("학기 조회 API 테스트")
    class SemesterInquiryTests {
        
        @BeforeEach
        void setUp() {
            // 테스트 데이터 준비
            semesterRepository.save(Semester.builder()
                    .name("2023-1학기")
                    .build());
            
            semesterRepository.save(Semester.builder()
                    .name("2023-2학기")
                    .build());
        }

        @Test
        @DisplayName("모든 기수 목록을 조회한다")
        void getAllSemesters() throws Exception {
            // when & then
            mockMvc.perform(get("/semesters"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpectAll(
                            content().string(containsString("2023-1학기")),
                            content().string(containsString("2023-2학기"))
                    );
        }
    }
}
