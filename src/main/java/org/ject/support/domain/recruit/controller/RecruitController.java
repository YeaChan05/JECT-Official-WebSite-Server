package org.ject.support.domain.recruit.controller;

import lombok.RequiredArgsConstructor;
import org.ject.support.domain.recruit.dto.RecruitRegisterRequest;
import org.ject.support.domain.recruit.service.RecruitUsecase;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/recruits")
@RequiredArgsConstructor
public class RecruitController {

    private final RecruitUsecase recruitUsecase;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void registerRecruit(@RequestBody List<RecruitRegisterRequest> requests) {
        recruitUsecase.registerRecruits(requests);
    }
}
