package be.flashapps.mwfwsoundboard.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import be.flashapps.mwfwsoundboard.App;
import be.flashapps.mwfwsoundboard.R;
import be.flashapps.mwfwsoundboard.databinding.VideoItemBinding;
import be.flashapps.mwfwsoundboard.models.Video;

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

    public static class VideoViewHolder extends RecyclerView.ViewHolder {
        VideoAdapterOnClickListener aaListener;
        VideoItemBinding videoItemBinding;
        public VideoViewHolder(VideoItemBinding videoItemBinding, VideoAdapterOnClickListener aaListener) {
            super(videoItemBinding.getRoot());
            this.videoItemBinding = videoItemBinding;
            this.aaListener = aaListener;
        }

        public void bind(Video current) {
            itemView.setOnClickListener(view ->{
                aaListener.onClick(view,current);
            });

            Glide.with(App.getContext())
                    .load(current.getImageId())
                    .fitCenter()
                    .into(videoItemBinding.ivPoster);
        }
    }

    public static interface VideoAdapterOnClickListener {
        public void onClick(View v,Video video);
    }

    public void setVideoOnclickListener(final VideoAdapterOnClickListener mItemClickListener) {
        this.videoAdapterOnClickListener = mItemClickListener;
    }


    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        VideoItemBinding v = VideoItemBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false);
        VideoViewHolder cvh = new VideoViewHolder(v, videoAdapterOnClickListener);
        return cvh;
    }

    //hier wordt alle data geset!!!!!
    @Override
    public void onBindViewHolder(VideoViewHolder videoViewHolder, int i) {
        Video current = movies.get(i);
        videoViewHolder.bind(current);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }


}
