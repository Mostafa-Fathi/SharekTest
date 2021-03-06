package com.example.khalid.sharektest.Utils;

/**
 * Created by Khalid on 5/17/2017.
 * <p/>
 * Copyright 2016 Google Inc. All Rights Reserved.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p/>
 * Copyright 2016 Google Inc. All Rights Reserved.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p/>
 * Copyright 2016 Google Inc. All Rights Reserved.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p>
 * Copyright 2016 Google Inc. All Rights Reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p>
 * Copyright 2016 Google Inc. All Rights Reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 * Copyright 2016 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.khalid.sharektest.HomePage;
import com.example.khalid.sharektest.MyProfile;
import com.example.khalid.sharektest.NotificationActivity;
import com.example.khalid.sharektest.R;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    Intent intent;
    String[] string;
    StringBuilder stringBuilder = new StringBuilder();
   ArrayList<String> NotificationMessages = new ArrayList<>();
    /**
     * Called when message is received.
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {


        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. Data messages are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
        // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages containing both notification
        // and data payloads are treated as notification messages. The Firebase console always sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());


      //   if (UserName.getString("CurrentUsername","") ==  UserPreference.getString("myUserName","")){
        // saved previous  notifications in Sharedpreference
        SharedPreferences Notifpreference = PreferenceManager.getDefaultSharedPreferences(MyFirebaseMessagingService.this);
        // get data as string and convert it as string array
        string = Notifpreference.getString("notifications", "").split(";");
        List<String> newList = Arrays.asList(string);
        // we should refer to this sharedpreference in notification Activity to display the data
         NotificationMessages.addAll(newList);
        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

            JSONObject jsonObject = new JSONObject(remoteMessage.getData());

            //           sendNotification(jsonObject);


            //convert coming data  to string element
            String stringObject = jsonObject.toString();
            //#define the string of sharedpreference as arraylist
            //#add this element to defined arrayList
            NotificationMessages.add(stringObject);
            //#transform the arrayList to string array with new size with new added data
            string = NotificationMessages.toArray(new String[NotificationMessages.size()]);
            //build the string array to one string with spliter ","
            for (int i = 0; i < string.length; i++) {
                stringBuilder.append(string[i]).append(";");
            }
            //assign the string element of overall data  to sharedpreference to update
            Notifpreference.edit().putString("notifications", stringBuilder.toString()).apply();
            SharedPreferences mypreference = PreferenceManager.getDefaultSharedPreferences(MyFirebaseMessagingService.this);
            if (mypreference.getBoolean("loggedIn", true)) {
                sendNotification(jsonObject);
            }




//if (/* Check if data needs to be processed by long running job */ true) {
//                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
//                scheduleJob();
//            } else {
//                // Handle message within 10 seconds
//                handleNow();
//            }

        }

        // Check if message contains a notification payload.
//        if (remoteMessage.getNotification() != null) {
//            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
//            sendNotification(remoteMessage.getNotification().getBody());
//
//
//        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
    // [END receive_message]

    /**
     * Schedule a job using FirebaseJobDispatcher.
     */
    private void scheduleJob() {
        // [START dispatch_job]
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(this));
        Job myJob = dispatcher.newJobBuilder()
                .setService(MyJobService.class)
                .setTag("my-job-tag")
                .build();
        dispatcher.schedule(myJob);
        // [END dispatch_job]
    }

    /**
     * Handle time allotted to BroadcastReceivers.
     */
    private void handleNow() {
        Log.d(TAG, "Short lived task is done.");
    }

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param dataPayLoad FCM message body received.
     */
    private void sendNotification(JSONObject dataPayLoad) {
/*
        // saved previous  notifications in Sharedpreference
        SharedPreferences Notifpreference = PreferenceManager.getDefaultSharedPreferences(MyFirebaseMessagingService.this);
        // get data as string and convert it as string array
        string = Notifpreference.getString("notifications", "").split(";");
        // we should refer to this sharedpreference in notification Activity to display the data

*/
        try {

            if (dataPayLoad.get("type").equals("proposal")) {
                intent = new Intent(this, MyProfile.class);

            } else if (dataPayLoad.get("type").equals("proposal-reaction")) {
                intent = new Intent(this, NotificationActivity.class);
                //String stringObject = dataPayLoad.toString();
                /*
                //convert coming data  to string element
                //define the string of sharedpreference as arraylist
                NotificationMessages = Arrays.asList(string);
                //add this element to defined arrayList
                NotificationMessages.add(stringObject);
                //transform the arrayList to string array with new size with new added data
                string = NotificationMessages.toArray(new String[NotificationMessages.size()]);
                //build the string array to one string with spliter ","
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < string.length; i++) {
                    stringBuilder.append(string[i]).append(";");
                }
                //assign the string element of overall data  to sharedpreference to update
                Notifpreference.edit().putString("notifications", stringBuilder.toString()).apply();
*/
                //intent.putExtra("data", stringObject);
            } else {
                intent = new Intent(this, HomePage.class);
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                    PendingIntent.FLAG_ONE_SHOT);

            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.notification_icon2)
                    .setContentTitle("Sharek")
                    .setContentText(dataPayLoad.getString("body"))
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
