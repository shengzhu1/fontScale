package top.androider.fontscale;

import android.util.Log;
import android.widget.TextView;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import top.androider.fontscale.filter.SizeAttrFilter;
import top.androider.fontscale.filter.TextSizeAttrFilter;

public class AttrRegister {

    private static HashMap<Class,IViewAttrFilter> filterMap = new HashMap<>();

    public static void init() {
        long time = System.nanoTime();
        register(new TextSizeAttrFilter());
        time = System.nanoTime();
        register(new SizeAttrFilter());
    }

    public static void register(IViewAttrFilter filter){
        filterMap.put(filter.getTag(), filter);
    }

    public static IViewAttrFilter getFilter(Class tag){
        //TextView特殊处理
        if (tag !=TextView.class && TextView.class.isAssignableFrom(tag)){
            tag = TextView.class;
        }
        return filterMap.get(tag);
    }

    public static void unRegister(IViewAttrFilter filter){
        if (null != filter){
            filterMap.remove(filter.getTag());
        }
    }
}
