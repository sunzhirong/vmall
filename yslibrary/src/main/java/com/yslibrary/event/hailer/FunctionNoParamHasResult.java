package com.yslibrary.event.hailer;

/**
 * Created by fysong on 2020-03-03
 **/
public abstract class FunctionNoParamHasResult<T> extends Function {
    public FunctionNoParamHasResult(String functionName) {
        super(functionName);
    }



    public abstract T invokeFunction();
}
