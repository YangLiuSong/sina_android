package com.example.yls.sina.classification;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.yls.sina.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


import cn.qqtheme.framework.picker.OptionPicker;
import cn.qqtheme.framework.widget.WheelView;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class classification_activity extends AppCompatActivity {

    private GridView gv;//按钮九宫格控件声明
    private String URLCount = "http://10.0.2.2:8080/getcount";
    private int month_index = 0;
    private String picURL = "http://10.0.2.2:8080/getpic";
    private String updateURL_data = "http://10.0.2.2:8080/updateData";
    private String updateURL_count = "http://10.0.2.2:8080/updateCount";
    private String re;
    OkHttpClient client = new OkHttpClient();
    private ImageView img;
    private String[] title = {"餐饮活动", "居民活动", "校园活动", "户外活动", "娱乐活动", "出行活动", "购物活动", "工作活动", "体育活动"};
    private int[] image = {R.drawable.food, R.drawable.life, R.drawable.school, R.drawable.outdoor, R.drawable.enterment, R.drawable.travel, R.drawable.shopping, R.drawable.work, R.drawable.sport};
    private String[] txid = {"1", "2", "3", "4", "5", "6", "7", "8", "9"};
    private String pic_id;
    private Bitmap bmp;
    private TextView tx_class_month;

    private String tag_ids;
    private String tag_names;

    private int rehastag;
    private int renotag;

    private Button btn_other;

    private Thread thread_get_pic;
    private Thread thread_update_pic;
    private Thread thread_update_count;

    @Override
    protected void onStart() {
        super.onStart();
        thread_get_pic = new Thread(get_pic);
        try {
            thread_get_pic.start();
            thread_get_pic.join();
            thread_get_pic.interrupt();
            img.setImageBitmap(pic_bmp.get(pic_index));//将图片显示在imageview控件中
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    Runnable get_pic = new Runnable() {
        @Override
        public void run() {//run()在新的线程中运行
            getpics(tx_class_month.getText().toString());
        }
    };
    Runnable update_pic = new Runnable() {
        @Override
        public void run() {//run()在新的线程中运行
            update_datapic(repicids.get(pic_index),tag_ids,tag_names);
        }
    };
    Runnable update_count = new Runnable() {
        @Override
        public void run() {//run()在新的线程中运行
            getdataCount(tx_class_month.getText().toString());
            update_dataCount(tx_class_month.getText().toString(),rehastag,renotag);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.class_activity);

        //控件的绑定
        img = (ImageView)findViewById(R.id.img_sina);
        tx_class_month = (TextView)findViewById(R.id.text_class_month);
        //创建九宫格按钮
        List<PicEntity> picEntityList = new ArrayList<PicEntity>();
        for(int i=0;i<title.length;i++){
            picEntityList.add(new PicEntity(title[i],image[i],txid[i]));
        }
        gv = (GridView) findViewById(R.id.gv);
        MyAdapter adapter = new MyAdapter(this,picEntityList);
        gv.setAdapter(adapter);
        //九宫格按钮响应代码段
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //获取点击的格子的信息
                PicEntity item = (PicEntity) parent.getItemAtPosition(position);

                tag_ids = item.getTextId();
                tag_names = item.getTitle();
                System.out.println(tag_ids);
                System.out.println(tag_names);
                thread_update_pic = new Thread(update_pic);
                thread_update_count = new Thread(update_count);
//                链接数据库，在完成一次分类后更换图片
                try {
                    thread_update_pic.start();
                    thread_update_pic.join();
                    thread_update_pic.interrupt();
                    pic_index++;
                    thread_update_count.start();
                    thread_update_count.join();
                    thread_update_count.interrupt();
                    if(pic_index!=20){
                        img.setImageBitmap(pic_bmp.get(pic_index));
                    }
                    else {
                        pic_bmp.clear();
                        repicids.clear();
                        thread_get_pic = new Thread(get_pic);
                        pic_index = 0;
                        try {
                            thread_get_pic.start();
                            thread_get_pic.join();
                            thread_get_pic.interrupt();
                            img.setImageBitmap(pic_bmp.get(pic_index));//将图片显示在imageview控件中
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        btn_other = (Button)findViewById(R.id.btn_class_other);
        btn_other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tag_ids = "10";
                tag_names = "其他类别";
                System.out.println(tag_ids);
                System.out.println(tag_names);
                thread_update_pic = new Thread(update_pic);
                thread_update_count = new Thread(update_count);
                try {
                    thread_update_pic.start();
                    thread_update_pic.join();
                    thread_update_pic.interrupt();
                    pic_index++;
                    thread_update_count.start();
                    thread_update_count.join();
                    thread_update_count.interrupt();
                    if(pic_index!=20){
                        img.setImageBitmap(pic_bmp.get(pic_index));
                    }
                    else {
                        pic_bmp.clear();
                        repicids.clear();
                        thread_get_pic = new Thread(get_pic);
                        pic_index = 0;
                        try {
                            thread_get_pic.start();
                            thread_get_pic.join();
                            thread_get_pic.interrupt();
                            img.setImageBitmap(pic_bmp.get(pic_index));//将图片显示在imageview控件中
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private List<String> repicids = new ArrayList<String>();
    private List<Bitmap> pic_bmp = new ArrayList<Bitmap>();
    private int pic_index = 0;
    private void getpics(String month){
        RequestBody Body = new FormBody.Builder().add("month",month).build();
        Request request = new Request.Builder().url(picURL).post(Body).build();
        Response response = null;
        try{
            response = client.newCall(request).execute();
            if (response.isSuccessful()){
                re = response.body().string();
                System.out.println("服务器响应为: " + re);
                JSONArray jsonArray = JSON.parseArray(re);
                for (int i = 0;i<jsonArray.size();i++){
                    JSONObject job = jsonArray.getJSONObject(i);
                    pic_bmp.add(getURLimage(job.getString("pic_url")));
                    repicids.add(job.getString("pic_id"));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //选择栏控件的相应函数段
    public void onOptionPicker(View view) {
        //选择栏的可选参数赋值
        OptionPicker picker = new OptionPicker(this, new String[]{"1", "2", "3","4","5","6","7","8","9","10","11","12"});
        picker.setCanceledOnTouchOutside(false);
        picker.setDividerRatio(WheelView.DividerConfig.FILL);
        picker.setShadowColor(Color.RED, 40);
        //选择栏初始显示的位置
        picker.setSelectedIndex(month_index);
        picker.setCycleDisable(true);
        picker.setTextSize(11);
        //选择确定后的响应事件
        picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(int index, String item) {
                System.out.println("index=" + index + ", item=" + item);
                tx_class_month.setText(item);
                month_index = index;
                pic_bmp.clear();
                repicids.clear();
                thread_get_pic = new Thread(get_pic);
                pic_index = 0;
                try {
                    thread_get_pic.start();
                    thread_get_pic.join();
                    thread_get_pic.interrupt();
                    img.setImageBitmap(pic_bmp.get(pic_index));//将图片显示在imageview控件中
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        picker.show();
    }
    //加载图片
    public Bitmap getURLimage(String url) {
        Bitmap bmp = null;
        try {
            URL myurl = new URL(url);
            // 获得连接
            HttpURLConnection conn = (HttpURLConnection) myurl.openConnection();
            conn.setConnectTimeout(6000);//设置超时
            conn.setDoInput(true);
            conn.setUseCaches(false);//不缓存
            conn.connect();
            InputStream is = conn.getInputStream();//获得图片的数据流
            bmp = BitmapFactory.decodeStream(is);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bmp;
    }
    //更新图片相关的类别信息
    private void update_datapic(String pic_id,String tag_ids,String tag_names){
        //构造http请求体
        System.out.println(pic_id);
        RequestBody Body = new FormBody.Builder()
                .add("pic_id", pic_id)
                .add("tag_ids",tag_ids)
                .add("tag_names",tag_names)
                .build();
        Request request = new Request.Builder()
                .url(updateURL_data)
                .post(Body)
                .build();
        Log.i("Request", "Request is :" + request);
        try {
            client.newCall(request).execute();
            System.out.println("post请求发送成功！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //更新计数相关的信息
    private void update_dataCount(String month,int hastag,int notag){
        //构造http请求体
        RequestBody Body = new FormBody.Builder()
                .add("month", month)
                .add("has_tag",hastag+"")
                .add("no_tag",notag+"")
                .build();
        Request request = new Request.Builder()
                .url(updateURL_count)
                .post(Body)
                .build();
        Log.i("Request", "Request is :" + request);
        try {
            client.newCall(request).execute();
            System.out.println("post请求发送成功！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //获取计数相关的信息
    private void getdataCount(String month){
        //构造http请求体
        RequestBody Body = new FormBody.Builder()
                .add("month",month )
                .build();
        Request request = new Request.Builder()
                .url(URLCount)
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
                rehastag = job.getInteger("has_tag");
                renotag = job.getInteger("no_tag");
                rehastag +=1;
                renotag -=1;
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
