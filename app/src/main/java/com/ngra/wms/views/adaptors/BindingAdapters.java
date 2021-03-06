package com.ngra.wms.views.adaptors;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.ngra.wms.R;
import com.ngra.wms.models.MD_Booth;
import com.ngra.wms.models.MD_GregorianToSun;
import com.ngra.wms.models.MD_ScoreListItem;
import com.ngra.wms.models.MD_SpinnerItem;
import com.ngra.wms.utility.ApplicationUtility;
import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.views.application.ApplicationWMS;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import params.com.stepview.StatusViewScroller;

public class BindingAdapters {

    //______________________________________________________________________________________________ SetJalaliDateDelivery
    @BindingAdapter(value = {"setJalaliDateFrom", "setJalaliDateTo"})
    public static void setJalaliDateDelivery(TextView textView, Date dateFrom , Date dateTo) {

        ApplicationUtility component = ApplicationWMS
                .getApplicationWMS(textView.getContext())
                .getUtilityComponent()
                .getApplicationUtility();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.US);

        MD_GregorianToSun toSun = component.gregorianToSun(dateFrom);
        String builder = toSun.getFullStringSun() + " از ساعت " + simpleDateFormat.format(dateFrom) + " تا " + simpleDateFormat.format(dateTo);

        textView.setText(builder);

    }
    //______________________________________________________________________________________________ SetJalaliDateDelivery


    //______________________________________________________________________________________________ setMaterialSpinnerItem
    @BindingAdapter(value = "setMaterialSpinnerItem")
    public static void setMaterialSpinnerItem(MaterialSpinner spinner, List<MD_SpinnerItem> items) {
        if (items == null)
            return;
        String tag = spinner.getTag().toString();
        if (tag.equalsIgnoreCase("buildingType"))
            setMaterialSpinnerBuildingType(spinner, items);
        else if (tag.equalsIgnoreCase("buildingUses"))
            setMaterialSpinnerUses(spinner, items);

    }
    //______________________________________________________________________________________________ setMaterialSpinnerItem


    //______________________________________________________________________________________________ setGenderRadio
    @BindingAdapter(value = "setGenderRadio")
    public static void setGenderRadio(RadioButton radio, Integer value) {
        String tag = radio.getTag().toString();
        if (tag.equalsIgnoreCase("man")) {
            if (value == 1)
                radio.setChecked(true);
            else
                radio.setChecked(false);
        } else {
            if (value == 1)
                radio.setChecked(false);
            else
                radio.setChecked(true);
        }
    }
    //______________________________________________________________________________________________ setGenderRadio


    //______________________________________________________________________________________________ setTextToEditText
    @BindingAdapter(value = "setTextToEditText")
    public static void setTextToEditText(EditText editText, String value) {
        editText.setText(value);
    }
    //______________________________________________________________________________________________ setTextToEditText



    @BindingAdapter(value = "setAccountFundRequestsState")
    public static void setAccountFundRequestsState(TextView textView, Byte state) {
        Resources resources = textView.getContext().getResources();
        if (state.equals(StaticValues.AccountFundRequest)) {
            textView.setText(resources.getString(R.string.WaitForAccept));
            textView.setTextColor(resources.getColor(R.color.mlCollectRight1));
        } else if (state.equals(StaticValues.AccountFundRequestAccept)) {
            textView.setText(resources.getString(R.string.WasteAcceptByOperator));
            textView.setTextColor(resources.getColor(R.color.Links));
        } else if (state.equals(StaticValues.AccountFundRequestCanceled)) {
            textView.setText(resources.getString(R.string.CancelRequest));
            textView.setTextColor(resources.getColor(R.color.mlMenuBackTrans));
        } else if (state.equals(StaticValues.AccountFundRequestPaid)) {
            textView.setText(resources.getString(R.string.AccountFundRequestPaid));
            textView.setTextColor(resources.getColor(R.color.mlCollectLeft2));
        }

    }


    //______________________________________________________________________________________________ splitNumberOfValue
    @BindingAdapter(value = "splitNumberOfValue")
    public static void splitNumberOfValue(TextView textView, double amount) {
        ApplicationUtility component = ApplicationWMS
                .getApplicationWMS(textView.getContext())
                .getUtilityComponent()
                .getApplicationUtility();

        textView.setText(component.splitNumberOfAmount((long) amount));
    }
    //______________________________________________________________________________________________ splitNumberOfValue


    //______________________________________________________________________________________________ splitNumberOfAmount
    @BindingAdapter(value = "splitNumberOfAmount")
    public static void splitNumberOfAmount(TextView textView, double amount) {
        ApplicationUtility component = ApplicationWMS
                .getApplicationWMS(textView.getContext())
                .getUtilityComponent()
                .getApplicationUtility();

        textView.setText(component.splitNumberOfAmount((long) amount) + " " +
                textView.getContext().getResources().getString(R.string.Rial));
    }
    //______________________________________________________________________________________________ splitNumberOfAmount



    //______________________________________________________________________________________________ setOrderStatus
    @BindingAdapter(value = {"setOrderStatus"})
    public static void setOrderStatus(StatusViewScroller scroller, Byte status) {

        scroller.getStatusView().setCurrentCount(status + 1);

    }
    //______________________________________________________________________________________________ setOrderStatus




    @BindingAdapter(value = {"setOrderStatusTextView"})
    public static void setOrderStatusTextView(TextView textView, Byte status) {

        String tag = (String) textView.getTag();

        if (status.equals(Byte.valueOf(tag)))
            textView.setBackground(textView.getContext().getResources().getDrawable(R.drawable.layout_border_black));
        else
            textView.setBackground(null);

    }



    @BindingAdapter(value = {"setBoothName"})
    public static void setBoothName(TextView textView, MD_Booth md_booth) {
        if (md_booth == null)
            textView.setVisibility(View.GONE);
        else {
            textView.setText(md_booth.getName());
            textView.setVisibility(View.VISIBLE);
        }
    }

    @BindingAdapter(value = {"SetWalletScore"})
    public static void SetWalletScore(TextView textView, MD_ScoreListItem item) {

        Context context = textView.getContext();
        String v = String.format("%.0f", item.getValue());

        StringBuilder builder = new StringBuilder();
        Integer amount = (int) item.getAmount() / 1000;
        builder.append("هر");
        builder.append(" ");
        builder.append(amount.toString());
        builder.append(" ");
        builder.append(context.getResources().getString(R.string.AmountK));
        builder.append(" ");
        builder.append(v);
        builder.append(" ");
        builder.append(context.getResources().getString(R.string.Score));

        textView.setText(builder.toString());

    }


    @BindingAdapter(value = {"SetScoreConfigItem"})
    public static void SetScoreConfigItem(TextView textView, MD_ScoreListItem item) {

        Context context = textView.getContext();
        StringBuilder builder = new StringBuilder();
        Integer amount = (int) item.getAmount() / 1000;
        builder.append("هر");
        builder.append(" ");
        builder.append(amount.toString());
        builder.append(" ");
        builder.append(context.getResources().getString(R.string.AmountK));
        builder.append(" ");
        builder.append(item.getWaste().getTitle());

        textView.setText(builder.toString());

    }


    @BindingAdapter(value = {"SetScorePrice"})
    public static void SetScorePrice(TextView textView, MD_ScoreListItem item) {

        Integer price = (int) item.getMd_wastePrice().getPrice();
        textView.setText(price.toString());

    }


    @BindingAdapter(value = {"SetScoreAmountValue"})
    public static void SetScoreAmountValue(TextView textView, double Amount) {

        Amount = Amount / 1000;
        String v = String.format("%.2f", Amount);
        textView.setText(v);

    }


    @BindingAdapter(value = {"SetScoreValue"})
    public static void SetScoreValue(TextView textView, double Value) {

        String v = String.format("%.2f", Value);
        textView.setText(v);

    }


    @BindingAdapter(value = {"SetScoreConfigItem", "SetScoreConfigTitle"})
    public static void SetScoreConfigItem(TextView textView, MD_ScoreListItem item, String Title) {

        Context context = textView.getContext();
        StringBuilder builder = new StringBuilder();
        Integer value = (int) item.getAmount() / 1000;

/*        builder.append(context.getResources().getString(R.string.FragmentPackRequestPrimaryReceive));
        builder.append(" ");*/
        builder.append("هر");
        builder.append(" ");
        builder.append(value.toString());
        builder.append(" ");
        builder.append(context.getResources().getString(R.string.KGr));
        builder.append(" ");
        builder.append(item.getWaste().getTitle());
/*        builder.append(" ");
        builder.append("به");*/
        builder.append(" ");
        builder.append(Title);

        textView.setText(builder.toString());

    }


    @BindingAdapter(value = {"SetScoreItemValue"})
    public static void SetScoreItemValue(TextView textView, double Amount) {

        int am = (int) Amount;
        textView.setText(Integer.toString(am));

    }


    @BindingAdapter(value = {"SetTicketDate"})
    public static void SetTicketDate(TextView textView, Date date) {

        if (date == null) {
            textView.setText("");
            return;
        }


        String tag = null;
        if (textView.getTag() != null)
            tag = textView.getTag().toString();

        ApplicationUtility component = ApplicationWMS
                .getApplicationWMS(textView.getContext())
                .getUtilityComponent()
                .getApplicationUtility();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.US);

        MD_GregorianToSun toSun = component.gregorianToSun(date);

        if (tag == null || tag.equalsIgnoreCase("")) {
            String builder = toSun.getIntYear() + "/" + toSun.getStringMonth() + "/" + toSun.getStringDay();
            builder = builder + " - " + simpleDateFormat.format(date);
            textView.setText(builder);
        } else if (tag.equalsIgnoreCase("FullString")) {
            String builder = toSun.getFullStringSun() + " " + textView.getContext().getResources().getString(R.string.Time) + " " + simpleDateFormat.format(date);
            textView.setText(builder);
        }


    }


    @BindingAdapter(value = {"SetTimeSheetTime"})
    public static void SetTimeSheetTime(TextView textView, Date date) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.US);
        textView.setText(simpleDateFormat.format(date));

    }


    @BindingAdapter(value = {"SetTicketStatus"})
    public static void SetTicketStatus(TextView textView, Byte deliveryType) {

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


    }


    @BindingAdapter(value = {"SetImageItemOfWast"})
    public static void SetImageItemOfWast(ImageView imageView, String url) {

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

    }


    @BindingAdapter(value = {"SetCountItemsWasteList"})
    public static void SetCountItemsWasteList(TextView textView, Integer count) {
        String builder = count.toString();
        textView.setText(builder);
    }


    @BindingAdapter(value = {"SetBoothAuthor"})
    public static void SetBoothAuthor(TextView textView, String author) {
        String builder = textView.getContext().getString(R.string.BoothAuthor) +
                " : " +
                author;
        textView.setText(builder);
    }


    @BindingAdapter(value = {"SetVisibleOrderCall"})
    public static void SetVisibleOrderCall(LinearLayout linearLayout, Byte deliveryType) {

        if (deliveryType.equals(StaticValues.RecyclingDeliveryTypeBooth))
            linearLayout.setVisibility(View.VISIBLE);
        else
            linearLayout.setVisibility(View.GONE);
    }


    @BindingAdapter(value = {"SetImageOrderType"})
    public static void SetImageOrderType(ImageView imageView, Byte deliveryType) {

        if (deliveryType.equals(StaticValues.RecyclingDeliveryTypeBooth))
            imageView.setImageDrawable(imageView.getContext().getResources().getDrawable(R.drawable.logobooth));
        else
            imageView.setImageDrawable(imageView.getContext().getResources().getDrawable(R.drawable.logocar));
    }


    @BindingAdapter(value = {"SetOrderTotalAmount"})
    public static void SetOrderTotalAmount(TextView textView, float Amount) {

        int am = Math.round(Amount);
        textView.setText(Integer.toString(am));

    }


    @BindingAdapter(value = {"SetOrderDate"})
    public static void SetOrderDate(TextView textView, Date date) {

        ApplicationUtility component = ApplicationWMS
                .getApplicationWMS(textView.getContext())
                .getUtilityComponent()
                .getApplicationUtility();

        MD_GregorianToSun toSun = component.gregorianToSun(date);
        String builder = toSun.getIntYear() + "/" + toSun.getStringMonth() + "/" + toSun.getStringDay();
        textView.setText(builder);

    }


    @BindingAdapter(value = {"SetOrderTime"})
    public static void SetOrderTime(TextView textView, Date date) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.US);

        String d = simpleDateFormat.format(date);
        textView.setText(d);

    }







    @BindingAdapter(value = {"SetAmountItemsWasteList"})
    public static void SetAmountItemsWasteList(TextView textView, float count) {
        if (count < 0) {
            textView.setText("");
            return;
        }

        StringBuilder builder = new StringBuilder();
        if (count < 1000) {
            builder.append(Math.round(count));
            builder.append(" ");
            builder.append(textView.getContext().getResources().getString(R.string.GR));
        } else {
            long kg = (long) (count / 1000);
            builder.append(kg);
            builder.append(" ");
            builder.append(textView.getContext().getResources().getString(R.string.AmountK));
            long gr = (long) (count % 1000);
            if (gr > 0) {
                builder.append(" و ");
                builder.append(gr);
                builder.append(" ");
                builder.append(textView.getContext().getResources().getString(R.string.GR));
            }
        }
        textView.setText(builder.toString());
    }


    //______________________________________________________________________________________________ setMaterialSpinnerBuildingType
    private static void setMaterialSpinnerBuildingType(MaterialSpinner spinner, List<MD_SpinnerItem> items) {
        List<String> buildingTypes = new ArrayList<>();
        buildingTypes.add("نوع واحد");
        for (MD_SpinnerItem item : items)
            buildingTypes.add(item.getTitle());
        spinner.setItems(buildingTypes);
    }
    //______________________________________________________________________________________________ setMaterialSpinnerBuildingType


    //______________________________________________________________________________________________ setMaterialSpinnerUses
    private static void setMaterialSpinnerUses(MaterialSpinner spinner, List<MD_SpinnerItem> items) {
        List<String> buildingUses = new ArrayList<>();
        buildingUses.add("کاربری ساختمان");
        for (MD_SpinnerItem item : items)
            buildingUses.add(item.getTitle());
        spinner.setItems(buildingUses);
    }
    //______________________________________________________________________________________________ setMaterialSpinnerUses


}
