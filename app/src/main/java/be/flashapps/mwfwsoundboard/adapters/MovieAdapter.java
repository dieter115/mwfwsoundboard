package be.flashapps.mwfwsoundboard.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import be.flashapps.mwfwsoundboard.App;
import be.flashapps.mwfwsoundboard.R;
import be.flashapps.mwfwsoundboard.models.Video;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dietervaesen on 8/09/15.
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.VideoViewHolder> {


    List<Video> movies;
    Context mContext;
    public VideoAdapterOnClickListener videoAdapterOnClickListener;

    public MovieAdapter(Context context) {
        movies = new ArrayList<Video>();
        this.mContext = context;
    }

    public void addVideos(List<Video> sounds) {
        this.movies = sounds;
    }

    public static class VideoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.iv_poster)
        ImageView ivPoster;

        List<Video> videoList;
        Context context;
        VideoAdapterOnClickListener aaListener;
        int position;

        public VideoViewHolder(View itemView, VideoAdapterOnClickListener aaListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
            this.aaListener = aaListener;
        }

        @Override
        public void onClick(View v) {
            aaListener.onClick(v, position, videoList);
        }
    }

    public static interface VideoAdapterOnClickListener {
        public void onClick(View v, int position, List<Video> sounds);
    }

    public void setVideoOnclickListener(final VideoAdapterOnClickListener mItemClickListener) {
        this.videoAdapterOnClickListener = mItemClickListener;
    }


    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.video_item, viewGroup, false);
        VideoViewHolder cvh = new VideoViewHolder(v, videoAdapterOnClickListener);
        return cvh;
    }

    //hier wordt alle data geset!!!!!
    @Override
    public void onBindViewHolder(VideoViewHolder videoViewHolder, int i) {
        Video current = movies.get(i);

        Glide.with(App.getContext())
                .load(current.getImageId())
                .fitCenter()
                .crossFade()
                .into(videoViewHolder.ivPoster);
        videoViewHolder.position = i;
        videoViewHolder.videoList = movies;
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }


}
