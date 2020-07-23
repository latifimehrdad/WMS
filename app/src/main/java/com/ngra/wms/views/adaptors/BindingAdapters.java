package com.ngra.wms.views.adaptors;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.ngra.wms.R;
import com.ngra.wms.utility.ApplicationUtility;
import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.views.application.ApplicationWMS;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import params.com.stepview.StatusViewScroller;

public class BindingAdapters {


    @BindingAdapter(value = {"SetTicketDate"})
    public static void SetTicketDate(TextView textView, Date date) {//_______________________________ SetOrderDate

        ApplicationUtility component = ApplicationWMS
                .getApplicationWMS(textView.getContext())
                .getUtilityComponent()
                .getApplicationUtility();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.US);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(component.MiladiToJalali(date, "FullJalaliNumber"));
        stringBuilder.append(" - ");
        stringBuilder.append(simpleDateFormat.format(date));


        textView.setText(stringBuilder.toString());

    }//_____________________________________________________________________________________________ SetOrderDate




    @BindingAdapter(value = {"SetTicketStatus"})
    public static void SetTicketStatus(TextView textView, Byte deliveryType) {//____________________ SetTicketStatus

        Resources resources = textView.getContext().getResources();

        if (deliveryType.equals(StaticValues.TicketStatusNew)) {
            textView.setText(resources.getString(R.string.TicketStatusNew));
            textView.setTextColor(resources.getColor(R.color.Links));
            return;
        }

        if (deliveryType.equals(StaticValues.TicketStatusPending)) {
            textView.setText(resources.getString(R.string.TicketStatusPending));
            textView.setTextColor(resources.getColor(R.color.colorPrimaryDark));
            return;
        }

        if (deliveryType.equals(StaticValues.TicketStatusWaiting)) {
            textView.setText(resources.getString(R.string.TicketStatusWaiting));
            textView.setTextColor(resources.getColor(R.color.colorPrymeryVeryDark));
            return;
        }

        if (deliveryType.equals(StaticValues.TicketStatusAnswered)) {
            textView.setText(resources.getString(R.string.TicketStatusAnswered));
            textView.setTextColor(resources.getColor(R.color.mlCollectRight1));
            return;
        }

        if (deliveryType.equals(StaticValues.TicketStatusReferred)) {
            textView.setText(resources.getString(R.string.TicketStatusReferred));
            textView.setTextColor(resources.getColor(R.color.TicketStatusReferred));
            return;
        }

        if (deliveryType.equals(StaticValues.TicketStatusSolved)) {
            textView.setText(resources.getString(R.string.TicketStatusSolved));
            textView.setTextColor(resources.getColor(R.color.mlCollectLeft1));
            return;
        }

        if (deliveryType.equals(StaticValues.TicketStatusClosed)) {
            textView.setText(resources.getString(R.string.TicketStatusClosed));
            textView.setTextColor(resources.getColor(R.color.mlHeader));
            return;
        }

        if (deliveryType.equals(StaticValues.TicketStatusArchived)) {
            textView.setText(resources.getString(R.string.TicketStatusArchived));
            textView.setTextColor(resources.getColor(R.color.mlEdit));
        }


    }//_____________________________________________________________________________________________ SetTicketStatus




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
    public static void SetBoothAuthor(TextView textView, String author) {//_________________________ SetCountItemsWasteList
        StringBuilder builder = new StringBuilder();
        builder.append(textView.getContext().getString(R.string.BoothAuthor));
        builder.append(" : ");
        builder.append(author);
        textView.setText(builder.toString());
    }//_____________________________________________________________________________________________ SetCountItemsWasteLis



    @BindingAdapter(value = {"SetVisibleOrderCall"})
    public static void SetVisibleOrderCall(LinearLayout linearLayout, Byte deliveryType) {//________ SetVisibleOrderCall

        if (deliveryType.equals(StaticValues.RecyclingDeliveryTypeBooth))
            linearLayout.setVisibility(View.VISIBLE);
        else
            linearLayout.setVisibility(View.GONE);
    }//_____________________________________________________________________________________________ SetVisibleOrderCall



    @BindingAdapter(value = {"SetImageOrderType"})
    public static void SetImageOrderType(ImageView imageView, Byte deliveryType) {//________________ SetImageOrderType

        if (deliveryType.equals(StaticValues.RecyclingDeliveryTypeBooth))
            imageView.setImageDrawable(imageView.getContext().getResources().getDrawable(R.drawable.logobooth));
        else
            imageView.setImageDrawable(imageView.getContext().getResources().getDrawable(R.drawable.logocar));
    }//_____________________________________________________________________________________________ SetImageOrderType


    @BindingAdapter(value = {"SetOrderTotalAmount"})
    public static void SetOrderTotalAmount(TextView textView, float Amount) {//________________ SetOrderTotalAmount

        Integer am = Math.round(Amount);
        textView.setText(am.toString());

    }//_____________________________________________________________________________________________ SetOrderTotalAmount


    @BindingAdapter(value = {"SetOrderDate"})
    public static void SetOrderDate(TextView textView, Date date) {//_______________________________ SetOrderDate

        ApplicationUtility component = ApplicationWMS
                .getApplicationWMS(textView.getContext())
                .getUtilityComponent()
                .getApplicationUtility();

        String d = component.MiladiToJalali(date, "FullJalaliNumber");
        textView.setText(d);

    }//_____________________________________________________________________________________________ SetOrderDate



    @BindingAdapter(value = {"SetOrderTime"})
    public static void SetOrderTime(TextView textView, Date date) {//_______________________________ SetOrderTime

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.US);

        String d = simpleDateFormat.format(date);
        textView.setText(d);

    }//_____________________________________________________________________________________________ SetOrderTime


    @BindingAdapter(value = {"SetOrderStatus"})
    public static void SetOrderStatus(StatusViewScroller scroller, Byte status) {//_________________ SetOrderStatus

        scroller.getStatusView().setCurrentCount(status + 1);

    }//_____________________________________________________________________________________________ SetOrderStatus


    @BindingAdapter(value = {"SetOrderStatusTextView"})
    public static void SetOrderStatusTextView(TextView textView, Byte status) {//___________________ SetOrderStatusTextView

        String tag = (String) textView.getTag();

        if (status.equals(Byte.valueOf(tag)))
            textView.setBackground(textView.getContext().getResources().getDrawable(R.drawable.layout_border_black));
        else
            textView.setBackground(null);

    }//_____________________________________________________________________________________________ SetOrderStatusTextView



    @BindingAdapter(value = {"SetAmountItemsWasteList"})
    public static void SetAmountItemsWasteList(TextView textView, float count) {//_________________ SetCountItemsWasteList
        StringBuilder builder = new StringBuilder();
        builder.append(Math.round(count));
        builder.append(" ");
        builder.append(textView.getContext().getResources().getString(R.string.KGr));
        textView.setText(builder.toString());
    }//_____________________________________________________________________________________________ SetCountItemsWasteLis


}
