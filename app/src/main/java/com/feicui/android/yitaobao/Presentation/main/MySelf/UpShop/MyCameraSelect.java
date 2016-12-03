package com.feicui.android.yitaobao.Presentation.main.MySelf.UpShop;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.feicui.android.yitaobao.Model.MyCameraentry;
import com.feicui.android.yitaobao.R;
import com.feicui.android.yitaobao.Tools.ImageLoadOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/11/30.\
 */
public class MyCameraSelect extends RecyclerView.Adapter<MyCameraSelect.MySelectViewHolder> {
    private ArrayList<MyCameraentry> list;
    private onItemClickListener listener;

    public void setList(ArrayList<MyCameraentry> list){
        this.list = list;
    }

    public ArrayList<MyCameraentry> getList(){
        return list;
    }
    @Override
    public MySelectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mycamera_select, null);
        return new MySelectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MySelectViewHolder holder, final int position) {
        if(position == 0){
            holder.cover.setVisibility(View.VISIBLE);
            holder.cover.getBackground().setAlpha(100);
        }
        else{
            holder.cover.setVisibility(View.GONE);
        }
        holder.delete.getBackground().setAlpha(100);
        ImageLoader.getInstance().displayImage("file:///" + list.get(position).getPath(), holder.image, ImageLoadOptions.build_mycamera());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.delete(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MySelectViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.mycamera_select)
        ImageView image;
        @Bind(R.id.mycamera_cover)
        TextView cover;
        @Bind(R.id.mycamera_delete)
        ImageView delete;
        public MySelectViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setListener(onItemClickListener listener){
        this.listener = listener;
    }
    public interface onItemClickListener{
        void delete(int position);
    }
}

