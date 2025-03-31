package org.ject.support.domain.recruit.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.ject.support.domain.recruit.dto.SemesterListResponse;
import org.ject.support.domain.recruit.repository.SemesterRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SemesterInquiryService implements SemesterInquiryUsecase {

    private final SemesterRepository semesterRepository;

    @Override
    @Transactional(readOnly = true)
    public List<SemesterListResponse> getAllSemesters() {
        return semesterRepository.findAll().stream()
                .map(SemesterListResponse::from)
                .collect(Collectors.toList());
    }
}
