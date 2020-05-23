package top.androider.fontscale;

import android.content.Context;
import android.util.AttributeSet;

public interface IViewAttrFilter {
    /**
     * @return xml 中标签名字
     */
    Class getTag();

    boolean filter(Context context, AttributeSet attrs);

    AttrChangeListener getListener();
}
