# fontScale

通过LayoutInflater.Factory2接口，拦截从xml中生成View的过程，解析属性，判断如果指定属性如textSize使用了sp则在修改字体缩放之后进行处理。

# 使用
- 使用之前
```java
// 只初始化一次，建议在app启动时候调用
    AttrRegister.init();
```

- 调用
```java
    private FontScaleFactory fontScaleFactory = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fontScaleFactory = new FontScaleFactory(getLayoutInflater());
        setContentView(R.layout.activity_font_scale);
    }
```

- 切换字体缩放之后调用
```java
    fontScaleFactory.update();
```

- 支持自定义属性
```java
    AttrRegister.register(IViewAttrFilter filter)
    
    public interface IViewAttrFilter {
        /**
         * @return xml 中标签名字
         */
        Class getTag();
    
        boolean filter(Context context, AttributeSet attrs);
    
        AttrChangeListener getListener();
    }
```

