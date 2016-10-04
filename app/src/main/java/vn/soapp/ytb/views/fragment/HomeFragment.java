package vn.soapp.ytb.views.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeStandalonePlayer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import vn.soapp.ytb.R;
import vn.soapp.ytb.helper.DividerItemDecoration;
import vn.soapp.ytb.model.VideoEntry;
import vn.soapp.ytb.util.Config;
import vn.soapp.ytb.util.NetworkConfig;
import vn.soapp.ytb.views.adapter.HomeRecycleViewAdapter;

public class HomeFragment extends Fragment {

    private static final List<VideoEntry> VIDEO_ENTRIES;
    private Context mContext;
    private NetworkConfig networkConf;

    private TextView mConnectionOff;
    private ImageView mIconConnectionOff;
    private RecyclerView mRecyclerView;

    static {
        List<VideoEntry> list = new ArrayList<>();
        list.add(new VideoEntry("YouTube Collection", "Y_UmWdcTrrc"));
        list.add(new VideoEntry("GMail Tap", "1KhZKNZO8mQ"));
        list.add(new VideoEntry("Chrome Multitask", "UiLSiqyDf4Y"));
        list.add(new VideoEntry("Google Fiber", "re0VRK6ouwI"));
        list.add(new VideoEntry("Autocompleter", "blB_X38YSxQ"));
        list.add(new VideoEntry("GMail Motion", "Bu927_ul_X0"));
        list.add(new VideoEntry("Translate for Animals", "3I24bSteJpw"));
        VIDEO_ENTRIES = Collections.unmodifiableList(list);
    }

    public HomeFragment(){ }

    public HomeFragment(Context context) {
        this.mContext = context;
    }

    // retained fragment
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mContext = (FragmentActivity) activity;
        setRetainInstance(true);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        networkConf = new NetworkConfig(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        if (container == null)
            return null;

        View v = inflater.inflate(R.layout.fragment_home, container, false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.home_recycle_view);
        mConnectionOff = (TextView) v.findViewById(R.id.text_connection_off);
        mIconConnectionOff = (ImageView) v.findViewById(R.id.icon_connection_off);

        if (networkConf.isNetworkAvailable()) {     // network available
            // hide icon connection off
            mConnectionOff.setVisibility(View.GONE);
            mIconConnectionOff.setVisibility(View.GONE);

            // setup recycle view
            mRecyclerView.setVisibility(View.VISIBLE);
            HomeRecycleViewAdapter adapter = new HomeRecycleViewAdapter(VIDEO_ENTRIES);
            mRecyclerView.setAdapter(adapter);
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.setItemViewCacheSize(20);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            mRecyclerView.setDrawingCacheEnabled(true);
            mRecyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
            mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext, LinearLayoutManager.VERTICAL));
            mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            adapter.SetOnItemClickListener(new HomeRecycleViewAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int pos) {
                    //Opens in the StandAlonePlayer, fullscreen, autoplay
                    startActivity(YouTubeStandalonePlayer.createVideoIntent(getActivity(),
                            Config.YOUTUBE_API_KEY,
                            VIDEO_ENTRIES.get(pos).getId(), 0,true,false));
                }
            });

        } else {
            // show icon connection Off
            mConnectionOff.setVisibility(View.VISIBLE);
            mIconConnectionOff.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        }
        return v;
    }

}
