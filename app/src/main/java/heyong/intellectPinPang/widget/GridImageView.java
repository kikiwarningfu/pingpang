package heyong.intellectPinPang.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import heyong.intellectPinPang.R;


/**
 * Created by Administrator on 2017/8/28 0028.
 */

public class GridImageView extends NoScrollGridView {

    private static final String TAG = GridImageView.class.getSimpleName();
    private UpLoadAdapter adapter;

    private ImageAddClickListener imageAddClickListener;

    private ImageDelClickListener imageDelClickListener;

    private ImageClickListener imageClickListener;

    public GridImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    List<String> images = new ArrayList<>();

    private int maxImageCount = 9;

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        //上传图片
        adapter = new UpLoadAdapter(R.layout.image_upload);
        adapter.addData("");
        this.setLayoutManager(new GridLayoutManager(getContext(), 3));
        this.addItemDecoration(new ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.right = 10;
                outRect.bottom = 10;
            }
        });
        this.setAdapter(adapter);

    }

    public List<String> getImages() {
        return images;
    }


    public List<String> getDisplayList() {
        List<String> list = new ArrayList<>();
        Log.e(TAG, "getDisplayList: " + images.size());
        for (int i = 0; i < images.size(); i++) {
            list.add(images.get(i));
        }
        Log.e(TAG, "getDisplayList: size====="+list.size() );
        if (images.size() < maxImageCount) {
            list.add("");
        }
        return list;
    }


    public void setDisplayList(List<String> images) {
        this.images.addAll(images);
        adapter.refresh();
    }

    public void resetImages(int position) {
        images.remove(position);
//        images.addAll(imagess);
        adapter.refresh();
    }

    public void setImages(List<String> images) {
        this.images.addAll(images);
        adapter.refresh();
    }

    private class UpLoadAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public UpLoadAdapter(int layoutResId) {
            super(layoutResId);
        }


        @Override
        protected void convert(BaseViewHolder helper, String item) {
            int position = helper.getPosition();
            ImageView imageView = helper.getView(R.id.iv_item);
            ImageView ivDelete = helper.getView(R.id.iv_delete);

            if (isUploadImage(position)) {
                ivDelete.setVisibility(View.GONE);
                Glide.with(getContext()).load(R.drawable.hand_tianjia).into(imageView);
                imageView.setOnClickListener(v -> {
                    if (imageAddClickListener != null) {
                        imageAddClickListener.onClick();
                    }
                });
            } else {
                imageView.setOnClickListener(v -> {
                    if (imageClickListener != null) {
                        imageClickListener.onImageClick(item, helper.getAdapterPosition());
                    }
                });
                ivDelete.setVisibility(View.GONE);
                Glide.with(getContext()).load(item).into(imageView);

                ivDelete.setOnClickListener(v -> {
                    images.remove(item);
                    if (imageDelClickListener != null) {
                        imageDelClickListener.onDelCLick(item, helper.getAdapterPosition());
                    }
                    refresh();
                });
            }
        }

        public boolean isUploadImage(int position) {

            return position == adapter.getData().size() - 1 && images.size() < maxImageCount;
        }

        public void refresh() {
            List<String> displayList = getDisplayList();
            setNewData(displayList);
        }

    }

    public void setMaxImageCount(int maxImageCount) {
        this.maxImageCount = maxImageCount;
    }

    public void setImageAddClickListener(ImageAddClickListener imageAddClickListener) {
        this.imageAddClickListener = imageAddClickListener;
    }

    public void setImageDelClickListener(ImageDelClickListener imageDelClickListener) {
        this.imageDelClickListener = imageDelClickListener;
    }

    public void setImageClickListener(ImageClickListener imageClickListener) {
        this.imageClickListener = imageClickListener;
    }

    public interface ImageAddClickListener {
        void onClick();
    }

    public interface ImageDelClickListener {
        void onDelCLick(String item, int position);
    }

    public interface ImageClickListener {
        void onImageClick(String item, int position);
    }
}