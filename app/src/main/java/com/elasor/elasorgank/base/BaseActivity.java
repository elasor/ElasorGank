package com.elasor.elasorgank.base;

import android.content.Intent;

import com.elasor.elasorgank.R;
import com.lify.elasor.base.ElasorActivity;

/**
 * @author Elasor
 */
public abstract class BaseActivity extends ElasorActivity{

    @Override
    protected void initActivity() {
        super.initActivity();
        immersive();
    }

    public void startActivityWithAnimation(Intent intent){
        startActivity(intent);
        overridePendingTransition(R.anim.anim_activity_enter_right_left, R.anim.anim_activity_exit_right_left);
    }

    public void finishWithAnimation() {
        finish();
        overridePendingTransition(R.anim.anim_activity_enter_left_right, R.anim.anim_activity_exit_left_right);
    }
}