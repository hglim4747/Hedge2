package hedge.johnny.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import hedge.johnny.HedgeObject.AlarmBackgroundService;
import hedge.johnny.HedgeObject.HttpClient.HedgeHttpClient;
import hedge.johnny.R;

/**
 * Created by Administrator on 2015-08-08.
 */
public class InitialActivity extends Activity {
    Timer mTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);

        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                SharedPreferences pref = getSharedPreferences("HedgeMembers", 0);
                String id = pref.getString("userid", "");
                String pw = pref.getString("password", "");
                HedgeHttpClient.GetInstance().SetAccount(id,pw);

                //if(true)
                if(id.equals("")==false && pw.equals("")==false) //  저장된 아이디가 있음
                {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject = HedgeHttpClient.GetInstance().HedgeRequest("ensure_member", jsonObject);

                    if(HedgeHttpClient.getValues(jsonObject,"result").equals("1")) // 저장된 아이디가 로그인에 성공하면 메인액티비티로
                    {
                        SharedPreferences.Editor edit = pref.edit();
                        edit.putString("username", HedgeHttpClient.getValues(jsonObject, "username"));
                        edit.putString("phonenum", HedgeHttpClient.getValues(jsonObject, "phonenum"));
                        edit.commit();
                        Intent i = new Intent(InitialActivity.this, MainActivity.class);
                        startActivity(i);

                        Intent s = new Intent(InitialActivity.this, AlarmBackgroundService.class);
                        startService(s);

                        finish(); return;
                    }
                    else // 저장된 아이디가 있으나 로그인에 실패하면 로그인시도
                    {
                        Intent i = new Intent(InitialActivity.this, LoginActivity.class);
                        startActivity(i);
                        finish(); return;
                    }

                }
                else // 저장된 아이디가 없으면 로그인시도
                {
                    Intent i = new Intent(InitialActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish(); return;
                }

            }
        }, 1200);
    }

    @Override
    protected void onDestroy(){
        mTimer.cancel();
        super.onDestroy();
    }
}
