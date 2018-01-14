package org.android.rest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.StringRequest;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


@SuppressWarnings("deprecation")
public class FakeHttpStack implements HttpStack {

    private static final String DEFAULT_STRING_RESPONSE = "Hello";
    private static final String DEFAULT_JSON_RESPONSE = " {\"a\":1,\"b\":2,\"c\":3}";
    private static final String LOGGER_TAG = "STACK_OVER_FLOW";
    private static final int SIMULATED_DELAY_MS = 60000;
    public static int Status_Code=200;


    private final Context context;
    FakeHttpStack(Context context) {
        this.context = context;
    }

    @Override
    public HttpResponse performRequest(Request<?> request, Map<String, String> stringStringMap)
            throws IOException, AuthFailureError {
        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        HttpResponse response= new BasicHttpResponse(new BasicStatusLine(HttpVersion.HTTP_1_1, 200, "OK"));
        //HttpResponse response= new BasicHttpResponse(new BasicStatusLine(HttpVersion.HTTP_1_1, 500, "User shamkhani9@gmail.com has exist."));
        List<Header> headers = defaultHeaders();
        response.setHeaders(headers.toArray(new Header[0]));
        response.setEntity(createEntity(request));
        return response;
    }

    private List<Header> defaultHeaders() {
        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("EEE, dd mmm yyyy HH:mm:ss zzz");
        List<Header> tempList= new ArrayList();
        tempList.add(new BasicHeader("Date", dateFormat.format(new Date())));
        tempList.add(new BasicHeader("Server",/* Data below is header info of my server */
                "Apache/1.3.42 (Unix) mod_ssl/2.8.31 OpenSSL/0.9.8e"));
        return tempList;
    }

    /**
     * returns the fake content using resource file in res/raw. fake_res_foo.txt is used for
     * request to http://example.com/foo
     */
    private HttpEntity createEntity(Request request) throws UnsupportedEncodingException {
        String resourceName = constructFakeResponseFileName(request);
        int resourceId = context.getResources().getIdentifier(resourceName, "raw", context.getApplicationContext().getPackageName());
        if (resourceId == 0) {
            Log.w(LOGGER_TAG, "No fake file named " + resourceName+ " found. default fake response should be used.");
        } else {
            InputStream stream = context.getResources().openRawResource(resourceId);
            try {
                BufferedReader r = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
                StringBuilder total = new StringBuilder();
                String line;
                while ((line = r.readLine()) != null) {
                    total.append(line);
                }
                //String string = CharStreams.toString(new InputStreamReader(stream, Charsets.UTF_8));
                //String string = line;
                return new StringEntity(total.toString());
            } catch (IOException e) {
                Log.e(LOGGER_TAG, "error reading " + resourceName, e);
            }
        }
        // Return default value since no fake file exists for given URL.
        if (request instanceof StringRequest) {
            return new StringEntity(DEFAULT_STRING_RESPONSE);
        }
        return new StringEntity(DEFAULT_JSON_RESPONSE);
    }

    /**
     * Map request URL to fake file name
     */
    private String constructFakeResponseFileName(Request request) {
        String reqUrl = request.getUrl();
        String apiName = reqUrl.substring(NetworkManager.prefixURL.length());
        apiName=apiName.replace("/","_");
        String[] arrSplit=apiName.split("_");
        return "fake_res_"+getNameOfMethod(request.getMethod())+"_" + arrSplit[0]+"_"+arrSplit[1];
    }

    private String getNameOfMethod(int requestType) {
        String str="";
        switch (requestType)
        {
            case Request.Method.DEPRECATED_GET_OR_POST:
                str= "DEPRECATED_GET_OR_POST";
                break;
            case Request.Method.GET:
                str= "GET";
                break;
            case Request.Method.POST:
                str= "POST";
                break;
            case  Request.Method.PUT:
                str= "PUT";
                break;
            case  Request.Method.DELETE:
                str= "DELETE";
                break;
            case  Request.Method.HEAD:
                str= "HEAD";
                break;
            case  Request.Method.OPTIONS:
                str= "OPTIONS";
                break;
            case  Request.Method.TRACE:
                str= "TRACE";
                break;
            case  Request.Method.PATCH:
                str= "PATCH";
                break;
        }
        return str.toLowerCase();
    }

}
