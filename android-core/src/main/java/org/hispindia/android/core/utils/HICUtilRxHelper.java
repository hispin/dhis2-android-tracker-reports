package org.hispindia.android.core.utils;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by NhanCao on 13-Sep-15.
 */
public class HICUtilRxHelper {

    private static final Observable.Transformer SCHEDULERS_TRANSFORMER = new Observable.Transformer<Observable<Object>, Observable<Object>>() {
        @Override
        public Observable<Observable<Object>> call(Observable<Observable<Object>> observable) {
            return observable.subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread());
        }
    };

    @SuppressWarnings("unchecked")
    public static <T> Observable.Transformer<T, T> applySchedulers() {
        return (Observable.Transformer<T, T>) SCHEDULERS_TRANSFORMER;
    }

    public static <T> void onNext(Subscriber<T> subscriber, T item) {
        if (!subscriber.isUnsubscribed()) {
            subscriber.onNext(item);
        }
    }

    public static void onError(Subscriber<?> subscriber, Exception e) {
        if (!subscriber.isUnsubscribed()) {
            subscriber.onError(e);
        }
    }

    public static void onCompleted(Subscriber<?> subscriber) {
        if (!subscriber.isUnsubscribed()) {
            subscriber.onCompleted();
        }
    }
}
