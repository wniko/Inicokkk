package com.example.inicokkk;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {

    public Button btn_login, btn_reset;
    public EditText username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("nixxxxxx", "onCreate");
        initView();
        initListener();
    }

    private void initView() {
        btn_login = findViewById(R.id.btn_login);
        btn_reset = findViewById(R.id.btn_reset);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        Log.i("nixxxxxx", "initView");
    }

    private void initListener() {
        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("nixxxxxx", "initListener，重置");
                username.setText("");
                password.setText("");
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("nixxxxxx", "initListener，登陆");
                doLoginGet();
                doLoginPost();
            }
        });
    }

    private void doLoginGet() {
        Log.i("nixxxxxx", "doLoginGet");
        String user = String.valueOf(username.getText());
        String pass = String.valueOf(password.getText());
        new Thread() {
            @Override
            public void run() {
                Log.i("nixxxxxx", "doLoginGet-run");
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url("http://192.168.5.113:5001/indexg").build();
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

    private void doLoginPost() {
        Log.i("nixxxxxx", "doLoginPost");
        String user = String.valueOf(username.getText());
        String pass = String.valueOf(password.getText());
        new Thread() {
            @Override
            public void run() {
                Log.i("nixxxxxx", "doLoginPost-run");
                OkHttpClient client = new OkHttpClient();
                FormBody formBody = new FormBody.Builder().add("username", "小溪").build();
                Request request = new Request.Builder().url("http://192.168.5.113:5001/indexp").post(formBody).build();
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