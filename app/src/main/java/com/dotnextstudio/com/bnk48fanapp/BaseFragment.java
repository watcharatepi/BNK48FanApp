package com.dotnextstudio.com.bnk48fanapp;


import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
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

    private RecyclerView mRecycler;
    public List<Data> mData;

    private LinearLayoutManager mManager;

    public static final String URL =
            "http://blog.teamtreehouse.com/api/get_recent_summary/";
 //   public CustomAdapter mAdapter;
    int mInt = 0;
    public   ListView mListView;
    private DatabaseReference mDatabase;

    private SliderLayout sliderLayout ;
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

        CircularFillableLoaders circularFillableLoaders = (CircularFillableLoaders)view.findViewById(R.id.circularFillableLoaders);

        circularFillableLoaders.setProgress(0);
        circularFillableLoaders.setVisibility(View.GONE);




        mListView = (ListView) view.findViewById(R.id.listV);

        //new SimpleTask().execute(URL);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("main");

        HashMapForURL = new HashMap<String, String>();
        Activity av = getActivity();
        mDatabase.limitToLast(5).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot msgSnapshot: snapshot.getChildren()) {
                    Hotsnews msg = msgSnapshot.getValue(Hotsnews.class);
                    Log.i("dev","==>"+msg.getTitle());
                    HashMapForURL.put("Cat Radio", "https://pbs.twimg.com/media/DDVwG0PVYAI7ah_.jpg");
                }



                for(String name : HashMapForURL.keySet()){

                    TextSliderView textSliderView = new TextSliderView(getActivity());

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

                sliderLayout.setDuration(10000);

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





    }

    private class SimpleTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            // Create Show ProgressBar
        }

        protected String doInBackground(String... urls)   {
            String result = "";

            Query query = mDatabase.child("data").orderByChild("updated_time").limitToLast(30);

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        Data blog =  dataSnapshot.getValue(Data.class);
                       // Log.d("Count " ,"===>"+dataSnapshot.getChildrenCount());
                        // dataSnapshot is the "issue" node with all children with id 0
                        for (DataSnapshot issue : dataSnapshot.getChildren()) {
                            // do something with the individual "issues"
                            //Log.v("dev","===>"+ issue.getKey()); //displays the key for the node
                           // Log.v("dev","===>"+ issue.child("updated_time").getValue());   //gives the value for given keyname


                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.d("dev","no===>"+databaseError);
                }
            });


            /*try {

                HttpGet httpGet = new HttpGet(urls[0]);
                HttpClient client = new DefaultHttpClient();

                HttpResponse response = client.execute(httpGet);

                int statusCode = response.getStatusLine().getStatusCode();

                if (statusCode == 200) {
                    InputStream inputStream = response.getEntity().getContent();
                    BufferedReader reader = new BufferedReader
                            (new InputStreamReader(inputStream));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        result += line;
                    }
                }

            } catch (ClientProtocolException e) {

            } catch (IOException e) {

            }*/






            return result;
        }

        protected void onPostExecute(String jsonString)  {
            // Dismiss ProgressBar
           // showData(jsonString);
        }
    }

    private void showData(String jsonString) {
        /*Gson gson = new Gson();
        Blog blog = gson.fromJson(jsonString, Blog.class);
        List<Post> posts = blog.getPosts();


        mAdapter = new CustomAdapter(getActivity(), posts);
        mListView.setAdapter(mAdapter);*/
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
}