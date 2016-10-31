package com.sunil.firebasecrashreport;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.crash.FirebaseCrash;

public class MainActivity extends AppCompatActivity {

    private boolean isCrashKnow = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Check if google play services is up to date
        final int playServicesStatus = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);
        if(playServicesStatus != ConnectionResult.SUCCESS){
            //If google play services in not available show an error dialog and return
            final Dialog errorDialog = GoogleApiAvailability.getInstance().getErrorDialog(this, playServicesStatus, 0, null);
            errorDialog.show();
            return;
        }

        Button btnCrash = (Button) findViewById(R.id.crash);
        btnCrash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseCrash.logcat(Log.INFO, "MainActivity", "Crash button clicked");

                // If catchCrashCheckBox is checked catch the exception and report is using
                // Crash.report(). Otherwise throw the exception and let Firebase Crash automatically
                // report the crash.
                if (isCrashKnow) {
                    try {
                        throw new NullPointerException();
                    } catch (NullPointerException ex) {
                        // [START log_and_report]
                        FirebaseCrash.logcat(Log.ERROR, "MainActivity", "NPE caught");
                        FirebaseCrash.report(ex);
                        // [END log_and_report]
                    }
                } else {
                    throw new NullPointerException();
                }
            }
        });

    }
}
