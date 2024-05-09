package com.example.happyness;

import android.app.Activity;
import android.os.Binder;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.CheckBox;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;

public class LoginActivity extends Activity {

    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login);

        webView = findViewById(R.id.webView);
        webView.setVerticalScrollBarEnabled(true);

        webView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // 터치 이벤트 처리 및 원하는 동작 수행
                return false; // 터치 이벤트가 웹뷰로 전달되도록 false 반환
            }
        });

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true); // JavaScript 활성화
        webSettings.setUseWideViewPort(true); // 와이드 뷰포트 사용
        webSettings.setLoadWithOverviewMode(true); // 오버뷰 모드로 로드
        webSettings.setSupportZoom(true); // 확대/축소 지원

        // 웹뷰에 HTML 로드
        webView.loadUrl("file:///android_asset/webb.html");



        Button logbackButton = findViewById(R.id.loginbbt);
        Button endButton = findViewById(R.id.endbt);
        logbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // MainActivity로 되돌아가는 Intent 생성
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                // Intent 시작
                startActivity(intent);
            }
        });


        endButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
