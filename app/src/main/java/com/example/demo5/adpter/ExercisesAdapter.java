package com.example.demo5.adpter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.demo5.ExercisesDetailActivity;
import com.example.demo5.R;
import com.example.demo5.bean.ExercisesBean;

import java.util.List;

public class ExercisesAdapter extends BaseAdapter {
    private Context mContext;
    private List<ExercisesBean> ebl;

    public ExercisesAdapter(Context context) {
        this.mContext = context;
    }

    //设置数据更新界面
    public void setDate(List<ExercisesBean> ebl) {
        this.ebl = ebl;
        notifyDataSetChanged();
    }

    @Override
    //获取Item的总数
    public int getCount() {
        return ebl == null ? 0 : ebl.size();
    }

    //根据position得到对应的Item的对象
    @Override
    public ExercisesBean getItem(int position) {
        return ebl == null ? null : ebl.get(position);
    }

    //根据position得到相应的Item的id
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;
        if (convertView == null) {

            convertView = LayoutInflater.from(mContext).inflate(R.layout.exercises_list_item, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        }else{
            vh=(ViewHolder) convertView.getTag();
        }

        //获取position对应的Item的数据对象
        final ExercisesBean bean = getItem(position);
        if(bean!=null){
            vh.tv_order.setText(position+1+"");
            vh.tv_content.setText(bean.content);
            vh.tv_title.setText(bean.title);
            vh.tv_order.setBackgroundResource(bean.background);
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bean==null){
                    return;
                }
                //跳转到习题详情页面
                Intent intent = new Intent(mContext, ExercisesDetailActivity.class);
                //把章节Id传递到习题详情页面
                intent.putExtra("id",bean.id);
                //把标题传递到习题详情页面
                intent.putExtra("title",bean.title);
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }

    public class ViewHolder {
        public View rootView;
        public TextView tv_order;
        public TextView tv_title;
        public TextView tv_content;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tv_order = (TextView) rootView.findViewById(R.id.tv_order);
            this.tv_title = (TextView) rootView.findViewById(R.id.tv_title);
            this.tv_content = (TextView) rootView.findViewById(R.id.tv_content);
        }

    }
}
