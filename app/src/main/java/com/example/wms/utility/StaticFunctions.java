package com.example.wms.utility;

import android.view.KeyEvent;
import android.view.View;

import com.example.wms.views.activitys.MainActivity;

public class StaticFunctions {

    public static View.OnKeyListener SetKey(View view){//_________________________________________________ Start SetKey
        return new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() != KeyEvent.ACTION_DOWN)
                    return true;

                if (keyCode != 4) {
                    return false;
                }
                MainActivity.FragmentMessage.onNext("Main");
                return true;
            }
        };
    }//_____________________________________________________________________________________________ End SetKey

}
