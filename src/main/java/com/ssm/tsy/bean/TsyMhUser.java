package com.ssm.tsy.bean;

public class TsyMhUser {
    private Integer id;

    private String mhUserNumber;

    private String mhUserPassword;

    private Integer mhUserRoleId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMhUserNumber() {
        return mhUserNumber;
    }

    public void setMhUserNumber(String mhUserNumber) {
        this.mhUserNumber = mhUserNumber == null ? null : mhUserNumber.trim();
    }

    public String getMhUserPassword() {
        return mhUserPassword;
    }

    public void setMhUserPassword(String mhUserPassword) {
        this.mhUserPassword = mhUserPassword == null ? null : mhUserPassword.trim();
    }

    public Integer getMhUserRoleId() {
        return mhUserRoleId;
    }

    public void setMhUserRoleId(Integer mhUserRoleId) {
        this.mhUserRoleId = mhUserRoleId;
    }
}