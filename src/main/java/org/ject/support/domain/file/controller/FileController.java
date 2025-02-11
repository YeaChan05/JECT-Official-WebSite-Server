package org.ject.support.domain.file.controller;

import lombok.RequiredArgsConstructor;
import org.ject.support.domain.file.dto.CreatePresignedUrlResponse;
import org.ject.support.external.s3.S3Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/upload")
@RequiredArgsConstructor
public class FileController {

    private final S3Service s3Service;

    // TODO 인증 어노테이션 추가
    @PostMapping("/url")
    public CreatePresignedUrlResponse createPresignedUrl(Long memberId, @RequestBody final String fileName) {
        return s3Service.createPresignedUrl(memberId, fileName);
    }
}
