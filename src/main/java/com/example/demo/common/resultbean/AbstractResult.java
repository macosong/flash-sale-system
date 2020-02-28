package com.example.demo.common.resultbean;

import com.example.demo.common.enums.ResultStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * AbstractResult
 *
 * @author maco
 * @data 2019/10/24
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractResult {
    private ResultStatus status;
    private int code;
    private String message;

    protected AbstractResult(ResultStatus status, String message) {
        this.code = status.getCode();
        this.message = message;
        this.status = status;
    }

    protected AbstractResult(ResultStatus status) {
        this.code = status.getCode();
        this.message = status.getMessage();
        this.status = status;
    }

    /**
     * 直接传递结果状态的对象
     *
     * @param status
     * @return
     */
    public AbstractResult withError(ResultStatus status) {
        this.status = status;
        return this;
    }

    /**
     * 传入message，定义系统错误
     *
     * @param message
     * @return
     */
    public AbstractResult withError(String message) {
        this.status = ResultStatus.SYSTEM_ERROR;
        this.message = message;
        return this;
    }

    /**
     * 自定义错误result
     *
     * @param code
     * @param message
     * @return
     */
    public AbstractResult withError(int code, String message) {
        this.code = code;
        this.message = message;
        return this;
    }

    public static boolean isSuccess(AbstractResult result) {
        return result != null && result.status == ResultStatus.SUCCESS && result.getCode() == ResultStatus.SUCCESS.getCode();
    }

    public String getMessage() {
        return this.message == null ? this.status.getMessage() : this.message;
    }

    public AbstractResult success() {
        this.status = ResultStatus.SUCCESS;
        return this;
    }


}
