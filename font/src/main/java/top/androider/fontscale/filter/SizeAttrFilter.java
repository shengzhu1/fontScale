package top.androider.fontscale.filter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import top.androider.fontscale.AttrChangeListener;
import top.androider.fontscale.listener.SizeAttrChangeListener;

public class SizeAttrFilter extends FontScaleFilter {
    @Override
    public Class getTag() {
        return View.class;
    }

    private Class<? extends AttrChangeListener> listener = SizeAttrChangeListener.class;
    private boolean useW;
    private boolean useH;
    @Override
    public boolean filter(Context context, AttributeSet attrs) {
        useW = useSp(context,attrs,ANDROID_NAMESPACE,"layout_width");
        useH = useSp(context,attrs,ANDROID_NAMESPACE,"layout_height");
        return  useW || useH;
    }

    @Override
    public AttrChangeListener getListener() {
        SizeAttrChangeListener listener = new SizeAttrChangeListener();
        listener.useW = useW;
        listener.useH = useH;
        return listener;
    }
}
