package top.androider.fontscale.filter;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
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
        String textSize = attrs.getAttributeValue(ANDROID_NAMESPACE, "textSize");
        if (!TextUtils.isEmpty(textSize)){
            return useSp(context,textSize);
        }
        String textAppearance = attrs.getAttributeValue(ANDROID_NAMESPACE,"textAppearance");
        boolean useSp = false;
        if (!TextUtils.isEmpty(textAppearance)){
            useSp = useSpInStyle(context,textAppearance);
        }
        String style = attrs.getAttributeValue(null,"style");
        if (!useSp && !TextUtils.isEmpty(style)){
            useSp = useSpInStyle(context,style);
        }
        return useSp;
    }

    private boolean useSpInStyle(Context context,String style){
        boolean useSp = false;
        String res = style.substring(1);
        int resId = Integer.parseInt(res);
        int[] attrArray = new int[]{android.R.attr.textSize};
        TypedArray array = context.getTheme().obtainStyledAttributes(resId, attrArray);
        int index = array.getIndex(0);
        useSp = useSp(context,array.getString(index));
        array.recycle();
        return useSp;
    }

    @Override
    public AttrChangeListener getListener() {
        return new TextSizeChangeListener();
    }
}
