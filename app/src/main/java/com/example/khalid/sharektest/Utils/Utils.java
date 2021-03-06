package com.example.khalid.sharektest.Utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.util.Base64;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.VolleyError;
import com.example.khalid.sharektest.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Khalid on 4/29/2017.
 */
public class Utils {
    public static String GetErrorDescription(VolleyError error, Context context) {

        String json = null;

        //K.A: to handle error message
        NetworkResponse response = error.networkResponse;
        if (response != null && response.data != null) {
            switch (response.statusCode) {

                case 400:
                    json = new String(response.data);
                    Log.i("Connection Error", json);
                    json = trimMessage(json, "errors");
                    json = GetErrorByKey(json, context);
                    break;
                case 401:

                    Activity activity = (Activity) context;
                    Log.i("Error activity", activity.toString());
                    if (activity.toString().contains("MainActivity")) {
                        json = context.getResources().getString(R.string.error_401);
                    } else if (activity.toString().contains("SignUp")) {
                        json = context.getResources().getString(R.string.error_LowerCase);
                    } else {
                        json = context.getResources().getString(R.string.error_unauthorized);
                    }
                    break;

                case 502:
                    json = context.getResources().getString(R.string.error_502);
                    break;
                case 500:
                    json = context.getResources().getString(R.string.error_500);
                    break;

                default:
                    json = context.getResources().getString(R.string.error_default);
            }

        } else {
            json = "Please check your internet connectivity";
        }

        return json;
    }

    public static String trimMessage(String json, String key) {
        String trimmedString = null;

        try {
            JSONObject obj = new JSONObject(json);
            trimmedString = obj.getString(key);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return trimmedString;
    }

    public static String GetErrorByKey(String errorMessage, Context context) {
        String error = errorMessage;
        try {
            JSONObject errorObject = new JSONObject(errorMessage);
            Activity activity = (Activity) context;
            if (errorObject.has("user")) {
                if (errorObject.getString("user").equals("unique")) {
                    if (activity.toString().contains("ProductPage")) {
                        error = "You've already proposed to this Product";
                    }


                }
            }

            if(errorObject.has("location")){
                if (errorObject.getString("location").equals("unavailable")) {
                    if(activity.toString().contains("SignUp")){
                    error = "Sorry, Registration is allowed only in Fayoum";
                }
            }}
              if (errorObject.has("email")){
                  if (errorObject.getString("email").equals("unique")) {
                      if(activity.toString().contains("SignUp")){
                  error = "Sorry, Your email is already used ";
              }}
              }
               if (errorObject.has("user")){
                   if (errorObject.getString("user").equals("unique")) {
                       if(activity.toString().contains("SignUp")){
                       error = "Sorry, Your user name is already used ";
                   } }}
               if (errorObject.has("phone")){
                   if (errorObject.getString("phone").equals("unique")) {
                       if(activity.toString().contains("SignUp")){
                       error = "Sorry, Your user phone number is already used ";
                   } }}


                if(errorObject.has("title")){
                    if (errorObject.getString("title").equals("minlength")) {
                        if(activity.toString().contains("AddShare")){
                        error = "Sorry, title cannot be shorter than 6 characters";
                    }}
                }
                if(errorObject.has("title")){
                    if (errorObject.getString("title").equals("maxlength")) {
                        if(activity.toString().contains("AddShare")){
                        error = "Sorry, title cannot be longer than 100 characters";
                    }}
                }

                if(errorObject.has("description")){
                    if (errorObject.getString("description").equals("maxlength")) {
                        if(activity.toString().contains("AddShare")){
                        error = "Sorry, cannot be longer than 1000 characters";
                    }}
                }
                if(errorObject.has("description")){
                    if (errorObject.getString("title").equals("minlength")) {
                        if(activity.toString().contains("AddShare")){
                        error = "Sorry, cannot be longer than 10 characters";
                    }}
                }
            if(errorObject.has("price")){
                if (errorObject.getString("price").equals("max")) {
                    if(activity.toString().contains("AddShare")){
                        error = "Sorry, cannot be more than 100";
                    }}
            }
            if(errorObject.has("price")){
                if (errorObject.getString("price").equals("min")) {
                    if(activity.toString().contains("AddShare")){
                        error = "Sorry, cannot be less than 0";
                    }}
            }
            if(errorObject.has("title")){
                if (errorObject.getString("title").equals("minlength")) {
                    if(activity.toString().contains("addinterest")){
                        error = "Sorry, title cannot be shorter than 6 characters";
                    }}
            }
            if(errorObject.has("title")){
                if (errorObject.getString("title").equals("maxlength")) {
                    if(activity.toString().contains("addinterest")){
                        error = "Sorry, title cannot be longer than 100 characters";
                    }}
            }

            if(errorObject.has("description")){
                if (errorObject.getString("description").equals("maxlength")) {
                    if(activity.toString().contains("addinterest")){
                        error = "Sorry, cannot be longer than 1000 characters";
                    }}
            }
            if(errorObject.has("description")){
                if (errorObject.getString("title").equals("minlength")) {
                    if(activity.toString().contains("addinterest")){
                        error = "Sorry, cannot be longer than 10 characters";
                    }}
            }
            if(errorObject.has("price")){
                if (errorObject.getString("price").equals("max")) {
                    if(activity.toString().contains("addinterest")){
                        error = "Sorry, cannot be more than 100";
                    }}
            }
            if(errorObject.has("price")){
                if (errorObject.getString("price").equals("min")) {
                    if(activity.toString().contains("addinterest")){
                        error = "Sorry, cannot be less than 0";
                    }}
            }

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return error;
    }

    public Map<String, String> getRequestHeaders(String token) {
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        if (token != null) {
            headers.put("Authorization", "Bearer " + token);
        }
        return headers;
    }

    public String convertBitMapToString(Bitmap image) {
        String photo = "";
        if (image != null) {
            image = getResizedBitmap(image, 150, 150);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageBytes = baos.toByteArray();
            photo = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        }
        return photo;
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height,
                matrix, false);

        return resizedBitmap;
    }

}