package com.yslibrary.event.hailer;

/**
 * Created by fysong on 2020-03-03
 **/
public abstract class FunctionNoParamNoResult extends Function {
    public FunctionNoParamNoResult(String functionName) {
        super(functionName);
    }



    public abstract void invokeFunction();
}
