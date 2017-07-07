package com.dotnextstudio.com.bnk48fanapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.koushikdutta.ion.Ion;

/**
 * Created by watcharatepinkong on 7/6/17.
 */

public class LiveBaseFragment extends Fragment {
    public static final String ARGS_INSTANCE = "com.dotnextstudio.com.bnk48fanapp.argsInstance";

    private LinearLayoutManager mManager;


    public RecyclerView mListView;

    private DatabaseReference mDatabase;

    private SliderLayout sliderLayout ;

    public FirebaseRecyclerAdapter<Video, VideoHolder> mAdapter;

    int mInt = 0;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mInt = args.getInt(ARGS_INSTANCE);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.live_menu, container, false);
        mDatabase = FirebaseDatabase.getInstance().getReference();


       /* Video video = new Video("https://scontent.fbkk1-6.fna.fbcdn.net/v/t15.0-10/19920463_1238434549617134_2632296202003021824_n.jpg?oh=b75742415cb9afe1d69a35cd481b9b6c&oe=5A0935CC","บ้านแคท","https://www.facebook.com/f768aa59-96de-4958-acbf-3d78beea8a22","music" );

        String key =  mDatabase.child("video").push().getKey();
        //Log.i("dev","getTitle==>"+key);
        mDatabase.child("video").child(key).setValue(video);*/


        mListView = (RecyclerView) view.findViewById(R.id.listV);



        mManager = new LinearLayoutManager(getActivity());
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        mListView.setLayoutManager(mManager);



        Query query =  mDatabase.child("video");

        ContextCompat.getDrawable(getActivity(),R.drawable.line_divider);

        mAdapter = new FirebaseRecyclerAdapter<Video, VideoHolder>(Video.class, R.layout.item_video,
                VideoHolder.class,query ) {
            @Override
            protected void populateViewHolder(VideoHolder viewHolder, final Video model, int position) {

                Ion.with(viewHolder.imagevideo).load(model.getVideo());

            }


        };
        mListView.setAdapter(mAdapter);

        return view;
    }


    public static class VideoHolder extends RecyclerView.ViewHolder {
        private final ImageView imagevideo;

        public VideoHolder(View itemView) {
            super(itemView);
            imagevideo = (ImageView) itemView.findViewById(R.id.imagevideo);

            // fbimage.setImageResource(R.drawable.test1);
        }

        public void setImage(String url) {


        }
    }
}
