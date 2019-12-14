package com.example.wms.utility;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.core.app.NotificationCompat;

import com.example.wms.R;
import com.example.wms.views.dialogs.DialogMessage;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Response;

public class ApplicationUtility {

//    private ArrayList<Citys> citys;
    //Type = "FullJalaliNumber = 1367/05/31"
    //Type = "YearJalaliNumber = 1367"
    //Type = "MonthJalaliNumber = 05"
    //Type = "DayJalaliNumber = 31"
    //Type = "FullJalaliString = پنجشنبه 31 مرداد 1367"
    //Type = "MonthJalaliString = مرداد"
    //Type = "DayJalaliString = پنجشنبه"


//    public ApplicationUtility() {
//        citys = new ArrayList<>();
//        initCity();
//    }

    public String MiladiToJalali(Date MiladiDate, String Type) {//_____________________________________________________________________________________________ Start calcSolarCalendar

        String strWeekDay = "";
        String strMonth = "";

        int date;
        int month;
        int year;

        int ld;

        int miladiYear = MiladiDate.getYear() + 1900;
        int miladiMonth = MiladiDate.getMonth() + 1;
        int miladiDate = MiladiDate.getDate();
        int WeekDay = MiladiDate.getDay();

        int[] buf1 = new int[12];
        int[] buf2 = new int[12];

        buf1[0] = 0;
        buf1[1] = 31;
        buf1[2] = 59;
        buf1[3] = 90;
        buf1[4] = 120;
        buf1[5] = 151;
        buf1[6] = 181;
        buf1[7] = 212;
        buf1[8] = 243;
        buf1[9] = 273;
        buf1[10] = 304;
        buf1[11] = 334;

        buf2[0] = 0;
        buf2[1] = 31;
        buf2[2] = 60;
        buf2[3] = 91;
        buf2[4] = 121;
        buf2[5] = 152;
        buf2[6] = 182;
        buf2[7] = 213;
        buf2[8] = 244;
        buf2[9] = 274;
        buf2[10] = 305;
        buf2[11] = 335;

        if ((miladiYear % 4) != 0) {
            date = buf1[miladiMonth - 1] + miladiDate;

            if (date > 79) {
                date = date - 79;
                if (date <= 186) {
                    switch (date % 31) {
                        case 0:
                            month = date / 31;
                            date = 31;
                            break;
                        default:
                            month = (date / 31) + 1;
                            date = (date % 31);
                            break;
                    }
                    year = miladiYear - 621;
                } else {
                    date = date - 186;

                    switch (date % 30) {
                        case 0:
                            month = (date / 30) + 6;
                            date = 30;
                            break;
                        default:
                            month = (date / 30) + 7;
                            date = (date % 30);
                            break;
                    }
                    year = miladiYear - 621;
                }
            } else {
                if ((miladiYear > 1996) && (miladiYear % 4) == 1) {
                    ld = 11;
                } else {
                    ld = 10;
                }
                date = date + ld;

                switch (date % 30) {
                    case 0:
                        month = (date / 30) + 9;
                        date = 30;
                        break;
                    default:
                        month = (date / 30) + 10;
                        date = (date % 30);
                        break;
                }
                year = miladiYear - 622;
            }
        } else {
            date = buf2[miladiMonth - 1] + miladiDate;

            if (miladiYear >= 1996) {
                ld = 79;
            } else {
                ld = 80;
            }
            if (date > ld) {
                date = date - ld;

                if (date <= 186) {
                    switch (date % 31) {
                        case 0:
                            month = (date / 31);
                            date = 31;
                            break;
                        default:
                            month = (date / 31) + 1;
                            date = (date % 31);
                            break;
                    }
                    year = miladiYear - 621;
                } else {
                    date = date - 186;

                    switch (date % 30) {
                        case 0:
                            month = (date / 30) + 6;
                            date = 30;
                            break;
                        default:
                            month = (date / 30) + 7;
                            date = (date % 30);
                            break;
                    }
                    year = miladiYear - 621;
                }
            } else {
                date = date + 10;

                switch (date % 30) {
                    case 0:
                        month = (date / 30) + 9;
                        date = 30;
                        break;
                    default:
                        month = (date / 30) + 10;
                        date = (date % 30);
                        break;
                }
                year = miladiYear - 622;
            }

        }

        switch (month) {
            case 1:
                strMonth = "فروردين";
                break;
            case 2:
                strMonth = "ارديبهشت";
                break;
            case 3:
                strMonth = "خرداد";
                break;
            case 4:
                strMonth = "تير";
                break;
            case 5:
                strMonth = "مرداد";
                break;
            case 6:
                strMonth = "شهريور";
                break;
            case 7:
                strMonth = "مهر";
                break;
            case 8:
                strMonth = "آبان";
                break;
            case 9:
                strMonth = "آذر";
                break;
            case 10:
                strMonth = "دي";
                break;
            case 11:
                strMonth = "بهمن";
                break;
            case 12:
                strMonth = "اسفند";
                break;
        }

        switch (WeekDay) {

            case 0:
                strWeekDay = "يکشنبه";
                break;
            case 1:
                strWeekDay = "دوشنبه";
                break;
            case 2:
                strWeekDay = "سه شنبه";
                break;
            case 3:
                strWeekDay = "چهارشنبه";
                break;
            case 4:
                strWeekDay = "پنج شنبه";
                break;
            case 5:
                strWeekDay = "جمعه";
                break;
            case 6:
                strWeekDay = "شنبه";
                break;
        }

        //Type = "FullJalaliNumber = 1367/05/31"
        //Type = "YearJalaliNumber = 1367"
        //Type = "MonthJalaliNumber = 05"
        //Type = "DayJalaliNumber = 31"
        //Type = "FullJalaliString = پنجشنبه 31 مرداد 1367"
        //Type = "MonthJalaliString = مرداد"
        //Type = "DayJalaliString = پنجشنبه"

        Locale loc = new Locale("en_US");
        String result = "";
        switch (Type) {
            case "FullJalaliNumber":
                result = String.valueOf(year) + "/" + String.format(loc, "%02d",
                        month) + "/" + String.format(loc, "%02d", date);
                break;

            case "YearJalaliNumber":
                result = String.valueOf(year);
                break;

            case "MonthJalaliNumber":
                result = String.format(loc, "%02d", month);
                break;

            case "DayJalaliNumber":
                result = String.format(loc, "%02d", date);
                break;

            case "FullJalaliString":
                result = strWeekDay + " " + String.format(loc, "%02d", date)
                        + " " + strMonth + " " + String.valueOf(year);
                break;

            case "MonthJalaliString":
                result = strMonth;
                break;

            case "DayJalaliString":
                result = strWeekDay;
                break;

            default:
                result = String.valueOf(year) + "/" + String.format(loc, "%02d",
                        month) + "/" + String.format(loc, "%02d", date);
                break;
        }

        return result;
    }//_____________________________________________________________________________________________ End calcSolarCalendar



    public void CustomToastShow(Context context, String message, int color) {

        //context.getResources().getColor(R.color.mlWhite)
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
        View view = toast.getView();
        view.setBackgroundColor(color);
        TextView text = (TextView) view.findViewById(android.R.id.message);
        text.setPadding(10, 2, 10, 2);
        text.setTextColor(context.getResources().getColor(R.color.mlBlack));
        text.setTextSize(2, 17.0f);
        text.setGravity(17);
        toast.setGravity(17, 0, 0);
        toast.show();
    }


    public TextWatcher SetTextWatcherSplitting(final EditText editText) {
        return new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            public void afterTextChanged(Editable editable) {
                NumberFormat formatter = new DecimalFormat("#,###");
                String m = editText.getText().toString();
                m = m.replaceAll(",", "");
                m = m.replaceAll("٬", "");
                if (!m.equalsIgnoreCase("")) {
                    editText.removeTextChangedListener(this);
                    editText.setText(formatter.format(Integer.valueOf(m)));
                    editText.addTextChangedListener(this);
                }
                editText.setSelection(editText.getText().length());

            }
        };
    }


    public Integer JalaliDatBetween(String Date1, String Date2, Integer intDate1, Integer intDate2) {
        Integer DateStart;
        Integer DateEnd;
        int c1;
        int b2;
        int c2;
        int b1;
        int a1;
        int a2;
        if (intDate1 != null) {
            DateStart = intDate1;
            DateEnd = intDate2;
        } else if (Date1.length() != 10 || Date1.length() != 10) {
            return 0;
        } else {
            DateStart = Integer.valueOf(Date1.replaceAll("/", ""));
            DateEnd = Integer.valueOf(Date2.replaceAll("/", ""));
        }
        if (DateStart.intValue() == 0 || DateEnd.intValue() == 0) {
            return 0;
        }
        if (DateStart.intValue() > DateEnd.intValue()) {
            a1 = Integer.valueOf(String.valueOf(DateStart).substring(0, 4)).intValue();
            b1 = Integer.valueOf(String.valueOf(DateStart).substring(4, 6)).intValue();
            c1 = Integer.valueOf(String.valueOf(DateStart).substring(6, 8)).intValue();
            a2 = Integer.valueOf(String.valueOf(DateEnd).substring(0, 4)).intValue();
            b2 = Integer.valueOf(String.valueOf(DateEnd).substring(4, 6)).intValue();
            c2 = Integer.valueOf(String.valueOf(DateEnd).substring(6, 8)).intValue();
        } else {
            a1 = Integer.valueOf(String.valueOf(DateEnd).substring(0, 4)).intValue();
            b1 = Integer.valueOf(String.valueOf(DateEnd).substring(4, 6)).intValue();
            c1 = Integer.valueOf(String.valueOf(DateEnd).substring(6, 8)).intValue();
            a2 = Integer.valueOf(String.valueOf(DateStart).substring(0, 4)).intValue();
            b2 = Integer.valueOf(String.valueOf(DateStart).substring(4, 6)).intValue();
            c2 = Integer.valueOf(String.valueOf(DateStart).substring(6, 8)).intValue();
        }
        int B = 0;
        int d = b2;
        while (a2 < a1) {
            while (d <= 12) {
                B += Switch(d, a2);
                d++;
            }
            a2++;
            d = 1;
        }
        while (d < b1) {
            B += Switch(d, a1);
            d++;
        }
        return Integer.valueOf((B + c1) - c2);
    }


    public String JalaliAddDay(String Date1, Integer intDate1, int day) {
        Integer DateStart;
        if (intDate1 != null) {
            DateStart = intDate1;
        } else if (Date1.length() != 10) {
            return null;
        } else {
            DateStart = Integer.valueOf(Date1.replaceAll("/", ""));
        }
        if (DateStart == 0) {
            return null;
        }
        int a1 = Integer.valueOf(String.valueOf(DateStart).substring(0, 4)).intValue();
        int b1 = Integer.valueOf(String.valueOf(DateStart).substring(4, 6)).intValue();
        int c1 = Integer.valueOf(String.valueOf(DateStart).substring(6, 8)).intValue();
        int day2 = day + c1;
        while (day2 > 0) {
            int temp = Switch(b1, a1);
            if (day2 >= temp) {
                day2 -= temp;
                b1++;
                c1 = 0;
            } else {
                c1 = day2;
                day2 = 0;
            }
            if (b1 > 12) {
                a1++;
                b1 = 1;
            }
        }

        if (c1 == 0) {
            b1--;
            c1 = Switch(b1, a1);
        }

        StringBuilder sb = new StringBuilder();
        sb.append(String.valueOf(a1));
        sb.append("/");
        String str2 = "%02d";
        sb.append(String.format(str2, new Object[]{Integer.valueOf(b1)}));
        sb.append("/");
        sb.append(String.format(str2, new Object[]{Integer.valueOf(c1)}));
        return sb.toString();
    }


    public String JalaliReduceDay(String Date1, Integer intDate1, int day) {
        Integer DateStart;
        if (intDate1 != null) {
            DateStart = intDate1;
        } else if (Date1.length() != 10 || Date1.length() != 10) {
            return null;
        } else {
            DateStart = Integer.valueOf(Date1.replaceAll("/", ""));
        }
        if (DateStart.intValue() == 0) {
            return null;
        }
        int a1 = Integer.valueOf(String.valueOf(DateStart).substring(0, 4)).intValue();
        int b1 = Integer.valueOf(String.valueOf(DateStart).substring(4, 6)).intValue();
        int day2 = day - Integer.valueOf(String.valueOf(DateStart).substring(6, 8)).intValue();
        while (day2 > 0) {
            b1--;
            day2 -= Switch(b1, a1);
            if (b1 == 1) {
                b1 = 13;
                a1--;
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append(String.valueOf(a1));
        sb.append("/");
        String str2 = "%02d";
        sb.append(String.format(str2, new Object[]{Integer.valueOf(b1)}));
        sb.append("/");
        if (day2 < 0)
            sb.append(String.format(str2, new Object[]{Integer.valueOf(day2 * -1)}));
        else
            sb.append("01");
        return sb.toString();
    }

    private int Switch(int d, int year) {
        switch (d) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                return 31;
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
                return 30;
            case 12:
                if (Kabise(year).booleanValue()) {
                    return 30;
                }
                return 29;
            default:
                return 0;
        }
    }

    private Boolean Kabise(int year) {
        int temp = year % 33;
        if (temp == 1 || temp == 5 || temp == 9 || temp == 13 || temp == 17 || temp == 22 || temp == 26 || temp == 30) {
            return Boolean.valueOf(true);
        }
        return Boolean.valueOf(false);
    }

//    public String[] getName() {
//        List<String> city = new ArrayList<>();
//        for (Citys item : citys)
//            city.add(item.getName());
//        String[] array = new String[city.size()];
//        city.toArray(array);
//        return array;
//    }

//    public double[] getLatLong(int position) {
//        double[] latlong = {citys.get(position).getLat(),citys.get(position).getLong()};
//        return latlong;
//    }

//    public double getLong(int position) {
//        return citys.get(position).getLong();
//    }

//    private void initCity() {
//        citys.add(new Citys("انتخاب استان", 0.0, 0.0));
//        citys.add(new Citys("آذربایجان شرقی", 38.0524675, 46.2849927));
//        citys.add(new Citys("آذربایجان غربی", 37.5296071, 45.0465487));
//        citys.add(new Citys("اردبیل", 38.246471, 48.295052));
//        citys.add(new Citys("اصفهان", 32.65139, 51.679192));
//        citys.add(new Citys("البرز", 35.491716, 50.574495));
//        citys.add(new Citys("ایلام", 33.638531, 46.422649));
//        citys.add(new Citys("بوشهر", 28922041, 50.833092));
//        citys.add(new Citys("تهران", 35.415903, 51.201698));
//        citys.add(new Citys("چهارمحال و بختیاری", 32.3555942, 50.8274267));
//        citys.add(new Citys("خراسان جنوبی", 32.8462159, 592911422));
//        citys.add(new Citys("خراسان رضوی", 36.3, 59.6));
//        citys.add(new Citys("خراسان شمالی", 37.4524383, 57.3235177));
//        citys.add(new Citys("خوزستان", 31.5317162, 49.8803281));
//        citys.add(new Citys("زنجان", 36.67094, 48.485111));
//        citys.add(new Citys("سمنان", 35.572269, 53.396049));
//        citys.add(new Citys("سیستان و بلوچستان", 29.4545786, 60.853647));
//        citys.add(new Citys("فارس", 29.6496884, 52.5088696));
//        citys.add(new Citys("قزوین", 36.266819, 50.003811));
//        citys.add(new Citys("قم", 34.643711, 50.89064));
//        citys.add(new Citys("کردستان", 35.3029422, 47.0026312));
//        citys.add(new Citys("کرمان", 3028027, 5706702));
//        citys.add(new Citys("کرمانشاه", 34.346481, 46.420559));
//        citys.add(new Citys("کهگیلویه وبویراحمد", 30.392547, 51.36106));
//        citys.add(new Citys("گلستان", 36.8633914, 54.4485782));
//        citys.add(new Citys("گیلان", 37.223431, 49.6355091));
//        citys.add(new Citys("لرستان", 33.4666667, 48.35));
//        citys.add(new Citys("مازندران", 36.5658333, 53.0597222));
//        citys.add(new Citys("مرکزی", 34.0800112, 49.6772334));
//        citys.add(new Citys("هرمزگان", 27.1833333, 56.2666667));
//        citys.add(new Citys("همدان", 34.8065, 485162472));
//        citys.add(new Citys("یزد", 3189661, 54.36068));
//    }


}
