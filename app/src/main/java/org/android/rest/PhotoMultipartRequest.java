package org.android.rest;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.protocol.HTTP;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;


public class PhotoMultipartRequest<T> extends com.android.volley.Request<T> {

    private static final String FILE_PART_NAME = "image";
    private final Gson gson = new Gson();
    private final Class<T> clazz;
    private final Map<String, String> params;
    private final MyNetworkListener<T> listener;
    private final File mImageFile;
    public String token="";
    protected Map<String, String> headers;
    private MultipartEntityBuilder mBuilder = MultipartEntityBuilder.create();
    private boolean needToken=false;
    private Context mContext;

    ContentType contentType = ContentType.create(
            HTTP.PLAIN_TEXT_TYPE, HTTP.UTF_8);

    public PhotoMultipartRequest(String url, int methodType, Class<T> clazz, Map<String, String> params, File imageFile, MyNetworkListener<T> listener){
        super(methodType, url, null);
        this.clazz = clazz;
        this.params = params;
        this.listener = listener;
        mImageFile = imageFile;
        setRetryPolicy(new DefaultRetryPolicy(200000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        buildMultipartEntity();
    }


    @Override
    public void deliverError(VolleyError error) {
        try {
            listener.getException(NetworkExceptionHandler.decodeError(error));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


    public void setToken(String token, Context mContext)
    {
        this.mContext=mContext;
        this.token=token;
        this.needToken=true;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        if(needToken)
        {
            Map<String,String> headers=new HashMap<>();
            headers.put("Authorization", String.format("Bearer %s", String.format("%s",token)));
            return headers;
        }
        else
        {
            return super.getHeaders();
        }
    }



    private void buildMultipartEntity(){
        if(params!=null)
        {
            for(Map.Entry<String, String> entry : params.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                mBuilder.addTextBody(key,value,contentType);
            }
        }

        if(mImageFile!=null)
            mBuilder.addBinaryBody(FILE_PART_NAME, mImageFile, ContentType.create("image/jpeg"), mImageFile.getName());
        mBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        mBuilder.setLaxMode().setBoundary("xx").setCharset(Charset.forName("UTF-8"));
    }

    @Override
    public String getBodyContentType(){
        return mBuilder.build().getContentType().getValue();
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            mBuilder.build().writeTo(bos);
        } catch (IOException e) {
            VolleyLog.e("IOException writing to ByteArrayOutputStream bos, building the multipart request.");
        }

        return bos.toByteArray();
    }


    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(response.data,HttpHeaderParser.parseCharset(response.headers));
            T jsonObject=gson.fromJson(json, clazz);
            return Response.success(jsonObject,HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(T response) {
            listener.getResult(response);
    }
}