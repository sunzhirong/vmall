package com.yslibrary.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * 数字相关的实用类
 *
 * @author Darcy yeguozhong@yeah.net
 */
public class NumberUtils {

    /**
     * 格式化浮点数,如果小数点后为0，则不保留后面的0
     *
     * @param value      值
     * @param maxDecimal 最多保留的小数点
     * @return formatFloat(1.111, 1) = 1.1 ; formatFloat(1.000,1) = 1
     */
    public static String formatJustDouble(double value, int maxDecimal) {
        if (maxDecimal == 0) {
            return String.valueOf((int) value);
        }
        StringBuilder pattenBuilder = new StringBuilder();
        pattenBuilder.append("0.");
        while (maxDecimal-- != 0) {
            pattenBuilder.append('#');
        }
        NumberFormat f = new DecimalFormat(pattenBuilder.toString());
        return f.format(value);
    }

    /**
     * 格式化浮点数,保留decimal位小数点
     *
     * @param value
     * @param decimal
     * @return
     */
    public static String formatReserveDouble(double value, int decimal) {
        if (decimal == 0) {
            return String.valueOf((int) value);
        }
        StringBuilder pattenBuilder = new StringBuilder();
        pattenBuilder.append("0.");
        while (decimal-- != 0) {
            pattenBuilder.append('0');
        }
        NumberFormat f = new DecimalFormat(pattenBuilder.toString());
        return f.format(value);
    }


    public static String readableNumber(long num, int base, String unit) {
        float k = num * 1.0F / base;
        return String.format(Locale.getDefault(), "%.1f%s", k, unit);

    }

    /**
     * 根据评分给出两位数的字符串结果，比如9.6，比如10
     *
     * @param value
     * @return
     */
    public static String readableScore(float value) {
        String formatted = String.format(Locale.getDefault(), "%.1f", value);

        int idxDot = formatted.indexOf(".");
        if (idxDot == 1) {
            // 9.6这种
            return formatted;
        } else if (idxDot == 2) {
            // 10.0这种
            return formatted.substring(0, 2);
        }

        return formatted;
    }

    public static float calF(long a, long b) {
        return b == 0 ? 0 : a * 1F / b;
    }

    /**
     * 返回白分比的整数
     *
     * @param a
     * @param b
     * @return
     */
    public static int cal100(long a, long b) {
        return b == 0 ? 0 : (int) (a * 100F / b);
    }

    /**
     * 返回白分比的整数
     *
     * @param a
     * @return
     */
    public static int cal100(float a) {
        return (int) (a * 100);
    }

    /**
     * 返回xx.xx
     *
     * @param a
     * @param b
     * @return
     */
    public static String cal100_00s(long a, long b) {
        float f = b == 0 ? 0 : a * 100F / b;
        return String.format("%.2f", f);
    }

    /**
     * 返回xx.xx
     *
     * @param a a < 1
     * @return
     */
    public static String cal100_00s(float a) {
        return String.format("%.2f", a * 100);
    }

    public static String cal100s(float a) {
        return String.valueOf((int) (a * 100));
    }
}
