package com.lilei.fitness.view;

import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.lilei.fitness.R;
import com.lilei.fitness.entity.Alarm;
import com.lilei.fitness.fragment.AlarmFragment;
import com.lilei.fitness.fragment.FoundFragment;
import com.lilei.fitness.fragment.MeFragment;
import com.lilei.fitness.fragment.TrainingFragment;
import com.lilei.fitness.utils.AppManager;
import com.lilei.fitness.view.base.BaseActivity;
import com.lilei.fitness.view.test.VideoPlayer;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout tabTrain;
    private LinearLayout tabFound;
    private LinearLayout tabMe;
    private LinearLayout tabAlarm;

    private ImageView btnCheck;
    private ImageView btnAddNews;
    private ImageView btnAddalarm;
    private ImageView icoTrain;
    private ImageView icoFound;
    private ImageView icoMe;
    private ImageView icoAlarm;

    private TextView txtTrain;
    private TextView txtFound;
    private TextView txtMe;
    private TextView txtAlarm;
    private TextView txtTitle;

    private TrainingFragment trainingFragment;
    private FoundFragment foundFragment;
    private MeFragment meFragment;
    private AlarmFragment alarmFragment;



    private long mExitTime;
    private MyBroadCastReceiver receiver = new MyBroadCastReceiver();
    String[] type_string = {"基础训练","进阶I","进阶II","跑步","跳绳","保护眼睛","调整坐姿"};
    String[] middle_strings = {"仅一次","15分钟","30分钟","1小时","2小时","4小时","永不"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById();
        initView();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_TIME_TICK);
        registerReceiver(receiver, filter);
    }

    @Override
    protected void findViewById() {
        tabTrain = $(R.id.bottom_train);
        tabFound = $(R.id.bottom_found);
        tabMe = $(R.id.bottom_me);
        tabAlarm = $(R.id.bottom_alarm);

        btnCheck = $(R.id.up_btn_check);
        btnAddNews = $(R.id.found_new_add);
        btnAddalarm = $(R.id.alarm_add);


        icoTrain = $(R.id.bottom_ico_train);
        icoFound = $(R.id.bottom_ico_found);
        icoMe = $(R.id.bottom_ico_me);
        icoAlarm = $(R.id.bottom_ico_alarm);


        txtTrain = $(R.id.bottom_txt_train);
        txtFound = $(R.id.bottom_txt_found);
        txtMe = $(R.id.bottom_txt_me);
        txtAlarm = $(R.id.bottom_txt_alarm);
        txtTitle = $(R.id.titleText);
    }

    @Override
    protected void initView() {
        tabTrain.setOnClickListener(this);
        tabFound.setOnClickListener(this);
        tabMe.setOnClickListener(this);
        tabAlarm.setOnClickListener(this);
        btnCheck.setOnClickListener(this);
        btnAddNews.setOnClickListener(this);
        btnAddalarm.setOnClickListener(this);
        trainingFragment = new TrainingFragment();
        foundFragment = new FoundFragment();
        meFragment = new MeFragment();
        alarmFragment = new AlarmFragment();
        refreashFragment(R.id.bottom_alarm);
        changeTabState(R.id.bottom_alarm);
        changeTitle(R.string.title_alarm);
        topButtonChange(R.id.alarm_add);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bottom_alarm:
                changeTabState(R.id.bottom_alarm);
                changeTitle(R.string.title_alarm);
                topButtonChange(R.id.alarm_add);
                refreashFragment(R.id.bottom_alarm);
                break;
            case R.id.bottom_train:
                changeTabState(R.id.bottom_train);
                changeTitle(R.string.title_train);
                topButtonChange(R.id.up_btn_check);
                refreashFragment(R.id.bottom_train);
                break;
            case R.id.bottom_found:
                topButtonChange(R.id.found_new_add);
                changeTabState(R.id.bottom_found);
                changeTitle(R.string.title_found);
                refreashFragment(R.id.bottom_found);
                break;
            case R.id.bottom_me:
                topButtonChange(0);
                changeTabState(R.id.bottom_me);
                changeTitle(R.string.title_me);
                refreashFragment(R.id.bottom_me);
                break;
            case R.id.up_btn_check:
                openActivity(BeforeDateCheckActivity.class);
                break;
            case R.id.found_new_add:
                openActivity(ReleaseNewsActivity.class);
                break;
            case R.id.alarm_add:

                final Alarm alarm = new Alarm();

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("添加提醒");
                View view = this.getLayoutInflater().inflate(R.layout.item_dialog, null);
                builder.setView(view);
                 final Spinner spinnerType = (Spinner) view.findViewById(R.id.alarm_button_type);
                 spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                     @Override
                     public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                         alarm.setAlarmtype(type_string[i]);
                     }

                     @Override
                     public void onNothingSelected(AdapterView<?> adapterView) {

                     }
                 });

                Button ok = (Button) view.findViewById(R.id.ok);
                Button cancel = (Button) view.findViewById(R.id.cancel);
                final EditText editText = (EditText) view.findViewById(R.id.alarm_button_time);
                Spinner spinnerMiddle = (Spinner) view.findViewById(R.id.alarm_button_middle);
                spinnerMiddle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        alarm.setAlarmmiddle(middle_strings[i]);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                ArrayAdapter<String> type_adapter = new ArrayAdapter(this,
                        R.layout.spinner_text, type_string);
                type_adapter.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
                spinnerType.setAdapter(type_adapter);
                ArrayAdapter<String> middle_adapter =
                        new ArrayAdapter(this,R.layout.spinner_text,middle_strings);
                middle_adapter.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);

                spinnerMiddle.setAdapter(middle_adapter);

                final AlertDialog dialog = builder.create();
                dialog.show();
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alarm.setAlarmtime(editText.getText().toString());
                        if (alarm.getAlarmtype()==null || alarm.getAlarmmiddle()==null||alarm.getAlarmtime()==null) {
                            Toast.makeText(MainActivity.this, "请输入完整！", Toast.LENGTH_SHORT).show();
                        }else {
                            alarmFragment.addAlarm(alarm);
                            dialog.dismiss();
                        }
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });


                break;
        }
    }

    public void topButtonChange(int id) {
        if (id == R.id.up_btn_check) {
            btnCheck.setVisibility(View.VISIBLE);
            btnAddNews.setVisibility(View.GONE);
            btnAddalarm.setVisibility(View.GONE);
        } else if (id == R.id.found_new_add) {
            btnCheck.setVisibility(View.GONE);
            btnAddNews.setVisibility(View.VISIBLE);
            btnAddalarm.setVisibility(View.GONE);
        } else if(id == R.id.alarm_add){
            btnAddalarm.setVisibility(View.VISIBLE);
            btnCheck.setVisibility(View.GONE);
            btnAddNews.setVisibility(View.GONE);
        } else {
            btnAddalarm.setVisibility(View.GONE);
            btnCheck.setVisibility(View.GONE);
            btnAddNews.setVisibility(View.GONE);
        }
    }

    private void changeTitle(int stringId) {
        // txtTitle.setText(getResources().getString(stringId));
    }

    /**
     * 切换Fragment
     * @param btnId
     */
    private void refreashFragment(int btnId) {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        switch (btnId) {
            case R.id.bottom_train:
                transaction.replace(R.id.fragment_container, trainingFragment);
                break;
            case R.id.bottom_found:
                transaction.replace(R.id.fragment_container, foundFragment);
                break;
            case R.id.bottom_me:
                transaction.replace(R.id.fragment_container, meFragment);
                break;
            case R.id.bottom_alarm:
                transaction.replace(R.id.fragment_container,alarmFragment);
                break;
        }
        transaction.commit();
    }

    private void changeTabState(int tabId) {
        if (tabId == R.id.bottom_train) {
            icoTrain.setImageResource(R.drawable.icon_train_pressed);
            txtTrain.setTextColor(getResources().getColor(R.color.bottom_tab_pressed));
        } else {
            icoTrain.setImageResource(R.drawable.icon_train_unpressed);
            txtTrain.setTextColor(getResources().getColor(R.color.bottom_tab_normal));
        }
        if (tabId == R.id.bottom_found) {
            icoFound.setImageResource(R.drawable.icon_found_pressed);
            txtFound.setTextColor(getResources().getColor(R.color.bottom_tab_pressed));
        } else {
            icoFound.setImageResource(R.drawable.icon_found_unpressed);
            txtFound.setTextColor(getResources().getColor(R.color.bottom_tab_normal));
        }
        if (tabId == R.id.bottom_me) {
            icoMe.setImageResource(R.drawable.icon_me_pressed);
            txtMe.setTextColor(getResources().getColor(R.color.bottom_tab_pressed));
        } else {
            icoMe.setImageResource(R.drawable.icon_me_unpressed);
            txtMe.setTextColor(getResources().getColor(R.color.bottom_tab_normal));
        }
        if (tabId == R.id.bottom_alarm) {
            icoAlarm.setImageResource(R.drawable.icon_alarm_pressed);
            txtAlarm.setTextColor(getResources().getColor(R.color.bottom_tab_pressed));
        } else {
            icoAlarm.setImageResource(R.drawable.icon_alarm_unpressed);
            txtAlarm.setTextColor(getResources().getColor(R.color.bottom_tab_normal));
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 800) {
                DisplayToast("再按一次退出Fitness");
                mExitTime = System.currentTimeMillis();
                return true;
            } else {
                AppManager.getInstance().killAllActivity();
                AppManager.getInstance().AppExit(this);
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    public void openTestVideo(View v) {

        openActivity(VideoPlayer.class);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    class MyBroadCastReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_TIME_TICK)){
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                String hour = calendar.get(Calendar.HOUR) + "";
                String minute = calendar.get(Calendar.MINUTE) + "";
                List<Alarm> alarms = alarmFragment.getmList();
                for (Alarm alarm : alarms) {
                    String[] times = alarm.getAlarmtime().split(":");
                    if (hour.equals(times[0]) && minute.equals(times[1])) {
                        switch (alarm.getAlarmtype()) {
                            case "保护眼睛" :
                                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                builder.setMessage("您该放松眼睛了！");
                                View view = MainActivity.this.getLayoutInflater().inflate(R.layout.eyes_dialog, null);
                                builder.setView(view);
                                Button ok = (Button) view.findViewById(R.id.eye_ok);
                                final AlertDialog dialog = builder.create();
                                dialog.show();
                                ok.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog.dismiss();
                                    }
                                });
                                break;
                            case "调整坐姿" :
                                AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                                builder1.setMessage("您该注意坐姿了！");
                                View view1 = MainActivity.this.getLayoutInflater().inflate(R.layout.sit_dialog, null);
                                builder1.setView(view1);
                                Button ok1 = (Button) view1.findViewById(R.id.sit_ok);
                                final AlertDialog dialog1 = builder1.create();
                                dialog1.show();
                                ok1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog1.dismiss();
                                    }
                                });
                                break;
                                default:
                                    AlertDialog.Builder builder2 = new AlertDialog.Builder(MainActivity.this);
                                    builder2.setTitle("注意：");
                                    builder2.setMessage("您该  "+alarm.getAlarmtype()+"  啦！！");

                                    builder2.setPositiveButton("好的", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int i) {
                                            dialog.dismiss();
                                        }
                                    });
                                    builder2.setNegativeButton("放弃", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int i) {

                                            dialog.dismiss();
                                        }
                                    });
                                    builder2.create().show();
                        }


                    }


                }
            }
        }
    }
}
