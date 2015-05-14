package com.example.root.musicNav;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.root.main.alarmandmusic.R;
import com.example.root.otherComponent.DensityUtil;

/**
 * Created by zhanglei on 15-4-29.
 * Email: zhangleicoding@163.com
 */
public class MusicNavLayout {
    private RelativeLayout mainActivity;

    private Context context;
    private MusicService musicService;

    private boolean isPlay = false;

    private LayoutInflater mInflater;

    private MusicNavView musicNavView;

    private Button btnMusicNavAni; // enable music nav
    private Button btnNextMusic;
    private Button btnLastMusic;
    private Button btnPlayOrStopMusic;


    public MusicNavLayout(Context context, RelativeLayout mainActivity) {
        this.context = context;
        this.mainActivity = mainActivity;
        init();
    }

    private void init() {
        mInflater = LayoutInflater.from(context);
        RelativeLayout musicNavLayout = (RelativeLayout) mInflater.inflate(R.layout.music_nav, null);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

        musicNavView = (MusicNavView)musicNavLayout.findViewById(R.id.music_nav_animate);

        layoutParams.width = DensityUtil.dptopx(context,100f);
        layoutParams.height = DensityUtil.dptopx(context,100f);

        mainActivity.addView(musicNavLayout, layoutParams);

        btnNextMusic = (Button)musicNavLayout.findViewById(R.id.music_nav_next_music);
        btnLastMusic = (Button)musicNavLayout.findViewById(R.id.music_nav_last_music);
        btnPlayOrStopMusic = (Button)musicNavLayout.findViewById(R.id.music_nav_stop_start);
        btnMusicNavAni = (Button)musicNavLayout.findViewById(R.id.music_nav_enable_animation);


        if (!musicNavView.getExpand()) {
            setMusicNavVisible(View.INVISIBLE);
        }



        btnMusicNavAni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonAnimator(btnMusicNavAni, 200);

                musicNavView.clickButton();


                if (!musicNavView.getExpand()) {
                    setMusicNavVisible(View.VISIBLE);
                    buttonAnimatorExp(btnLastMusic, 400);
                    buttonAnimatorExp(btnNextMusic, 400);
                    buttonAnimatorExp(btnPlayOrStopMusic, 400);
                } else {
                    buttonAnimatorFolder(btnLastMusic, 400);
                    buttonAnimatorFolder(btnNextMusic, 400);
                    buttonAnimatorFolder(btnPlayOrStopMusic, 400);
                    setMusicNavVisible(View.INVISIBLE);
                }


            }
        });



    }

    public void setMusicService(MusicService musicService) {
        this.musicService = musicService;
    }

    public boolean addListeners() {
        boolean result = true;
        if (musicService==null) {
            result = false;
            return result;
        }
        btnLastMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(MainActivity.this, "Last music", Toast.LENGTH_LONG).show();
                musicService.lastMusic();
            }
        });

        btnNextMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(MainActivity.this, "Next music", Toast.LENGTH_LONG).show();
                musicService.nextMusic();

            }
        });

        btnPlayOrStopMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this, "Play or Stop", Toast.LENGTH_LONG).show();
                if (isPlay) {
                    musicService.stopMusic();
                } else {
                    musicService.playMusic();
                }
                isPlay = !isPlay;
            }
        });



        return result;
    }

    // music nav start
    private void setMusicNavVisible(int visible) {
        btnNextMusic.setVisibility(visible);
        btnLastMusic.setVisibility(visible);
        btnPlayOrStopMusic.setVisibility(visible);

    }


    /**
     * Button from 0 to 1 size as original
     * @param btn
     * @param duration
     */

    private void buttonAnimatorExp(final Button btn, final long duration) {
        ObjectAnimator btnAimatorExpX = ObjectAnimator.ofFloat(btn, "scaleX", 0f, 1f);
        btnAimatorExpX.setDuration(duration);
        ObjectAnimator btnAnimatorExpY = ObjectAnimator.ofFloat(btn, "scaleY", 0f, 1f);
        btnAnimatorExpY.setDuration(duration);

        AnimatorSet btnAnimatorExp = new AnimatorSet();
        btnAnimatorExp.play(btnAimatorExpX).with(btnAnimatorExpY);
        btnAnimatorExp.start();
    }


    private void buttonAnimatorFolder(final Button btn, final long duration) {
        ObjectAnimator btnAimatorExpX = ObjectAnimator.ofFloat(btn, "scaleX", 1f, 0f);
        btnAimatorExpX.setDuration(duration);
        ObjectAnimator btnAnimatorExpY = ObjectAnimator.ofFloat(btn, "scaleY", 1f, 0f);
        btnAnimatorExpY.setDuration(duration);

        AnimatorSet btnAnimatorExp = new AnimatorSet();
        btnAnimatorExp.play(btnAimatorExpX).with(btnAnimatorExpY);
        btnAnimatorExp.start();
    }


    /**
     * Animator from 1 size as or orginal to 0 and from 0 to 1
     * @param btn
     */
    private void buttonAnimator(final Button btn, final long duration) {
        ObjectAnimator btnAimatorX = ObjectAnimator.ofFloat(btn, "scaleX", 1f, 0f);
        btnAimatorX.setDuration(duration);
        ObjectAnimator btnAnimatorY = ObjectAnimator.ofFloat(btn, "scaleY", 1f, 0f);
        btnAnimatorY.setDuration(duration);
        AnimatorSet btnAnimatorFolder = new AnimatorSet();
        btnAnimatorFolder.play(btnAimatorX).with(btnAnimatorY);

        btnAnimatorFolder.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

                ObjectAnimator btnAimatorExpX = ObjectAnimator.ofFloat(btn, "scaleX", 0f, 1f);
                btnAimatorExpX.setDuration(duration);
                ObjectAnimator btnAnimatorExpY = ObjectAnimator.ofFloat(btn, "scaleY", 0f, 1f);
                btnAnimatorExpY.setDuration(duration);

                AnimatorSet btnAnimatorExp = new AnimatorSet();
                btnAnimatorExp.play(btnAimatorExpX).with(btnAnimatorExpY);
                btnAnimatorExp.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });





        btnAnimatorFolder.start();


    }
}
