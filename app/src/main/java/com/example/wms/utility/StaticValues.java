package com.example.wms.utility;

public class StaticValues {



    //***** Package Request *****
    public static Byte PR_NotRequested = 0;
    public static Byte PR_Requested = 1;
    public static Byte PR_OnProgress = 2;
    public static Byte PR_Delivered = 3;
    //***** Package Request *****



//    public static boolean isCancel = true;

    //***** Observable Control *****
    public static Byte ML_ResponseError = 127;//درخواست ارسالی ایراد داشته
    public static Byte ML_ResponseFailure = 126;// درخواست انجام نشده به هر دلیلی مثل در دسترس نبودن شبکه
    public static Byte ML_RequestCancel = 125;// در observable شبکه آزاد شده و سرویس لغو شده
    public static Byte ML_GoToHome = 2;// در observable رفتن به صفحه خانه
    public static Byte ML_GotoLogin = 3;// در observable رفتن به صفحه ورد
    public static Byte ML_Success = 4;// یک عملیات با موفقت انجام شده
    public static Byte ML_GotoProfile = 5;// در observable رفتن به صفحه پروفایل
    public static Byte ML_GetProfileInfo = 6;// در observable دریافت اطلاعات از سرور
    public static Byte ML_EditProfile = 7;// در observable ویرایش پروفایل
    public static Byte ML_GetRegion = 8;// در observable در پروفایل دریافت محله
    public static Byte ML_GetCities = 9;// در observable در پروفایل دریافت شهر
    public static Byte ML_GetProvince = 10;// در observable در پروفایل دریافت استان
    public static Byte ML_GetAccountNumbers = 11;// در observable در پروفایل دریافت شماره حساب
    public static Byte ML_GetAccountNumberNull = 12;// در observable در پروفایل دریافت شماره حساب ، شماره حسابی ذخیره نشده باشد
    public static Byte ML_GetBanks = 13;// در observable در پروفایل دریافت لیست بانک ها از سرور
    public static Byte ML_GetRenovationCode = 14;// در observable در پروفایل دریافت کد نوسازی
    public static Byte ML_GetAddress = 15;// در observable دریافت آدرس از روی موقعیت
    public static Byte ML_SetAddress = 16;// در observable آدرس دریافتی تبدیل به یک رشته آدرس شود
    public static Byte ML_GetHousingBuildings = 17;// در observable دریافت نوع ساختمان
    public static Byte ML_GetTimeSheetTimes = 18;// در observable دریافت زمان
    public static Byte ML_SendPackageRequest = 19;// در observable  پکیج ارسال شده
    public static Byte ML_EditUserAddress = 20;// در observable آدرس ویرایش شده
    //***** Observable Control *****


}
