package cn.dlc.guankungongxiangjicunji.main.activity;

import android.app.Activity;
import android.app.Presentation;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.MediaController;
import cn.dlc.commonlibrary.okgo.callback.Bean01Callback;
import cn.dlc.guankungongxiangjicunji.App;
import cn.dlc.guankungongxiangjicunji.R;
import cn.dlc.guankungongxiangjicunji.main.MainHttp;
import cn.dlc.guankungongxiangjicunji.main.bean.VideoBean;
import cn.dlc.guankungongxiangjicunji.main.widget.CustomerVideoView;
import com.danikula.videocache.HttpProxyCacheServer;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2018/7/5.
 */

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
public class SecondDisplay extends Presentation {

    public CustomerVideoView mVideo;
    private Long mTaskId;
    private Map<String, String> fileName = new HashMap<>();
    private List<String> videoPath = new ArrayList<>();
    String uri = Environment.getExternalStorageDirectory().getPath() + "/";
    private int payCount = 0;

    private MediaController mediaController;

    private Activity activity;

    private Handler mHandler = new Handler();

    private Timer timer;

    private TimerTask task;

    public SecondDisplay(Context outerContext, Display display) {
        super(outerContext, display);

    }

    public void setActivity(Activity activity){
        this.activity = activity;
    }

    public SecondDisplay(Context outerContext, Display display, Activity activity) {
        super(outerContext, display);
        this.activity = activity;

    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {

        activity.onTouchEvent(event);

        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(@NonNull MotionEvent ev) {
        activity.dispatchTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }


    public SecondDisplay(Context outerContext, Display display, int theme){
        super(outerContext, display,theme);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_layout);
        mVideo = findViewById(R.id.video);
        mVideo.setOnPreparedListener(mp -> mp.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING));
        mediaController = new MediaController(getContext());

        HttpProxyCacheServer proxy = App.getProxy(getContext());

        if(task != null){
            task.cancel();
        }
        if(timer != null){
            timer.cancel();
        }
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                MainHttp.get().videoList(new Bean01Callback<VideoBean>() {
                    @Override
                    public void onSuccess(VideoBean videoBean) {
                        //获取下载管理器
                        for (VideoBean.DataBean.ListBean listBean : videoBean.data.list) {
                            videoPath.add(listBean.down_url);
                            String path1 = listBean.down_url;
                            FileDownloader.getImpl().create(path1)
                                    .setPath(uri + path1.substring(path1.lastIndexOf("/")+1))
                                    .setListener(new FileDownloadListener() {
                                        @Override
                                        protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                                            Log.i("Jim","pending");
                                        }

                                        @Override
                                        protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                                            Log.i("Jim","progress");
                                        }

                                        @Override
                                        protected void completed(BaseDownloadTask task) {
                                            Log.i("Jim","completed");
                                        }

                                        @Override
                                        protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {

                                        }

                                        @Override
                                        protected void error(BaseDownloadTask task, Throwable e) {
                                            Log.i("Jim","error" + e.getMessage());
                                        }

                                        @Override
                                        protected void warn(BaseDownloadTask task) {
                                            Log.i("Jim","warn");
                                        }
                                    }).start();
                        }
                        if(videoPath.size() != 0){
                            String path = uri + videoPath.get(0).substring(videoPath.get(0).lastIndexOf("/")+1);
                            File file = new File(path);
                            if(file.exists()){
                                mVideo.setVideoPath(path);
                            }else{
                                mVideo.setVideoURI(Uri.parse(videoPath.get(0)));

                            }
                            mVideo.start();
                        }
                    }

                    @Override
                    public void onFailure(String message, Throwable tr) {

                    }
                });
            }
        };

        timer.scheduleAtFixedRate(task,0,3600 * 1000);



        mVideo.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if(videoPath.size() != 0){
                    payCount++;
                    if(payCount >= videoPath.size()){
                        payCount = 0;
                    }
                    String path = uri + videoPath.get(payCount).substring(videoPath.get(payCount).lastIndexOf("/")+1);
                    File file = new File(path);
                    if(file.exists()){
                        mVideo.setVideoPath(path);
                    }else{
                        mVideo.setVideoURI(Uri.parse(videoPath.get(0)));
                    }
                    mVideo.start();
                }
            }
        });

        mVideo.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(videoPath.size() != 0){
                            payCount = 0;
                            String path = uri + videoPath.get(payCount).substring(videoPath.get(payCount).lastIndexOf("/")+1);
                            File file = new File(path);
                            if(file.exists()){
                                mVideo.setVideoPath(path);
                            }else{
                                mVideo.setVideoURI(Uri.parse(videoPath.get(0)));
                            }
                            mVideo.start();
                        }

                    }
                },5000);

                return false;
            }
        });





    }

    @Override
    public boolean dispatchKeyEvent(@NonNull KeyEvent event) {
        return super.dispatchKeyEvent(event);
    }


    @Override
    public void dismiss() {
        super.dismiss();
        if(task != null)
            task.cancel();
        if(timer != null)
            timer.cancel();
    }
}
