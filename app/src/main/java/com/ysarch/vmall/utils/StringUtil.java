package com.ysarch.vmall.utils;

import android.content.Context;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;

import com.yslibrary.utils.ArrayUtils;

import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Morphine
 * @date 2018-04-18.
 */

public class StringUtil {

    public static final Charset UTF8 = Charset.forName("UTF-8");
    private static final int M_BYTES = 1024 * 1024;
    private static final int G_BYTES = 1024 * 1024 * 1024;



    public static String correctUrl(String url){
        if(!TextUtils.isEmpty(url) && url.startsWith("//")){
            return "http:" + url;
        }
        return url;
    }

    /**
     * 转换Str为Int
     *
     * @param numStr
     * @param defaultVal
     * @return
     */
    public static int parseInt(String numStr, int defaultVal) {
        if (numStr == null) {
            return defaultVal;
        }
        try {
            return Integer.parseInt(numStr.trim());
        } catch (NumberFormatException e) {
            return defaultVal;
        }
    }

    /**
     * 提取url中的参数
     *
     * @param url url
     * @return 装着参数的 map
     */
    public static
    @NonNull
    Map<String, String> extractParams(String url) {
        Map<String, String> paramMap = new HashMap<>();

        if (isTrimEmpty(url)) {
            return paramMap;
        }

        int separatorIndex = url.indexOf('?');
        if (separatorIndex == -1) {
            return paramMap;
        }

        String paramsStr = url.substring(separatorIndex + 1);
        String[] paramPairs = paramsStr.split("&");
        if (ArrayUtils.isNotEmpty(paramPairs)) {
            for (String param : paramPairs) {
                String[] nameValue = param.split("=");
                if (nameValue.length != 2) {
                    continue;
                }
                try {
                    paramMap.put(nameValue[0], URLDecoder.decode(nameValue[1], "utf-8"));
                } catch (Exception e) {
                }
            }
        }
        return paramMap;
    }


    /**
     * 获取域名下的目录路径
     *
     * @return
     */
    public static String sufixPath(String url) {
        if (isTrimEmpty(url)) {
            return null;
        }

        int protocolIndex = url.indexOf("://");
        if (protocolIndex == -1) {
            return null;
        }

        int index = url.indexOf("/", protocolIndex + 3);
        if (index == -1 || index == url.length() - 1) {
            return "";
        }


        int qIndex = url.indexOf("?");
        String sufixP;
        if (qIndex != -1) {
            sufixP = url.substring(index + 1, qIndex);
        } else {
            sufixP = url.substring(index + 1);
        }
//

//        sufixP = url.substring(index + 1, url.indexOf("?") == -1 ? url.length() - index : );
        return sufixP;
    }


    public static boolean isTrimEmpty(String string) {
        if (string == null || string.length() == 0) {
            return true;
        } else {
            return string.trim().length() == 0;
        }
    }

    public static String trimZeroOnHeader(String string) {
        int len = string.length();
        String regExp = "^0*";
        String newStr = string.replaceAll(regExp, "");
        if (newStr.length() == 0) {
            newStr = "0";
        }
        return newStr;
    }


    /**
     * 验证是否是完好的密码
     * 6-16
     * 包含数字字母
     *
     * @param password
     * @return
     */
    public static boolean isValidatePassWord(String password) {
        String regex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(password);
        return m.matches();
    }


    /**
     * 手机号码格式是否正确
     *
     * @return
     */
    public static boolean checkPhone(String phoneNum) {
        if (isTrimEmpty(phoneNum)) {
            return false;
        }
        String regExp = "^1\\d{10}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(phoneNum);
        return m.matches();
    }


    /**
     * qq
     *
     * @return
     */
    public static boolean checkQQ(String qq) {
        String regExp = "[0-9]*";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(qq);
        return m.matches();
    }

    /**
     * weixin
     *
     * @return
     */
    public static boolean checkWechat(String wechat) {
//        String regExp = "^[a-zA-Z\\d_]{5,}$";
//        Pattern p = Pattern.compile(regExp);
//        Matcher m = p.matcher(wechat);
//        return m.matches();

        return true;
    }

    /**
     * 设置当前输入数量和最大数量提示
     *
     * @param editText 输入框,需设置tag,tag值为可输入最大的数量值(int)
     * @param textView
     * @param colorId  字数提示字体颜色
     */
    public static void setMaxCountTip(EditText editText, TextView textView, @ColorRes int colorId) {
        Context context = editText.getContext();
        int maxLength = Integer.parseInt((String) editText.getTag());
        int length = 0;
        if (!TextUtils.isEmpty(editText.getText().toString())) {
            length = editText.getText().toString().length();
        }
        SpannableString spannableString = new SpannableString(length + "/" + maxLength);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(context.getResources().getColor(colorId));
        spannableString.setSpan(colorSpan, 0, String.valueOf(length).length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        textView.setText(spannableString);
    }

    /**
     * 检查EditText是否为空,并弹出提示
     *
     * @param editText
     * @param tipMsg   提示内容
     * @return
     */
    public static boolean checkInputEmpty(TextView editText, String tipMsg) {
        if (TextUtils.isEmpty(editText.getText().toString())) {
            Toast.makeText(editText.getContext(), tipMsg, Toast.LENGTH_SHORT).show();
            return true;
        } else {
            if (TextUtils.isEmpty(editText.getText().toString().trim())) {
                Toast.makeText(editText.getContext(), tipMsg, Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        return false;
    }

    /**
     * 给edittext设置过滤器 过滤emoji
     *
     * @param et
     */
    public static void setEmojiFilter(EditText et) {
        InputFilter emojiFilter = new InputFilter() {
            Pattern pattern = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]", Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);

            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                Matcher matcher = pattern.matcher(source);
                if (matcher.find()) {
                    return "";
                }
                return null;
            }
        };
        et.setFilters(new InputFilter[]{emojiFilter});
    }

    /**
     * 设置edittext最大输入
     *
     * @param et
     * @param maxLength
     */
    public static void setEditMaxLength(EditText et, int maxLength) {
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!TextUtils.isEmpty(et.getText().toString()) && et.getText().toString().length() > maxLength) {
                    et.setText(et.getText().toString().substring(0, maxLength));
                    et.setSelection(et.getText().toString().length());
                }
            }
        });
    }

    /**
     * 隐藏真实姓名名字关键信息
     *
     * @param realName
     * @return
     */
    public static String hideRealNameInfo(String realName) {
        if (realName == null) return "";
        if (realName.length() > 1) {
            StringBuilder sb = new StringBuilder();
            for (int i = 1; i < realName.length(); i++) {
                sb.append("*");
            }
            return realName.charAt(0) + sb.toString();
        } else return realName;
    }

    /**
     * 隐藏身份证号关键信息
     *
     * @param cardNum
     * @return
     */
    public static String hideCardNumInfo(String cardNum) {
        if (cardNum == null) return "";
        if (cardNum.length() > 8) {
            StringBuilder sb = new StringBuilder();
            for (int i = 4; i < cardNum.length() - 4; i++) {
                sb.append("*");
            }
            return cardNum.substring(0, 4) + sb.toString() + cardNum.substring(cardNum.length() - 4);
        }
        return cardNum;
    }

    /**
     * 隐藏手机信息
     *
     * @param phone
     * @return
     */
    public static String hidePhoneInfo(String phone) {
        if (phone == null) return "";
        if (phone.length() > 7) {
            StringBuilder sb = new StringBuilder();
            for (int i = 3; i < phone.length() - 4; i++) {
                sb.append("*");
            }
            return phone.substring(0, 3) + sb.toString() + phone.substring(phone.length() - 4);
        }
        return phone;
    }


    public static String hideAccountInfo(String account) {
        if (account == null) return "";
        if (account.length() > 4) {
            StringBuilder sb = new StringBuilder();
            for (int i = 2; i < account.length() - 2; i++) {
                sb.append("*");
            }
            return account.substring(0, 2) + sb.toString() + account.substring(account.length() - 2);
        }
        return account;
    }

    /**
     * 格式化部落游戏文本
     *
     * @param gameList
     * @return
     */
    public static String formatTribeGame(List<String> gameList) {
        StringBuffer sb = new StringBuffer();
        if (gameList == null) return sb.toString();
        for (int i = 0; i < gameList.size(); i++) {
            sb.append(gameList.get(i));
            if (i != gameList.size() - 1)
                sb.append("/");
        }
        return sb.toString();
    }



    /**
     * 获取搜索结果内容变色html
     *
     * @param content
     * @param keyword
     * @return
     */
    public static Spanned getSearchSpanString(String content, String keyword) {
        if (TextUtils.isEmpty(content)) return Html.fromHtml("");
        if (TextUtils.isEmpty(keyword)) return Html.fromHtml(content);
        if (content.contains(keyword)) {
            String[] split = content.split(keyword);
            StringBuffer sb = new StringBuffer();

            for (int i = 0; i < split.length; i++) {
                sb.append(split[i]);
                if (i != split.length - 1) {
                    sb.append("<font color='#F33738'>");
                    sb.append(keyword);
                    sb.append("</font>");
                }
            }

            if (content.lastIndexOf(keyword) == content.length() - keyword.length()) {
                sb.append("<font color='#F33738'>");
                sb.append(keyword);
                sb.append("</font>");
            }
            return Html.fromHtml(sb.toString());
        }
        return Html.fromHtml(content);
    }


    /**
     * 根据规则显示下载人数
     *
     * @param textView
     * @param
     */
    public static void setDownloadNum(TextView textView, String num) {
        StringBuffer numText = new StringBuffer();
        numText.append(countNum(num));
        //numText.append("人学习");
        textView.setText(numText.toString());
    }


    /**
     * 根据规则计算人数
     *
     * @param downloadNum
     * @return
     */
    public static String countNum(String downloadNum) {
        float num = Float.parseFloat(downloadNum);
        StringBuffer numText = new StringBuffer(downloadNum);
        if (num > 10000) {
            num = (num / 10000);
            DecimalFormat format = new DecimalFormat("#.#");
            // 清空StringBuffer
            numText.setLength(0);
            numText.append(format.format(num)).append("万");
        }
        return numText.toString();
    }


    /**
     * 获取可视效果更好的容量大小
     *
     * @param bytes
     * @return
     */
    public static String getPrefStorageSize(long bytes) {
        if (bytes >= G_BYTES) {
            return String.format("%.2f GB", ((double) bytes) / G_BYTES);
        } else if (bytes >= M_BYTES) {
            return String.format("%.1f MB", ((double) bytes) / M_BYTES);
        } else if (bytes >= 1024) {
            return String.format("%.1f KB", bytes / 1024f);
        } else {
            return String.format("%s B", bytes);
        }
    }


    /**
     * 获取文件后缀名
     *
     * @param filename
     * @return
     */
    public static String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return filename;
    }

}
