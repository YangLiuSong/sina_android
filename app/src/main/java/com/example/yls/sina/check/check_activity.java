package com.example.yls.sina.check;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ListView;

import com.alibaba.fastjson.JSONObject;
import com.example.yls.sina.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class check_activity extends AppCompatActivity {

    private List<Check> list = new ArrayList<Check>();

    private static final String URL = "http://10.0.2.2:8080/getcount";
    private String re;
    OkHttpClient client = new OkHttpClient();
    private String mon[]={"1","2","3","4","5","6","7","8","9","10","11","12"};



    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_activity);

        //设置表格标题的背景颜色
        ViewGroup tableTitle = (ViewGroup) findViewById(R.id.table_title);
        tableTitle.setBackgroundColor(Color.rgb(177, 173, 172));

        //设置线程以完成对数据库的连接与读取
        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
                try{
                    for(int i=0;i<12;i++){
                        //按月份获取到标记数据
                        getdata(mon[i]);
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        });
        //先进行线程--完成数据的获取，在生成表格
        try {
            thread.start();
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ListView tableListView = (ListView) findViewById(R.id.list);

        TableAdapter adapter = new TableAdapter(this, list);

        tableListView.setAdapter(adapter);
    }


    private void getdata(String month) throws IOException {
        //构造http请求体
        RequestBody Body = new FormBody.Builder()
                .add("month", month)
                .build();
        Request request = new Request.Builder()
                .url(URL)
                .post(Body)
                .build();
        Log.i("Request", "Request is :" + request);
        Response response = null;
        try {
            response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                re = response.body().string();
                System.out.println("服务器响应为: " + re);
                JSONObject job = JSONObject.parseObject(re);
                String remonth = job.getString("month");
                int rehastag = job.getInteger("has_tag");
                int renotag = job.getInteger("no_tag");
                int reallcount = job.getInteger("all_count");
                System.out.println(remonth + "  " + rehastag + "   " + renotag  + "    "+ reallcount);

                list.add(new Check(remonth,rehastag,renotag,reallcount));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                response.body().close();
            }
        }
    }
}
