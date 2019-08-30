package com.example.huge.fzugang.CarpoolBlock;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huge.fzugang.CarpoolBlock.FromJSON.CarpoolAddResponse;
import com.example.huge.fzugang.CarpoolBlock.ToJSON.CarpoolReleasePost;
import com.example.huge.fzugang.R;
import com.example.huge.fzugang.Utils.ActivityCollector;
import com.example.huge.fzugang.Utils.OkHttpUtil;
import com.example.huge.fzugang.Utils.SharedPreferencesUtil;
import com.google.gson.Gson;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

import static com.example.huge.fzugang.Utils.constantUtil.SUCCESS_CODE;

public class CarpoolContentAddActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.carpool_main_activity_meeting_place)
    TextView carpoolMainActivityMeetingPlace;
    @BindView(R.id.carpool_main_activity_date)
    TextView carpoolMainActivityDate;
    @BindView(R.id.carpool_card_view_arrow)
    ImageView carpoolCardViewArrow;
    @BindView(R.id.carpool_main_activity_num_of_people)
    TextView carpoolMainActivityNumOfPeople;
    @BindView(R.id.carpool_main_activity_destination)
    TextView carpoolMainActivityDestination;
    @BindView(R.id.carpool_main_activity_time)
    TextView carpoolMainActivityTime;
    @BindView(R.id.carpool_main_activity_user_name)
    TextView carpoolMainActivityUserName;
    @BindView(R.id.carpool_main_activity_price)
    TextView carpoolMainActivityPrice;
    @BindView(R.id.carpool_content_edit_text)
    EditText carpoolContentEditText;
    @BindView(R.id.carpool_upload_button)
    QMUIRoundButton carpoolUploadButton;

    String meetPlace;
    String destination;
    String time;
    String date;
    String price;
    int numOfPeople;

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.carpool_upload_button:
                postCarpoolData();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carpool_content_add);
        ActivityCollector.addActivity(this);
        ButterKnife.bind(this);

        init();

        carpoolUploadButton.setOnClickListener(CarpoolContentAddActivity.this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    private void init() {
        Intent intent = getIntent();
        meetPlace = intent.getStringExtra("meetPlace");
        destination = intent.getStringExtra("destination");
        time = intent.getStringExtra("time");
        date = intent.getStringExtra("date");
        price = intent.getStringExtra("price");
        numOfPeople = intent.getIntExtra("numOfPeople", 0);
        carpoolMainActivityMeetingPlace.setText(meetPlace);
        carpoolMainActivityDestination.setText(destination);
        carpoolMainActivityTime.setText("见面时间:  "+time);
        carpoolMainActivityDate.setText("日期: "+date);
        carpoolMainActivityPrice.setText("约￥"+price);
        carpoolMainActivityNumOfPeople.setText(Integer.toString(numOfPeople));
    }

    private void postCarpoolData() {
        CarpoolReleasePost post=new CarpoolReleasePost();
        post.setToken(SharedPreferencesUtil.getStoredMessage(CarpoolContentAddActivity.this,"token"));
        post.setMeetPlace(meetPlace);
        post.setDestination(destination);
        post.setTime(time);
        post.setDate(date);
        post.setPrice(price);
        post.setNumOfPeople(Integer.toString(numOfPeople));
        Gson gson=new Gson();
        String postJSONData=gson.toJson(post);
        Log.d("postJSON", "postCarpoolData: "+postJSONData);
        OkHttpUtil.sendOkHttpRequeat("fdb1.0.0/carpooling/add",postJSONData,new okhttp3.Callback(){
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                CarpoolAddResponse carpoolAddResponse=parseResponse(response.body().string());
                ActivityCollector.finishAll();
                if(carpoolAddResponse.getCode()==SUCCESS_CODE){
                    Log.d("post", "onResponse: succeeded1");
                    showToast("发布成功");
                }else{
                    Log.d("post", "onResponse: succeeded2");
                    showToast("发布失败,错误代码:  "+carpoolAddResponse.getCode());
                }
            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("post", "onResponse: failure");
                showToast("请求异常,请检查网络");
            }
        });
    }

    private void showToast(final String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
            }
        });
    }

    private CarpoolAddResponse parseResponse(String responseStr) {
        Gson gson=new Gson();
        Log.d("response", "parseResponse: "+responseStr);
        return gson.fromJson(responseStr,CarpoolAddResponse.class);
    }
}
