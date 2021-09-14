package com.ysarch.vmall.domain.bean;

import com.contrarywind.interfaces.IPickerViewData;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created by fysong on 2020/10/16
 **/
public class BankItemBean implements Serializable, IPickerViewData {

    /**
     * id : 1
     * bankName : 中国银行
     * bankNo : 123123
     * bankAccount : 柬前贸易
     */

    private long id;
    private String bankName;
    private String bankNo;
    private String bankAccount;

    private boolean select;

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    @Override
    public String getPickerViewText() {
        return bankName + " " + bankNo + " " + bankAccount;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BankItemBean that = (BankItemBean) o;
        return id == that.id &&
                Objects.equals(bankName, that.bankName) &&
                Objects.equals(bankNo, that.bankNo) &&
                Objects.equals(bankAccount, that.bankAccount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bankName, bankNo, bankAccount);
    }
}
