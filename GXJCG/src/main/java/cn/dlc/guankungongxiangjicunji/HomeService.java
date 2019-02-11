package cn.dlc.guankungongxiangjicunji;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.lang.reflect.Field;

public class HomeService extends Service {

    public static MediaPlayer mMediaPlayer;

    public static Context mContext;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mMediaPlayer = new MediaPlayer();
        this.mContext = this;
    }

    public static void playMedia(String name){
        try {
            Field idField = R.raw.class.getDeclaredField(name);
            int res = idField.getInt(idField);
            if(mMediaPlayer.isPlaying()){
                mMediaPlayer.stop();
                mMediaPlayer.release();
                mMediaPlayer = MediaPlayer.create(mContext,res);
            }else{
                mMediaPlayer=MediaPlayer.create(mContext, res);
            }
//            mMediaPlayer.setDataSource(idF);
//            mMediaPlayer.setDataSource(this, Uri.parse("android.resource://" + this.getPackageName() + "/raw/" + name));
            try {
//                mMediaPlayer.prepare();

            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
            mMediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
