package com.iprody.paymentservice.aop;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Response class in case of exception.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponse {

    /**
     * The server response code.
     */
    private int status;

    /**
     * The message from exception.
     */
    private String message;

    /**
     * The exception details list.
     */
    private List<String> details;
}
