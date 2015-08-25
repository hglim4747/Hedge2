package hedge.johnny.HedgeObject.HttpClient;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import hedge.johnny.HedgeObject.Weather;

/**
 * Created by Gyu on 2015-08-16.
 */
public class HedgeHttpClient {
    final static String addr = "http://hglim.me:2025";
//    final static String addr = "http://hglim.me:2025";

    private static final HedgeHttpClient instance = new HedgeHttpClient();
    private static DefaultHttpClient client = new DefaultHttpClient();

    public static HedgeHttpClient GetInstance()
    {
        return instance;
    }

    private HedgeHttpClient()
    {

    }

    public static String InsertMember(String username,String userid, String password, String phonenum)
    {
        InsertMemberT t = new InsertMemberT();
        t.execute(username,userid, password,phonenum);
        String result;
        try {
            result = t.get();
        } catch (InterruptedException e) {
            result = "Exception";
            e.printStackTrace();
        } catch (ExecutionException e) {
            result = "Exception";
            e.printStackTrace();
        }
        return result;
    }

    public static String EnsureMember(String userid, String password) // 아이디,비밀번호로 이름 리턴, 로그인 실패시 false 리턴
    {
        EnsureMemberT t = new EnsureMemberT();
        t.execute(userid, password);
        String result;
        try {
            result = t.get();
        } catch (InterruptedException e) {
            result = "Exception";
            e.printStackTrace();
        } catch (ExecutionException e) {
            result = "Exception";
            e.printStackTrace();
        }
        return result;
    }

    public static String FindMember(String userid)
    {
        FindMemberT t = new FindMemberT();
        t.execute(userid);
        String result;
        try {
            result = t.get();
        } catch (InterruptedException e) {
            result = "Exception";
            e.printStackTrace();
        } catch (ExecutionException e) {
            result = "Exception";
            e.printStackTrace();
        }
        return result;
    }

    public static String RequestFriend(String userid, String password, String friendid)
    {
        RequestFriendT t = new RequestFriendT();
        t.execute(userid,password,friendid);
        String result;
        try {
            result = t.get();
        } catch (InterruptedException e) {
            result = "Exception";
            e.printStackTrace();
        } catch (ExecutionException e) {
            result = "Exception";
            e.printStackTrace();
        }
        return result;
    }

    public static String AcceptFriend(String userid, String password, String friendid)
    {
        AcceptFriendT t = new AcceptFriendT();
        t.execute(userid,password,friendid);
        String result;
        try {
            result = t.get();
        } catch (InterruptedException e) {
            result = "Exception";
            e.printStackTrace();
        } catch (ExecutionException e) {
            result = "Exception";
            e.printStackTrace();
        }
        return result;
    }

    public static String DeleteFriend(String userid, String password, String friendid)
    {
        DeleteFriendT t = new DeleteFriendT();
        t.execute(userid,password,friendid);
        String result;
        try {
            result = t.get();
        } catch (InterruptedException e) {
            result = "Exception";
            e.printStackTrace();
        } catch (ExecutionException e) {
            result = "Exception";
            e.printStackTrace();
        }
        return result;
    }

    public static String FriendRequestList(String userid, String password, String sended, ArrayList<String[]> dest)
    {
        FriendRequestListT t = new FriendRequestListT();
        t.execute(userid,password,sended);
        String result;
        try {
            result = t.get();
        } catch (InterruptedException e) {
            result = "Exception";
            e.printStackTrace();
        } catch (ExecutionException e) {
            result = "Exception";
            e.printStackTrace();
        }
        String[] rows = result.split("/");
        if(!rows[0].equals(""))
        {
            for(int i=0; i<rows.length; i++)
            {
                dest.add( rows[i].split(",") );
            }
        }
        return result;
    }

    public static String FriendList(String userid, String password, ArrayList<String[]> dest)
    {
        FriendListT t = new FriendListT();
        t.execute(userid,password);
        String result = "";
        try {
            result = t.get();

        } catch (InterruptedException e) {
            result = "Exception";
            e.printStackTrace();
        } catch (ExecutionException e) {
            result = "Exception";
            e.printStackTrace();
        }
        String[] rows = result.split("/");
        if(!rows[0].equals(""))
        {
            for(int i=0; i<rows.length; i++)
            {
                dest.add( rows[i].split(",") );
            }
        }
        return result;
    }

    public static String AlarmList(String userid, String password, ArrayList<String[]> dest, String sended)
    {
        AlarmListT t = new AlarmListT();
        t.execute(userid, password,sended);
        String result = "";
        try {
            result = t.get();

        } catch (InterruptedException e) {
            result = "Exception";
            e.printStackTrace();
        } catch (ExecutionException e) {
            result = "Exception";
            e.printStackTrace();
        }
        String[] rows = result.split("/");
        if(!rows[0].equals(""))
        {
            for(int i=0; i<rows.length; i++)
            {
                dest.add( rows[i].split(",") );
            }
        }
        return result;
    }

    public static String InsertAlarm(String userid, String password, String hour, String min,
                                      String day, String weather, String alarm_type, String on_off, String repeating, String fromid, String toid, String title)
    {
        InsertAlarmT t = new InsertAlarmT();
        t.execute( userid,  password,  hour,  min, day,  weather, alarm_type, on_off, repeating, fromid, toid, title);
        String result;
        try {
            result = t.get();
        } catch (InterruptedException e) {
            result = "Exception";
            e.printStackTrace();
        } catch (ExecutionException e) {
            result = "Exception";
            e.printStackTrace();
        }
        return result;
    }

    public static String ModifyAlarm(String userid, String password, String hour, String min,
                                     String day, String weather, String alarm_type, String on_off, String repeating, String title, String alarmid)
    {
        ModifyAlarmT t = new ModifyAlarmT();
        t.execute( userid,  password,  hour,  min, day,  weather, alarm_type, on_off, repeating, title, alarmid);
        String result;
        try {
            result = t.get();
        } catch (InterruptedException e) {
            result = "Exception";
            e.printStackTrace();
        } catch (ExecutionException e) {
            result = "Exception";
            e.printStackTrace();
        }
        return result;
    }

    public static String OnOffAlarm(String userid, String password,String alarmid, String on_off)
    {
        OnoffAlarmT t = new OnoffAlarmT();
        t.execute(userid, password, alarmid, on_off);
        String result;
        try {
            result = t.get();
        } catch (InterruptedException e) {
            result = "Exception";
            e.printStackTrace();
        } catch (ExecutionException e) {
            result = "Exception";
            e.printStackTrace();
        }
        return result;
    }

    public static String DeleteAlarm(String userid, String password, String alarmid)
    {
        DeleteAlarmT t = new DeleteAlarmT();
        t.execute(userid, password, alarmid);
        String result;
        try {
            result = t.get();
        } catch (InterruptedException e) {
            result = "Exception";
            e.printStackTrace();
        } catch (ExecutionException e) {
            result = "Exception";
            e.printStackTrace();
        }
        return result;
    }

    public static String AlarmUpdateList(String userid, String password, ArrayList<String[]> dest)
    {
        AlarmUpdateListT t = new AlarmUpdateListT();
        t.execute(userid,password);
        String result;
        try {
            result = t.get();
        } catch (InterruptedException e) {
            result = "Exception";
            e.printStackTrace();
        } catch (ExecutionException e) {
            result = "Exception";
            e.printStackTrace();
        }
        String[] rows = result.split("/");
        if(!rows[0].equals(""))
        {
            for(int i=0; i<rows.length; i++)
            {
                dest.add( rows[i].split(",") );
            }
        }
        return result;
    }

    public static String InsertAlarmUpdate(String userid, String password, String modifier, String alarmid, String state)
    {
        InsertAlarmUpdateT t = new InsertAlarmUpdateT();
        t.execute(userid, password, modifier, alarmid, state);
        String result;
        try {
            result = t.get();
        } catch (InterruptedException e) {
            result = "Exception";
            e.printStackTrace();
        } catch (ExecutionException e) {
            result = "Exception";
            e.printStackTrace();
        }
        return result;
    }

    public static String GetAlarmWithAlarmID(String userid, String password, String alarmid, ArrayList<String[]> dest)
    {
        GetAlarmWithAlarmIDT t = new GetAlarmWithAlarmIDT();
        t.execute(userid, password,alarmid);
        String result;
        try {
            result = t.get();
        } catch (InterruptedException e) {
            result = "Exception";
            e.printStackTrace();
        } catch (ExecutionException e) {
            result = "Exception";
            e.printStackTrace();
        }
        String[] rows = result.split("/");
        if(!rows[0].equals(""))
        {
            for(int i=0; i<rows.length; i++)
            {
                dest.add( rows[i].split(",") );
            }
        }
        return result;
    }

    public static String PopAlarmWithAlarmID(String userid, String password, String alarmid, ArrayList<String[]> dest)
    {
        PopAlarmWithAlarmIDT t = new PopAlarmWithAlarmIDT();
        t.execute(userid, password,alarmid);
        String result;
        try {
            result = t.get();
        } catch (InterruptedException e) {
            result = "Exception";
            e.printStackTrace();
        } catch (ExecutionException e) {
            result = "Exception";
            e.printStackTrace();
        }
        String[] rows = result.split("/");
        if(!rows[0].equals(""))
        {
            for(int i=0; i<rows.length; i++)
            {
                dest.add( rows[i].split(",") );
            }
        }
        return result;
    }

}

class InsertMemberT extends AsyncTask<String, Void, String>{
    @Override
    protected String doInBackground(String... params) {
        return (String)InsertMember((String) params[0], (String) params[1], (String) params[2], (String) params[3]);
    }

    private String InsertMember(String username,String userid, String password, String phonenum)
    {
        String result = "";
        String url = "http://hglim.me:2025" + "/member_insert";
        HttpPost httppost = new HttpPost(url);
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("username", username));
        nameValuePairs.add(new BasicNameValuePair("userid", userid));
        nameValuePairs.add(new BasicNameValuePair("password", password));
        nameValuePairs.add(new BasicNameValuePair("phonenum", phonenum));
        try {
            UrlEncodedFormEntity entityRequest = new UrlEncodedFormEntity(nameValuePairs, "UTF-8");
            httppost.setEntity(entityRequest);

            DefaultHttpClient client = new DefaultHttpClient();
            HttpResponse response = client.execute(httppost);
            String line = null;
            BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            while((line = br.readLine()) != null){
                result += line;
            }

        }catch(Exception e){
            e.printStackTrace();
            result = "Fail";
        }
        return result;
    }
}
class EnsureMemberT extends AsyncTask<String, Void, String>{
    @Override
    protected String doInBackground(String... params) {
        return (String)EnsureMember((String) params[0], (String) params[1]);
    }

    private String EnsureMember(String userid, String password)
    {
        String result = "";
        String url = "http://hglim.me:2025" + "/ensure_member";
        HttpPost httppost = new HttpPost(url);
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("userid", userid));
        nameValuePairs.add(new BasicNameValuePair("password", password));

        try {
            UrlEncodedFormEntity entityRequest = new UrlEncodedFormEntity(nameValuePairs, "UTF-8");
            httppost.setEntity(entityRequest);

            DefaultHttpClient client = new DefaultHttpClient();
            HttpResponse response = client.execute(httppost);
            String line = null;
            BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            while((line = br.readLine()) != null){
                result += line;
            }

        }catch(Exception e){
            e.printStackTrace();
            result = "Fail";
        }
        return result;
    }
}
class FindMemberT extends AsyncTask<String, Void, String>{
    @Override
    protected String doInBackground(String... params) {
        return (String)FindMember((String) params[0]);
    }

    private String FindMember(String userid)
    {
        String result = "";
        String url = "http://hglim.me:2025" + "/find_member";
        HttpPost httppost = new HttpPost(url);
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("userid", userid));

        try {
            UrlEncodedFormEntity entityRequest = new UrlEncodedFormEntity(nameValuePairs, "UTF-8");
            httppost.setEntity(entityRequest);

            DefaultHttpClient client = new DefaultHttpClient();
            HttpResponse response = client.execute(httppost);
            String line = null;
            BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            while((line = br.readLine()) != null){
                result += line;
            }

        }catch(Exception e){
            e.printStackTrace();
            result = "Fail";
        }
        return result;
    }
}
class RequestFriendT extends AsyncTask<String, Void, String>{
    @Override
    protected String doInBackground(String... params) {
        return (String)RequestFriend((String) params[0], (String) params[1], (String) params[2]);
    }

    private String RequestFriend(String userid, String password, String friendid)
    {
        String result = "";
        String url = "http://hglim.me:2025" + "/request_friend";
        HttpPost httppost = new HttpPost(url);
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("userid", userid));
        nameValuePairs.add(new BasicNameValuePair("password", password));
        nameValuePairs.add(new BasicNameValuePair("friendid", friendid));


        try {
            UrlEncodedFormEntity entityRequest = new UrlEncodedFormEntity(nameValuePairs, "UTF-8");
            httppost.setEntity(entityRequest);

            DefaultHttpClient client = new DefaultHttpClient();
            HttpResponse response = client.execute(httppost);
            String line = null;
            BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            while((line = br.readLine()) != null){
                result += line;
            }

        }catch(Exception e){
            e.printStackTrace();
            result = "Fail";
        }
        return result;
    }
}
class AcceptFriendT extends AsyncTask<String, Void, String>{
    @Override
    protected String doInBackground(String... params) {
        return (String)AcceptFriend((String) params[0], (String) params[1], (String) params[2]);
    }

    private String AcceptFriend(String userid, String password, String friendid)
    {
        String result = "";
        String url = "http://hglim.me:2025" + "/accept_friend";
        HttpPost httppost = new HttpPost(url);
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("userid", userid));
        nameValuePairs.add(new BasicNameValuePair("password", password));
        nameValuePairs.add(new BasicNameValuePair("friendid", friendid));


        try {
            UrlEncodedFormEntity entityRequest = new UrlEncodedFormEntity(nameValuePairs, "UTF-8");
            httppost.setEntity(entityRequest);

            DefaultHttpClient client = new DefaultHttpClient();
            HttpResponse response = client.execute(httppost);
            String line = null;
            BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            while((line = br.readLine()) != null){
                result += line;
            }

        }catch(Exception e){
            e.printStackTrace();
            result = "Fail";
        }
        return result;
    }
}
class DeleteFriendT extends AsyncTask<String, Void, String>{
    @Override
    protected String doInBackground(String... params) {
        return (String)DeleteFriend((String) params[0], (String) params[1], (String) params[2]);
    }

    private String DeleteFriend(String userid, String password, String friendid)
    {
        String result = "";
        String url = "http://hglim.me:2025" + "/delete_friend";
        HttpPost httppost = new HttpPost(url);
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("userid", userid));
        nameValuePairs.add(new BasicNameValuePair("password", password));
        nameValuePairs.add(new BasicNameValuePair("friendid", friendid));

        try {
            UrlEncodedFormEntity entityRequest = new UrlEncodedFormEntity(nameValuePairs, "UTF-8");
            httppost.setEntity(entityRequest);

            DefaultHttpClient client = new DefaultHttpClient();
            HttpResponse response = client.execute(httppost);
            String line = null;
            BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            while((line = br.readLine()) != null){
                result += line;
            }

        }catch(Exception e){
            e.printStackTrace();
            result = "Fail";
        }
        return result;
    }
}
class FriendRequestListT extends AsyncTask<String, Void, String>{
    @Override
    protected String doInBackground(String... params) {
        return (String)FriendRequestList((String) params[0], (String) params[1], (String) params[2]);
    }

    private String FriendRequestList(String userid, String password, String sended)
    {
        String result = "";
        String url = "http://hglim.me:2025" + "/friend_request_list";
        HttpPost httppost = new HttpPost(url);
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("userid", userid));
        nameValuePairs.add(new BasicNameValuePair("password", password));
        nameValuePairs.add(new BasicNameValuePair("sended", sended));

        try {
            UrlEncodedFormEntity entityRequest = new UrlEncodedFormEntity(nameValuePairs, "UTF-8");
            httppost.setEntity(entityRequest);

            DefaultHttpClient client = new DefaultHttpClient();
            HttpResponse response = client.execute(httppost);
            String line = null;
            BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            while((line = br.readLine()) != null){
                result += line;
            }

        }catch(Exception e){
            e.printStackTrace();
            result = "Fail";
        }
        return result;
    }
}
class FriendListT extends AsyncTask<String, Void, String>{
    @Override
    protected String doInBackground(String... params) {
        return (String)FriendList((String) params[0], (String) params[1]);
    }

    private String FriendList(String userid, String password)
    {
        String result = "";
        String url = "http://hglim.me:2025" + "/friend_list";
        HttpPost httppost = new HttpPost(url);
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("userid", userid));
        nameValuePairs.add(new BasicNameValuePair("password", password));

        try {
            UrlEncodedFormEntity entityRequest = new UrlEncodedFormEntity(nameValuePairs, "UTF-8");
            httppost.setEntity(entityRequest);

            DefaultHttpClient client = new DefaultHttpClient();
            HttpResponse response = client.execute(httppost);
            String line = null;
            BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            while((line = br.readLine()) != null){
                result += line;
            }

        }catch(Exception e){
            e.printStackTrace();
            result = "Fail";
        }
        return result;
    }
}

class AlarmListT extends AsyncTask<String, Void, String>{
    @Override
    protected String doInBackground(String... params) {
        return (String)AlarmList((String) params[0], (String) params[1], (String) params[2]);
    }

    private String AlarmList(String userid, String password, String sended)
    {
        String result = "";
        String url = "http://hglim.me:2025" + "/alarm_list";
        HttpPost httppost = new HttpPost(url);
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("userid", userid));
        nameValuePairs.add(new BasicNameValuePair("password", password));
        nameValuePairs.add(new BasicNameValuePair("sended", sended));


        try {
            UrlEncodedFormEntity entityRequest = new UrlEncodedFormEntity(nameValuePairs, "UTF-8");
            httppost.setEntity(entityRequest);

            DefaultHttpClient client = new DefaultHttpClient();
            HttpResponse response = client.execute(httppost);
            String line = null;
            BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            while((line = br.readLine()) != null){
                result += line;
            }

        }catch(Exception e){
            e.printStackTrace();
            result = "Fail";
        }
        return result;
    }
}
class InsertAlarmT extends AsyncTask<String, Void, String>{
    @Override
    protected String doInBackground(String... params) {
        return (String)InsertAlarm((String) params[0], (String) params[1], (String) params[2], (String) params[3], (String) params[4],
                (String) params[5], (String) params[6], (String) params[7], (String) params[8], (String) params[9], (String) params[10], (String) params[11]);
    }

    private String InsertAlarm(String userid, String password, String hour, String min,
                             String day, String weather, String alarm_type, String on_off, String repeating, String fromid, String toid, String title)
    {
        String result = "";
        String url = "http://hglim.me:2025" + "/insert_alarm";
        HttpPost httppost = new HttpPost(url);
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("userid", userid));
        nameValuePairs.add(new BasicNameValuePair("password", password));
        nameValuePairs.add(new BasicNameValuePair("hour", hour));
        nameValuePairs.add(new BasicNameValuePair("min", min));
        nameValuePairs.add(new BasicNameValuePair("day", day));
        nameValuePairs.add(new BasicNameValuePair("weather", weather));
        nameValuePairs.add(new BasicNameValuePair("alarm_type", alarm_type));
        nameValuePairs.add(new BasicNameValuePair("on_off", on_off));
        nameValuePairs.add(new BasicNameValuePair("repeating", repeating));
        nameValuePairs.add(new BasicNameValuePair("fromid", fromid));
        nameValuePairs.add(new BasicNameValuePair("toid", toid));
        nameValuePairs.add(new BasicNameValuePair("title", title));



        try {
            UrlEncodedFormEntity entityRequest = new UrlEncodedFormEntity(nameValuePairs, "UTF-8");
            httppost.setEntity(entityRequest);

            DefaultHttpClient client = new DefaultHttpClient();
            HttpResponse response = client.execute(httppost);
            String line = null;
            BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            while((line = br.readLine()) != null){
                result += line;
            }

        }catch(Exception e){
            e.printStackTrace();
            result = "Fail";
        }
        return result;
    }
}
class ModifyAlarmT extends AsyncTask<String, Void, String>{
    @Override
    protected String doInBackground(String... params) {
        return (String)ModifyAlarm((String) params[0], (String) params[1], (String) params[2], (String) params[3], (String) params[4],
                (String) params[5], (String) params[6], (String) params[7], (String) params[8], (String) params[9], (String) params[10]);
    }

    private String ModifyAlarm(String userid, String password, String hour, String min,
                               String day, String weather, String alarm_type, String on_off, String repeating, String title, String alarmid)
    {
        String result = "";
        String url = "http://hglim.me:2025" + "/modify_alarm";
        HttpPost httppost = new HttpPost(url);
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("userid", userid));
        nameValuePairs.add(new BasicNameValuePair("password", password));
        nameValuePairs.add(new BasicNameValuePair("hour", hour));
        nameValuePairs.add(new BasicNameValuePair("min", min));
        nameValuePairs.add(new BasicNameValuePair("day", day));
        nameValuePairs.add(new BasicNameValuePair("weather", weather));
        nameValuePairs.add(new BasicNameValuePair("alarm_type", alarm_type));
        nameValuePairs.add(new BasicNameValuePair("on_off", on_off));
        nameValuePairs.add(new BasicNameValuePair("repeating", repeating));
        nameValuePairs.add(new BasicNameValuePair("title", title));
        nameValuePairs.add(new BasicNameValuePair("alarmid", alarmid));

        try {
            UrlEncodedFormEntity entityRequest = new UrlEncodedFormEntity(nameValuePairs, "UTF-8");
            httppost.setEntity(entityRequest);

            DefaultHttpClient client = new DefaultHttpClient();
            HttpResponse response = client.execute(httppost);
            String line = null;
            BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            while((line = br.readLine()) != null){
                result += line;
            }

        }catch(Exception e){
            e.printStackTrace();
            result = "Fail";
        }
        return result;
    }
}
class OnoffAlarmT extends AsyncTask<String, Void, String>{
    @Override
    protected String doInBackground(String... params) {
        return (String)OnoffAlarm((String) params[0], (String) params[1], (String) params[2], (String) params[3]);
    }

    private String OnoffAlarm(String userid, String password, String alarmid, String on_off)
    {
        String result = "";
        String url = "http://hglim.me:2025" + "/onoff_alarm";
        HttpPost httppost = new HttpPost(url);
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("userid", userid));
        nameValuePairs.add(new BasicNameValuePair("password", password));
        nameValuePairs.add(new BasicNameValuePair("alarmid", alarmid));
        nameValuePairs.add(new BasicNameValuePair("on_off", on_off));

        try {
            UrlEncodedFormEntity entityRequest = new UrlEncodedFormEntity(nameValuePairs, "UTF-8");
            httppost.setEntity(entityRequest);

            DefaultHttpClient client = new DefaultHttpClient();
            HttpResponse response = client.execute(httppost);
            String line = null;
            BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            while((line = br.readLine()) != null){
                result += line;
            }

        }catch(Exception e){
            e.printStackTrace();
            result = "Fail";
        }
        return result;
    }
}
class DeleteAlarmT extends AsyncTask<String, Void, String>{
    @Override
    protected String doInBackground(String... params) {
        return (String)DeleteAlarm((String) params[0], (String) params[1], (String) params[2]);
    }

    private String DeleteAlarm(String userid, String password, String alarmid)
    {
        String result = "";
        String url = "http://hglim.me:2025" + "/delete_alarm";
        HttpPost httppost = new HttpPost(url);
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("userid", userid));
        nameValuePairs.add(new BasicNameValuePair("password", password));
        nameValuePairs.add(new BasicNameValuePair("alarmid", alarmid));

        try {
            UrlEncodedFormEntity entityRequest = new UrlEncodedFormEntity(nameValuePairs, "UTF-8");
            httppost.setEntity(entityRequest);

            DefaultHttpClient client = new DefaultHttpClient();
            HttpResponse response = client.execute(httppost);
            String line = null;
            BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            while((line = br.readLine()) != null){
                result += line;
            }

        }catch(Exception e){
            e.printStackTrace();
            result = "Fail";
        }
        return result;
    }
}

class AlarmUpdateListT extends AsyncTask<String, Void, String>{
    @Override
    protected String doInBackground(String... params) {
        return (String)AlarmUpdateList((String) params[0], (String) params[1]);
    }

    private String AlarmUpdateList(String userid, String password)
    {
        String result = "";
        String url = "http://hglim.me:2025" + "/alarm_update_list";
        HttpPost httppost = new HttpPost(url);
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("userid", userid));
        nameValuePairs.add(new BasicNameValuePair("password", password));

        try {
            UrlEncodedFormEntity entityRequest = new UrlEncodedFormEntity(nameValuePairs, "UTF-8");
            httppost.setEntity(entityRequest);

            DefaultHttpClient client = new DefaultHttpClient();
            HttpResponse response = client.execute(httppost);
            String line = null;
            BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            while((line = br.readLine()) != null){
                result += line;
            }

        }catch(Exception e){
            e.printStackTrace();
            result = "Fail";
        }
        return result;
    }
}
class InsertAlarmUpdateT extends AsyncTask<String, Void, String>{
    @Override
    protected String doInBackground(String... params) {
        return (String)InsertAlarmUpdate((String) params[0], (String) params[1], (String) params[2], (String) params[3], (String) params[4]);
    }

    private String InsertAlarmUpdate(String userid, String password, String modifier, String alarmid, String state)
    {
        String result = "";
        String url = "http://hglim.me:2025" + "/insert_alarm_update";
        HttpPost httppost = new HttpPost(url);
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("userid", userid));
        nameValuePairs.add(new BasicNameValuePair("password", password));
        nameValuePairs.add(new BasicNameValuePair("modifier", modifier));
        nameValuePairs.add(new BasicNameValuePair("alarmid", alarmid));
        nameValuePairs.add(new BasicNameValuePair("state", state));

        try {
            UrlEncodedFormEntity entityRequest = new UrlEncodedFormEntity(nameValuePairs, "UTF-8");
            httppost.setEntity(entityRequest);

            DefaultHttpClient client = new DefaultHttpClient();
            HttpResponse response = client.execute(httppost);
            String line = null;
            BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            while((line = br.readLine()) != null){
                result += line;
            }

        }catch(Exception e){
            e.printStackTrace();
            result = "Fail";
        }
        return result;
    }
}
class GetAlarmWithAlarmIDT extends AsyncTask<String, Void, String>{
    @Override
    protected String doInBackground(String... params) {
        return (String)GetAlarmWithAlarmID((String) params[0], (String) params[1], (String) params[2]);
    }

    private String GetAlarmWithAlarmID(String userid, String password, String alarmid)
    {
        String result = "";
        String url = "http://hglim.me:2025" + "/get_alarm_with_alarmid";
        HttpPost httppost = new HttpPost(url);
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("userid", userid));
        nameValuePairs.add(new BasicNameValuePair("password", password));
        nameValuePairs.add(new BasicNameValuePair("alarmid", alarmid));

        try {
            UrlEncodedFormEntity entityRequest = new UrlEncodedFormEntity(nameValuePairs, "UTF-8");
            httppost.setEntity(entityRequest);

            DefaultHttpClient client = new DefaultHttpClient();
            HttpResponse response = client.execute(httppost);
            String line = null;
            BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            while((line = br.readLine()) != null){
                result += line;
            }

        }catch(Exception e){
            e.printStackTrace();
            result = "Fail";
        }
        return result;
    }
}
class PopAlarmWithAlarmIDT extends AsyncTask<String, Void, String>{
    @Override
    protected String doInBackground(String... params) {
        return (String)PopAlarmWithAlarmID((String) params[0], (String) params[1], (String) params[2]);
    }

    private String PopAlarmWithAlarmID(String userid, String password, String alarmid)
    {
        String result = "";
        String url = "http://hglim.me:2025" + "/pop_alarm_with_alarmid";
        HttpPost httppost = new HttpPost(url);
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("userid", userid));
        nameValuePairs.add(new BasicNameValuePair("password", password));
        nameValuePairs.add(new BasicNameValuePair("alarmid", alarmid));

        try {
            UrlEncodedFormEntity entityRequest = new UrlEncodedFormEntity(nameValuePairs, "UTF-8");
            httppost.setEntity(entityRequest);

            DefaultHttpClient client = new DefaultHttpClient();
            HttpResponse response = client.execute(httppost);
            String line = null;
            BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            while((line = br.readLine()) != null){
                result += line;
            }

        }catch(Exception e){
            e.printStackTrace();
            result = "Fail";
        }
        return result;
    }
}