package com.example.demo.common.resultbean;

import com.example.demo.common.enums.ResultStatus;

import java.io.Serializable;

/**
 * MyResult
 *
 * @author maco
 * @data 2019/10/24
 */
public class MyResult<T> extends AbstractResult implements Serializable {
    private static final long serialVersionUID = 867933019328199779L;
    private T data;
    private Integer count;

    protected MyResult(ResultStatus status, String message) {
        super(status, message);
    }

    protected MyResult(ResultStatus status) {
        super(status);
    }

    /**
     * 返回到模板的结果对象
     *
     * @param <T>
     * @return
     */
    public static <T> MyResult<T> build() {
        return new MyResult(ResultStatus.SUCCESS, null);
    }

    public static <T> MyResult<T> build(String message) {
        return new MyResult(ResultStatus.SUCCESS, message);
    }

    public static <T> MyResult<T> error(ResultStatus status) {
        return new MyResult<>(status);
    }



    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Integer getCount() {
        return this.count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public void success(T value) {
        this.success();
        this.data = value;
        this.count = 0;
    }
}
