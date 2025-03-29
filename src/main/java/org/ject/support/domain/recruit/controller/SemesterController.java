package org.ject.support.domain.recruit.controller;

import lombok.RequiredArgsConstructor;
import org.ject.support.domain.recruit.dto.SemesterRegisterRequest;
import org.ject.support.domain.recruit.service.SemesterRegisterUsecase;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/semesters")
public class SemesterController {
    private final SemesterRegisterUsecase semesterRegisterUsecase;

    @PostMapping("/register")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void register(@RequestBody SemesterRegisterRequest request) {
        semesterRegisterUsecase.registerSemester(request);
    }
}
