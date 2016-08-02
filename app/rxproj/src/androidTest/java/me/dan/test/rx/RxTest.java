/*
 * RxTest
 * Version : 1.0
 * 2016-08-01
 * CopyRight (c) Huami Company 2015
 *      All rights reserved
 */
package me.dan.test.rx;

import java.util.concurrent.TimeUnit;

import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

import me.dan.test.danlib.me.dan.test.danlib.util.Log;

/**
 * @author DanLv
 */
public class RxTest extends AndroidTestCase {
    @Override
    public void setUp() throws Exception {
        Log.d("DDDD", "Test Setup!!");
    }

    @SmallTest
    public void testEmpty() {
        Observable
                .<Void>empty()
                .delay(5, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Void>() {
                    @Override
                    public void onCompleted() {
                        Log.d("DDDD", "OnCall!!");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Void aVoid) {

                    }
                });

        try {
            Thread.sleep(7 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
