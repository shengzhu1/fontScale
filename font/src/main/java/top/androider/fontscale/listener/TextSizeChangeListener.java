package top.androider.fontscale.listener;

import android.util.TypedValue;
import android.widget.TextView;

public class TextSizeChangeListener extends TextSizeAttrChangeListener<TextView> {

    @Override
    public void update(TextView targetView,Float oldValue, Float newValue) {
        int newSize = (int) (targetView.getTextSize()*newValue/oldValue);
        targetView.setTextSize(TypedValue.COMPLEX_UNIT_PX,newSize);
    }
}
