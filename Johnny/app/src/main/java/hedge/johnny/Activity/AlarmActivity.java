package hedge.johnny.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONObject;

import java.util.ArrayList;

import hedge.johnny.HedgeObject.HttpClient.HedgeHttpClient;
import hedge.johnny.HedgeObject.Navigators.NavigationActivity;
import hedge.johnny.R;

/**
 * Created by Administrator on 2015-07-21.
 */
public class AlarmActivity extends NavigationActivity implements AdapterView.OnItemClickListener {
    private ListView mMyListView = null, mSendListView = null;
    private AlarmAdapter mMyAdapter = null, mSendAdapter = null;
    private ArrayList<Alarm> myarray, sendarray;
    public static String tag;
    //private int selectPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        mMyListView = (ListView) findViewById(R.id.my_alarm_list);
        mMyListView.setDivider(new ColorDrawable(Color.WHITE)); // 리스트내 아이템간 경계선
        mMyListView.setDividerHeight(5);
        mSendListView = (ListView) findViewById(R.id.send_alarm_list);
        mSendListView.setDivider(new ColorDrawable(Color.WHITE)); // 리스트내 아이템간 경계선
        mSendListView.setDividerHeight(5);

        //fillMyAlarmArray();
        myarray = new ArrayList<Alarm>();
        sendarray = new ArrayList<Alarm>();

        refresh();

        // 어댑터
        mMyAdapter = new AlarmAdapter(this, R.layout.alarm_row, myarray);
        mSendAdapter = new AlarmAdapter(this, R.layout.alarm_row, sendarray);

        // 클릭 리스너
        mMyListView.setOnItemClickListener(this);
        mSendListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String db_id = view.getTag().toString();
                //_id값으로 db에 접속해서 off상태로 만들어

                Alarm check = sendarray.get(position);
                SharedPreferences pref = getSharedPreferences("HedgeMembers", 0);
                String userid = pref.getString("userid", "None");
                String pw = pref.getString("password", "None");

                if( check.getOnOff() )
                {
            //        HedgeHttpClient.GetInstance().OnOffAlarm(userid,pw,db_id,"0");
                }
                else
                {
          //          HedgeHttpClient.GetInstance().OnOffAlarm(userid,pw,db_id,"1");
                }

           //     HedgeHttpClient.GetInstance().InsertAlarmUpdate(userid,pw,userid,db_id,"2");
                refresh();
            }
        });
    }

    public void refresh(){
        myarray.clear();
        sendarray.clear();

        fillMyAlarmArray();

        mMyListView.setAdapter(mMyAdapter);
        mSendListView.setAdapter(mSendAdapter);
    }

    private Alarm CreateAlarmWithString(String[] row, String identifier)
    {
        int id = Integer.parseInt(row[0]);

        boolean daynight = Integer.parseInt(row[1]) >= 12;       //오전인지 오후인지 확인
        int hour = Integer.parseInt(row[1]) - (daynight ? 12 : 0);                //오후라면 12시간을 뺌
        int minute = Integer.parseInt(row[2]);
        int dayinfo = Integer.parseInt(row[3]);
        boolean day_of_week[] = new boolean[7];

        for(int j=6; j >= 0; j--)
        {
            day_of_week[j] = (dayinfo % 10 == 1);  // dayinfo : ex 1010101 : 월,수,금,일
            dayinfo /= 10;
        }

        boolean weather = row[4].equals("true");
        int alarm_type = Integer.parseInt(row[5]);
        boolean onoff = row[6].equals("true");
        boolean repeat = row[7].equals("true");
        String sender = row[8];
        String receiver = row[9];
        String title = row[10];

        return new Alarm(id, daynight, hour, minute, day_of_week, onoff, title, sender, receiver, alarm_type,identifier);
    }

    private void fillMyAlarmArray(){
        JSONObject jsonObject = new JSONObject();
        HedgeHttpClient.addValues(jsonObject,"sended","0");
        jsonObject = HedgeHttpClient.HedgeRequest("alarm_list",jsonObject);
        if(HedgeHttpClient.getValues(jsonObject,"result").equals("1") == false)
        {
            //error
            return;
        }

        for(int i=0; i<jsonObject.length()-1; i++)
        {
            JSONObject row = HedgeHttpClient.getObject(jsonObject,String.valueOf(i));
            String[] p = new String[11];
            p[0] = HedgeHttpClient.getValues(row,"id");
            p[1] = HedgeHttpClient.getValues(row,"hour");
            p[2] = HedgeHttpClient.getValues(row,"min");
            p[3] = HedgeHttpClient.getValues(row,"day");
            p[4] = HedgeHttpClient.getValues(row,"weather");
            p[5] = HedgeHttpClient.getValues(row,"alarm_type");
            p[6] = HedgeHttpClient.getValues(row,"on_off");
            p[7] = HedgeHttpClient.getValues(row,"repeating");
            p[8] = HedgeHttpClient.getValues(row,"fromid");
            p[9] = HedgeHttpClient.getValues(row,"toid");
            p[10] = HedgeHttpClient.getValues(row,"title");
            myarray.add(CreateAlarmWithString(p, "0"));
        }

        jsonObject = new JSONObject();
        HedgeHttpClient.addValues(jsonObject,"sended","1");
        jsonObject = HedgeHttpClient.HedgeRequest("alarm_list",jsonObject);

        for(int i=0; i<jsonObject.length()-1; i++)
        {
            JSONObject row = HedgeHttpClient.getObject(jsonObject,String.valueOf(i));
            String[] p = new String[11];
            p[0] = HedgeHttpClient.getValues(row,"id");
            p[1] = HedgeHttpClient.getValues(row,"hour");
            p[2] = HedgeHttpClient.getValues(row,"min");
            p[3] = HedgeHttpClient.getValues(row,"day");
            p[4] = HedgeHttpClient.getValues(row,"weather");
            p[5] = HedgeHttpClient.getValues(row,"alarm_type");
            p[6] = HedgeHttpClient.getValues(row,"on_off");
            p[7] = HedgeHttpClient.getValues(row,"repeating");
            p[8] = HedgeHttpClient.getValues(row,"fromid");
            p[9] = HedgeHttpClient.getValues(row,"toid");
            p[10] = HedgeHttpClient.getValues(row,"title");
            sendarray.add(CreateAlarmWithString(p, "1"));
        }

    }

    public void btnClickAlarm(View v) {
        switch (v.getId()) {
            case R.id.btn_add_alarm:
            {
                Intent intent = new Intent(AlarmActivity.this, AddAlarmActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_SINGLE_TOP);
                tag = "-1";
                goNextHistory("AddAlarmActivity", intent);
                break;
            }
            case R.id.btn_del:
            {
                int index = (Integer)v.getTag();
                String alarmid = String.valueOf(index);
                //서버에 삭제를 요청
                // HedgeHttpClient.GetInstance().DeleteAlarm(id,pw,alarmid);
                JSONObject jsonObject = new JSONObject();
                HedgeHttpClient.addValues(jsonObject,"on_off","1");
                HedgeHttpClient.addValues(jsonObject,"alarmid",alarmid);
                jsonObject = HedgeHttpClient.HedgeRequest("delete_alarm",jsonObject);
                refresh();
                //Toast.makeText(getApplicationContext(), (Integer)v.getTag() + " del", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.btn_modify:
            {
                int index = (Integer)v.getTag();
                Intent intent = new Intent(AlarmActivity.this, AddAlarmActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("alarmid", String.valueOf(index));
                tag = String.valueOf(index);
                goNextHistory("AddAlarmActivity", intent);
                //Toast.makeText(getApplicationContext(), (Integer)v.getTag() + " mod", Toast.LENGTH_SHORT).show();
                break;
            }
            default:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        // 리스트를 터치하면 토스트를 띄운다.
        //Toast.makeText(getApplicationContext(), position + "번째 리스트 클릭", Toast.LENGTH_SHORT).show();

        //그 몇번째 아이템을 불러와서 _id값을 가져와
        String db_id = view.getTag().toString();
        //_id값으로 db에 접속해서 off상태로 만들어

        Alarm check = myarray.get(position);
        JSONObject jsonObject = new JSONObject();
        HedgeHttpClient.addValues(jsonObject,"alarmid",db_id);

        if( check.getOnOff() )
        {
            HedgeHttpClient.addValues(jsonObject,"on_off","0");
            jsonObject = HedgeHttpClient.HedgeRequest("onoff_alarm",jsonObject);
        }
        else
        {
            HedgeHttpClient.addValues(jsonObject,"on_off","1");
            jsonObject = HedgeHttpClient.HedgeRequest("onoff_alarm",jsonObject);
        }

        // HedgeHttpClient.GetInstance().InsertAlarmUpdate(userid,pw,userid,db_id,"2");
        // 업데이트 리스트
        refresh();
    }
}
