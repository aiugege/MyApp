package com.leeky.asplibrary;

/**
 * Created by Leeky on 2020/11/16.
 */
public interface TrackCallBack {

    /**
     * 当View被点击
     *
     * @param pageName
     * @param viewIdName
     */
    void onClick(String pageName, String viewIdName);

    /**
     * 当页面打开时
     *
     * @param pageName
     */
    void onPageOpen(String pageName);

    /**
     * 当页面关闭时
     *
     * @param pageName
     */
    void onPageClose(String pageName);

}
