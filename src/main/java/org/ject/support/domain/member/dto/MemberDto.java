package org.ject.support.domain.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.ject.support.domain.member.Role;
import org.ject.support.domain.member.entity.Member;

public class MemberDto {

    @Data
    @Builder
    @AllArgsConstructor
    public static class TempMemberJoinRequest {

        @NotBlank
        @Pattern(regexp = "^[가-힣]{1,5}$", message = "한글 1~5글자만 입력 가능합니다.")
        private String name;

        @NotBlank
        @Pattern(regexp = "^010\\d{8}$", message = "010으로 시작하는 11자리 숫자를 입력하세요.")
        private String phoneNumber;

        @NotNull
        @Size(max = 30)
        @Email(regexp = "^[a-zA-Z0-9._%+-]{1,20}@[a-zA-Z0-9.-]{1,10}\\.[a-zA-Z]{2,}$")
        private String email;

        public static Member toEntity(String name, String email, String phoneNumber) {
            return Member.builder()
                    .name(name)
                    .phoneNumber(phoneNumber)
                    .email(email)
                    .role(Role.TEMP)
                    .build();
        }
    }
}
