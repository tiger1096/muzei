package com.alexqzhang.util;

import android.content.res.Configuration;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.util.LruCache;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class LogUtils {

    private static final Configuration sConfiguration = new Configuration();

    private static ILog sLog = null;
    private static boolean ENABLED = true;
    private static boolean ENABLE_PROFILE = true;

    private static long mLastTime;
    private static long mInitTime;

    private static LruCache<String, Long> mTimeStampMap = new LruCache<>(1000);

    public static boolean isEnabled() {
        return ENABLED;
    }

    public static void setEnable(boolean enable) {
        LogUtils.ENABLED = enable;
    }

    public static boolean isEnableProfile() {
        return ENABLE_PROFILE;
    }

    public static void setEnableProfile(boolean enableProfile) {
        ENABLE_PROFILE = enableProfile;
    }

    private static String getTag(Object o) {
        if (o == null) {
            return "filter process";
        }
        if (o instanceof String) {
            return (String) o;
        }
        return o.getClass().getSimpleName();
    }

    public static void setLog(ILog log) {
        sLog = log;
    }

    public static ILog getLog() {
        return sLog;
    }

    // 初始化记录消耗的系统初始时间
    public static long initTime() {
        mLastTime = SystemClock.currentThreadTimeMillis();
        mInitTime = mLastTime;
        return mLastTime;
    }

    public static void printTime(String key, String msg) {
        if (!ENABLED || !ENABLE_PROFILE || TextUtils.isEmpty(key) || TextUtils.isEmpty(msg)) {
            return;
        }
        if (msg.contains("BEGIN")) {
            long currentTimeMillis = System.currentTimeMillis();
            mTimeStampMap.put(key, currentTimeMillis);
            LogUtils.i("LOG_PERFORMANCE_" + key, msg);
        } else if (msg.contains("END")) {
            Long beginTimeObj = mTimeStampMap.get(key);
            long beginTime;
            if (beginTimeObj != null) {
                beginTime = beginTimeObj;
            } else {
                beginTime = System.currentTimeMillis();
            }
            long currentTimeMillis = System.currentTimeMillis();
            long timeCost = currentTimeMillis - beginTime;
            LogUtils.i("LOG_PERFORMANCE_" + key, msg + " = " + timeCost);
        } else {
            LogUtils.i("LOG_PERFORMANCE_" + key, msg);
        }
    }
    public static void i(Object tag, String msg) {
        i(getTag(tag), msg);
    }

    public static void d(Object tag, String msg) {
        d(getTag(tag), msg);
    }

    public static void w(Object tag, String msg) {
        w(getTag(tag), msg);
    }

    public static void e(Object tag, String msg) {
        e(getTag(tag), msg);
    }

    public static void v(Object tag, String msg) {
        v(getTag(tag), msg);
    }

    public static void v(String tag, String msg) {
        if (ENABLED) {
            StackTraceElement stackTrace = Thread.currentThread().getStackTrace()[3];
            String fileInfo = "(" + Thread.currentThread().getName()
                    + ")" + stackTrace.getClassName() + "("
                    + stackTrace.getLineNumber() + ")[" + stackTrace.getMethodName() + "]";
            String all = fileInfo + ": " + msg;
            if (sLog != null) {
                sLog.v(tag, all);
            } else {
                Log.v(tag, all);
            }
        }
    }

    public static void v(String tag, String msg, Object... args) {
        if (ENABLED) {
            msg = getFormatMessage(tag, msg, args);
            if (sLog != null) {
                sLog.v(tag, msg);
            } else {
                Log.v(tag, msg);
            }
        }
    }

    public static void v(String tag, String msg, Throwable tr, Object... args) {
        if (ENABLED) {
            msg = getFormatMessage(tag, msg, args);
            if (sLog != null) {
                sLog.v(tag, msg, tr);
            } else {
                Log.v(tag, msg, tr);
            }
        }
    }

    public static void i(String tag, String msg) {
//        if (ENABLED) {
//            StackTraceElement stackTrace = Thread.currentThread().getStackTrace()[3];
//            String fileInfo = "(" + Thread.currentThread().getName() + ")"
//                    + stackTrace.getClassName()
//                    + "(" + stackTrace.getLineNumber() + ")["
//                    + stackTrace.getMethodName() + "]";
//            String all = fileInfo + ": " + msg;
        if (tag == null || msg == null)
            return;
        if (sLog != null) {
            sLog.i(tag, msg);
        } else {
            Log.i(tag, msg);
        }
//        }
    }

    public static void i(String tag, String msg, Object... args) {
        if (ENABLED) {
            msg = getFormatMessage(tag, msg, args);
            if (sLog != null) {
                sLog.i(tag, msg);
            } else {
                Log.i(tag, msg);
            }
        }
    }

    public static void i(String tag, String msg, Throwable tr, Object... args) {
        if (ENABLED) {
            msg = getFormatMessage(tag, msg, args);
            if (sLog != null) {
                sLog.i(tag, msg, tr);
            } else {
                Log.i(tag, msg, tr);
            }
        }
    }

    public static void d(String tag, String msg) {
        if (ENABLED) {
            StackTraceElement stackTrace = Thread.currentThread().getStackTrace()[3];
            String fileInfo = "(" + Thread.currentThread().getName() + ")"
                    + stackTrace.getClassName() + "(" + stackTrace.getLineNumber()
                    + ")[" + stackTrace.getMethodName() + "]";
            String all = fileInfo + ": " + msg;
            if (sLog != null) {
                sLog.d(tag, all);
            } else {
                Log.d(tag, all);
            }
        }
    }

    public static void d(String tag, String msg, Object... args) {
        if (ENABLED) {
            msg = getFormatMessage(tag, msg, args);
            if (sLog != null) {
                sLog.d(tag, msg);
            } else {
                Log.d(tag, msg);
            }
        }
    }

    public static void d(String tag, String msg, Throwable tr, Object... args) {
        if (ENABLED) {
            msg = getFormatMessage(tag, msg, args);
            if (sLog != null) {
                sLog.d(tag, msg, tr);
            } else {
                Log.d(tag, msg, tr);
            }
        }
    }

    public static void w(String tag, String msg) {
        if (ENABLED) {
            StackTraceElement stackTrace = Thread.currentThread().getStackTrace()[3];
            String fileInfo = "(" + Thread.currentThread().getName() + ")"
                    + stackTrace.getClassName() + "(" + stackTrace.getLineNumber()
                    + ")[" + stackTrace.getMethodName() + "]";
            String all = fileInfo + ": " + msg;
            if (sLog != null) {
                sLog.w(tag, all);
            } else {
                Log.w(tag, all);
            }
        }
    }

    public static void w(String tag, String msg, Object... args) {
        if (ENABLED) {
            msg = getFormatMessage(tag, msg, args);
            if (sLog != null) {
                sLog.w(tag, msg);
            } else {
                Log.w(tag, msg);
            }
        }
    }

    public static void w(String tag, String msg, Throwable tr, Object... args) {
        if (ENABLED) {
            msg = getFormatMessage(tag, msg, args);
            if (sLog != null) {
                sLog.w(tag, msg, tr);
            } else {
                Log.w(tag, msg, tr);
            }
        }
    }

    public static void e(Throwable tr) {
        e("", tr.getMessage());
    }

    public static void e(String tag, Throwable tr) {
        if (ENABLED) {
            if (sLog != null) {
                sLog.e(tag, "", tr);
            } else {
                Log.e(tag, "", tr);
            }
        }
    }

    public static void e(String tag, String msg) {
//        if (ENABLED) {
//            StackTraceElement stackTrace = Thread.currentThread().getStackTrace()[3];
//            String fileInfo = "(" + Thread.currentThread().getName() + ")"
//                    + stackTrace.getClassName() + "(" + stackTrace.getLineNumber()
//                    + ")[" + stackTrace.getMethodName() + "]";
//            String all = fileInfo + ": " + msg;
        if (tag == null || msg == null)
            return;
        if (sLog != null) {
            sLog.e(tag, msg);
        } else {
            Log.e(tag, msg);
        }
//        }
    }

    public static void e(String tag, String msg, Object... args) {
        if (ENABLED) {
            msg = getFormatMessage(tag, msg, args);
            if (sLog != null) {
                sLog.e(tag, msg);
            } else {
                Log.e(tag, msg);
            }
        }
    }

    public static void e(String tag, String msg, Throwable tr, Object... args) {
        if (ENABLED) {
            msg = getFormatMessage(tag, msg, args);
            if (sLog != null) {
                sLog.e(tag, msg, tr);
            } else {
                Log.e(tag, msg, tr);
            }
        }
    }

    private static String getFormatMessage(String tag, String msg, Object... args) {
        if (args != null) {
            try {
                msg = String.format(sConfiguration.locale, msg, args);
            } catch (Exception e) {
                // ignore
            }
        }
        return generateLogPrefix(tag) + msg;
    }

    /**
     * 生成Log日志的前缀信息。如下格式：
     * 当前线程名+文件名+行号+方法名
     *
     * @param simpleClassName
     * @return
     */
    private static String generateLogPrefix(String simpleClassName) {
        StackTraceElement[] sts = Thread.currentThread().getStackTrace();
        if (sts == null) {
            return "";
        }
        for (StackTraceElement st : sts) {
            if (st.isNativeMethod()) {
                continue;
            }
            if (st.getClassName().equals(Thread.class.getName())) {
                continue;
            }
            if (st.getClassName().endsWith(simpleClassName)) {
                return "(" + Thread.currentThread().getName() + ")"
                        + st.getClassName() + "(" + st.getLineNumber()
                        + ")[" + st.getMethodName() + "]: ";
            }
        }
        return "";
    }

    public static int writeLog(Object o, String msg) {
        if (ENABLED && ENABLE_PROFILE) {
            String tag = getTag(o);
            try {
                msg += "\n";
                long currentTime = System.currentTimeMillis();
                FileOutputStream file = new FileOutputStream("/mnt/sdcard/log.txt", true);
                String time = String.valueOf(currentTime) + "--\t";
                file.write(time.getBytes());
                file.write(tag.getBytes());
                file.write(new String("\t").getBytes());
                file.write(msg.getBytes());

                file.flush();
                file.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return 0;
    }
}
