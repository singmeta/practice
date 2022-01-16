package com.example.metabus;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

import android.widget.TextView;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    WebView wView;      // 웹뷰
    EditText urlEt;     // +추가> 주소 입력창
    Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wView = findViewById(R.id.wView);   // 웹뷰

        initWebView();                      // 웹뷰 초기화



        button2 = (Button)findViewById(R.id.button2);



        button2.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {

                OkHttpClient client = new OkHttpClient().newBuilder()
                        .build();
                Request request = new Request.Builder()
                        .url("http://localhost:1444/123")
                        .method("GET", null)
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });
        // +추가> 주소 입력창 (주소 입력 -> 키보드 엔터 -> 해당 웹사이트 접속)
        //helloworld


        urlEt = findViewById(R.id.urlEt);
        urlEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE){                  // 키보드의 엔터키를 눌러서
                    wView.loadUrl("https://"+urlEt.getText().toString()+""); // 입력한 주소 접속
                    System.out.println("hello");
                    System.out.println(urlEt.getText().toString());


                }



                return false;
            }
        });
    }

    // 웹뷰 초기화 함수
    public void initWebView(){
        // 1. 웹뷰클라이언트 연결 (로딩 시작/끝 받아오기)
        wView.setWebViewClient(new WebViewClient(){
            @Override                                   // 1) 로딩 시작
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }
            @Override                                   // 2) 로딩 끝
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
            @Override                                   // 3) 외부 브라우저가 아닌 웹뷰 자체에서 url 호출
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        // 2. WebSettings: 웹뷰의 각종 설정을 정할 수 있다.
        WebSettings ws = wView.getSettings();
        ws.setJavaScriptEnabled(true); // 자바스크립트 사용 허가
        // 3. 웹페이지 호출
        wView.loadUrl("http://192.168.1.82:1444");
    }

    // 뒤로가기 동작 컨트롤
    @Override
    public void onBackPressed() {
        if(wView.canGoBack()){      // 이전 페이지가 존재하면
            wView.goBack();         // 이전 페이지로 돌아가고
        }else{
            super.onBackPressed();  // 없으면 앱 종료
        }
    }
}