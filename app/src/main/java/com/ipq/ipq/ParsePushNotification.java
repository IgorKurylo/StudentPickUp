package com.ipq.ipq;

import android.app.Application;
import android.content.Intent;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseAnalytics;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.ParseUser;
import com.parse.PushService;


public class ParsePushNotification extends Application
{

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, "3hKMBPlqDuOi3XG8zRhxTdVjuEDobhkN8yWBxBti", "YGn44vXPkOAUOMXJyF5Jjbf4alTNVWohx7YZcmdm");
        ParseInstallation.getCurrentInstallation().saveInBackground();


    }
}
