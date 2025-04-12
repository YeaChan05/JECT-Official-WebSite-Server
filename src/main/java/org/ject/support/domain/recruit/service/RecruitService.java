package org.ject.support.domain.recruit.service;

import lombok.RequiredArgsConstructor;
import org.ject.support.domain.member.service.OngoingSemesterProvider;
import org.ject.support.domain.recruit.domain.Recruit;
import org.ject.support.domain.recruit.dto.RecruitRegisterRequest;
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
        Long ongoingSemesterId = ongoingSemesterProvider.getOngoingSemesterId();
        List<Recruit> recruits = requests.stream()
                .map(request -> request.toEntity(ongoingSemesterId))
                .toList();
        recruitRepository.saveAll(recruits);
    }
}
