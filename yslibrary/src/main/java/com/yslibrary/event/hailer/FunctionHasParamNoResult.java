package com.yslibrary.event.hailer;

/**
 * Created by fysong on 2020-03-03
 **/
public abstract class FunctionHasParamNoResult<P> extends Function {
    public FunctionHasParamNoResult(String functionName) {
        super(functionName);
    }


    public abstract void invokeFunction(P p);
}
