package top.androider.fontscale;

import android.view.View;

import java.lang.ref.WeakReference;

public abstract class AttrChangeListener<V extends View,DATA> {

    private WeakReference<V> targetView;

    public void setTargetView(V targetView) {
        this.targetView = new WeakReference<>(targetView);
    }

    public boolean update(DATA oldValue,DATA newValue){
        V v = null;
        if (null != targetView){
            v = targetView.get();
        }
        if (null != v){
            update(v,oldValue,newValue);
            return true;
        }
        return false;
    }
    public abstract void update(V targetView,DATA oldValue,DATA newValue);
}
