package com.ysarch.vmall.domain.bean;

import java.io.Serializable;

/**
 * 登录结果
 * Created by fysong on 14/09/2020
 **/
public class LoginResult implements Serializable {

    /**
     * tokenHead : Bearer
     * token : eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiLmspnkuJYiLCJjcmVhdGVkIjoxNjAwMDUzNTE0NzQ2LCJleHAiOjE2MDA2NTgzMTR9.rhVQyqlOsBsV7w9x5STTjx-xbozqFnofRiSh8sXJbZXdxSMkNM0hmZSi3mY2dhGyH_zpyBkZPhuu8ZcMoCNEig
     */

    private String tokenHead;
    private String token;
    private MemberBean member;

    public MemberBean getMember() {
        return member;
    }

    public void setMember(MemberBean member) {
        this.member = member;
    }

    public String getTokenHead() {
        return tokenHead;
    }

    public void setTokenHead(String tokenHead) {
        this.tokenHead = tokenHead;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
