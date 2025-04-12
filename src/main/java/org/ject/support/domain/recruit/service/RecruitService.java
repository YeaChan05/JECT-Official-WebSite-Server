package org.ject.support.domain.recruit.service;

import lombok.RequiredArgsConstructor;
import org.ject.support.domain.member.JobFamily;
import org.ject.support.domain.member.service.OngoingSemesterProvider;
import org.ject.support.domain.recruit.domain.Recruit;
import org.ject.support.domain.recruit.dto.RecruitRegisterRequest;
import org.ject.support.domain.recruit.exception.RecruitErrorCode;
import org.ject.support.domain.recruit.exception.RecruitException;
import org.ject.support.domain.recruit.repository.RecruitRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecruitService implements RecruitUsecase {

    private final RecruitRepository recruitRepository;
    private final OngoingSemesterProvider ongoingSemesterProvider;

    @Override
    public void registerRecruits(List<RecruitRegisterRequest> requests) {
        // 1. 모집중인 기수 ID 조회
        Long ongoingSemesterId = ongoingSemesterProvider.getOngoingSemesterId();

        // 2. 이미 모집중인 직군인지 검증
        validateDuplicatedJobFamily(requests, ongoingSemesterId);

        // 3. recruit 엔티티 저장
        List<Recruit> recruits = requests.stream()
                .map(request -> request.toEntity(ongoingSemesterId))
                .toList();
        recruitRepository.saveAll(recruits);
    }

    private void validateDuplicatedJobFamily(List<RecruitRegisterRequest> requests, Long ongoingSemesterId) {
        List<JobFamily> jobFamilies = requests.stream()
                .map(RecruitRegisterRequest::jobFamily)
                .toList();
        if (recruitRepository.existsByJobFamilyAndStatusIsNotClosed(ongoingSemesterId, jobFamilies)) {
            throw new RecruitException(RecruitErrorCode.DUPLICATED_JOB_FAMILY);
        }
    }
}
