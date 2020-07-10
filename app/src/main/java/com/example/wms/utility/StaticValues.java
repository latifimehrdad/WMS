package com.example.wms.utility;

public class StaticValues {


    //***** Package Request *****
    public static Byte PR_NotRequested = 0;
    public static Byte PR_Requested = 1;
    public static Byte PR_OnProgress = 2;
    public static Byte PR_Delivered = 3;
    //***** Package Request *****


    //***** Bad Events For Request *****
    public static Byte ML_ResponseError = -125;//درخواست ارسالی ایراد داشته
    public static Byte ML_ResponseFailure = -126;// درخواست انجام نشده به هر دلیلی مثل در دسترس نبودن شبکه
    public static Byte ML_RequestCancel = -127;// شبکه آزاد شده و سرویس لغو شده
    //***** Bad Events For Request *****


    //***** Events Of Collect Request *****
    public static Byte ML_GetItemsOfWasteIsSuccess = 1;
    public static Byte ML_ItemsOFWasteReduce = 0;
    public static Byte ML_ItemsOFWasteAddition = 1;
    //***** Events Of Collect Request *****


    //***** Event Waste Collection State *****
    public static Byte WasteCollectionStateRequested = 1;
    public static Byte WasteCollectionStateOnProgress = 2;
    public static Byte WasteCollectionStateNoDelivery = 3;
    public static Byte WasteCollectionStateDelivered = 4;
    //***** Event Waste Collection State *****


    //**** Event Recycling Delivery Type *****
    public static Byte RecyclingDeliveryTypeBooth = 0;
    public static Byte RecyclingDeliveryTypeVehicle = 1;
    //**** Event Recycling Delivery Type *****


    //***** Observable Control *****
    public static Byte ML_GoToHome = 2;// رفتن به صفحه خانه
    public static Byte ML_GotoLogin = 3;// رفتن به صفحه ورد
    public static Byte ML_Success = 4;// یک عملیات با موفقت انجام شده
    public static Byte ML_GotoProfile = 5;// رفتن به صفحه پروفایل
    public static Byte ML_GetProfileInfo = 6;// دریافت اطلاعات از سرور
    public static Byte ML_EditProfile = 7;// ویرایش پروفایل
    public static Byte ML_GetRegion = 8;// در پروفایل دریافت محله
    public static Byte ML_GetCities = 9;// در پروفایل دریافت شهر
    public static Byte ML_GetProvince = 10;// در پروفایل دریافت استان
    public static Byte ML_GetAccountNumbers = 11;// در پروفایل دریافت شماره حساب
    public static Byte ML_GetAccountNumberNull = 12;// در observable در پروفایل دریافت شماره حساب ، شماره حسابی ذخیره نشده باشد
    public static Byte ML_GetBanks = 13;// در observable در پروفایل دریافت لیست بانک ها از سرور
    public static Byte ML_GetRenovationCode = 14;// در observable در پروفایل دریافت کد نوسازی
    public static Byte ML_GetAddress = 15;// در observable دریافت آدرس از روی موقعیت
    public static Byte ML_SetAddress = 16;// در observable آدرس دریافتی تبدیل به یک رشته آدرس شود
    public static Byte ML_GetHousingBuildings = 17;// در observable دریافت نوع ساختمان
    public static Byte ML_GetTimeSheetTimes = 18;// در observable دریافت زمان
    public static Byte ML_SendPackageRequest = 19;// در observable  پکیج ارسال شده
    public static Byte ML_EditUserAddress = 20;// در observable آدرس ویرایش شده
    public static Byte ML_GetBoothList = 21;// در observable دریافت لیست غرفه ها
    public static Byte ML_CollectRequestDone = 22;// در observable دریافت لیست غرفه ها
    public static Byte ML_CollectOrderDone = 22;// در observable دریافت لیست سفارش ها
    //***** Observable Control *****


}
