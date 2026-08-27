package com.txt.kill.unifix;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        FrameLayout root = new FrameLayout(this);
        root.setBackgroundColor(Color.WHITE);
        
        TextView message = new TextView(this);
        message.setTextSize(18f);
        message.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
        message.setLineSpacing(0, 1.5f);
        
        int padding = (int) (32 * getResources().getDisplayMetrics().density);
        message.setPadding(padding, padding, padding, padding);
        
        String text = "Kill UniFix\n\n绕过网易版《我的世界》UniFix SDK更新检测\n\n使用方法：\n1. 在LSPosed中激活模块\n2. 选择目标应用：com.netease.x19\n3. 重启游戏\n\nGitHub: https://github.com/TXTNB/kill_unifix";
        
        SpannableString spannable = new SpannableString(text);
        
        int start = text.indexOf("https://github.com/TXTNB/kill_unifix");
        int end = start + "https://github.com/TXTNB/kill_unifix".length();
        
        if (start != -1) {
            spannable.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, 
                        Uri.parse("https://github.com/TXTNB/kill_unifix"));
                    startActivity(browserIntent);
                }
            }, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        
        message.setText(spannable);
        message.setMovementMethod(LinkMovementMethod.getInstance());
        message.setHighlightColor(Color.TRANSPARENT);
        
        root.addView(message, new FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, 
            ViewGroup.LayoutParams.MATCH_PARENT
        ));
        
        setContentView(root);
    }
}