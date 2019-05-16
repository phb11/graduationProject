package com.lilei.fitness.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lilei.fitness.R;
import com.lilei.fitness.entity.Alarm;

import java.util.List;

public class AlarmAdapter extends BaseAdapter {
    private List<Alarm> mList;

    public AlarmAdapter (List<Alarm> list) {
        mList = list;
    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View contentView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_alarm, viewGroup, false);
        TextView type = (TextView) contentView.findViewById(R.id.alarm_type);
        TextView time = (TextView) contentView.findViewById(R.id.alarm_time);
        TextView middle = (TextView) contentView.findViewById(R.id.alarm_middle);
        Alarm alarm = mList.get(i);
        type.setText(alarm.getAlarmtype());
        time.setText(alarm.getAlarmtime());
        middle.setText(alarm.getAlarmmiddle());
        return contentView;
    }



}
