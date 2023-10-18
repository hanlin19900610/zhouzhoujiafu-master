package com.mufeng.libs.net;

import com.rxjava.rxlife.RxLife;
import com.rxjava.rxlife.Scope;

import java.util.List;

import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.BiFunction;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Function3;
import rxhttp.wrapper.param.RxHttp;

public class NetUtils {

    private RxHttp rxHttp;

    private NetUtils() {
    }

    public static NetUtils getInstance() {
        return new NetUtils();
    }

    public NetUtils get(String url, Object... formatArgs) {
        rxHttp = Net.INSTANCE.get(url, formatArgs);
        return this;
    }

    public NetUtils head(String url, Object... formatArgs) {
        rxHttp = Net.INSTANCE.head(url, formatArgs);
        return this;
    }

    public NetUtils postBody(String url, Object... formatArgs) {
        rxHttp = Net.INSTANCE.postBody(url, formatArgs);
        return this;
    }

    public NetUtils putBody(String url, Object... formatArgs) {
        rxHttp = Net.INSTANCE.putBody(url, formatArgs);
        return this;
    }

    public NetUtils patchBody(String url, Object... formatArgs) {
        rxHttp = Net.INSTANCE.patchBody(url, formatArgs);
        return this;
    }

    public NetUtils deleteBody(String url, Object... formatArgs) {
        rxHttp = Net.INSTANCE.deleteBody(url, formatArgs);
        return this;
    }

    public NetUtils postForm(String url, Object... formatArgs) {
        rxHttp = Net.INSTANCE.postForm(url, formatArgs);
        return this;
    }

    public NetUtils putForm(String url, Object... formatArgs) {
        rxHttp = Net.INSTANCE.putForm(url, formatArgs);
        return this;
    }

    public NetUtils patchForm(String url, Object... formatArgs) {
        rxHttp = Net.INSTANCE.patchForm(url, formatArgs);
        return this;
    }

    public NetUtils deleteForm(String url, Object... formatArgs) {
        rxHttp = Net.INSTANCE.deleteForm(url, formatArgs);
        return this;
    }

    public NetUtils postJson(String url, Object... formatArgs) {
        rxHttp = Net.INSTANCE.postJson(url, formatArgs);
        return this;
    }

    public NetUtils putJson(String url, Object... formatArgs) {
        rxHttp = Net.INSTANCE.putJson(url, formatArgs);
        return this;
    }

    public NetUtils patchJson(String url, Object... formatArgs) {
        rxHttp = Net.INSTANCE.patchJson(url, formatArgs);
        return this;
    }

    public NetUtils deleteJson(String url, Object... formatArgs) {
        rxHttp = Net.INSTANCE.deleteJson(url, formatArgs);
        return this;
    }

    public NetUtils postJsonArray(String url, Object... formatArgs) {
        rxHttp = Net.INSTANCE.postJsonArray(url, formatArgs);
        return this;
    }

    public NetUtils putJsonArray(String url, Object... formatArgs) {
        rxHttp = Net.INSTANCE.putJsonArray(url, formatArgs);
        return this;
    }

    public NetUtils patchJsonArray(String url, Object... formatArgs) {
        rxHttp = Net.INSTANCE.patchJsonArray(url, formatArgs);
        return this;
    }

    public NetUtils deleteJsonArray(String url, Object... formatArgs) {
        rxHttp = Net.INSTANCE.deleteJsonArray(url, formatArgs);
        return this;
    }

    public <T> void toResponse(
            Scope scope,
            Class<T> clazz,
            Function<Disposable, Void> onStart,
            Function<ResponseBean<T>, Void> onSuccess,
            BiFunction<String, String, Void> onError
    ) {
        rxHttp.toObservableResponse(clazz)
                .doOnSubscribe(onStart::apply)
                .to(RxLife.to(scope))
                .subscribe(onSuccess::apply, throwable -> {
                    String code = ThrowableExtKt.getCode(throwable);
                    String msg = ThrowableExtKt.getMsg(throwable);
                    onError.apply(code, msg);
                });
    }

//    public <T> void toResultResponse(
//            Scope scope,
//            Class<T> clazz,
//            Function<Disposable, Void> onStart,
//            Function<T, Void> onSuccess,
//            BiFunction<String, String, Void> onError
//    ) {
//        rxHttp.toObservableResultResponse(clazz)
//                .doOnSubscribe(onStart::apply)
//                .to(RxLife.to(scope))
//                .subscribe(onSuccess::apply, throwable -> {
//                    String code = ThrowableExtKt.getCode(throwable);
//                    String msg = ThrowableExtKt.getMsg(throwable);
//                    onError.apply(code, msg);
//                });
//    }


    public void download(
            Scope scope,
            String destPath,
            Function3<Integer, Long, Long, Void> onProgress,
            Function<Disposable, Void> onStart,
            Function<String, Void> onSuccess,
            BiFunction<String, String, Void> onError
    ) {
        rxHttp.toDownloadObservable(destPath, false)
                .onMainProgress(progress -> {
                    //下载进度回调,0-100，仅在进度有更新时才会回调，最多回调101次，最后一次回调文件存储路径
                    int currentProgress = progress.getProgress();//当前进度 0-100
                    Long currentSize = progress.getCurrentSize();//当前已下载的字节大小
                    Long totalSize = progress.getTotalSize();//要下载的总字节大小
                    onProgress.apply(currentProgress, currentSize, totalSize);
                })
                .doOnSubscribe(onStart::apply)
                .to(RxLife.to(scope))
                .subscribe(onSuccess::apply, throwable -> {
                    String code = ThrowableExtKt.getCode(throwable);
                    String msg = ThrowableExtKt.getMsg(throwable);
                    onError.apply(code, msg);
                });
    }

}
