package com.hzxc.chz.dto;

/**
 * create by chz on 2018/3/15
 */
public class AddFormIdRequest extends CommonRequest{
    private String formId;
    private int org;

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public int getOrg() {
        return org;
    }

    public void setOrg(int org) {
        this.org = org;
    }
}
