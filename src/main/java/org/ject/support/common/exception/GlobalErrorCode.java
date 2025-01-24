package org.ject.support.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GlobalErrorCode implements ErrorCode {
    UNSUPPORTED_PARAMETER_TYPE("G-01", "Unsupported parameter type"),
    RESOURCE_NOT_FOUND("G-02", "Resource not found"),
    METHOD_NOT_ALLOWED("G-03","Method not allowed" );

    private final String code;
    private final String message;
}
