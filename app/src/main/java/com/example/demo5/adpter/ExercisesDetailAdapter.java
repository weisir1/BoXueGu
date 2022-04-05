package com.example.demo5.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.demo5.R;
import com.example.demo5.Utils.AnalysisUtils;
import com.example.demo5.bean.ExercisesBean;

import java.util.ArrayList;
import java.util.List;

public class ExercisesDetailAdapter extends BaseAdapter {

    private Context mContext;
    private List<ExercisesBean> ebl;
    private OnSelectListener onSelectListener;

    public ExercisesDetailAdapter(Context context, OnSelectListener onSelectListener) {
        this.mContext = context;
        this.onSelectListener = onSelectListener;
    }

    public void setData(List<ExercisesBean> ebl) {
        this.ebl = ebl;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return ebl == null ? 0 : ebl.size();
    }

    @Override
    public ExercisesBean getItem(int position) {
        return ebl == null ? null : ebl.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //记录点击的位置
    private ArrayList<String> selectedPosition = new ArrayList<String>();

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.exercises_detail_list_item, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        }else{
            vh=(ViewHolder) convertView.getTag();
        }
        final ExercisesBean bean = getItem(position);
        if(bean!=null){
            vh.tv_subject.setText(bean.subject);
            vh.tv_a.setText(bean.a);
            vh.tv_b.setText(bean.b);
            vh.tv_c.setText(bean.c);
            vh.tv_d.setText(bean.d);
        }
        if(!selectedPosition.contains(""+position)){
            vh.iv_a.setImageResource(R.drawable.exercises_a);
            vh.iv_b.setImageResource(R.drawable.exercises_b);
            vh.iv_c.setImageResource(R.drawable.exercises_c);
            vh.iv_d.setImageResource(R.drawable.exercises_d);
            AnalysisUtils.setABCDEnable(true,vh.iv_a,vh.iv_b,vh.iv_c,vh.iv_d);
        }else{
            AnalysisUtils.setABCDEnable(false,vh.iv_a,vh.iv_b,vh.iv_c,vh.iv_d);
            switch (bean.select) {
                case 0:
                    //用户所选项是正确的
                    if(bean.answer==1){
                        vh.iv_a.setImageResource(R.drawable.exercises_right_icon);
                        vh.iv_b.setImageResource(R.drawable.exercises_b);
                        vh.iv_c.setImageResource(R.drawable.exercises_c);
                        vh.iv_d.setImageResource(R.drawable.exercises_d);
                    }else if(bean.answer==2){
                        vh.iv_a.setImageResource(R.drawable.exercises_a);
                        vh.iv_b.setImageResource(R.drawable.exercises_right_icon);
                        vh.iv_c.setImageResource(R.drawable.exercises_c);
                        vh.iv_d.setImageResource(R.drawable.exercises_d);
                    }else if(bean.answer==3){
                        vh.iv_a.setImageResource(R.drawable.exercises_a);
                        vh.iv_b.setImageResource(R.drawable.exercises_b);
                        vh.iv_c.setImageResource(R.drawable.exercises_right_icon);
                        vh.iv_d.setImageResource(R.drawable.exercises_d);
                    }else if(bean.answer==4){
                        vh.iv_a.setImageResource(R.drawable.exercises_a);
                        vh.iv_b.setImageResource(R.drawable.exercises_b);
                        vh.iv_c.setImageResource(R.drawable.exercises_c);
                        vh.iv_d.setImageResource(R.drawable.exercises_right_icon);
                    }
                    break;
                case 1:
                    //用户所选项A是错误的
                    vh.iv_a.setImageResource(R.drawable.exercises_error_icon);
                    if(bean.answer==2){
                        vh.iv_b.setImageResource(R.drawable.exercises_right_icon);
                        vh.iv_c.setImageResource(R.drawable.exercises_c);
                        vh.iv_d.setImageResource(R.drawable.exercises_d);
                    }else if(bean.answer==3){
                        vh.iv_b.setImageResource(R.drawable.exercises_b);
                        vh.iv_c.setImageResource(R.drawable.exercises_right_icon);
                        vh.iv_d.setImageResource(R.drawable.exercises_d);
                    }else if(bean.answer==4){
                        vh.iv_b.setImageResource(R.drawable.exercises_b);
                        vh.iv_c.setImageResource(R.drawable.exercises_c);
                        vh.iv_d.setImageResource(R.drawable.exercises_right_icon);
                    }
                    break;
                case 2:
                    //用户所选项B是错误的
                    vh.iv_b.setImageResource(R.drawable.exercises_error_icon);
                    if(bean.answer==1){
                        vh.iv_a.setImageResource(R.drawable.exercises_right_icon);
                        vh.iv_c.setImageResource(R.drawable.exercises_c);
                        vh.iv_d.setImageResource(R.drawable.exercises_d);
                    }else if(bean.answer==3){
                        vh.iv_b.setImageResource(R.drawable.exercises_b);
                        vh.iv_c.setImageResource(R.drawable.exercises_right_icon);
                        vh.iv_d.setImageResource(R.drawable.exercises_d);
                    }else if(bean.answer==4){
                        vh.iv_b.setImageResource(R.drawable.exercises_b);
                        vh.iv_c.setImageResource(R.drawable.exercises_c);
                        vh.iv_d.setImageResource(R.drawable.exercises_right_icon);
                    }
                    break;
                case 3:
                    //用户所选项C是错误的
                    vh.iv_c.setImageResource(R.drawable.exercises_error_icon);
                    if(bean.answer==1){
                        vh.iv_a.setImageResource(R.drawable.exercises_right_icon);
                        vh.iv_b.setImageResource(R.drawable.exercises_b);
                        vh.iv_d.setImageResource(R.drawable.exercises_d);
                    }else if(bean.answer==2){
                        vh.iv_a.setImageResource(R.drawable.exercises_a);
                        vh.iv_b.setImageResource(R.drawable.exercises_right_icon);
                        vh.iv_d.setImageResource(R.drawable.exercises_d);
                    }else if(bean.answer==4){
                        vh.iv_a.setImageResource(R.drawable.exercises_a);
                        vh.iv_b.setImageResource(R.drawable.exercises_b);
                        vh.iv_d.setImageResource(R.drawable.exercises_right_icon);
                    }
                    break;
                case 4:
                    //用户所选项D是错误的
                    vh.iv_d.setImageResource(R.drawable.exercises_error_icon);
                    if(bean.answer==1){
                        vh.iv_a.setImageResource(R.drawable.exercises_right_icon);
                        vh.iv_b.setImageResource(R.drawable.exercises_b);
                        vh.iv_c.setImageResource(R.drawable.exercises_c);
                    }else if(bean.answer==2){
                        vh.iv_a.setImageResource(R.drawable.exercises_a);
                        vh.iv_b.setImageResource(R.drawable.exercises_right_icon);
                        vh.iv_c.setImageResource(R.drawable.exercises_c);
                    }else if(bean.answer==3){
                        vh.iv_a.setImageResource(R.drawable.exercises_a);
                        vh.iv_b.setImageResource(R.drawable.exercises_b);
                        vh.iv_c.setImageResource(R.drawable.exercises_right_icon);
                    }
                    break;
                default:
                    break;
            }
        }
        //当用户点击A选项的点击事件
        vh.iv_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断selectedPosition中是否包含此时点击的position
                if(selectedPosition.contains(""+position)){
                    selectedPosition.remove(""+position);
                }else{
                    selectedPosition.add(position+"");
                }
                onSelectListener.onSelectA(position,vh.iv_a,vh.iv_b,vh.iv_c,vh.iv_d);
            }
        });
        //当用户点击B选项的点击事件
        vh.iv_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断selectedPosition中是否包含此时点击的position
                if(selectedPosition.contains(""+position)){
                    selectedPosition.remove(""+position);
                }else{
                    selectedPosition.add(position+"");
                }
                onSelectListener.onSelectB(position,vh.iv_a,vh.iv_b,vh.iv_c,vh.iv_d);
            }
        });
        //当用户点击C选项的点击事件
        vh.iv_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断selectedPosition中是否包含此时点击的position
                if(selectedPosition.contains(""+position)){
                    selectedPosition.remove(""+position);
                }else{
                    selectedPosition.add(position+"");
                }
                onSelectListener.onSelectC(position,vh.iv_a,vh.iv_b,vh.iv_c,vh.iv_d);
            }
        });
        //当用户点击D选项的点击事件
        vh.iv_d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断selectedPosition中是否包含此时点击的position
                if(selectedPosition.contains(""+position)){
                    selectedPosition.remove(""+position);
                }else{
                    selectedPosition.add(position+"");
                }
                onSelectListener.onSelectD(position,vh.iv_a,vh.iv_b,vh.iv_c,vh.iv_d);
            }
        });
        return convertView;
    }

    public class ViewHolder {
        public View rootView;
        public TextView tv_subject;
        public ImageView iv_a;
        public TextView tv_a;
        public ImageView iv_b;
        public TextView tv_b;
        public ImageView iv_c;
        public TextView tv_c;
        public ImageView iv_d;
        public TextView tv_d;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tv_subject = (TextView) rootView.findViewById(R.id.tv_subject);
            this.iv_a = (ImageView) rootView.findViewById(R.id.iv_a);
            this.tv_a = (TextView) rootView.findViewById(R.id.tv_a);
            this.iv_b = (ImageView) rootView.findViewById(R.id.iv_b);
            this.tv_b = (TextView) rootView.findViewById(R.id.tv_b);
            this.iv_c = (ImageView) rootView.findViewById(R.id.iv_c);
            this.tv_c = (TextView) rootView.findViewById(R.id.tv_c);
            this.iv_d = (ImageView) rootView.findViewById(R.id.iv_d);
            this.tv_d = (TextView) rootView.findViewById(R.id.tv_d);
        }

    }
    public interface OnSelectListener {
        void onSelectA(int position, ImageView iv_a, ImageView iv_b, ImageView iv_c, ImageView iv_d);

        void onSelectB(int position, ImageView iv_a, ImageView iv_b, ImageView iv_c, ImageView iv_d);

        void onSelectC(int position, ImageView iv_a, ImageView iv_b, ImageView iv_c, ImageView iv_d);

        void onSelectD(int position, ImageView iv_a, ImageView iv_b, ImageView iv_c, ImageView iv_d);
    }
}
