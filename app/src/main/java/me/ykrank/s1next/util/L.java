package me.ykrank.s1next.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.tencent.bugly.crashreport.BuglyLog;
import com.tencent.bugly.crashreport.CrashReport;

import me.ykrank.s1next.BuildConfig;

import static me.ykrank.s1next.App.LOG_TAG;

/**
 * Created by AdminYkrank on 2016/4/20.
 * 对Log的包装
 */
public class L {

    public static void init(@NonNull Context context) {
        CrashReport.setIsDevelopmentDevice(context, BuildConfig.DEBUG);
        CrashReport.initCrashReport(context.getApplicationContext());
        Logger.init(LOG_TAG).logLevel(showLog() ? LogLevel.FULL : LogLevel.NONE);
    }

    public static void setUser(final String id, final String name) {
        CrashReport.setUserId("id:" + id + ",name:" + name);
    }

    public static boolean showLog() {
        return BuildConfig.DEBUG;
    }

    public static void d(String msg) {
        Logger.d(msg);
    }

    public static void d(String tag, String msg) {
        Logger.t(tag).d(msg);
    }

    public static void i(String msg) {
        Logger.i(msg);
    }

    public static void i(String tag, String msg) {
        Logger.t(tag).i(msg);
    }

    public static void w(String msg) {
        Logger.w(msg);
    }

    public static void w(String tag, String msg) {
        Logger.t(tag).w(msg);
    }

    public static void e(String msg) {
        e(null, msg, null);
    }

    public static void e(Throwable e) {
        e(null, null, e);
    }

    public static void e(String msg, Throwable tr) {
        e(null, msg, tr);
    }

    public static void e(String tag, String msg) {
        e(tag, msg, null);
    }

    public static void e(String tag, String msg, Throwable tr) {
        Logger.t(tag).e(tr, msg);
        BuglyLog.e(LOG_TAG + tag, msg, tr);
        if (showLog() && tr != null) {
            CrashReport.postCatchedException(tr);
        }
    }

    public static void report(Throwable tr) {
        report(tr, Log.WARN);
    }

    public static void report(Throwable tr, int severity) {
        CrashReport.postCatchedException(tr);
        BuglyLog.e(LOG_TAG, "Report error", tr);
    }

    public static void report(String msg, Throwable tr) {
        leaveMsg(msg);
        report(tr);
    }

    public static void leaveMsg(String msg) {
        BuglyLog.i("MSG", msg);
    }

    public static void leaveMsg(Throwable tr) {
        BuglyLog.e("MSG", "Error", tr);
    }

    public static void test() {
        throw new RuntimeException("Just test");
    }
}
