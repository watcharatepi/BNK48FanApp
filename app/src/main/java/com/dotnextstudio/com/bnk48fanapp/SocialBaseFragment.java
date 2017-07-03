package com.dotnextstudio.com.bnk48fanapp;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.koushikdutta.ion.Ion;
import com.mikhaellopez.circularfillableloaders.CircularFillableLoaders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by niccapdevila on 3/26/16.
 */
public class SocialBaseFragment extends Fragment {
    public static final String ARGS_INSTANCE = "com.dotnextstudio.com.bnk48fanapp.argsInstance";
    private HashMap<String, String> HashMapForURL ;
    public  CircularFillableLoaders circularFillableLoaders;
    private DatabaseReference mDatabase;
    public int mInt;
    public FirebaseRecyclerAdapter<Data, ChatHolder> mAdapter;
    public ListView mListView;
    private RecyclerView mRecycler;
    public List<Data> mData;

    private LinearLayoutManager mManager;


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
        View view = inflater.inflate(R.layout.social, container, false);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("data");

      //  circularFillableLoaders = (CircularFillableLoaders)view.findViewById(R.id.circularFillableLoaders);

      //  circularFillableLoaders.setProgress(10);
      //  circularFillableLoaders.setVisibility(View.GONE);
        mRecycler = (RecyclerView) view.findViewById(R.id.listV);


        /*mAdapter = new FirebaseRecyclerAdapter<data, ChatHolder>(
                data.class,
                R.layout.cards,
                ChatHolder.class,
                mDatabase.child("data")
        ) {

            @Override
            protected void populateViewHolder(final ChatHolder viewHolder, final data model, int position) {
                Log.d("dev","===>");
                  viewHolder.setImage(model.full_picture);
                // viewHolder.setStatus(getStatus(model.getStatus()));
            }

        };

        mRecycler.setAdapter(mAdapter);*/


        // new SimpleTask().execute(0);


        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Set up Layout Manager, reverse layout
        mManager = new LinearLayoutManager(getActivity());
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        mRecycler.setLayoutManager(mManager);
        Query query =  mDatabase.orderByChild("updated_time").limitToLast(10);

        mAdapter = new FirebaseRecyclerAdapter<Data, ChatHolder>(Data.class, R.layout.cards,
                ChatHolder.class,query ) {
            @Override
            protected void populateViewHolder(ChatHolder viewHolder, Data model, int position) {


                final DatabaseReference eventRef = getRef(position);


                final String postKey = eventRef.getKey();

                Ion.with(viewHolder.fbimage).load(model.getFull_picture());

            }


        };
        mRecycler.setAdapter(mAdapter);


    }

    public static class ChatHolder extends RecyclerView.ViewHolder {
        private final ImageView fbimage;

        public ChatHolder(View itemView) {
            super(itemView);
            fbimage = (ImageView) itemView.findViewById(R.id.fbimage);


           // fbimage.setImageResource(R.drawable.test1);
        }

        public void setImage(String url) {


        }
    }


}