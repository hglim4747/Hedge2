package hedge.johnny.HedgeObject;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;

import hedge.johnny.HedgeObject.HttpClient.HedgeHttpClient;

/**
 * Created by EDGE01 on 2015-08-21.
 */
public class AlarmBackgroundService extends Service implements Runnable{
    AlarmManager alarm;
    Thread thread;

    @Override
    public void onCreate() {
        alarm = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
        checkChanges();

        thread = new Thread(this);
        thread.start();
    }

    public void run(){
        while (true){
            try {
                Log.e("Thread Running Check :", "ok");

                thread.sleep(10000);

                //확인
                checkChanges();
            }catch (Exception e){
                Log.e("Thread", e.toString());
            }
        }
    }

    private void checkChanges(){
        SharedPreferences pref = getSharedPreferences("HedgeMembers", 0);
        String id = pref.getString("userid", "None");
        String pw = pref.getString("password", "None");

        ArrayList<String[]> alarmUpdateList = new ArrayList<String[]>();
        HedgeHttpClient.GetInstance().AlarmUpdateList(id,pw,alarmUpdateList);

        for(int i=0; i<alarmUpdateList.size(); i++)
        {
            String[] row = alarmUpdateList.get(i);
            String modifier = row[0]; // 수정자 (아직 안쓰임)
            String owner = row[1]; // 알람을 받은 사람 (백그라운드 업데이트가 필요한 유저)
            String alarmid = row[2]; // 알람의 id

            ArrayList<String[]> src = new ArrayList<String[]>();
            HedgeHttpClient.GetInstance().PopAlarmWithAlarmID(id,pw,alarmid,src);
            String[] alarminfo = src.get(0);

            if(alarminfo[0].equals("Deleted"))
            {
                cancelAlarm(Integer.parseInt(alarmid));
            }
            else if(alarminfo[3].equals("2"))
            {
                cancelAlarm(Integer.parseInt(alarmid));
                setAlarm(alarminfo, alarmid);
            }
            else
            {
                setAlarm(alarminfo, alarmid);
            }

            Log.e("Thread Running : " + alarmid + "알람매니저와 동기화시키기", "ok");
        }
    }

    private void cancelAlarm(int alarmId){
        Intent intent = new Intent(this, HedgeAlarmService.class);
        PendingIntent pender = PendingIntent.getService(this, alarmId, intent, 0);
        alarm.cancel(pender);
    }

    private void setAlarm(String[] alarminfo, String alarmId){
        Intent intent = new Intent(this, HedgeAlarmService.class);
        intent.putExtra("repeat", alarminfo[7]);    //intent에 요일 on/off정보를 넣음.
        intent.putExtra("weather_alarm", alarminfo[4]);
        intent.putExtra("alarm_type", alarminfo[5]);
        intent.putExtra("db_id", alarmId);

        PendingIntent pender = PendingIntent.getService(this, Integer.parseInt(alarmId), intent, 0);

        long OneDay = 24 * 60 * 60 * 1000;  //하루를 MilliSecond로 표현

        Calendar current = Calendar.getInstance();
        current.set(Calendar.SECOND, 0);
        current.set(Calendar.MILLISECOND, 0);
        long cTime = current.getTimeInMillis(); //currentTime = cTime

        Calendar target = Calendar.getInstance();
        target.set(Calendar.HOUR_OF_DAY, Integer.parseInt(alarminfo[1]));
        target.set(Calendar.MINUTE, Integer.parseInt(alarminfo[2]));
        target.set(Calendar.SECOND, 0);
        target.set(Calendar.MILLISECOND, 0);
        long tTime = target.getTimeInMillis();  //targetTime = tTime

        long trigger = cTime > tTime ? tTime + OneDay - cTime : tTime - cTime;

        long intervalTime = OneDay; //하루마다 반복하고, 알람시 켜지는 클래스에서 요일반복을 처리한다.

        //alarm.set(AlarmManager.RTC, System.currentTimeMillis() + 1000, pender);
        alarm.setRepeating(AlarmManager.RTC_WAKEUP, cTime + trigger, intervalTime, pender);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // TODO Auto-generated method stub
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }
}
