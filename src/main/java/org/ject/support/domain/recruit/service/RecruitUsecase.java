package org.ject.support.domain.recruit.service;

import org.ject.support.domain.recruit.dto.RecruitRegisterRequest;

import java.util.List;

public interface RecruitUsecase {

    /**
     * 모집 정보를 등록합니다.
     */
    void registerRecruits(List<RecruitRegisterRequest> requests);
}
