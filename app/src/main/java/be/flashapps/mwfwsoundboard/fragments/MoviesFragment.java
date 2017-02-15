package be.flashapps.mwfwsoundboard.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import be.flashapps.mwfwsoundboard.R;
import be.flashapps.mwfwsoundboard.adapters.MovieAdapter;
import be.flashapps.mwfwsoundboard.models.Video;
import butterknife.BindView;
import butterknife.ButterKnife;


public class MoviesFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static ArrayList<Video> videos;

    private String mParam1;
    private String mParam2;
    @BindView(R.id.rv_movies)
    RecyclerView rv_movies;

    private Activity mActivity;

    public MoviesFragment() {
        // Required empty public constructor
    }


    public static MoviesFragment newInstance(String param1, String param2) {
        MoviesFragment fragment = new MoviesFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_movies, container, false);
        ButterKnife.bind(this, root);

        makeVideos();

        MovieAdapter movieAdapter = new MovieAdapter(mActivity.getApplicationContext());
        rv_movies.setLayoutManager(new GridLayoutManager(mActivity.getApplicationContext(), 2));
        movieAdapter.addVideos(videos);
        movieAdapter.setVideoOnclickListener(new MovieAdapter.VideoAdapterOnClickListener() {
            @Override
            public void onClick(View v, int position, List<Video> sounds) {
                Video current = sounds.get(position);
                if (current.getUrl().equals("coming soon")) {
                    Snackbar.make(mActivity.findViewById(android.R.id.content), "Coming soon", Snackbar.LENGTH_LONG).show();
                } else {
                    startVideo(current.getUrl());
                }
            }
        });
        rv_movies.setAdapter(movieAdapter);


        return root;
    }

    private void makeVideos() {
        videos = new ArrayList<>();
        videos.add(new Video(2008, "https://www.youtube.com/watch?v=wrol1J0E-ZM", R.drawable.mwfw2008));//2008
        videos.add(new Video(2009, "https://www.youtube.com/watch?v=9d3r7Q_sLx8", R.drawable.mwfw2009));//2009
        videos.add(new Video(2010, "https://www.youtube.com/watch?v=B5NBQoT_Mqw&gl=BE", R.drawable.mwfw2010));//2010
        videos.add(new Video(20011, "https://www.youtube.com/watch?v=eHDOAjy9DiA", R.drawable.mwfw2011));//2011
        videos.add(new Video(2012, "https://www.youtube.com/watch?v=zRIi4XSJgGQ", R.drawable.mwfw2012));//2012
        videos.add(new Video(2013, "https://www.youtube.com/watch?v=ewFf1Kb6XZg", R.drawable.mwfw2013));//2013
        videos.add(new Video(2014, "https://www.youtube.com/watch?v=nXNugahgdBQ", R.drawable.mwfw2014));//2014
        videos.add(new Video(2015, "https://www.youtube.com/watch?v=rKMJu4UWf3s", R.drawable.mwfw2015));//2015
        videos.add(new Video(2016, "https://www.youtube.com/watch?v=WMhppMK9H6c", R.drawable.mwfw2016));//2016
        videos.add(new Video(2017, "https://www.youtube.com/watch?v=hOgLMwfjnZI", R.drawable.mwfw2017));//2017
    }

    public void startVideo(String url) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = getActivity();
    }


}
