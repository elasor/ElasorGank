package com.elasor.elasorgank.ui.content.image;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.elasor.elasorgank.R;
import com.elasor.elasorgank.base.BaseActivity;
import com.elasor.elasorgank.bean.CommonListBean;
import com.elasor.elasorgank.interfaces.IMenuMore;
import com.elasor.elasorgank.view.popup.ContentMenuPopupWindow;
import com.lify.elasor.http.ElasorHttp;
import com.lify.elasor.io.ElasorIO;
import com.lify.elasor.msg.ElasorLog;
import com.lify.elasor.msg.ElasorToast;
import com.lify.elasor.secret.ElasorSecret;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ImageContentActivity extends BaseActivity {

    @BindView(R.id.iv_content_back)
    ImageView ivContentBack;
    @BindView(R.id.iv_content_more)
    ImageView ivContentMore;
    @BindView(R.id.iv_content_download)
    ImageView ivContentDownload;
    @BindView(R.id.iv_content_image)
    ImageView ivContentImage;
    @BindView(R.id.activity_image_content)
    LinearLayout activityImageContent;
    @BindView(R.id.tv_content_title)
    TextView tvContentTitle;
    private CommonListBean bean;
    private String url;
    private String desc;
    private InputStream is;

    private boolean isShare = false;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String obj = msg.obj.toString();
            switch (msg.what){
                case 0:
                    showToast(obj);
                    break;
                case 1:
                    showToast(obj);
                    if(isShare){
                        sharePicture();
                    }
                    break;
            }
        }
    };

    @Override
    protected void initActivity() {

        super.initActivity();
    }

    @Override
    protected int layoutResId() {
        return R.layout.activity_image_content;
    }

    @Override
    protected void initControls() {
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        ViewCompat.setTransitionName(ivContentImage, "image");
        ViewCompat.setTransitionName(tvContentTitle, "title");
    }

    @Override
    protected void initControlListener() {
        ivContentDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isShare = false;
                downloadPicture();
            }
        });
    }

    @OnClick(R.id.iv_content_back)
    public void onBack() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();
        } else {
            finishWithAnimation();
        }
    }

    @OnClick(R.id.iv_content_more)
    public void showMore() {
        List<String> list = new ArrayList<>();
        list.add("复制链接");
        list.add("分享图片");

        final ContentMenuPopupWindow pw = new ContentMenuPopupWindow(this, ivContentMore, list);

        pw.setMenuMore(new IMenuMore() {

            @Override
            public void copyUrl() {
                //获取剪贴板管理器：
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                // 创建普通字符型ClipData
                ClipData mClipData = ClipData.newPlainText("Label", url);
                // 将ClipData内容放到系统剪贴板里。
                cm.setPrimaryClip(mClipData);
                ElasorToast.make(ImageContentActivity.this, "已复制到剪贴板", Toast.LENGTH_SHORT);
                pw.dismiss();
            }

            @Override
            public void sharePicture() {
                pw.dismiss();
                isShare = true;

                if (isDownloaded()) {
                    ImageContentActivity.this.sharePicture();
                } else {
                    downloadPicture();
                }
            }
        });
    }

    private void sharePicture() {
        Intent textIntent = new Intent(Intent.ACTION_SEND);
        textIntent.setType("image/*");
        textIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + File.separator + ElasorSecret.md5(url)+url.substring(url.lastIndexOf(".")))));
        startActivity(Intent.createChooser(textIntent, "分享"));
    }

    public void downloadPicture() {

        if (isDownloaded()) {
            showToast("图片已下载");
            return;
        }

        ElasorToast.make(this, "开始下载图片", Toast.LENGTH_SHORT);
        ElasorHttp.get(url, new Callback() {
            @Override
            public void onFailure(@android.support.annotation.NonNull Call call, @android.support.annotation.NonNull IOException e) {
                Message msg = handler.obtainMessage();
                msg.what = 0;
                msg.obj = "下载图片失败:"+e.getMessage();
                msg.sendToTarget();
            }

            @Override
            public void onResponse(@android.support.annotation.NonNull Call call, @android.support.annotation.NonNull Response response) throws IOException {
                if (!response.isSuccessful()) {
                    Message msg = handler.obtainMessage();
                    msg.what = 0;
                    msg.obj = "下载图片失败:"+response.code();
                    msg.sendToTarget();
                    return;
                }

                is = response.body().byteStream();

                if(ActivityCompat.checkSelfPermission(ImageContentActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(ImageContentActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                }else{
                    writePicture(is);
                }
            }
        });
    }

    private void writePicture(InputStream is) {

        if(null == is){
            Message msg = handler.obtainMessage();
            msg.what = 0;
            msg.obj = "图片下载失败:字节流为空";
            msg.sendToTarget();
            return;
        }

        File file = new File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + File.separator + ElasorSecret.md5(url)+url.substring(url.lastIndexOf(".")));
        ElasorIO.writeFile(file, is, new ElasorIO.OnWritingListener() {
            @Override
            public void onWriting(int currentLength, int totalLength) {
                ElasorLog.e("currentLength/totalLength = "+currentLength+"/"+totalLength);
                if (currentLength == totalLength) {
                    Message msg = handler.obtainMessage();
                    msg.what = 1;
                    msg.obj = "图片下载成功";
                    msg.sendToTarget();
                }
            }
        });
    }

    private boolean isDownloaded() {
        File file;
        try {
            file = new File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + File.separator + ElasorSecret.md5(url)+url.substring(url.lastIndexOf(".")));
            return file.exists() && file.isFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        onBack();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    writePicture(is);
                }else{
                    ElasorToast.make(this, "已取消授权", Toast.LENGTH_SHORT);
                }
                break;
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
        Glide.with(this).load(url).into(ivContentImage);
    }
}
