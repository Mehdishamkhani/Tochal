package org.android.rest;

import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.android.rest.models.ErrorResponse;

import java.io.UnsupportedEncodingException;


public class NetworkExceptionHandler {
    public int error_number;
    public String error_server_message;
    public String error_fa_message;

    public static NetworkExceptionHandler decodeError(VolleyError error) throws UnsupportedEncodingException {
        if (error.networkResponse == null) {
            NetworkExceptionHandler neh = new NetworkExceptionHandler();
            neh.error_number = -1;
            if (error instanceof TimeoutError) {
                neh.error_fa_message = "سرعت اتصال شما به اینترنت بسیار کنداست.";
            } else if (error instanceof AuthFailureError) {
                neh.error_fa_message = "خطا در اعتبار سنجی";
            } else if (error instanceof NoConnectionError) {
                neh.error_fa_message = "ارتباط با سرور برقرار نمیشود";
            } else if (error instanceof ParseError) {
                neh.error_fa_message = "اطلاعات دریافتی ناقص است.";
            } else if (error instanceof ServerError) {
                neh.error_fa_message = "سرور خطا میدهد";
            } else {
                neh.error_fa_message = "خطا در ارتباط با سرور رخ داده است.";
            }

            return neh;
        }
        String str = new String(error.networkResponse.data, "UTF-8");
        NetworkExceptionHandler neh = null;
        if (error.networkResponse.statusCode == 503) {
            neh = new NetworkExceptionHandler();
            neh.error_number = 503;
            neh.error_server_message = "server down";
            neh.error_fa_message = "ارتباط با سرور برقرار نمیشود.";
        } else if (str.length() == 0) {
            neh = new NetworkExceptionHandler();
            neh.error_number = 500;
            neh.error_server_message = "server internal error";
            neh.error_fa_message = "در سرور یک خطا رخ داده";
        } else if (str.length() > 0) {
            Gson g = new Gson();
            neh = new NetworkExceptionHandler();
            try {

                ErrorResponse er = g.fromJson(str, ErrorResponse.class);
                neh.error_number = error.networkResponse.statusCode;
                neh.error_server_message = er.message;
                neh.error_fa_message = er.message;

            } catch (JsonSyntaxException e) {

                neh.error_fa_message = "خطا در پردازش اطلاعات";

            }
        }
        return neh;
    }
}
