package com.ysarch.vmall.helper;

import android.content.Context;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.ysarch.vmall.R;
import com.ysarch.vmall.component.dialog.PayPasswordVerifyDialog;
import com.ysarch.vmall.domain.bean.BankItemBean;
import com.ysarch.vmall.domain.bean.EnumBean;
import com.ysarch.vmall.utils.ResUtils;

import java.util.List;

/**
 * Created by fysong on 2020/10/16
 **/
public class DialogHelper {


    /**
     * 银行选择对话框
     *
     * @param context
     * @param bankItemBeans
     * @param bankItemBeanSelected
     * @param listener
     */
    public static void showBankDialog(Context context, List<BankItemBean> bankItemBeans, BankItemBean bankItemBeanSelected,
                                      OnOptionsSelectListener listener) {
        int index = 0;
        if (bankItemBeanSelected != null) {
            for (int i = 0; i < bankItemBeans.size(); i++) {
                if (bankItemBeans.get(i).getId() == bankItemBeanSelected.getId()) {
                    index = i;
                    break;
                }
            }
        }
        OptionsPickerView pvOptions = new OptionsPickerBuilder(context,
                listener)
                .setTitleText(ResUtils.getString(R.string.label_city_choose))
                .setDividerColor(ResUtils.getColor(R.color.gray_divide_line))
                .setTextColorCenter(ResUtils.getColor(R.color.colorPrimary)) //设置选中项文字颜色
                .setCancelColor(0xff666666)
                .setSubCalSize(14)
                .setCancelText(ResUtils.getString(R.string.label_cancel))
                .setSubmitText(ResUtils.getString(R.string.label_confirm))
                .setSubmitColor(0xff666666)
                .setContentTextSize(14)
                .build();
        pvOptions.setPicker(bankItemBeans);
        pvOptions.setSelectOptions(index);
        pvOptions.show();
    }

    /**
     * 提货仓库选择对话框
     *
     * @param context
     * @param warehouseDatas
     * @param warehouseSelected
     * @param listener
     */
    public static void showWarehouseDialog(Context context, List<EnumBean> warehouseDatas, EnumBean warehouseSelected,
                                           OnOptionsSelectListener listener) {
        int index = 0;
        if (warehouseSelected != null) {
            for (int i = 0; i < warehouseDatas.size(); i++) {
                if (warehouseDatas.get(i).getId() == warehouseSelected.getId()) {
                    index = i;
                    break;
                }
            }
        }
        OptionsPickerView pvOptions = new OptionsPickerBuilder(context,
                listener)
                .setTitleText(ResUtils.getString(R.string.label_warehouse_choose))
                .setDividerColor(ResUtils.getColor(R.color.gray_divide_line))
                .setTextColorCenter(ResUtils.getColor(R.color.colorPrimary)) //设置选中项文字颜色
                .setCancelColor(0xff666666)
                .setSubCalSize(14)
                .setCancelText(ResUtils.getString(R.string.label_cancel))
                .setSubmitText(ResUtils.getString(R.string.label_confirm))
                .setSubmitColor(0xff666666)
                .setContentTextSize(14)
                .build();
        pvOptions.setPicker(warehouseDatas);
        pvOptions.setSelectOptions(index);
        pvOptions.show();
    }


    /**
     * 选择发货方式
     * @param context
     * @param deliveryTypes
     * @param deliveryTypeSelected
     * @param listener
     */
    public static void showDeliveryType(Context context, List<EnumBean> deliveryTypes, EnumBean deliveryTypeSelected,
                                        OnOptionsSelectListener listener){
        int index = 0;
        if (deliveryTypeSelected != null) {
            for (int i = 0; i < deliveryTypes.size(); i++) {
                if (deliveryTypes.get(i).getId() == deliveryTypeSelected.getId()) {
                    index = i;
                    break;
                }
            }
        }
        OptionsPickerView pvOptions = new OptionsPickerBuilder(context,
                listener)
                .setTitleText(ResUtils.getString(R.string.label_mode_delivery_choose))
                .setDividerColor(ResUtils.getColor(R.color.gray_divide_line))
                .setTextColorCenter(ResUtils.getColor(R.color.colorPrimary)) //设置选中项文字颜色
                .setCancelColor(0xff666666)
                .setSubCalSize(14)
                .setCancelText(ResUtils.getString(R.string.label_cancel))
                .setSubmitText(ResUtils.getString(R.string.label_confirm))
                .setSubmitColor(0xff666666)
                .setContentTextSize(14)
                .build();
        pvOptions.setPicker(deliveryTypes);
        pvOptions.setSelectOptions(index);
        pvOptions.show();
    }

    /**
     * 支付密码验证
     *
     * @param confirmLabel
     * @param payCallback
     */
    public static void showPayPasswordVerifyDialog(Context context, String confirmLabel, PayPasswordVerifyDialog.PayCallback payCallback) {
        PayPasswordVerifyDialog.Builder builder = new PayPasswordVerifyDialog.Builder(context)
                .setCancelable(false)
                .setConfirmLabel(confirmLabel)
                .setPayCallback(payCallback);
        builder.build().show();
    }
}
