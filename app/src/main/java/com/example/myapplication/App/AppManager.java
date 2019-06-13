package com.example.myapplication.App;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import java.util.Stack;

/**
 * activity 管理器
 */
public class AppManager {
    // activity的栈堆
    private Stack<Activity> activityStack = new Stack<>();
    private volatile static AppManager appManager;

    /**
     * 单一实例
     */
    public static AppManager getAppManager() {
        if (appManager == null) {
            synchronized (AppManager.class) {
                if (appManager == null) {
                    appManager = new AppManager();
                }
            }
        }
        return appManager;
    }

    /**
     * 添加activity 到 堆栈
     */
    public void addActivity(Activity activity) {
        activityStack.add(activity);
    }

    /**
     * 结束当前activity（最后一个加入的activity）
     */
    public void finshActivity() {
        Activity activity = activityStack.lastElement();
        activity.finish();
    }

    /**
     * 获取所有activity
     */
    public Stack<Activity> getActivityStack() {
        return activityStack;
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity() {
        try {
            Activity activity = activityStack.lastElement();
            return activity;
        } catch (Exception e) {
//            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取当前页面的上一个activity
     */
    public Activity preActivity() {
        if (activityStack.size() <= 2) {
            return null;
        }
        Activity activity = activityStack.get(activityStack.size() - 2);
        return activity;
    }

    /**
     * j结束指定activity
     */
    public void FinishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类名activity
     */
    public void finishActivity(Class<?> cls) {
        try {
            for (Activity activity : activityStack) {
                if (activity.getClass().equals(cls)) {
                    FinishActivity(activity);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }
    /**
     * 结束所有activity
     */
    public void finishAllAcivity() {
        if (activityStack == null) return;
        for (int i = 0; i < activityStack.size(); i++) {
            if (activityStack.get(i) != null) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 返回指定activity
     */
    public void returnToActivity(Class<?> cls) {
        while (activityStack.size() != 0) {
            if (activityStack.peek().getClass() == cls) {
                break;
            } else {
                FinishActivity(activityStack.peek());
            }
        }
    }

    /**
     * 是否已经打开指定的activity
     *
     * @param cls
     * @return
     */
    public boolean isOpenActivity(Class<?> cls) {
        if (activityStack != null) {
            for (int i = 0, size = activityStack.size(); i < size; i++) {
                if (cls == activityStack.peek().getClass()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 退出应用程序
     *
     * @param context      上下文
     * @param isBackground 是否开开启后台运行
     */
    public void AppExit(Context context, Boolean isBackground) {
        try {
            finishAllAcivity();
            ActivityManager activityMgr = (ActivityManager) context
                    .getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.restartPackage(context.getPackageName());
        } catch (Exception e) {

        } finally {
            // 注意，如果您有后台程序运行，请不要支持此句子
            if (!isBackground) {
                System.exit(0);
            }
        }
    }
}
