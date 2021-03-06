package com.example.khalid.sharektest.Utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.khalid.sharektest.R;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Abdelrahman on 5/5/2017.
 */
// build array of notification class argus
public class NotificationAdaptor extends ArrayAdapter<Notification> {
    // build constructor
    public NotificationAdaptor(Context context, ArrayList<Notification> notifications) {
        super(context, 0, notifications);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Notification notification = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.notification_row, parent, false);
        }

        TextView description = (TextView) convertView.findViewById(R.id.notification_textView);
        TextView NotificationDate = (TextView) convertView.findViewById(R.id.notification_date);
//        Date dateObject = new Date(notification.getDate());
//        Date now = new Date();
//
//        long diff = now.getTime() - dateObject.getTime() ;
//        long differenceInSeconds = diff * 1000;
//        String time = String.valueOf(differenceInSeconds);

        description.setText(notification.getBody());
        String date = "At " + notification.getDate().substring(0, notification.getDate().indexOf("G"));
//        String date = time + "Seconds";
        NotificationDate.setText(date);
        return convertView;
    }
}
