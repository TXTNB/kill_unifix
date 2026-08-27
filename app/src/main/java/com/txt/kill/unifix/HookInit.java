package com.txt.kill.unifix;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class HookInit implements IXposedHookLoadPackage {
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        if (!loadPackageParam.packageName.equals("com.netease.x19")) {
            return;
        }

        ClassLoader classLoader = loadPackageParam.classLoader;
        Object[] objArr = new Object[2];
        try {
            objArr[0] = Class.forName("android.content.Context");
            objArr[1] = new XC_MethodHook() {
                protected void afterHookedMethod(XC_MethodHook.MethodHookParam methodHookParam) throws Throwable {
                    String str = (String) methodHookParam.getResult();
                    if (str != null) {
                        methodHookParam.setResult(str.replace("unifix.netease.com", "sekaiproject.netease"));
                    }
                }
            };
            XposedHelpers.findAndHookMethod("com.netease.ntunisdk.unifix.UniFixBase", classLoader, "a", objArr);
            
            ClassLoader classLoader2 = loadPackageParam.classLoader;
            Object[] objArr2 = new Object[2];
            try {
                objArr2[0] = Class.forName("android.content.Context");
                objArr2[1] = new XC_MethodHook() {
                    protected void afterHookedMethod(XC_MethodHook.MethodHookParam methodHookParam) throws Throwable {
                        String str = (String) methodHookParam.getResult();
                        if (str != null) {
                            methodHookParam.setResult(str.replace("unifix.netease.com", "sekaiproject.netease"));
                        }
                    }
                };
                XposedHelpers.findAndHookMethod("com.netease.ntunisdk.unifix.util.v", classLoader2, "b", objArr2);
            } catch (ClassNotFoundException e) {
                throw new NoClassDefFoundError(e.getMessage());
            }
        } catch (ClassNotFoundException e2) {
            throw new NoClassDefFoundError(e2.getMessage());
        }
    }
}