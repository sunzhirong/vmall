package cn.droidlover.xdroidmvp.net;

/**
 * Author: Ly
 * Data：2018/3/30-13:47
 * Description:
 */
public class ErrCodeMessage {
    /**
     * 成功的返回码
     */
    public static String statusSuc = "success";


    public static int CODE_SUCCESS = 200;


    public static int hasTribeCode=2002;
    public static int noLogin=1002;



    public static boolean isSuccess(int codeNet) {
        return CODE_SUCCESS == codeNet;
    }
}
