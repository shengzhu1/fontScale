package top.androider.fontscale.filter;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;

public abstract class FontScaleFilter extends AndroidAttrFilter {


    public boolean useSp(Context context, AttributeSet attrs, String namespace, String name){
        String string = attrs.getAttributeValue(namespace, name);
        return useSp(context,string);
    }

    public boolean useSp(Context context, String valueStr){
        if (TextUtils.isEmpty(valueStr)){
            return false;
        }
        if (valueStr.endsWith("sp")){
            return true;
        }
        if (valueStr.startsWith("@")){
            TypedValue value = new TypedValue();
            int id = Integer.parseInt(valueStr.substring(1));
            context.getResources().getValue(id,value,false);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                if (value.getComplexUnit() == TypedValue.COMPLEX_UNIT_SP){
                    return true;
                }
            } else if ((TypedValue.COMPLEX_UNIT_MASK & (value.data>>TypedValue.COMPLEX_UNIT_SHIFT)) == TypedValue.COMPLEX_UNIT_SP){
                return true;
            }
        }
        return false;
    }
}
