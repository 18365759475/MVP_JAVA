package com.example.myapplication.util;

import android.annotation.SuppressLint;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogManager {
    private static final int DEBUG = 111;
    private static final int ERROR = 112;
    private static  boolean debugOpen = false;
    private static  boolean isCommonLog = false;
    private static String pkgname = "";
    private static String filename = null;
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH-mm:ss");
    public static void setDebugOpen(boolean debugOpenFlag,boolean isWriteFileFlag){
        debugOpen = debugOpenFlag;
        isCommonLog = isWriteFileFlag;
    }

    public static void LogShow(String format, Object... argues) {
        if (debugOpen) {
            StringBuffer result = new StringBuffer("");
            try {
                result.append(String.format(format, argues));
            } catch (Exception e) {
                // TODO: handle exception
                result.append("format is err");
            }

            LogShow(result.toString(), DEBUG);
        }
    }

    public static void LogShow(String msg) {
        if (debugOpen) {
            LogShow(msg, DEBUG);
        }
    }

    public static void setSaveName(String name) {
        pkgname = new String(name);
    }

    public static void LogShow(Exception e) {
        if (debugOpen) {
            StringBuffer exceptionStr = new StringBuffer();
            StackTraceElement[] elements = e.getStackTrace();
            for (int i = 0; i < elements.length; i++) {
                exceptionStr.append(elements[i].toString() + "\r\n");
            }
            LogShow(exceptionStr.toString(), ERROR);
        }
    }

    public static String logcat = "xxxxx";

    public static void LogShow(String msg, int style) {

        if (debugOpen) {
            if (filename == null && Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                filename = Environment.getExternalStorageDirectory() + File.separator;
            }
            String name = getFunctionName();
            String Message = (name == null ? msg : (msg));
            String tag = pkgname;
            switch (style) {
                case DEBUG:
                    if (isCommonLog)
                        writeFileSdcard(name + " - " + Message);
                    Log.d(tag, logcat + " - " + Message);
                    break;
                case ERROR:
                    if (isCommonLog)
                        writeFileSdcard(name + " - " + Message);
                    Log.e(tag, logcat + " - " + Message);
                    break;
            }
        }
    }

    private static String getTagName(String name) {
        String reatmp = "";
        if (!name.equals("")) {
            String tmp = new String(name);
            int i;
            String[] arrar = tmp.split("\\.");
            for (i = 3; arrar != null && i < arrar.length; i++) {
                reatmp = reatmp + "." + arrar[i];
            }
        }
        if (reatmp.equals(""))
            reatmp = "default";
        return reatmp;
    }

    private static String getPkgName(String name) {
        // "me.psdfplay.sdf.xxx.xx";
        if (pkgname.equals("") || pkgname.equals("default.txt")) {
            String tmp = new String(name);
            String[] arrar = tmp.split("\\.");
            if (arrar != null && arrar[0] != null && arrar[1] != null && arrar[2] != null)
                pkgname = arrar[0] + "." + arrar[1] + "." + arrar[2] + ".txt";
            else
                pkgname = "default.txt";
        }
        return pkgname;
    }

    private static void writeFileSdcard(String message) {
        try {
            if (filename == null)
                return;
            String cf = "\r\n";
            FileOutputStream fout = new FileOutputStream(filename + pkgname, true);
            byte[] bytes = message.getBytes("UTF-8");
            fout.write(bytes);
            fout.write(cf.getBytes());
            fout.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("SimpleDateFormat")
    private static String getFunctionName() {
        StackTraceElement[] sts = Thread.currentThread().getStackTrace();
        if (sts == null) {
            return null;
        }

        for (StackTraceElement st : sts) {
            if (st.isNativeMethod()) {
                continue;
            }

            if (st.getClassName().equals(Thread.class.getName())) {
                continue;
            }

            if (st.getClassName() != null && st.getClassName().contains("LogManager"))
                continue;

            getPkgName(st.getClassName());
            String filename = getTagName(st.getClassName());

            logcat = "[" + Thread.currentThread().getName() + filename + ":" + st.getLineNumber() + "]";
            return "[" + simpleDateFormat.format(new Date()) + " " + Thread.currentThread().getName() + "(" + Thread.currentThread().getId() + "): " + filename + ":" + st.getLineNumber() + "]";
        }
        return null;
    }
}
