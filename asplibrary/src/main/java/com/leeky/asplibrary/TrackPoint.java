package com.leeky.asplibrary;

/**
 * Created by Leeky on 2020/11/16.
 */
public class TrackPoint {

    private static TrackCallBack mTrackCallBack;

    private TrackPoint() {

    }

    /**
     * 初始化
     * @param trackCallBack
     */
    public static void init(TrackCallBack trackCallBack) {
        mTrackCallBack = trackCallBack;
    }

    static void onClick(String pageName, String viewIdName) {
        if (mTrackCallBack == null) {
            return;
        }
        mTrackCallBack.onClick(pageName, viewIdName);
    }

    static void onPageOpen(String pageName) {
        if (mTrackCallBack == null) {
            return;
        }
        mTrackCallBack.onPageOpen(pageName);
    }

    static void onPageClose(String pageName) {
        if (mTrackCallBack == null) {
            return;
        }
        mTrackCallBack.onPageClose(pageName);
    }

}
