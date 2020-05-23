package top.androider.fontscale.listener;

import android.view.View;
import android.view.ViewGroup;

public class SizeAttrChangeListener extends TextSizeAttrChangeListener<View> {
    public boolean useW;
    public boolean useH;

    @Override
    public void update(View targetView,Float oldValue, Float newValue) {
        ViewGroup.LayoutParams params = targetView.getLayoutParams();
        if (useW){
            params.width = (int) (params.width * newValue/oldValue);
        }
        if (useH){
            params.height = (int) (params.height * newValue/oldValue);
        }
    }
}
