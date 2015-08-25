package hedge.johnny.Activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import hedge.johnny.R;

/**
 * Created by Administrator on 2015-07-21.
 */
public class SettingActivity extends Activity implements View.OnClickListener {
    Button logoutBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        logoutBtn = (Button)findViewById(R.id.logout);
        logoutBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        SharedPreferences pref = getSharedPreferences("HedgeMembers",0);
        SharedPreferences.Editor edit = pref.edit();
        edit.putString("userid", "");
        edit.putString("password", "");
        edit.commit();

        Intent i = new Intent(SettingActivity.this, InitialActivity.class);
        startActivity(i);
        finish(); return;
    }
}