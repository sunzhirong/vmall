package com.yslibrary.event.hailer;

/**
 * Created by fysong on 2020-03-03
 **/
public abstract class FunctionHasParamHasResult<T, P> extends Function {
    public FunctionHasParamHasResult(String functionName) {
        super(functionName);
    }

    public abstract T invokeFunction(P p);
}
