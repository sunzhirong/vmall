package com.ysarch.vmall.utils;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.ysarch.vmall.R;
import com.ysarch.vmall.domain.bean.KeyValueBean;
import com.ysarch.vmall.domain.constant.Constants;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by fysong on 23/09/2020
 **/
public class VMallUtils {
    public static String fixContentToFitScreen(String htmltext) {
        try {
            Document doc = Jsoup.parse(htmltext);
            Elements elements = doc.getElementsByTag("img");
            for (Element element : elements) {
                element.attr("width", "100%").attr("height", "auto");
                element.removeAttr("size");
                element.removeAttr("align");


                // 数据的链接都缺协议头
                String imgurl = element.attributes().get("src");
                if (!TextUtils.isEmpty(imgurl)) {
                    if (imgurl.startsWith("//")) {
                        element.attr("src", "http:" + imgurl);
                    }
                }
            }

            return doc.toString();
        } catch (Exception e) {
            return htmltext;
        }
    }

    public static String convertString(double value) {
//        return new DecimalFormat("0.00").format(value);

        if (value == 0)
            return "0";
        if (value == (int) ((value * 100) / 100)) {
            return (int) value + "";
        }

        return value + "";
    }

    public static String convertTo2String(double value) {
        return new DecimalFormat("0.00").format(value);

//        if (value == 0)
//            return "0";
//        if (value == (int) ((value * 100) / 100)) {
//            return (int) value + "";
//        }
//
//        return value + "";
    }

    public static String convertTo3String(double value) {
        return new DecimalFormat("0.000").format(value);
    }


    public static String correctUrl(String url) {
        if (!TextUtils.isEmpty(url)) {
            if (url.startsWith("//")) {
                url = "http:" + url;
            }
        }
        return url;
    }


    public static String convertPriceString(double price) {
        if (price < 0) {
            return "-" + getCurrencySign() + convertTo2String(price * (-1));
        }
        return getCurrencySign() + convertTo2String(price);
    }


    public static String decodeString(String value) {
        if (TextUtils.isEmpty(value))
            return "";
        String valueNew = value;
        try {
            valueNew = URLDecoder.decode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (valueNew.length() != value.length())
            return decodeString(valueNew);
        else {
            return valueNew;
        }
    }


    public static String getStringExactNull(String value) {
        if (value == null || value.equals("null")) {
            return "";
        }
        return value;
    }

    public static String getCurrencySign() {
        return "$";
    }


    /**
     * 商品来源平台名称
     *
     * @param platformType
     * @return
     */
    public static String getPlatformName(int platformType) {
        String name;
        switch (platformType) {
            case Constants
                    .TYPE_PLATFORM_JD:
                name = ResUtils.getString(R.string.label_platform_jd);
                break;
            case Constants
                    .TYPE_PLATFORM_PDD:
                name = ResUtils.getString(R.string.label_platform_pdd);
                break;
            case Constants
                    .TYPE_PLATFORM_1688:
                name = ResUtils.getString(R.string.label_platform_1688);
                break;
            case Constants
                    .TYPE_PLATFORM_TB:
            default:
                name = ResUtils.getString(R.string.label_platform_tb);
                break;
        }

        return name;
    }


    public static String get1688GoodsUrl(String goodsId) {
        return String.format("https://detail.1688.com/offer/%s.html", goodsId);
    }

    public static String extractID(String value) {
        String id = null;
        if (value.matches("^[tjpa]id[0-9]*$")) {
            if (value.startsWith("tid")) {
                id = value.substring(3);
            } else if (value.startsWith("aid")) {
                id = value.substring(3);
            }/*else  if(value.startsWith("tid")){

            } else  if(value.startsWith("tid")){

            }*/
        }
        return id;
    }


    /**
     * 判断手机号是否正确
     *
     * @param phone
     * @return
     */
    public static boolean checkPhoneLegal(String phone) {
        if (TextUtils.isEmpty(phone))
            return false;

        if (phone.startsWith("0")) {
            return phone.length() >= 9 && phone.length() <= 12;
        } else {
            return phone.length() >= 8 && phone.length() <= 12;
        }
    }


    /**
     * 密码6-16位
     *
     * @param password
     * @return
     */
    public static boolean checkPwdLegal(String password) {
        if (TextUtils.isEmpty(password)) {
            return false;
        }

        return password.length() >= 6 && password.length() <= 16;
    }


    public static String getProductAttr(String productAttr) {
        try {
            List<KeyValueBean> keyValueBeans = JSON.parseArray(productAttr, KeyValueBean.class);
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < keyValueBeans.size(); i++) {
                KeyValueBean bean = keyValueBeans.get(i);
                if (i == keyValueBeans.size() - 1) {
                    stringBuilder.append(bean.key).append(" ").append(bean.value);
                } else {
                    stringBuilder.append(bean.key).append(" ").append(bean.value).append(" | ");
                }
            }
//            for(KeyValueBean bean : keyValueBeans){
//                stringBuilder.append(bean.key).append(" ").append(bean.value).append("|");
//            }
            return stringBuilder.toString();
        } catch (Exception e) {
            return productAttr;
        }

    }


    public static String updateBirthday(String birthday){
        String s = GTMToLocal(birthday, "yyyy-MM-dd");
        return s.replaceAll("-","");
    }

    public static String GTMToLocal(String GTMDate) {
        return GTMToLocal(GTMDate,"yyyy-MM-dd HH:mm:ss");
    }

    public static String GTMToLocal(String GTMDate,String formatString) {
        int tIndex = GTMDate.indexOf("T");
        String dateTemp = GTMDate.substring(0, tIndex);
        String timeTemp = GTMDate.substring(tIndex + 1, GTMDate.length() - 6);
        String convertString = dateTemp + " " + timeTemp;
        SimpleDateFormat format;
        format = new SimpleDateFormat(formatString);
        Date result_date;
        long result_time = 0;
        if (null == GTMDate) {
            return GTMDate;
        } else {
            try {
                format.setTimeZone(TimeZone.getTimeZone("GMT00:00"));
                result_date = format.parse(convertString);
                result_time = result_date.getTime();
                format.setTimeZone(TimeZone.getDefault());
                return format.format(result_time);
            } catch (Exception e) {
                e.printStackTrace();
                return "";
            }
        }
//        return GTMDate;
    }

    //    public static String getTranceDateString(){
//
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
////        String now = sdf.format(new Date().getTime());
//
//        Calendar calendar1 = Calendar.getInstance();
//        calendar1.setTime(new Date());
//        calendar1.add(Calendar.DATE, 8);
//
//        Date date1 = calendar1.getTime();
//        String start  = sdf.format(date1);
//
//
//        Calendar calendar2 = Calendar.getInstance();
//        calendar2.setTime(new Date());
//        calendar2.add(Calendar.DATE, 16);
//
//        Date date2 = calendar2.getTime();
//        String end  = sdf.format(date1);
//
//
//
//        return date2 +"-"+end;
//    }
    public static String getTranceDateString() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        String now = sdf.format(new Date().getTime());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, 8);

        Date date1 = calendar.getTime();
        String next = sdf.format(date1);


        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(new Date());
        calendar2.add(Calendar.DATE, 16);
        Date date2 = calendar2.getTime();
        String end  = sdf.format(date2);
        return next + "-" + end;
    }
}
