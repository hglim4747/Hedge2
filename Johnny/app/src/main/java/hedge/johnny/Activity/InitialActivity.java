package hedge.johnny.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;


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
                String result[] = new String[2];
                SharedPreferences pref = getSharedPreferences("HedgeMembers", 0);
                String id = pref.getString("userid", "None");
                String pw = pref.getString("password", "None");

                //if(true)
                if(id.equals("None")==false && pw.equals("None")==false) //  저장된 아이디가 있음
                {
                    result = HedgeHttpClient.GetInstance().EnsureMember(id, pw).split(","); // 로그인시도
                    if(result[0].equals("None")==false && result[0].equals("None")==false) // 저장된 아이디가 로그인에 성공하면 메인액티비티로
                    {
                        SharedPreferences.Editor edit = pref.edit();
                        edit.putString("username", result[0]);
                        edit.putString("phonenum", result[1]);
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
