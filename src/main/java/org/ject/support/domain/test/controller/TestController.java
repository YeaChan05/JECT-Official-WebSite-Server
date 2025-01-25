package org.ject.support.domain.test.controller;

import lombok.RequiredArgsConstructor;
import org.ject.support.domain.test.dto.TestDto;
import org.ject.support.domain.test.usecase.TestUseCase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/test")
public class TestController {
    private final TestUseCase testUsecase;

    @GetMapping("/{id}")
    public TestDto getTest(@PathVariable Long id) {
        return testUsecase.get(id);
    }

    @PostMapping
    public void createTest(TestDto testDto) {
        testUsecase.save(testDto);
    }
}
