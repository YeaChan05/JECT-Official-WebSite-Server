package org.ject.support.domain.recruit.service;

import org.ject.support.domain.recruit.domain.Question;
import org.ject.support.domain.recruit.domain.Recruit;
import org.ject.support.domain.recruit.dto.ApplyPortfolioDto;
import org.ject.support.domain.recruit.exception.ApplyException;
import org.ject.support.domain.recruit.repository.RecruitRepository;
import org.ject.support.domain.tempapply.service.TemporaryApplyService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.ject.support.domain.member.JobFamily.BE;
import static org.ject.support.domain.member.JobFamily.FE;
import static org.ject.support.domain.recruit.domain.Question.InputType.TEXT;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ApplyServiceTest {

    @InjectMocks
    ApplyService applyService;

    @Mock
    TemporaryApplyService temporaryApplyService;

    @Mock
    RecruitRepository recruitRepository;

    @Test
    @DisplayName("지원서 직군 변경")
    void change_job_family() {
        // given
        when(temporaryApplyService.hasSameJobFamilyWithRecentTemporaryApplication(1L, FE)).thenReturn(false);

        // when
        applyService.changeJobFamily(1L, FE);

        // then
        verify(temporaryApplyService).deleteTemporaryApplicationsByMemberId(1L);
    }

    @Test
    @DisplayName("변경 요청한 직군이 이전에 임시 저장한 지원서의 직군과 동일한 경우 실패")
    void change_job_family_Fail_by_duplicate_job_family() {
        // given
        when(temporaryApplyService.hasSameJobFamilyWithRecentTemporaryApplication(1L, FE)).thenReturn(true);

        // when, then
        assertThatThrownBy(() -> applyService.changeJobFamily(1L, FE))
                .isInstanceOf(ApplyException.class);
    }

    @Test
    @DisplayName("지원서 임시 저장 시 포트폴리오 최대 용량을 초과해 실패")
    void exceeded_portfolio_max_size() {
        // given
        Recruit recruit = Recruit.builder()
                .startDate(LocalDate.now().minusDays(1))
                .endDate(LocalDate.now().plusDays(1))
                .semester("2025-1")
                .jobFamily(BE)
                .build();
        recruit.addQuestion(Question.builder()
                .id(1L)
                .sequence(1)
                .inputType(TEXT)
                .isRequired(true)
                .title("title1")
                .body("question1")
                .build());

        when(recruitRepository.findActiveRecruits(LocalDate.now())).thenReturn(List.of(recruit));

        List<ApplyPortfolioDto> applyTemporaryPortfolios =
                List.of(new ApplyPortfolioDto("url", "name", "52428800", "1"),
                        new ApplyPortfolioDto("url", "name", "62428800", "2"));

        // when, then
        assertThatThrownBy(() ->
                applyService.applyTemporary(BE, 1L, Map.of("1", "answer"), applyTemporaryPortfolios))
                .isInstanceOf(ApplyException.class);
    }
}