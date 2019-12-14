package com.example.wms.utility;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import com.example.wms.R;
import com.example.wms.views.activitys.MainActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import retrofit2.Response;

public class StaticFunctions {

    public static TextWatcher TextChangeForChangeBack(EditText editText) {//______________________________ Satart TextChangeForChangeBack

        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editText.setBackgroundResource(R.drawable.edit_normal_background);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

    }//_____________________________________________________________________________________________ End TextChangeForChangeBack


    public static View.OnKeyListener SetKey(View view) {//_________________________________________________ Start SetKey
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


    public static String CheckResponse(Response response, Boolean Authorization) {//_______________________ Start CheckResponse
        if (response.body() != null)
            return null;
        else {
            if (Authorization) {
                try{
                    JSONObject jObjError = new JSONObject(response.errorBody().string());
                    return jObjError.getString("error_description");
                }catch (Exception ex){
                    return "Failure";
                }
            } else {
                try{
                    JSONObject jObjError = new JSONObject(response.errorBody().string());
                    JSONArray jsonArray = jObjError.getJSONArray("messages");
                    String message = "";
                    for(int i = 0; i<jsonArray.length(); i++){
                        JSONObject temp = new JSONObject(jsonArray.get(i).toString());
                        message = message + temp.getString("message");
                        message = message + "\n";
                    }
                    return message;
                }catch (Exception ex){
                    return "Failure";
                }
            }
        }

    }//_____________________________________________________________________________________________ End CheckResponse


}
