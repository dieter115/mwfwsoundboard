package be.flashapps.mwfwsoundboard.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import be.flashapps.mwfwsoundboard.R;
import be.flashapps.mwfwsoundboard.databinding.SoundItemBinding;
import be.flashapps.mwfwsoundboard.models.Sound;

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

    public static class SoundViewHolder extends RecyclerView.ViewHolder{
        SoundAdapterOnClickListener aaListener;
        SoundItemBinding soundItemBinding;

        public SoundViewHolder(SoundItemBinding binding, SoundAdapterOnClickListener aaListener){
            super(binding.getRoot());

            this.soundItemBinding = binding;
            this.aaListener = aaListener;
        }

        public void bind(Sound sound){
            soundItemBinding.tvSoundTitle.setText(sound.getTitle());
            itemView.setOnClickListener(view -> {
                aaListener.onClick(view,getAbsoluteAdapterPosition(),sound);
            });
        }
    }

    public static interface SoundAdapterOnClickListener {
        public void onClick(View v, int position, Sound sound);
    }

    public void setSoundClickListener(final SoundAdapterOnClickListener mItemClickListener) {
        this.soundAdapterOnClickListener = mItemClickListener;
    }


    @Override
    public SoundViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        SoundItemBinding soundItemBinding = SoundItemBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false);
        SoundViewHolder cvh = new SoundViewHolder(soundItemBinding, soundAdapterOnClickListener);
        return cvh;
    }

    //hier wordt alle data geset!!!!!
    @Override
    public void onBindViewHolder(SoundViewHolder soundViewHolder, int i) {
        Sound current = sounds.get(i);
        soundViewHolder.bind(current);
    }

    @Override
    public int getItemCount() {
        return sounds.size();
    }


}
