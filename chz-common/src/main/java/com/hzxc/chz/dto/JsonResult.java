package com.hzxc.chz.dto;

import com.hzxc.chz.common.enums.ResultCodeEnum;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * create by chz on 2017/11/9
 */
public class JsonResult<T> {
    private int code;
    private String msg;
    private String cpparam;
    private T data;

    public String getCpparam () {
        return cpparam;
    }

    public void setCpparam (String cpparam) {
        this.cpparam = cpparam;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public JsonResult<T> data(T data){
        this.data = data;
        return this;
    }

    // 增加一个透传参数。
    public JsonResult(String cpp) {
        cpparam = cpp;
    }

    public JsonResult() {
    }

    public JsonResult<T> success(){
        return this.setCode(ResultCodeEnum.SUCCESS);
    }

    public JsonResult<T> setCode(ResultCodeEnum code) {
        this.code = code.val();
        this.msg = code.msg();
        return this;
    }

    public JsonResult<T> msg(String msg){
        this.msg = msg;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
