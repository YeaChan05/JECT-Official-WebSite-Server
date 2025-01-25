package org.ject.support.domain.test.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.ject.support.common.exception.GlobalErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
class TestControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("intergration test")
    void intergration_test() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/api/test"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        assertThat(contentAsString).contains("SUCCESS");
        assertThat(contentAsString).isNotNull();
    }

    @Test
    @DisplayName("exception test")
    void exception_test() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/api/test/a"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        assertThat(contentAsString).doesNotContain("SUCCESS");
        assertThat(contentAsString).contains(GlobalErrorCode.METHOD_NOT_ALLOWED.getMessage());
    }
}
