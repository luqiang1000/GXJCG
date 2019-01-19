package cn.dlc.guankungongxiangjicunji;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.IOException;
import java.lang.reflect.Field;

public class MyService extends Service {

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
            mMediaPlayer=MediaPlayer.create(mContext, res);
//            mMediaPlayer.setDataSource(idF);
//            mMediaPlayer.setDataSource(this, Uri.parse("android.resource://" + this.getPackageName() + "/raw/" + name));
            try {
                mMediaPlayer.prepare();
                if(mMediaPlayer.isPlaying()){
                    mMediaPlayer.stop();
                    mMediaPlayer.release();
                    mMediaPlayer = MediaPlayer.create(mContext,res);
                }
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mMediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
