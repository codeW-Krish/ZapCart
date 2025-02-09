package com.example.zapcart.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;

import java.util.List;

public class ProductDetailsAdapter extends PagerAdapter {
    private Context context;
    private List<String> imageUrls;

    public ProductDetailsAdapter(Context context, List<String> imageUrls){
        this.context = context;
        this.imageUrls = imageUrls;
    }


    @Override
    public int getCount() {
        return imageUrls.size();  // Number of items in the ViewPager
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(context);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);  // For proper image fitting

        Glide.with(context)
                .load(imageUrls.get(position))
                .into(imageView);

        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
