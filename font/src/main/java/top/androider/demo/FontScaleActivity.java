package top.androider.demo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import top.androider.fontscale.FontScaleFactory;
import top.androider.fontscale.FontSizeUtil;

public class FontScaleActivity extends Activity {


    private FontScaleFactory fontScaleFactory = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fontScaleFactory = new FontScaleFactory(getLayoutInflater());
        setContentView(R.layout.activity_font_scale);
        LinearLayout layout = findViewById(R.id.layout);
        LayoutInflater.from(this).inflate(R.layout.layout_font_scale,layout,true);
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        //Android 7.0之后需要更新BaseContext才能生效
        super.attachBaseContext(FontSizeUtil.attachBaseContext(newBase));
    }

    public void update(){
        if (null != fontScaleFactory){
            fontScaleFactory.update();
        }
    }

    public void scale(View view) {
        FontSizeUtil.setFontScale(this,1.5f);
        update();
    }

    public void reset(View view) {
        FontSizeUtil.setFontScale(this,1f);
        update();
    }
}
