package com.example.demo.exception;

import com.example.demo.common.enums.ResultStatus;

/**
 * GlobalException
 *
 * @author maco
 * @data 2019/10/24
 */
public class GlobalException extends RuntimeException {
    private ResultStatus status;

    public GlobalException(ResultStatus status) {
        super();
        this.status = status;
    }

    public ResultStatus getStatus() {
        return status;
    }

    public void setStatus(ResultStatus status) {
        this.status = status;
    }
}
