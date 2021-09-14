package com.ysarch.uibase.listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ysarch.vmall.R;
import com.ysarch.vmall.domain.bean.OrderTraceBean;
import com.ysarch.vmall.utils.VMallUtils;

import java.util.ArrayList;
import java.util.List;

public class TraceListAdapter extends BaseAdapter {
    private Context context;
    private List<OrderTraceBean> traceList = new ArrayList<>(1);
    private static final int TYPE_TOP = 0x0000;
    private static final int TYPE_NORMAL= 0x0001;

    public TraceListAdapter(Context context, List<OrderTraceBean> traceList) {
        this.context = context;
        this.traceList = traceList;
    }

    @Override
    public int getCount() {
        return traceList.size();
    }

    @Override
    public OrderTraceBean getItem(int position) {
        return traceList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        final OrderTraceBean trace = getItem(position);
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_trace, parent, false);
            holder.tvAcceptTime = (TextView) convertView.findViewById(R.id.tv_accept_time);
            holder.tvAcceptStation = (TextView) convertView.findViewById(R.id.tv_accept_station);
            holder.tvTopLine = (TextView) convertView.findViewById(R.id.tvTopLine);
            holder.tvStatus = (TextView) convertView.findViewById(R.id.tv_trace_status);
            holder.ivStatus = (ImageView)convertView.findViewById(R.id.iv_status);
//            holder.tvDot = (TextView) convertView.findViewById(R.id.tvDot);
            convertView.setTag(holder);
        }

        if (getItemViewType(position) == TYPE_TOP) {
            // 第一行头的竖线不显示
            holder.tvTopLine.setVisibility(View.INVISIBLE);
            // 字体颜色加深
            holder.tvAcceptTime.setTextColor(0xff333333);
            holder.tvAcceptStation.setTextColor(0xff333333);
            holder.tvStatus.setTextColor(0xff333333);
//            holder.tvDot.setBackgroundResource(R.drawable.timelline_dot_first);
            holder.ivStatus.setBackgroundResource(R.drawable.ic_trace_big_red);
        } else if (getItemViewType(position) == TYPE_NORMAL) {
            holder.tvTopLine.setVisibility(View.VISIBLE);
            holder.tvAcceptTime.setTextColor(0xff999999);
            holder.tvAcceptStation.setTextColor(0xff999999);
            holder.tvStatus.setTextColor(0xff999999);
//            holder.tvDot.setBackgroundResource(R.drawable.timelline_dot_normal);
            if(trace.isFirstInfo()) {
                holder.ivStatus.setBackgroundResource(R.drawable.ic_trace_big_gray);
            }else {
                holder.ivStatus.setBackgroundResource(R.drawable.ic_trace_small_gray);
            }

        }

        holder.tvAcceptTime.setText(VMallUtils.GTMToLocal(trace.getCreateTime()));
        holder.tvAcceptStation.setText(trace.getDesc());
        holder.tvStatus.setText(trace.getTag());

        return convertView;
    }

    @Override
    public int getItemViewType(int id) {
        if (id == 0) {
            return TYPE_TOP;
        }
        return TYPE_NORMAL;
    }

    static class ViewHolder {
        public TextView tvAcceptTime, tvAcceptStation,tvStatus;
        public TextView tvTopLine;
        public ImageView ivStatus;
    }
}
