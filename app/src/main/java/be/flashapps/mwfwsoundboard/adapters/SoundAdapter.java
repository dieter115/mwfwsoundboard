package be.flashapps.mwfwsoundboard.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


import be.flashapps.mwfwsoundboard.R;
import be.flashapps.mwfwsoundboard.models.Sound;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dietervaesen on 8/09/15.
 */
public class SoundAdapter extends RecyclerView.Adapter<SoundAdapter.SoundViewHolder> {


    List<Sound> sounds;
    Context mContext;
    public SoundAdapterOnClickListener soundAdapterOnClickListener;

    public SoundAdapter(Context context) {
        sounds = new ArrayList<Sound>();
        this.mContext = context;
    }

    public void addSounds(List<Sound> sounds) {
        this.sounds = sounds;
    }

    public static class SoundViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.tv_sound_title)
        TextView tv_sound_title;

        List<Sound> soundList;
        Context context;
        SoundAdapterOnClickListener aaListener;
        int position;

        public SoundViewHolder(View itemView, SoundAdapterOnClickListener aaListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
            this.aaListener = aaListener;
        }

        @Override
        public void onClick(View v) {
            aaListener.onClick(v, position, soundList);
        }
    }

    public static interface SoundAdapterOnClickListener {
        public void onClick(View v, int position, List<Sound> sounds);
    }

    public void setSoundClickListener(final SoundAdapterOnClickListener mItemClickListener) {
        this.soundAdapterOnClickListener = mItemClickListener;
    }


    @Override
    public SoundViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sound_item, viewGroup, false);
        SoundViewHolder cvh = new SoundViewHolder(v, soundAdapterOnClickListener);
        return cvh;
    }

    //hier wordt alle data geset!!!!!
    @Override
    public void onBindViewHolder(SoundViewHolder soundViewHolder, int i) {
        Sound current = sounds.get(i);

        soundViewHolder.tv_sound_title.setText(current.getTitle());
        soundViewHolder.position = i;
        soundViewHolder.soundList = sounds;
    }

    @Override
    public int getItemCount() {
        return sounds.size();
    }


}
