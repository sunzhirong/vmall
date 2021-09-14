package com.yslibrary.event.hailer;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by fysong on 2020-03-04
 **/
public class FunctionsManager {


    private Map<String, WeakReference<FunctionNoParamNoResult>> mFunctionNoParamNoResults = new HashMap<>();
    private Map<String, WeakReference<FunctionHasParamNoResult>> mFunctionHasParamNoResults = new HashMap<>();
    private Map<String, WeakReference<FunctionNoParamHasResult>> mFunctionNoParamHasResults = new HashMap<>();
    private Map<String, WeakReference<FunctionHasParamHasResult>> mFunctionHasParamHasResults = new HashMap<>();

    public static FunctionsManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public void addFunctionNoParamNoResult(FunctionNoParamNoResult functionNoParamNoResult) {
        mFunctionNoParamNoResults.remove(functionNoParamNoResult.functionName);
        mFunctionNoParamNoResults.put(functionNoParamNoResult.functionName,
                new WeakReference<>(functionNoParamNoResult));
    }

    public void addFunctionHasParamHasResult(FunctionHasParamHasResult functionHasParamHasResult) {
        mFunctionHasParamHasResults.remove(functionHasParamHasResult.functionName);
        mFunctionHasParamHasResults.put(functionHasParamHasResult.functionName,
                new WeakReference<>(functionHasParamHasResult));
    }

    public void addFunctionNoParamHasResult(FunctionNoParamHasResult functionNoParamHasResult) {
        mFunctionNoParamHasResults.remove(functionNoParamHasResult.functionName);
        mFunctionNoParamHasResults.put(functionNoParamHasResult.functionName,
                new WeakReference<>(functionNoParamHasResult));
    }

    public void addFunctionHasParamNoResult(FunctionHasParamNoResult functionHasParamNoResult) {
        mFunctionHasParamNoResults.remove(functionHasParamNoResult.functionName);
        mFunctionHasParamNoResults.put(functionHasParamNoResult.functionName,
                new WeakReference<>(functionHasParamNoResult));
    }


    public void invokeFunction(String functionName) {
        WeakReference<FunctionNoParamNoResult> weakReference = mFunctionNoParamNoResults.get(functionName);
        if (weakReference != null && weakReference.get() != null) {
            weakReference.get().invokeFunction();
        }
    }


    public <P> void invokeFunction(String functionName, P p) {
        WeakReference<FunctionHasParamNoResult> weakReference = mFunctionHasParamNoResults.get(functionName);
        if (weakReference != null && weakReference.get() != null) {
            weakReference.get().invokeFunction(p);
        }
    }


    public <T> T invokeFunction(String functionName, Class<T> t) {
        WeakReference<FunctionNoParamHasResult> weakReference = mFunctionNoParamHasResults.get(functionName);
        if (weakReference != null && weakReference.get() != null && t != null) {
            return t.cast(weakReference.get().invokeFunction());
        }

        return null;
    }

    public <T, P> T invokeFunction(String functionName, P p, Class<T> t) {
        WeakReference<FunctionHasParamHasResult> weakReference = mFunctionHasParamHasResults.get(functionName);
        if (weakReference != null && weakReference.get() != null && t != null) {
            return t.cast(weakReference.get().invokeFunction(p));
        }

        return null;
    }


    public void removeFunction(String functionName) {
        mFunctionHasParamHasResults.remove(functionName);
        mFunctionHasParamNoResults.remove(functionName);
        mFunctionNoParamNoResults.remove(functionName);
        mFunctionNoParamHasResults.remove(functionName);
    }

    private static class SingletonHolder {
        static FunctionsManager INSTANCE = new FunctionsManager();
    }
}
