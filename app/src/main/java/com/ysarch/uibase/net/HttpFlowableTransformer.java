package com.ysarch.uibase.net;


import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

import cn.droidlover.xdroidmvp.net.ErrCodeMessage;
import cn.droidlover.xdroidmvp.net.ResponseError;
import cn.droidlover.xdroidmvp.net.SimpleResponse;
import io.reactivex.Flowable;
import io.reactivex.FlowableOperator;
import io.reactivex.FlowableTransformer;
import io.reactivex.functions.Function;

/**
 * @author Morphine
 * @date 2018-04-20.
 */

public class HttpFlowableTransformer<T> implements FlowableTransformer<SimpleResponse<T>, T> {

    private Class<T> clazz;

    public HttpFlowableTransformer(Class<T> clazz) {
        this.clazz = clazz;
    }

    public HttpFlowableTransformer() {
    }

    @Override
    public Publisher<T> apply(Flowable<SimpleResponse<T>> upstream) {
        return upstream.flatMap(new Function<SimpleResponse<T>, Publisher<T>>() {
            @Override
            public Publisher<T> apply(SimpleResponse<T> response) throws Exception {
//                T data = response.data;
                T data = null;
                if (response.data != null) System.out.println("data t=>" + response.data.toString());

                if (response.data == null) {
                    Class cls = (Class) response.getClass().getGenericSuperclass();
                    if (cls == Object.class) {
                        if (clazz == null) {
                            data = (T) new Object();
                        } else {
                            data = clazz.newInstance();
                        }
                    }
                } else {
                    data = response.data;
                }

//                if (!response.status.equals(ErrCodeMessage.statusSuc)) {
//                    data = (T) new Object();
//                }

                return Flowable.just(data).lift(new FlowableOperator<T, T>() {
                    @Override
                    public Subscriber<? super T> apply(Subscriber<? super T> observer) throws Exception {

                        if (!ErrCodeMessage.isSuccess(response.code)) {
                            observer.onError(new ResponseError(response.code + "", response.message));
                        }
                        return observer;
                    }
                });
            }
        });

    }
}
