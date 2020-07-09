package com.example.wms.views.adaptors;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.example.wms.R;
import com.example.wms.views.application.ApplicationWMS;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class BindingAdapters {


    @BindingAdapter(value = {"SetImageItemOfWast"})
    public static void SetImageItemOfWast(ImageView imageView, String url) {//______________________ SetImageItemOfWast

        ImageLoader imageLoader = ApplicationWMS
                .getApplicationWMS(imageView.getContext())
                .getImageLoaderComponent()
                .getImageLoader();


        imageLoader.displayImage(url, imageView, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                imageView.setImageResource(R.drawable.wmslogo);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                imageView.setImageResource(R.drawable.wmslogo);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                if (loadedImage == null)
                    imageView.setImageResource(R.drawable.wmslogo);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                imageView.setImageResource(R.drawable.wmslogo);
            }
        });

    }//_____________________________________________________________________________________________ SetImageItemOfWast





    @BindingAdapter(value = {"SetCountItemsWasteList"})
    public static void SetCountItemsWasteList(TextView textView, Integer count) {//_________________ SetCountItemsWasteList
        StringBuilder builder = new StringBuilder();
        builder.append(count);
        builder.append(" ");
        builder.append(textView.getContext().getResources().getString(R.string.KGr));
        textView.setText(builder.toString());
    }//_____________________________________________________________________________________________ SetCountItemsWasteLis


    @BindingAdapter(value = {"SetBoothAuthor"})
    public static void SetBoothAuthor(TextView textView, String author) {//_________________ SetCountItemsWasteList
        StringBuilder builder = new StringBuilder();
        builder.append(textView.getContext().getString(R.string.BoothAuthor));
        builder.append(" : ");
        builder.append(author);
        textView.setText(builder.toString());
    }//_____________________________________________________________________________________________ SetCountItemsWasteLis


}
