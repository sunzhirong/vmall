package com.ysarch.vmall.domain.bean;

import java.io.Serializable;

/**
 * Created by fysong on 26/10/2020
 **/
public class OssBean implements Serializable {

    /**
     * securityToken : CAIStgJ1q6Ft5B2yfSjIr5fRDd7dm+0WwbezdXbo11YhPeFIuZyYgDz2IHtFeXBsAuEet/4wm2FT7v8YlqFxSpZDXE3Na5OiWTKTT0XzDbDasumZsJbh4f/MQBp8YnyW9cvWZPqDHLG5U/yxalfCuzZuyL/hD1uLVECkNpv7wfwAac8MDC25diZhDtVbLRd5yqodLmCDGvuxFRToj2HMdmYK3DBxkmRi86+y79SB4x7F9j3Ax/QSup76L+rkDbBlN4wtVMyujq4kNPja2SRZ+nox/axt3qtf4mWF7JPPGFBb6gTBccisq4M3fVYoOfdmQ/AV8qGiyscV4LKDy97FrD9WJvxQXijlQ4St/dDJAuvBNKxiLuemYiiWg4jWbMaq4lh8OCgBVxlDft06MWR5AxU9Wr52lhonYdWgGoABPyjHOVxLo/ihIdShYF77b5HRj5zSc/4TTBUG8PUkTEa/o7SRfC41v064Pk0BHvMsrura2JQY6nP7dwERHLM+oGFlvmbjZXc7o3GNvZKiKWcCnPiHeDIi+SYuhc1iy2M5cefoNeC8Nz7Ra+I+ox7kNl+3QnnmlspX/Zb0Aa0Xeb0=
     * accessKeySecret : Fmbyobkn4YczcEN9n4BotZua7dgncFMdFCjSH5FL2MG1
     * accessKeyId : STS.NTdFdiv27vuXwPY3Vt1ndVY3b
     * expiration : 2020-10-26T15:42:59Z
     * requestId : 92759CAB-74E6-46D6-A1A4-12134243B423
     */

    private String securityToken;
    private String accessKeySecret;
    private String accessKeyId;
    private String expiration;
    private String requestId;

    public String getSecurityToken() {
        return securityToken;
    }

    public void setSecurityToken(String securityToken) {
        this.securityToken = securityToken;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}
