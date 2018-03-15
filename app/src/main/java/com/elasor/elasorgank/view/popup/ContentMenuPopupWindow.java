package com.elasor.elasorgank.view.popup;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.elasor.elasorgank.R;
import com.elasor.elasorgank.interfaces.IMenuMore;
import com.lify.elasor.msg.ElasorToast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Elasor
 */
public class ContentMenuPopupWindow {

    @BindView(R.id.rv_popup_content)
    RecyclerView rvPopupContent;
    private Context mContext;
    private List<String> mList;
    private final PopupWindow pw;
    private final MyAdapter adapter;

    public ContentMenuPopupWindow(Context context, View dropDownView, List<String> list) {
        this.mContext = context;
        this.mList = list;

        View view = View.inflate(context, R.layout.popup_content_menu_more, null);
        ButterKnife.bind(this, view);

        rvPopupContent.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        adapter = new MyAdapter();
        rvPopupContent.setAdapter(adapter);

        pw = new PopupWindow(view, mContext.getResources().getDisplayMetrics().widthPixels/2, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        pw.setBackgroundDrawable(new BitmapDrawable());
        pw.setOutsideTouchable(true);
        pw.showAsDropDown(dropDownView, 0, -dropDownView.getHeight());

    }

    public void setMenuMore(IMenuMore iMenuMore){
        adapter.setMenuMore(iMenuMore);
    }

    public void dismiss(){
        if(null != pw && pw.isShowing()){
            pw.dismiss();
        }
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.Holder> {

        private IMenuMore iMenuMore;

        public void setMenuMore(IMenuMore iMenuMore) {
            this.iMenuMore = iMenuMore;
        }

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_popup_menu_more, parent, false);
            return new Holder(view);
        }

        @Override
        public void onBindViewHolder(Holder holder, int position) {
            final String s = mList.get(position);
            holder.tvItemTitle.setText(s);

            holder.tvItemTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (s){
                        case "重新加载":
                            if(null != iMenuMore){
                                iMenuMore.reload();
                            }
                            break;
                        case "复制链接":
                            if(null != iMenuMore){
                                iMenuMore.copyUrl();
                            }
                            break;
                        case "分享链接":
                            if(null != iMenuMore){
                                iMenuMore.shareUrl();
                            }
                            break;
                        case "分享图片":
                            if(null != iMenuMore){
                                iMenuMore.sharePicture();
                            }
                            break;
                        default:
                            ElasorToast.make(mContext, s, Toast.LENGTH_SHORT);
                            break;
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        class Holder extends RecyclerView.ViewHolder {

            @BindView(R.id.tv_item_title)
            TextView tvItemTitle;

            Holder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }
        }
    }
}
