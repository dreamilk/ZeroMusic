package com.shanghq.zeromusic.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shanghq.zeromusic.R;
import com.shanghq.zeromusic.Utils.MusicUtils;
import com.shanghq.zeromusic.bean.SongBean;

import java.util.List;

public class SearchRecyclerViewAdapter extends RecyclerView.Adapter<SearchRecyclerViewAdapter.SearchViewHolder> {

    private List<SongBean> songBeanList;
    private onSearchItemClickListener onSearchItemClickListener;
    private Context context;

    public SearchRecyclerViewAdapter(Context context,List<SongBean> songBeans){
        this.context=context;
        this.songBeanList = songBeans;
    }

    public void setSongBeanList(List<SongBean> songBeanList) {
        this.songBeanList = songBeanList;
    }

    public void setOnSearchItemClickListener(SearchRecyclerViewAdapter.onSearchItemClickListener onSearchItemClickListener) {
        this.onSearchItemClickListener = onSearchItemClickListener;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_search,parent,false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, final int position) {

        SongBean songBean=songBeanList.get(position);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onSearchItemClickListener!=null){
                    onSearchItemClickListener.onItemCLick(position);
                }
            }
        });

        holder.song.setText(songBean.getTitle());
        holder.singer.setText(songBean.getSinger());
        if(!songBean.getAlbum().equals("")){
            holder.album.setText("《"+ songBean.getAlbum()+"》");
        }else{
            holder.album.setText("");
        }
        holder.duration.setText(MusicUtils.formatTime(songBean.getDuration())+"");
    }

    @Override
    public int getItemCount() {
        return songBeanList.size();
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder{

        TextView song;
        TextView singer;
        TextView album;
        TextView duration;
        ImageView imageView;

        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            song=itemView.findViewById(R.id.tv_search_song);
            singer=itemView.findViewById(R.id.tv_search_singer);
            album=itemView.findViewById(R.id.tv_search_album);
            duration=itemView.findViewById(R.id.tv_search_duration);
            imageView=itemView.findViewById(R.id.imgbtn_search_more);
        }
    }

    public interface onSearchItemClickListener{
        void onItemCLick(int position);
    }
}
