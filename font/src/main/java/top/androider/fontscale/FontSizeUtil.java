package top.androider.fontscale;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;


import java.lang.reflect.Method;

public class FontSizeUtil {



    public static Context attachBaseContext(Context base){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            try {
                Configuration configuration = FontSizeUtil.setFontScale(base,FontSizeUtil.getFontScale(base));
                return base.createConfigurationContext(configuration);
            } catch (Throwable throwable){
                Log.e("xxx","",throwable);
            }
        }
        return base;
    }


    public static Configuration setFontScale(Context context, float fontScale){
        // 禁止字体大小随系统设置变化
        Configuration configuration = setFontScaleInternal(context, fontScale);
        if (!(context instanceof Application)){
            context = context.getApplicationContext();
            setFontScaleInternal(context, fontScale);
        }
        return configuration;
    }

    private static Configuration setFontScaleInternal(Context context, float fontScale){
        if (null == context){
            return null;
        }
        // 禁止字体大小随系统设置变化
        Resources resources = context.getResources();
        if (resources != null) {
            Configuration configuration = resources.getConfiguration();
            configuration.fontScale = fontScale;
            sFontScale = fontScale;
            updateConfiguration(configuration, context);
            return configuration;
        }
        return null;
    }

    private static float sFontScale = -1;
    public static float getFontScale(Context context){
        if (-1 != sFontScale){
            return sFontScale;
        }
        //默认使用1.3倍
        sFontScale = 1f;
        return sFontScale;
    }

    public static void resetFontScale(Context context){
        float fontScale = 1.0f;
        // 禁止字体大小随系统设置变化
        Resources resources = context.getResources();
        if (resources != null && resources.getConfiguration().fontScale != fontScale) {
            android.content.res.Configuration configuration = resources.getConfiguration();
            configuration.fontScale = fontScale;
            updateConfiguration(configuration, context);
        }
    }

    public static boolean resumseFontScale(Context context){
        float fontScale = getFontScale(context);
        // 禁止字体大小随系统设置变化
        Resources resources = context.getResources();
        if (resources != null && resources.getConfiguration().fontScale != fontScale) {
            android.content.res.Configuration configuration = resources.getConfiguration();
            configuration.fontScale = fontScale;
            sFontScale = fontScale;
            updateConfiguration(configuration, context);
            return true;
        }
        return false;
    }

    public static void updateConfiguration(Configuration configuration, Context context) {
        updateConfigurationInternal(configuration, context);
    }

    private static void updateConfigurationInternal(Configuration configuration, Context context) {
        Resources resources = context.getResources();
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        try {
            Class activityThreadCls = Class.forName("android.app.ActivityThread");
            Method currentActivityThreadMethod = activityThreadCls.getMethod("currentActivityThread");
            Object activityThreadObj =  currentActivityThreadMethod.invoke(null);

            Method getApplicationThreadMethod = activityThreadCls.getMethod("getApplicationThread");
            Object applicationThreadObj =  getApplicationThreadMethod.invoke(activityThreadObj);
            try {
                ReflectUtils.invokeMethod(applicationThreadObj,"scheduleConfigurationChanged",new Class[]{Configuration.class},new Object[]{configuration});
            } catch (NoSuchMethodException e){
                ReflectUtils.invokeMethod(activityThreadObj,"updatePendingConfiguration",new Class[]{Configuration.class},new Object[]{configuration});
            }
        } catch (Exception e) {
            Log.e("xxx-setFontScale","updateConfiguration",e);
        }
    }

    public static void setHeightWithFontScale(View view, Context context,int height){
        float fontScale = getFontScale(context);
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (null != params){
            params.height = (int)(fontScale*height);
            Log.e("xxx-setHeight",""+params.height);
        }
    }
}
