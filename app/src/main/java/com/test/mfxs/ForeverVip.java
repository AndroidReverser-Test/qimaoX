package com.test.mfxs;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;





public class ForeverVip implements IXposedHookLoadPackage {


    public static String TAG = "xingtong";
    public Context Main_context;

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        if (loadPackageParam.packageName.equals("com.kmxs.reader")){
            XposedHelpers.findAndHookMethod(Application.class, "attach", Context.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    Main_context = (Context) param.args[0];
                }
            });
            XposedHelpers.findAndHookMethod("com.km.app.update.UpdateVersionV2Activity", loadPackageParam.classLoader, "onCreate",android.os.Bundle.class,new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    Log.d(TAG, "七猫pass更新弹窗");
                    XposedHelpers.callMethod(param.thisObject,"finish");
                    Toast.makeText(Main_context,"更新弹窗已被去除",Toast.LENGTH_SHORT).show();
                }
            });
            XposedHelpers.findAndHookMethod("zg", loadPackageParam.classLoader, "isVipUser", Context.class, new XC_MethodReplacement() {
                @Override
                protected Object replaceHookedMethod(MethodHookParam methodHookParam) throws Throwable {
                    Log.d(TAG, "七猫vip check");
                    return true;
                }
            });
            XposedHelpers.findAndHookMethod("com.qimao.qmad.entity.AdBusinessConfig", loadPackageParam.classLoader, "getRewardFreeAdEndTime",new XC_MethodReplacement() {
                @Override
                protected Object replaceHookedMethod(MethodHookParam methodHookParam) throws Throwable {
                    Log.d(TAG, "七猫 get FreeAdEndTime");
                    return 8880582530000L;
                }
            });
            XposedHelpers.findAndHookMethod("com.qimao.qmad.entity.AdBusinessConfig", loadPackageParam.classLoader, "getRewardFreeAdTotalDuration",new XC_MethodReplacement() {
                @Override
                protected Object replaceHookedMethod(MethodHookParam methodHookParam) throws Throwable {
                    Log.d(TAG, "七猫 get FreeAdTotalDuration");
                    return 9990582530000L;
                }
            });

            XposedHelpers.findAndHookMethod("f7$b", loadPackageParam.classLoader, "run",new XC_MethodReplacement() {
                @Override
                protected Object replaceHookedMethod(MethodHookParam methodHookParam) throws Throwable {
                    Log.d(TAG, "pass七猫开屏广告");
                    return null;
                }
            });

        }
    }
}
