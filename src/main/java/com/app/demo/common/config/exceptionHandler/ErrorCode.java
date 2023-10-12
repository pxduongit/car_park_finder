package com.app.demo.common.config.exceptionHandler;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class ErrorCode implements Serializable {
    private static final long serialVersionUID = 1L;

    private String code;

    private String message;

    private String fieldName;

    public ErrorCode(String code, String errorMessage) {
        this.code = code;
        this.message = errorMessage;
    }

    public ErrorCode(String code, String message, String category, String fieldName) {
        this.code = code;
        this.message = message;
        this.fieldName = fieldName;
    }

}
