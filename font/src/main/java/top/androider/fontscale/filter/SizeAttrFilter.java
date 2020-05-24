package top.androider.fontscale.filter;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
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
        useW = filterAttr(context,attrs,"layout_width",new int[] {android.R.attr.layout_width});
        useH = filterAttr(context,attrs,"layout_height",new int[] {android.R.attr.layout_height});
        return  useW || useH;
    }

    public boolean filterAttr(Context context, AttributeSet attrs,String attr,int[] attrsArray){
        String attrValue = attrs.getAttributeValue(ANDROID_NAMESPACE, attr);
        if (!TextUtils.isEmpty(attrValue)){
            return useSp(context,attrValue);
        }
        String style = attrs.getAttributeValue(null,"style");
        boolean useSp = false;
        if (!TextUtils.isEmpty(style)){
            String res = style.substring(1);
            int resId = Integer.parseInt(res);
            TypedArray array = context.getTheme().obtainStyledAttributes(resId, attrsArray);
            int index = array.getIndex(0);
            useSp = useSp(context,array.getString(index));
            array.recycle();
        }
        return useSp;
    }

    @Override
    public AttrChangeListener getListener() {
        SizeAttrChangeListener listener = new SizeAttrChangeListener();
        listener.useW = useW;
        listener.useH = useH;
        return listener;
    }
}
