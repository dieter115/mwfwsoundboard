package be.flashapps.mwfwsoundboard.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import be.flashapps.mwfwsoundboard.R;
import be.flashapps.mwfwsoundboard.adapters.SoundAdapter;
import be.flashapps.mwfwsoundboard.databinding.FragmentHomeBinding;
import be.flashapps.mwfwsoundboard.models.Sound;


public class HomeFragment extends Fragment {

    List<Sound> sounds;
    MediaPlayer player;

    private FragmentHomeBinding binding;


    public HomeFragment() {
        // Required empty public constructor
    }


    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    Activity mActivity;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity=getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        sounds=new ArrayList<>();

        String[] fileNames = new String[0];
        SoundAdapter soundAdapter=new SoundAdapter(mActivity.getApplicationContext());
        binding.rvSounds.setLayoutManager(new GridLayoutManager(mActivity.getApplicationContext(),2));
        player = new MediaPlayer();

        try {
            fileNames = mActivity.getAssets().list("");
            Sound sound;
            for(String name:fileNames){
                if(name.contains(".mp3")) {
                    sound = new Sound(name.replace("_", " ").replace(".mp3",""), name);
                    sounds.add(sound);
                }
            }
            soundAdapter.addSounds(sounds);

        } catch (IOException e) {
            e.printStackTrace();
        }
        soundAdapter.setSoundClickListener(new SoundAdapter.SoundAdapterOnClickListener() {
            @Override
            public void onClick(View v, int position, Sound current) {
                try {
                    AssetFileDescriptor afd = mActivity.getAssets().openFd(current.getName());
                    player.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
                    player.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    player.prepare();
                    player.start();
                } catch (Exception e) {
                    if(e instanceof IOException ){
                        e.printStackTrace();
                    }
                    else{
                        player.stop();
                        player.reset();
                        e.printStackTrace();
                        try {
                            AssetFileDescriptor afd = mActivity.getAssets().openFd(current.getName());
                            player.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
                            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
                            player.prepare();
                            player.start();
                        }catch (Exception ex){
                            ex.printStackTrace();
                        }
                    }
                }
            }
        });

        binding.rvSounds.setAdapter(soundAdapter);


        return binding.getRoot();
    }
}
