package com.elasor.elasorgank.ui.content.web;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.elasor.elasorgank.R;
import com.elasor.elasorgank.base.BaseActivity;
import com.elasor.elasorgank.bean.CommonListBean;
import com.elasor.elasorgank.interfaces.IMenuMore;
import com.elasor.elasorgank.util.WebViewUtil;
import com.elasor.elasorgank.view.popup.ContentMenuPopupWindow;
import com.lify.elasor.msg.ElasorToast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WebContentActivity extends BaseActivity {

    @BindView(R.id.iv_content_back)
    ImageView ivContentBack;
    @BindView(R.id.iv_content_more)
    ImageView ivContentMore;
    @BindView(R.id.tv_content_title)
    TextView tvContentTitle;
    @BindView(R.id.pb_content_progress)
    ProgressBar pbContentProgress;
    @BindView(R.id.wv_content_content)
    WebView wvContentContent;
    @BindView(R.id.activity_web_content)
    LinearLayout activityWebContent;
    private String desc;
    private String url;

    @Override
    protected void initActivity() {

        super.initActivity();
    }

    @Override
    protected int layoutResId() {
        return R.layout.activity_web_content;
    }

    @Override
    protected void initControls() {
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        ViewCompat.setTransitionName(tvContentTitle, "title");

        WebSettings ws = wvContentContent.getSettings();
        ws.setJavaScriptEnabled(true);
        ws.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        ws.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        ws.setDisplayZoomControls(false);

        wvContentContent.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (url.contains("www.vmovier.com")) {
                    WebViewUtil.injectCSS(WebContentActivity.this,
                            WebContentActivity.this.wvContentContent, "vmovier.css");
                } else if (url.contains("video.weibo.com")) {
                    WebViewUtil.injectCSS(WebContentActivity.this,
                            WebContentActivity.this.wvContentContent, "weibo.css");
                } else if (url.contains("m.miaopai.com")) {
                    WebViewUtil.injectCSS(WebContentActivity.this,
                            WebContentActivity.this.wvContentContent, "miaopai.css");
                }
            }
        });

        wvContentContent.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                WebContentActivity.this.pbContentProgress.setProgress(progress);
                pbContentProgress.setProgress(progress);
                if (progress >= 80) {
                    WebContentActivity.this.pbContentProgress.setVisibility(View.GONE);
                } else {
                    WebContentActivity.this.pbContentProgress.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    @Override
    protected void initControlListener() {

    }

    @OnClick(R.id.iv_content_back)
    public void onBack(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            finishAfterTransition();
        }else{
            finishWithAnimation();
        }
    }

    @OnClick(R.id.iv_content_more)
    public void showMore(){
        List<String> list = new ArrayList<>();
        list.add("重新加载");
        list.add("复制链接");
        list.add("分享链接");

        final ContentMenuPopupWindow pw = new ContentMenuPopupWindow(this, ivContentMore, list);

        pw.setMenuMore(new IMenuMore() {
            @Override
            public void reload() {
                pw.dismiss();
                wvContentContent.reload();
            }

            @Override
            public void copyUrl() {
                //获取剪贴板管理器：
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                // 创建普通字符型ClipData
                ClipData mClipData = ClipData.newPlainText("Label", url);
                // 将ClipData内容放到系统剪贴板里。
                cm.setPrimaryClip(mClipData);
                ElasorToast.make(WebContentActivity.this, "已复制到剪贴板", Toast.LENGTH_SHORT);
                pw.dismiss();
            }

            @Override
            public void shareUrl() {
                pw.dismiss();
                Intent textIntent = new Intent(Intent.ACTION_SEND);
                textIntent.setType("text/plain");
                textIntent.putExtra(Intent.EXTRA_TEXT, desc + url);
                startActivity(Intent.createChooser(textIntent, "分享"));
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(wvContentContent.canGoBack()){
            wvContentContent.goBack();
        }else{
            onBack();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void requestData(CommonListBean commonListBean){
        desc = commonListBean.getDesc();

        url = commonListBean.getUrl();

        tvContentTitle.setText(desc);
        wvContentContent.loadUrl(url);
    }
}
