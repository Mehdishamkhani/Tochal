package org.android.rest;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by USER on 11/26/2015.
 */
public class CustomGsonRequest<T> extends Request<T> {
    private final Gson gson = new Gson();
    private final Class<T> clazz;
    private final Map<String, String> params;
    private final MyNetworkListener<T> listener;
    public String token="";
    public Context mContext;
    private boolean needToken=false;
    public final String TAG="WEBSERVICE";
    private String url;
    private String pass;
    private boolean needAuth = false;
    private String username;

    public CustomGsonRequest(String url, int methodType, Class<T> clazz, Map<String, String> params, MyNetworkListener<T> listener) {
        super(methodType, url, null);
        setRetryPolicy(new DefaultRetryPolicy(200000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        this.clazz = clazz;
        this.url=url;
        this.params = params;
        this.listener=listener;
    }

    public void setToken(String token, Context mContext)
    {
        this.mContext=mContext;
        this.token=token;
        this.needToken=true;
    }


    public void setAuth(String user , String pass, Context mContext)
    {
        this.mContext=mContext;
        this.pass=pass;
        this.username = user;
        this.needAuth=true;
    }


    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        if(needToken)
        {
            Map<String,String> headers=new HashMap<>();
            headers.put("Authorization", String.format("Bearer %s", String.format("%s",token)));
            return headers;
        }else if(needAuth){

            HashMap<String, String> params = new HashMap<String, String>();
            String creds = String.format("%s:%s",this.username,this.pass);
            String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
            params.put("Authorization", auth);
            return params;
        }
        else
        {
            return super.getHeaders();
        }
    }

    @Override
    public Map<String, String> getParams() throws AuthFailureError {
        return  params;
        //return headers != null ? headers : super.getHeaders();
    }


    @Override
    protected void deliverResponse(T response) {
        listener.getResult(response);
    }

    @Override
    public void deliverError(VolleyError error) {
        try {
            listener.getException(NetworkExceptionHandler.decodeError(error));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        T jsonObject=null;
        try {
            String json = new String(response.data,HttpHeaderParser.parseCharset(response.headers));

            json=json.replace("\"links\":[]","\"links\":null");

            Log.d(TAG,"response url="+url+"\n"+json);
            //URLDecoder.decode(json, "utf-8")
            jsonObject=gson.fromJson(json, clazz);

        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
            return Response.success(jsonObject,HttpHeaderParser.parseCacheHeaders(response));
    }
}