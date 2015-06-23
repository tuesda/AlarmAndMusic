package com.example.root.alarms;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Vibrator;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.example.root.alarmModel.AlarmItem;
import com.example.root.alarmModel.Alarms;
import com.example.root.main.alarmandmusic.Log;
import com.example.root.main.alarmandmusic.R;

/**
 * Created by zhanglei on 15/6/22.
 */
public class AlarmService extends Service {

    /** Play alarm up to 10 minutes before silencing */
    private static final int ALARM_TIMEOUT_SECONDS = 10 * 60;

    private boolean mPlaying = false;
    private Vibrator mVibrator;
    private MediaPlayer mMediaPlayer;


    private TelephonyManager mTelephonyManager;
    private int mInitialCallState;

    private static final long[] sVibratePattern = new long[] { 500, 500 };

    // Internal messages
    private static final int KILLER = 1000;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case KILLER:
                    if (Log.LOGV) {
                        Log.v("*********** Alarm killer triggered ***********");
                    }
                    stopSelf();
                    break;
            }
        }
    };

    private PhoneStateListener mPhoneStateListener = new PhoneStateListener() {
        @Override
        public void onCallStateChanged(int state, String ignored) {
            // The user might already be in a call when the alarm fires. When
            // we register onCallStateChanged, we get the initial in-call state
            // which kills the alarm. Check against the initial call state so
            // we don't kill the alarm during a call.
            if (state != TelephonyManager.CALL_STATE_IDLE
                    && state != mInitialCallState) {
                stopSelf();
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        Log.v("operation: in AlarmService.onCreate()");
        mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Listen for incoming calls to kill the alarm.
        mTelephonyManager =
                (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        mTelephonyManager.listen(
                mPhoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
        AlarmAlertWakeLock.acquireCpuWakeLock(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stop();
        mTelephonyManager.listen(mPhoneStateListener, 0);
        AlarmAlertWakeLock.releaseCpuLock();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.v("operation: in AlarmService.onStartCommand()");
        // No intent, tell the system not to restart us.
        if (intent == null) {
            stopSelf();
            return START_NOT_STICKY;
        }

        int alarmId = intent.getIntExtra("alarm_id", -1);

        final AlarmItem alarm = Alarms.getAlarmWithId(this, alarmId);


        if (alarm == null) {
            Log.v("AlarmKlaxon failed to parse the alarm from the intent");
            stopSelf();
            return START_NOT_STICKY;
        }



        play(alarm);


        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


//    private void sendKillBroadcast(AlarmItem alarm) {
//        long millis = System.currentTimeMillis() - mStartTime;
//        int minutes = (int) Math.round(millis / 60000.0);
//        Intent alarmKilled = new Intent(Alarms.ALARM_KILLED);
//        alarmKilled.putExtra(Alarms.ALARM_INTENT_EXTRA, alarm);
//        alarmKilled.putExtra(Alarms.ALARM_KILLED_TIMEOUT, minutes);
//        sendBroadcast(alarmKilled);
//    }

    // Volume suggested by media team for in-call alarms.
    private static final float IN_CALL_VOLUME = 0.125f;

    private void play(AlarmItem alarmItem) {
        stop();

        if (Log.LOGV) {
            Log.v("AlarmService.play() " + alarmItem.getId() + " alert " + alarmItem.getAlert());
        }

        if (!alarmItem.getAlert().equals("silent")) {
            String alertString = alarmItem.getAlert();
            Uri alert;
            if (alertString.equals("") || alertString==null || alertString.equals("default")) {
                alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
                if (Log.LOGV) {
                    Log.v("Using default alarm: " + alert.toString());
                }
            } else {
                alert = Uri.parse(alertString);
            }

            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                    Log.e("Error occured while playing audio.");
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mMediaPlayer = null;
                    return true;
                }
            });

            try {
                // Check if we are in a call. If we are, use the in-call alarm
                // resource at a low volume to not dirupt the call
                if (mTelephonyManager.getCallState() != TelephonyManager.CALL_STATE_IDLE) {
                    Log.v("Using the in-call alarm");
                    mMediaPlayer.setVolume(IN_CALL_VOLUME, IN_CALL_VOLUME);
                    setDataSourceFromResource(getResources(), mMediaPlayer, R.raw.in_call_alarm);
                } else {
                    mMediaPlayer.setDataSource(this, alert);
                }
                startAlarm(mMediaPlayer);
            } catch (Exception ex) {
                Log.v("Using fallback ringtone");
                try {
                    mMediaPlayer.reset();
                    setDataSourceFromResource(getResources(), mMediaPlayer, R.raw.in_call_alarm);
                    startAlarm(mMediaPlayer);
                } catch (Exception ex2) {
                    Log.e("Failed to play back fallback ringtone", ex2);
                }
            }
        }

        if (alarmItem.getVibrate() == 1) {
            mVibrator.vibrate(sVibratePattern, 0);
        } else {
            mVibrator.cancel();
        }

        enableKiller(alarmItem);
        mPlaying = true;
    }

    private void startAlarm(MediaPlayer player) throws java.io.IOException, IllegalArgumentException
                                , IllegalStateException {
        final AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        // do not play alarms if stream volume is 0
        // (typically because ringer mode is silent).
        if (audioManager.getStreamVolume(AudioManager.STREAM_ALARM) != 0) {
            player.setAudioStreamType(AudioManager.STREAM_ALARM);
            player.setLooping(true);
            player.prepare();
            player.start();
        }
    }

    private void stop() {
        if (Log.LOGV) Log.v("AlarmService.stop()");
        if (mPlaying) {
            mPlaying = false;

            if (mMediaPlayer != null) {
                mMediaPlayer.stop();
                mMediaPlayer.release();
                mMediaPlayer = null;
            }

            mVibrator.cancel();
        }
        disableKiller();
    }

    private void setDataSourceFromResource (Resources resources,
                                            MediaPlayer player, int res) throws  java.io.IOException {
        AssetFileDescriptor afd = resources.openRawResourceFd(res);
        if (afd != null) {
            player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            afd.close();
        }
    }


    /**
     * Kills alarm audio after ALARM_TIMEOUT_SECONDS, so the alarm
     * won't run all day.
     *
     * This just cancels the audio, but leaves the notification
     * popped, so the user will know that the alarm tripped.
     */
    private void enableKiller(AlarmItem alarm) {
        mHandler.sendMessageDelayed(mHandler.obtainMessage(KILLER, alarm),
                1000 * ALARM_TIMEOUT_SECONDS);
    }

    private void disableKiller() {
        mHandler.removeMessages(KILLER);
    }
}

