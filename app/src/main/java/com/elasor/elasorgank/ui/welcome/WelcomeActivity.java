package com.elasor.elasorgank.ui.welcome;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.elasor.elasorgank.R;
import com.elasor.elasorgank.base.BaseActivity;
import com.elasor.elasorgank.ui.main.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Elasor
 */
public class WelcomeActivity extends BaseActivity {

    @BindView(R.id.tv_welcome_name)
    TextView tvWelcomeName;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

        }
    };
    private Runnable runnable;

    @Override
    protected int layoutResId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initControls() {
        ButterKnife.bind(this);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_welcome_logo);
        tvWelcomeName.startAnimation(animation);

        runnable = new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivityWithAnimation(intent);
                finish();
            }
        };
        handler.postDelayed(runnable, 1500);
    }

    @Override
    protected void initControlListener() {

    }

    @Override
    public void onBackPressed() {
        handler.removeCallbacks(runnable);
        super.onBackPressed();
    }
}
