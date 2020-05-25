package com.shanghq.zeromusic.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.shanghq.zeromusic.R;
import com.shanghq.zeromusic.Utils.MusicUtils;
import com.shanghq.zeromusic.bean.SongBean;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {


    private List<SongBean> songBeanList;
    private Context context;

    public void setSongBeanList(List<SongBean> songBeanList) {
        this.songBeanList = songBeanList;
    }

    public List<SongBean> getSongBeanList() {
        return songBeanList;
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public MyRecyclerViewAdapter(Context context, List<SongBean> songBeans) {
        this.songBeanList = songBeans;
        this.context = context;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_my, parent, false);
        MyViewHolder viewHolder = new MyRecyclerViewAdapter.MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        SongBean songBean = songBeanList.get(position);

        holder.singer.setText(songBean.getSinger());
        holder.title.setText(songBean.getTitle());

        Glide.with(context).load(R.mipmap.ic_launcher).
                apply(RequestOptions.bitmapTransform(new CircleCrop())).into(holder.imgMy);

//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        Bitmap bitmap=MusicUtils.getAlbumArt(context,songBean.getAlbumId());
//        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
//        byte[] bytes=baos.toByteArray();
//        Glide.with(context).load(bytes).into(holder.imgMy);

        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onClickListener(position);
                }
            });
            holder.btMy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onMoreClickListener(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return songBeanList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView singer;
        TextView title;
        ImageView imgMy;
        ImageView btMy;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            singer = itemView.findViewById(R.id.tv_my_singer);
            title = itemView.findViewById(R.id.tv_my_title);
            imgMy = itemView.findViewById(R.id.img_my_img);
            btMy = itemView.findViewById(R.id.bt_item_my);
        }
    }

    public interface OnItemClickListener {
        void onClickListener(int position);

        void onMoreClickListener(int position);
    }
}
