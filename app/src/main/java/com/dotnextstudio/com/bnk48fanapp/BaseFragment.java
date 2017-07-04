package com.dotnextstudio.com.bnk48fanapp;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
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
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.mikhaellopez.circularfillableloaders.CircularFillableLoaders;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;


import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Created by niccapdevila on 3/26/16.
 */
public class BaseFragment extends Fragment implements BaseSliderView.OnSliderClickListener ,  ViewPagerEx.OnPageChangeListener{
    public static final String ARGS_INSTANCE = "com.dotnextstudio.com.bnk48fanapp.argsInstance";
    private HashMap<String, String> HashMapForURL ;

    private HashMap<String, Integer> HashMapForLocalRes ;
    Button mButton;
    FragmentNavigation mFragmentNavigation;

    public List<Data> mData;

    private LinearLayoutManager mManager;

    public static final String URL =
            "http://blog.teamtreehouse.com/api/get_recent_summary/";
 //   public CustomAdapter mAdapter;
    int mInt = 0;
    public   RecyclerView mListView;

    private DatabaseReference mDatabase;

    private SliderLayout sliderLayout ;

    public FirebaseRecyclerAdapter<Data, NewsHolder> mAdapter;

    public ActionBar toolbar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mInt = args.getInt(ARGS_INSTANCE);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_news, container, false);




        sliderLayout = (SliderLayout)view.findViewById(R.id.slider);

      /*  CircularFillableLoaders circularFillableLoaders = (CircularFillableLoaders)view.findViewById(R.id.circularFillableLoaders);

        circularFillableLoaders.setProgress(0);
        circularFillableLoaders.setVisibility(View.GONE);*/




        mListView = (RecyclerView) view.findViewById(R.id.listV);

        //new SimpleTask().execute(URL);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



        toolbar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        toolbar.setTitle("Hot News");

        mManager = new LinearLayoutManager(getActivity());
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        mListView.setLayoutManager(mManager);


        HashMapForURL = new HashMap<String, String>();


        mDatabase = FirebaseDatabase.getInstance().getReference();

        Hotsnews hotsnews = new Hotsnews("test2", "test2","https://pbs.twimg.com/media/DD4j64vUMAQON3j.jpg");

        String key =  mDatabase.child("main").push().getKey();
        //Log.i("dev","getTitle==>"+key);
      //  mDatabase.child(key).setValue(hotsnews);


        Activity av = getActivity();
        mDatabase.child("main").limitToLast(5).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {


                HashMapForURL.clear();
                Log.i("dev","onDataChange==>"+HashMapForURL.size());

                for (DataSnapshot msgSnapshot: snapshot.getChildren()) {
                    Hotsnews msg = msgSnapshot.getValue(Hotsnews.class);

                    HashMapForURL.put(msg.getTitle(), msg.getLink());
                   // Log.i("dev","getTitle==>"+msg.getLink());
                }

              //  Log.i("dev","getTitle==>"+ HashMapForURL.size());

                for(String name : HashMapForURL.keySet()){

                    TextSliderView textSliderView = new TextSliderView(getActivity());
                  //  Log.i("dev","getTitle 1==>"+HashMapForURL.get(name));
                    textSliderView
                            .description(name)
                            .image(HashMapForURL.get(name))
                            .setScaleType(BaseSliderView.ScaleType.CenterCrop)
                            .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                                @Override
                                public void onSliderClick(BaseSliderView slider) {

                                }
                            });

                    textSliderView.bundle(new Bundle());

                    textSliderView.getBundle()
                            .putString("extra",name);

                    sliderLayout.addSlider(textSliderView);
                }
                // sliderLayout.setPresetTransformer(SliderLayout.Transformer.DepthPage);

                sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);

                sliderLayout.setCustomAnimation(new DescriptionAnimation());

                sliderLayout.setDuration(5000);

                sliderLayout.addOnPageChangeListener(new ViewPagerEx.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {

                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });


            }
            @Override
            public void onCancelled(DatabaseError error) {
                //Log.e("Chat", "The read failed: " + error.getText());
            }
        });


        Query query =  mDatabase.child("data").orderByChild("updated_time").limitToLast(5);

        ContextCompat.getDrawable(getActivity(),R.drawable.line_divider);

        mAdapter = new FirebaseRecyclerAdapter<Data, NewsHolder>(Data.class, R.layout.item_news,
                NewsHolder.class,query ) {
            @Override
            protected void populateViewHolder(NewsHolder viewHolder, final Data model, int position) {


                final DatabaseReference eventRef = getRef(position);


                final String postKey = eventRef.getKey();

               Ion.with(viewHolder.newsimage).load(model.getFull_picture());

                viewHolder.newstitle.setText(model.getMessage());



            }


        };
        mListView.setAdapter(mAdapter);


        mListView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));

    }





    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentNavigation) {
            mFragmentNavigation = (FragmentNavigation) context;
        }
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public interface FragmentNavigation {
        public void pushFragment(Fragment fragment);
    }

    public static class NewsHolder extends RecyclerView.ViewHolder {
        private final ImageView newsimage;
        private final TextView newstitle;

        public NewsHolder(View itemView) {
            super(itemView);
            newsimage = (ImageView) itemView.findViewById(R.id.newsimage);

            newstitle = (TextView) itemView.findViewById(R.id.newstitle);


            // fbimage.setImageResource(R.drawable.test1);
        }

        public void setImage(String url) {


        }
    }

    public class SimpleDividerItemDecoration extends RecyclerView.ItemDecoration {
        private Drawable mDivider;

        public SimpleDividerItemDecoration(Context context) {
            mDivider = context.getResources().getDrawable(R.drawable.line_divider);
        }

        @Override
        public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();

            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = parent.getChildAt(i);

                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

                int top = child.getBottom() + params.bottomMargin;
                int bottom = top + mDivider.getIntrinsicHeight();

                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }
    }
}