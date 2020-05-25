package com.shanghq.zeromusic.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shanghq.zeromusic.R;
import com.shanghq.zeromusic.bean.SongBean;

import java.util.List;

public class FindRecyclerViewAdapter extends RecyclerView.Adapter<FindRecyclerViewAdapter.MyViewHolder> {

    private List<SongBean> songBeanList;

    public FindRecyclerViewAdapter(List<SongBean> songBeans){
        this.songBeanList = songBeans;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycleview_find,parent,false);
        MyViewHolder viewHolder=new MyViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.singer.setText(songBeanList.get(position).getSinger());
        holder.song.setText(songBeanList.get(position).getTitle());

    }


    @Override
    public int getItemCount() {
        return songBeanList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView song;
        TextView singer;
        ImageView imageView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            song=itemView.findViewById(R.id.tv_play_now_title);
            singer=itemView.findViewById(R.id.tv_find_singer);
            imageView=itemView.findViewById(R.id.tv_find_img);
        }
    }
}
