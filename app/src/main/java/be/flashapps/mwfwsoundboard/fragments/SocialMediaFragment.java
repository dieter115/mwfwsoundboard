package be.flashapps.mwfwsoundboard.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.orhanobut.logger.Logger;

import be.flashapps.mwfwsoundboard.App;
import be.flashapps.mwfwsoundboard.R;
import be.flashapps.mwfwsoundboard.databinding.FragmentSocialMediaBinding;

public class SocialMediaFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Activity mActivity;
    FragmentSocialMediaBinding fragmentSocialMediaBinding;

    public SocialMediaFragment() {
        // Required empty public constructor
    }

    public void startFacebook(){
        String url="https://www.facebook.com/mwfwhds/?fref=ts";
        Uri uri = Uri.parse(url);
        try {
            ApplicationInfo applicationInfo = mActivity.getPackageManager().getApplicationInfo("com.facebook.katana", 0);
            if (applicationInfo.enabled) {
                // http://stackoverflow.com/a/24547437/1048340
                uri = Uri.parse("fb://facewebmodal/f?href=" + url);
            }
        } catch (PackageManager.NameNotFoundException ignored) {
            Logger.d("ignored the sjizzle out of it");
        }
        startActivity(new Intent(Intent.ACTION_VIEW, uri));

    }


    public void startWebsite(){
        Uri uri = Uri.parse("http://www.mwfw.be/home.html");
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }

    public void startWebsiteHawp(){
        Uri uri = Uri.parse("http://www.herkseafterworkparty.be");
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }

    public static SocialMediaFragment newInstance() {
        SocialMediaFragment fragment = new SocialMediaFragment();
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
        fragmentSocialMediaBinding = FragmentSocialMediaBinding.inflate(inflater, container, false);

        fragmentSocialMediaBinding.llFacebook.setOnClickListener(view -> {
            startFacebook();
        });

        fragmentSocialMediaBinding.llWebsite.setOnClickListener(view -> {
            startWebsite();
        });

        fragmentSocialMediaBinding.llWebsiteHawp.setOnClickListener(view -> {
            startWebsiteHawp();
        });

        Glide.with(App.getContext())
                .load(R.drawable.snapcode)
                .fitCenter()
                .into(fragmentSocialMediaBinding.ivSnap);
        return fragmentSocialMediaBinding.getRoot();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity=getActivity();
    }


}
