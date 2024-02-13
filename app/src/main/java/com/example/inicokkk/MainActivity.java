package com.example.inicokkk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.inicokkk.untils.ApiService;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    public Button btn_login, btn_reset;

    public EditText username, password;

    public OkHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("nixxxxxx", "onCreate");
        initView();
        initInterceptor();
        initListener();
    }

    private void initListener() {
        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username.setText("");
                password.setText("");
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // doLoginOkhttp();
                doLoginRetrofit();
            }
        });
    }

    private void initInterceptor() {
        // HTTP的拦截器
        Interceptor interceptor = new Interceptor() {
            @NonNull
            @Override
            public Response intercept(@NonNull Chain chain) throws IOException {
                String sign = "sdfsdfsdf";
                Request request = chain.request().newBuilder().addHeader("x-gorgon", sign).build();
                //发送请求前
                Response response = chain.proceed(request);
                //发送请求后
                return response;
            }
        };
        client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        Log.i("nixxxxxx", "initInterceptor");
    }

    private void initView() {
        btn_login = findViewById(R.id.btn_login);
        btn_reset = findViewById(R.id.btn_reset);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        Log.i("nixxxxxx", "initView");
    }

    private void doLoginRetrofit() {
        Log.i("nixxxxxx", "doLoginRetrofit");
        new Thread() {
            @Override
            public void run() {
                Retrofit retrofit = new Retrofit.Builder().baseUrl("http://192.168.5.113:5001").build();
                ApiService httpRequest = retrofit.create(ApiService.class);
                Call call = httpRequest.postLogin("wupeigi", "123123");
                try {
                    ResponseBody responseBody = call.execute().body();
                    String responseString = responseBody.string();
                    Log.i("登录", responseString);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void doLoginOkhttp() {
        Log.i("nixxxxxx", "doLoginOkhttp");
        String user = String.valueOf(username.getText());
        String pass = String.valueOf(password.getText());
        HashMap<String, String> dataMap = new HashMap<String, String>();
        dataMap.put("username", user);
        dataMap.put("password", pass);
        // 发送JSON请求
        new Thread() {
            @Override
            public void run() {
                Log.i("nixxxxxx", "doLoginPost-json");
                JSONObject jsonObject = new JSONObject(dataMap);
                String jsonString = jsonObject.toString();
                RequestBody jsonBody = RequestBody.create(MediaType.parse("anolication/ison:charset=ut†-8"), jsonString);
                Request request = new Request.Builder().url("http://192.168.5.113:5001/index_post").post(jsonBody).build();
                Call call = client.newCall(request);
                try {
                    Response response = call.execute();
                    ResponseBody responseBody = response.body();
                    String resString = responseBody.string();
                    Gson gson = new Gson();
                    Log.i("nixxxxxx", resString);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("nixxxxxx", e.getMessage());
                }
            }
        }.start();
        // 发送GET请求
        new Thread() {
            @Override
            public void run() {
                Log.i("nixxxxxx", "doLoginGet-form");
                Request request = new Request.Builder().url("http://192.168.5.113:5001/index_get?username=zhangsan").build();
                Call call = client.newCall(request);
                try {
                    Response response = call.execute();
                    ResponseBody responseBody = response.body();
                    String resString = responseBody.string();
                    Log.i("nixxxxxx", resString);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("nixxxxxx", e.getMessage());
                }
            }
        }.start();
        // 发送POST请求
        new Thread() {
            @Override
            public void run() {
                Log.i("nixxxxxx", "doLoginPost-form");
                FormBody formBody = new FormBody.Builder().add("username", "小溪").build();
                Request request = new Request.Builder().url("http://192.168.5.113:5001/index_post").post(formBody).build();
                Call call = client.newCall(request);
                try {
                    Response response = call.execute();
                    ResponseBody responseBody = response.body();
                    String resString = responseBody.string();
                    Log.i("nixxxxxx", resString);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("nixxxxxx", e.getMessage());
                }
            }
        }.start();
    }

}