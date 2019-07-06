package com.imall.exception;

import com.imall.enums.ExceptionEnum;

import lombok.Getter;

@Getter
public class ImException extends RuntimeException {

    private ExceptionEnum exceptionEnum;

    public ImException(ExceptionEnum exceptionEnum) {
        this.exceptionEnum = exceptionEnum;
    }


}