package top.androider.fontscale.filter;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import top.androider.fontscale.AttrChangeListener;
import top.androider.fontscale.listener.TextSizeChangeListener;

public class TextSizeAttrFilter extends FontScaleFilter {
    @Override
    public Class getTag() {
        return TextView.class;
    }

    @Override
    public boolean filter(Context context, AttributeSet attrs) {
        return useSp(context,attrs,ANDROID_NAMESPACE,"textSize");
    }

    @Override
    public AttrChangeListener getListener() {
        return new TextSizeChangeListener();
    }
}
