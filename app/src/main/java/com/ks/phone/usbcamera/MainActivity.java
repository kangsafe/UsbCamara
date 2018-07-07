package com.ks.phone.usbcamera;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.widget.LinearLayout.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    //
    CameraPreview cp;
    private int winWidth;
    private int winHeight;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高
        winWidth=d.getWidth();
        winHeight=d.getHeight();
        cp = (CameraPreview) findViewById(R.id.cp);
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        p.height = winHeight; // 高度设置为屏幕的1.0
        p.width = winHeight*4/3; // 宽度设置为屏幕高度的4/3
        cp.setLayoutParams(p);
    }

}
