package org.ject.support.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.ject.support.domain.semseter.SemesterRepository;
import org.ject.support.domain.semseter.exception.SemesterErrorCode;
import org.ject.support.domain.semseter.exception.SemesterException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OngoingSemesterProvider {
    private final SemesterRepository semesterRepository;

    public Long getOngoingSemesterId() {
        return semesterRepository.findOngoingSemester()
                .orElseThrow(() -> new SemesterException(SemesterErrorCode.ONGOING_SEMESTER_NOT_FOUND));
    }
}
