package com.feicui.android.yitaobao.Presentation.main.MySelf.UpShop;

import android.content.Context;
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
 * Created by Administrator on 2016/11/30.
 */
public class UpAdapter extends RecyclerView.Adapter<UpAdapter.UpShopViewHolder> {

    private ArrayList<MyCameraentry> list;
    private Context context;

    private setOnListener l;

    public void setOnItemClickListener(setOnListener l) {
        this.l = l;
    }

    public ArrayList<MyCameraentry> getList() {
        return list;
    }


    public UpAdapter(ArrayList<MyCameraentry> list){
        this.list = list;
    }

    public void addList(ArrayList<MyCameraentry> list){
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void addData(MyCameraentry m){
        list.add(m);
        notifyDataSetChanged();
    }

    @Override
    public UpShopViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.upshop_item, null);
        return new UpShopViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UpShopViewHolder holder, final int position) {
        if (position == 0) {
            holder.cover.setVisibility(View.VISIBLE);
            holder.cover.getBackground().setAlpha(150);
        } else
            holder.cover.setVisibility(View.GONE);
        if (list.get(position).getPath() != null) {
            ImageLoader.getInstance().displayImage("file:///" + list.get(position).getPath(), holder.image, ImageLoadOptions.build_mycamera());
        } else {
            holder.image.setImageBitmap(null);
        }
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((position == 0 && list.get(position).getPath() == null)
                        || (position != 0
                        && list.get(position - 1).getPath() != null
                        && list.get(position).getPath() == null)) {
                    l.onImageClick();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return 8;
    }

    class UpShopViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.upshop_image)
        ImageView image;
        @Bind(R.id.tv_cover)
        TextView cover;
        public UpShopViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface setOnListener{
        void onImageClick();
    }
}
