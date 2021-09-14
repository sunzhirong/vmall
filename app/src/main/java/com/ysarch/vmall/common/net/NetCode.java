package com.ysarch.vmall.common.net;

/**
 * Created by fysong on 08/10/2020
 **/
public class NetCode {
    public static final int CODE_TOKEN_INVALID = 401;


    public static boolean needToLogin(int code){
        return code == CODE_TOKEN_INVALID;
    }


}
