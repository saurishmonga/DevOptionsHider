package com.devoptionshider;

import android.content.ContentResolver;
import android.provider.Settings;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class MainHook implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        // Hook Settings.Global.getInt()
        XposedHelpers.findAndHookMethod(Settings.Global.class, "getInt",
                ContentResolver.class, String.class, int.class,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) {
                        String name = (String) param.args[1];
                        if ("development_settings_enabled".equals(name) || "adb_enabled".equals(name)) {
                            param.setResult(0);
                        }
                    }
                });

        // Hook Settings.Secure.getInt()
        XposedHelpers.findAndHookMethod(Settings.Secure.class, "getInt",
                ContentResolver.class, String.class, int.class,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) {
                        String name = (String) param.args[1];
                        if ("development_settings_enabled".equals(name) || "adb_enabled".equals(name)) {
                            param.setResult(0);
                        }
                    }
                });
    }
}
