package com.lilei.fitness.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.lilei.fitness.R;
import com.lilei.fitness.adapter.AlarmAdapter;
import com.lilei.fitness.entity.Alarm;

import java.util.ArrayList;
import java.util.List;


public class AlarmFragment extends Fragment implements AdapterView.OnItemClickListener {
    private ListView Alarmlist;
    private List<Alarm> mList= new ArrayList<>();

    public List<Alarm> getmList() {
        return mList;
    }

    private AlarmAdapter mAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_alarm, null);
        Alarmlist = (ListView) v.findViewById(R.id.alarm_list);
        mAdapter = new AlarmAdapter(mList);
        Alarmlist.setAdapter(mAdapter);
        return v;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent();
        intent.setClass(getActivity(),Alarm.class);
       // intent.putExtra("alarmID",mList.get)


    }

    public void addAlarm(Alarm alarm) {
        mList.add(alarm);
        mAdapter.notifyDataSetChanged();
    }


}
