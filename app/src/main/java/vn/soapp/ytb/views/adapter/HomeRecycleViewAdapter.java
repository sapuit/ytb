package vn.soapp.ytb.views.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import vn.soapp.ytb.R;
import vn.soapp.ytb.model.VideoEntry;
import vn.soapp.ytb.util.Config;

public class HomeRecycleViewAdapter extends RecyclerView.Adapter<HomeRecycleViewAdapter.ViewHoder> {

    private static final String TAG = HomeRecycleViewAdapter.class.getSimpleName();
    private List<VideoEntry> mVideoList;
    private final ThumbnailListener thumbnailListener;
    private final Map<YouTubeThumbnailView, YouTubeThumbnailLoader> thumbnailViewToLoaderMap;
    private OnItemClickListener onItemClickListener;

    public HomeRecycleViewAdapter(List<VideoEntry> mVideoList) {
        this.mVideoList = mVideoList;
        thumbnailViewToLoaderMap = new HashMap<YouTubeThumbnailView, YouTubeThumbnailLoader>();
        thumbnailListener = new ThumbnailListener();
    }

    @Override
    public HomeRecycleViewAdapter.ViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.video_item, parent, false);
        return new ViewHoder(v);
    }

    @Override
    public void onBindViewHolder(HomeRecycleViewAdapter.ViewHoder holder, final int position) {
        VideoEntry mVideoEntry = mVideoList.get(position);
        holder.video_title.setText(mVideoEntry.getTitle());
        holder.thumbnail.setTag(mVideoEntry.getId());
        holder.thumbnail.initialize(Config.YOUTUBE_API_KEY, thumbnailListener);
        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(view, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mVideoList.size();
    }

    public class ViewHoder extends RecyclerView.ViewHolder {

        public TextView video_title;
        public YouTubeThumbnailView thumbnail;

        public ViewHoder(View v) {
            super(v);
            video_title = (TextView) v.findViewById(R.id.title);
            thumbnail = (YouTubeThumbnailView) v.findViewById(R.id.thumbnail);
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int pos);
    }

    public void SetOnItemClickListener(final OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    // load thumbnail image
    private final class ThumbnailListener implements
            YouTubeThumbnailView.OnInitializedListener,
            YouTubeThumbnailLoader.OnThumbnailLoadedListener {

        @Override
        public void onInitializationSuccess(YouTubeThumbnailView view, YouTubeThumbnailLoader loader) {
            loader.setOnThumbnailLoadedListener(this);
            thumbnailViewToLoaderMap.put(view, loader);
//            view.setImageResource(R.drawable.loading_thumbnail);
            view.setImageResource(R.color.colorloadingThumbnail);

            String videoId = (String) view.getTag();
            loader.setVideo(videoId);
        }

        @Override
        public void onInitializationFailure(
                YouTubeThumbnailView view, YouTubeInitializationResult loader) {
            view.setImageResource(R.mipmap.no_thumbnail);
        }

        @Override
        public void onThumbnailLoaded(YouTubeThumbnailView view, String videoId) {
        }

        @Override
        public void onThumbnailError(YouTubeThumbnailView view, YouTubeThumbnailLoader.ErrorReason errorReason) {
            view.setImageResource(R.mipmap.no_thumbnail);
        }

    }


}