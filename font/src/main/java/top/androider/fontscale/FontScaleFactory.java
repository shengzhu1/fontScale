package top.androider.fontscale;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;

import top.androider.demo.FontScaleActivity;

public class FontScaleFactory implements LayoutInflater.Factory2{
    public static final String TAG = FontScaleActivity.class.getSimpleName();

    private LayoutInflater inflater;
    private Context context;
    private ArrayList<AttrChangeListener> listeners = new ArrayList<>();
    private float oldFontScale = 1.0f;

    public FontScaleFactory(LayoutInflater inflater) {

        this.inflater = inflater;
        this.context = inflater.getContext();
        oldFontScale = FontSizeUtil.getFontScale(context);
        inflater.setFactory2(this);
    }


    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        return null;
    }

    @Nullable
    @Override
    public View onCreateView(@Nullable View parent, @NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        View view = null;
        try {
            if (-1 == name.indexOf('.')) {
                if ("View".equals(name)){
                    view = inflater.createView(name,"android.view.",attrs);
                }
                if (null == view){
                    view = inflater.createView(name,"android.widget.",attrs);
                }
                if (null == view){
                    view = inflater.createView(name,"android.webkit.",attrs);
                }
            } else {
                view = inflater.createView(name, null, attrs);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        IViewAttrFilter filter = null;
        if (null != view){
            Class viewCls = view.getClass();
            filter = AttrRegister.getFilter(viewCls);
        }
        if (null != filter){
            if (filter.filter(context,attrs)){
                addListener(view,filter);
            }
        }
        return view;
    }

    public void addListener(AttrChangeListener listener){
        if (null != listener){
            listeners.add(listener);
        }
    }

    /**
     * 添加view变化监听，用户非xml生成代码时加入到监听中
     * @param view
     * @param listener
     */
    public void addListener(View view,AttrChangeListener listener){
        if (null == view || null == listener){
            return;
        }
        listener.setTargetView(view);
        listeners.add(listener);
    }

    public void addListener(View view){
        if (null == view){
            return;
        }
        IViewAttrFilter filter = null;
        Class viewCls = view.getClass();
        filter = AttrRegister.getFilter(viewCls);
        addListener(view,filter);
    }

    public void addListener(View view,IViewAttrFilter filter){
        if (null != filter){
            AttrChangeListener listener = filter.getListener();
            listener.setTargetView(view);
            listeners.add(listener);
        }
    }

    public void update() {
        float newScale = FontSizeUtil.getFontScale(context);
        for (int i = listeners.size()-1; i >= 0 ; i--) {
            AttrChangeListener listener = listeners.get(i);
            if (!listener.update(oldFontScale,newScale)){
                listeners.remove(i);
            }
        }
        oldFontScale = newScale;
    }
}
