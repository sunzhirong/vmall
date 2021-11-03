package com.ysarch.vmall.domain.bean;

public class UpdateBean {
    private boolean currentVersionNewest;
    private boolean forceUpdate;
    private String modifyContent;
    private String versionName;
    private int versionCode;
    private int os;

    public boolean isCurrentVersionNewest() {
        return currentVersionNewest;
    }

    public void setCurrentVersionNewest(boolean currentVersionNewest) {
        this.currentVersionNewest = currentVersionNewest;
    }

    public boolean isForceUpdate() {
        return forceUpdate;
    }

    public void setForceUpdate(boolean forceUpdate) {
        this.forceUpdate = forceUpdate;
    }

    public String getModifyContent() {
        return modifyContent;
    }

    public void setModifyContent(String modifyContent) {
        this.modifyContent = modifyContent;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public int getOs() {
        return os;
    }

    public void setOs(int os) {
        this.os = os;
    }
}
