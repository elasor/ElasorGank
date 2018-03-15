package com.elasor.elasorgank.ui.main.common;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.elasor.elasorgank.Constant;
import com.elasor.elasorgank.R;
import com.elasor.elasorgank.base.BaseActivity;
import com.elasor.elasorgank.bean.CommonListBean;
import com.elasor.elasorgank.ui.content.image.ImageContentActivity;
import com.elasor.elasorgank.ui.content.web.WebContentActivity;
import com.elasor.elasorgank.util.TimeUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Elasor
 */
public class CommonAdapter extends RecyclerView.Adapter<CommonAdapter.Holder> {

    private static final int TYPE_NO_PICTURE = 0;
    private static final int TYPE_WITH_PICTURE = 1;
    private static final int TYPE_OTHER = 2;

    private Context mContext;
    private List<CommonListBean> mList;

    public CommonAdapter(Context mContext, List<CommonListBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        CommonListBean commonListBean = mList.get(position);
        String type = commonListBean.getType();

//        if (type.equals(Constant.Other.CHANNEL_ANDROID) || type.equals(Constant.Other.CHANNEL_IOS)
//                || type.equals(Constant.Other.CHANNEL_WELFARE) || type.equals(Constant.Other.CHANNEL_FRONT)
//                || type.equals(Constant.Other.CHANNEL_VIDEO) || type.equals(Constant.Other.CHANNEL_EXPAND)
//                || type.equals(Constant.Other.CHANNEL_RECOMMEND) || type.equals(Constant.Other.CHANNEL_APP)) {
//            List<String> images = commonListBean.getImages();
//            return (null == images || images.isEmpty()) ? TYPE_NO_PICTURE : TYPE_WITH_PICTURE;
//        }

        if(type.equals(Constant.Other.CHANNEL_WELFARE)){
            return TYPE_WITH_PICTURE;
        }

        return TYPE_NO_PICTURE;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_NO_PICTURE:
                View v1 = LayoutInflater.from(mContext).inflate(R.layout.item_common_type_no_picture, parent, false);
                return new NonePictureHolder(v1);
            case TYPE_WITH_PICTURE:
                View v2 = LayoutInflater.from(mContext).inflate(R.layout.item_common_type_with_picture, parent, false);
                return new PictureHolder(v2);
            case TYPE_OTHER:
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        if (holder instanceof NonePictureHolder) {
            nonePictureHolder((NonePictureHolder) holder, position);
        } else if (holder instanceof PictureHolder) {
            pictureHolder((PictureHolder) holder, position);
        }
    }

    private void nonePictureHolder(final NonePictureHolder holder, int position) {
        final CommonListBean commonListBean = mList.get(position);
        String desc = commonListBean.getDesc();
        String who = commonListBean.getWho();
        String source = commonListBean.getSource();
        String publishedAt = commonListBean.getPublishedAt();
        publishedAt = publishedAt.split("T")[0];
        int daysAgo = TimeUtil.daysAgo(publishedAt);

//        String createdAt = commonListBean.getCreatedAt();

        holder.tvItemTitle.setText(desc);
        holder.tvItemSource.setText(source);
        holder.tvItemAuthor.setText(TextUtils.isEmpty(who)?"":"via."+who);
        holder.tvItemTime.setText(daysAgo==0?"今天":daysAgo+"天前");

        switch (source){
            case "web":
                holder.tvItemSource.setBackgroundResource(R.drawable.shape_main_item_source_grey900);
                break;
            case "chrome":
                holder.tvItemSource.setBackgroundResource(R.drawable.shape_main_item_source_green300);
                break;
            default:
                holder.tvItemSource.setBackgroundResource(R.drawable.shape_main_item_source_amber500);
                break;
        }


        holder.llItemRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().postSticky(commonListBean);
                Intent intent = new Intent(mContext, WebContentActivity.class);
//                intent.putExtra("bean", commonListBean);
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){

                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(((BaseActivity) mContext), Pair.create((View)holder.tvItemTitle, "title"));

                    ActivityCompat.startActivity(mContext, intent, options.toBundle());
                }else{
                    ((BaseActivity) mContext).startActivityWithAnimation(intent);
                }
            }
        });
    }

    private void pictureHolder(final PictureHolder holder, int position) {
        final CommonListBean commonListBean = mList.get(position);
        String desc = commonListBean.getDesc();
        String who = commonListBean.getWho();
        String source = commonListBean.getSource();
        String url = commonListBean.getUrl();
        String publishedAt = commonListBean.getPublishedAt();
        publishedAt = publishedAt.split("T")[0];
        int daysAgo = TimeUtil.daysAgo(publishedAt);

//        String createdAt = commonListBean.getCreatedAt();

        holder.tvItemTitle.setText(desc);
        holder.tvItemSource.setText(source);
        holder.tvItemAuthor.setText(TextUtils.isEmpty(who)?"":"via."+who);
        holder.tvItemTime.setText(daysAgo==0?"今天":daysAgo+"天前");

        switch (source){
            case "web":
                holder.tvItemSource.setBackgroundResource(R.drawable.shape_main_item_source_grey900);
                break;
            case "chrome":
                holder.tvItemSource.setBackgroundResource(R.drawable.shape_main_item_source_green300);
                break;
            default:
                holder.tvItemSource.setBackgroundResource(R.drawable.shape_main_item_source_amber500);
                break;
        }

        Glide.with(mContext).load(url).into(holder.ivItemImage);

        holder.llItemRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ImageContentActivity.class);
                intent.putExtra("bean", commonListBean);
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){

                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(((BaseActivity) mContext), Pair.create((View)holder.ivItemImage, "image"), Pair.create((View)holder.tvItemTitle, "title"));

                    ActivityCompat.startActivity(mContext, intent, options.toBundle());
                }else{
                    ((BaseActivity) mContext).startActivityWithAnimation(intent);
                }
            }
        });

        holder.ivItemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().postSticky(commonListBean);
                Intent intent = new Intent(mContext, ImageContentActivity.class);
//                intent.putExtra("bean", commonListBean);
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){

                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(((BaseActivity) mContext), Pair.create((View)holder.ivItemImage, "image"), Pair.create((View)holder.tvItemTitle, "title"));

                    ActivityCompat.startActivity(mContext, intent, options.toBundle());
                }else{
                    ((BaseActivity) mContext).startActivityWithAnimation(intent);
                }
            }
        });
    }

    public class NonePictureHolder extends Holder {

        @BindView(R.id.tv_item_title)
        TextView tvItemTitle;
        @BindView(R.id.tv_item_author)
        TextView tvItemAuthor;
        @BindView(R.id.tv_item_source)
        TextView tvItemSource;
        @BindView(R.id.tv_item_time)
        TextView tvItemTime;
        @BindView(R.id.ll_item_root)
        LinearLayout llItemRoot;

        public NonePictureHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public class PictureHolder extends Holder {

        @BindView(R.id.tv_item_title)
        TextView tvItemTitle;
        @BindView(R.id.iv_item_image)
        ImageView ivItemImage;
        @BindView(R.id.tv_item_author)
        TextView tvItemAuthor;
        @BindView(R.id.tv_item_source)
        TextView tvItemSource;
        @BindView(R.id.tv_item_time)
        TextView tvItemTime;
        @BindView(R.id.ll_item_root)
        LinearLayout llItemRoot;

        public PictureHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public class Holder extends RecyclerView.ViewHolder {
        public Holder(View view) {
            super(view);
        }
    }
}
