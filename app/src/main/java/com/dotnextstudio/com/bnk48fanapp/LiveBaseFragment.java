package com.dotnextstudio.com.bnk48fanapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
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
import com.yalantis.phoenix.PullToRefreshView;

/**
 * Created by watcharatepinkong on 7/6/17.
 */

public class LiveBaseFragment extends Fragment {
    public static final String ARGS_INSTANCE = "com.dotnextstudio.com.bnk48fanapp.argsInstance";

    private LinearLayoutManager mManager;
    private int visibleThreshold = 6;
    private boolean isLoading = false;
    public RecyclerView mListView;

    private DatabaseReference mDatabase;

    private SliderLayout sliderLayout ;

    public FirebaseRecyclerAdapter<Video, VideoHolder> mAdapter;
    public PullToRefreshView mPullToRefreshView;
    int mInt = 0;
    public String lastkey;
    private int pages = 0;
  //  private int lastVisibleItem, totalItemCount;
    private static final int TOTAL_ITEM_EACH_LOAD = 10;

    private int currentPage = 0;
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





        mListView = (RecyclerView) view.findViewById(R.id.listV);



        mManager = new LinearLayoutManager(getActivity());
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        mListView.setLayoutManager(mManager);



        mPullToRefreshView  = (PullToRefreshView) view.findViewById(R.id.pull_to_refresh1);
      /*  Video video = new Video("https://scontent.fbkk1-6.fna.fbcdn.net/v/t15.0-10/19920463_1238434549617134_2632296202003021824_n.jpg?oh=b75742415cb9afe1d69a35cd481b9b6c&oe=5A0935CC","บ้านแคท","https://www.facebook.com/f768aa59-96de-4958-acbf-3d78beea8a22","music" );

        String key =  mDatabase.child("video").push().getKey();
        //Log.i("dev","getTitle==>"+key);
        mDatabase.child("video").child(key).setValue(video);
*/
        mPullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {

              //

               /* Video video = new Video("https://scontent.fbkk12-2.fna.fbcdn.net/v/t15.0-10/p526x395/19948406_1240278962766026_6664097584623124480_n.jpg?oh=e63ccf37fbe2005edff9a3d2cffa496c&oe=5A0C48F2","บ้านแคท","https://www.facebook.com/f768aa59-96de-4958-acbf-3d78beea8a22","music" );

                String key =  mDatabase.child("video").push().getKey();
                //Log.i("dev","getTitle==>"+key);
                mDatabase.child("video").child(key).setValue(video);*/


                Query query =  mDatabase.child("video").limitToLast(5);

                ContextCompat.getDrawable(getActivity(),R.drawable.line_divider);

                mAdapter = new FirebaseRecyclerAdapter<Video, VideoHolder>(Video.class, R.layout.item_video,
                        VideoHolder.class,query ) {
                    @Override
                    protected void populateViewHolder(VideoHolder viewHolder, final Video model, int position) {
                        mPullToRefreshView.setRefreshing(false);

                        lastkey =  this.getRef(position).getKey();
                        Log.d("dev","==>"+lastkey);
                        Ion.with(viewHolder.imagevideo).load(model.getVideo());
                        viewHolder.imagevideo.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        });

                    }


                };
                mListView.setAdapter(mAdapter);


            }
        });



        /*mListView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    Log.d("dev","load more==>");
                    isLoading = true;
                }
            }
        });*/


       /* mListView.addOnScrollListener(new EndlessRecyclerViewScrollListener(mManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                // how can I add (not overwrite) more items here?
                Log.d("dev","load more==>");
            }
        });*/


      /*  mListView.addOnScrollListener(new EndlessRecyclerViewScrollListener(mManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
              Log.d("dev","page===>"+page);
            }
        });
        loadData();*/

       /* mListView.setOnScrollListener(new EndlessRecyclerOnScrollListener(mManager) {
            @Override
            public void onLoadMore(int current_page) {
              Log.d("dev","setOnScrollListener===>");
            }
        });*/

        loadData();

        mListView.addOnScrollListener(recyclerViewOnScrollListener);


        return view;
    }

    public boolean loading = true;
    public int pastVisiblesItems, visibleItemCount, totalItemCount;
    private RecyclerView.OnScrollListener recyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
        int ydy = 0;
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int offset = dy - ydy;
            int firstVisibleItemPosition = mManager.findFirstVisibleItemPosition();
           // Log.d("dev","==>"+totalItemCount+"/"+lastVisibleItem+"/"+visibleThreshold+"/"+visibleItemCount);

           /* boolean shouldPullUpRefresh = mManager.findLastCompletelyVisibleItemPosition() == mManager.getChildCount() - 1
                    && recyclerView.getScrollState() == RecyclerView.SCROLL_STATE_DRAGGING && offset < -30;

            Log.d("dev","shouldPullUpRefresh==>"+ mManager.findLastCompletelyVisibleItemPosition()+" / "+(mManager.getChildCount() - 1)+"/"+offset+"/"+recyclerView.getScrollState());

            if (shouldPullUpRefresh) {

                Log.d("dev","loading==>");
                //swipeRefreshLayout.setRefreshing(true);
                //refresh to load data here.
                return;
            }*/
            Log.d("dev","loading==>"+dy);
            if(dy > 0) //check for scroll down
            {
                visibleItemCount = mManager.getChildCount();
                totalItemCount = mManager.getItemCount();
                pastVisiblesItems = mManager.findFirstVisibleItemPosition();

                if (loading)
                {
                    Log.d("dev","loading==>");
                    if ( (visibleItemCount + pastVisiblesItems) >= totalItemCount)
                    {
                        loading = false;
                        Log.v("...", "Last Item Wow !");
                        //Do pagination.. i.e. fetch new data
                    }
                }
            }
        }
    };

    private void loadData() {

        Query query =  mDatabase.child("video").limitToLast(5);

        ContextCompat.getDrawable(getActivity(),R.drawable.line_divider);

        mAdapter = new FirebaseRecyclerAdapter<Video, VideoHolder>(Video.class, R.layout.item_video,
                VideoHolder.class,query ) {
            @Override
            protected void populateViewHolder(VideoHolder viewHolder, final Video model, int position) {
                lastkey =  this.getRef(position).getKey();
                Ion.with(viewHolder.imagevideo).load(model.getVideo());
                viewHolder.imagevideo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       /* Intent intent = new Intent(v.getContext(),MyPlayerActivity.class);
                        startActivity(intent);*/
                      /*  Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
                        String facebookUrl = getFacebookPageURL(v.getContext(),model.getUrl());
                        facebookIntent.setData(Uri.parse(facebookUrl));
                        startActivity(facebookIntent);*/
                    }
                });
               /* mListView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                        totalItemCount = linearLayoutManager.getItemCount();
                        lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                        if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                            Log.d("dev","load more==>");
                            isLoading = true;
                        }
                    }
                });*/

              /*  if (mAdapter.getItemCount() > 0){
                    initRecyclerView();
                }*/

            }


        };
        mListView.setAdapter(mAdapter);

    }
    private void initRecyclerView() {
        mListView.setLayoutManager(mManager);
        mListView.setAdapter(mAdapter);
        mListView.addOnScrollListener(new EndlessRecyclerViewScrollListener(mManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                if (page < pages) {
                    Log.d("dev","===>pages");
                  //  Timber.d("Loading more offers, Page: %d", page + 1);

                   // loadMoreOffersData(page + 1);
                }
            }
        });
    }

    public String getFacebookPageURL(Context context,String url) {
        PackageManager packageManager = context.getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) { //newer versions of fb app
                return "fb://facewebmodal/f?href=" + url;
            } else { //older versions of fb app
                return "fb://page/" + url;
            }
        } catch (PackageManager.NameNotFoundException e) {
            return url; //normal web url
        }
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
