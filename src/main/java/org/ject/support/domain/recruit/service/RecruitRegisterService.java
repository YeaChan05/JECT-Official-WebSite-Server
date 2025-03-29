package org.ject.support.domain.recruit.service;

import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.ject.support.domain.recruit.domain.Recruit;
import org.ject.support.domain.recruit.dto.RegisterRecruitEvent;
import org.ject.support.domain.recruit.repository.RecruitRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
@RequiredArgsConstructor
public class RecruitRegisterService {
    private final RecruitRepository recruitRepository;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)// recruit 저장 실패시 전체 트랜잭션 rollback
    public void handleRegisterRecruitEvent(RegisterRecruitEvent event) {
        Long semesterId = event.id();
        LocalDate startDate = event.startDate();
        LocalDate endDate = event.endDate();
        List<Recruit> recruits = event.recruitRegisterRequests().stream()
                .map(request -> request.toEntity(semesterId, startDate, endDate))
                .toList();
        recruitRepository.saveAll(recruits);
    }
}
