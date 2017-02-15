package be.flashapps.mwfwsoundboard.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import be.flashapps.mwfwsoundboard.R;
import be.flashapps.mwfwsoundboard.adapters.SoundAdapter;
import be.flashapps.mwfwsoundboard.models.Sound;
import butterknife.BindView;
import butterknife.ButterKnife;


public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    @BindView(R.id.rv_sounds)
    RecyclerView rv_sounds;
    List<Sound> sounds;
    MediaPlayer player;


    public HomeFragment() {
        // Required empty public constructor
    }


    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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
        View root= inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this,root);
        sounds=new ArrayList<>();

        String[] fileNames = new String[0];
        SoundAdapter soundAdapter=new SoundAdapter(mActivity.getApplicationContext());
        rv_sounds.setLayoutManager(new GridLayoutManager(mActivity.getApplicationContext(),2));
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
            public void onClick(View v, int position, List<Sound> sounds) {
                Sound current=sounds.get(position);
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
        rv_sounds.setAdapter(soundAdapter);


        return root;
    }
}
