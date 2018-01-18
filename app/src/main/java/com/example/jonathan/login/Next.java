package com.example.jonathan.login;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ViewFlipper;
import android.widget.SearchView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.SignInButton;
/**
 * Created by Jonathan on 9/8/2016.
 */
//
// public class Next extends Activity
public class Next extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener
{
    //hi
    ViewFlipper newsong;
    private Button btnLogout;
    private Button btnDisconnect;
    Button btnProfile;
    private GoogleApiClient mGoogleApiClient;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.next);

        newsong = (ViewFlipper) findViewById(R.id.release);
        newsong.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                newsong.showNext();
            }
        });
        newsong.setFlipInterval(5000);
        newsong.startFlipping();

        btnProfile = (Button) findViewById(R.id.btnProfile);
        btnProfile.setOnClickListener(new View.OnClickListener()
        {
            public void onClick (View view)
            {
                Intent intent = new Intent(Next.this,Profile.class);
                startActivity(intent);
            }

        });

        findViewById(R.id.btnLogout).setOnClickListener(this);
        btnLogout = (Button)findViewById(R.id.btnLogout);

        findViewById(R.id.btnDisconnect).setOnClickListener(this);
        btnDisconnect = (Button)findViewById(R.id.btnDisconnect);
/*
        findViewById(R.id.btnDisconnect).setOnClickListener(this);
        btnDisconnect = (Button)findViewById(R.id.btnDisconnect);
*/
    }

    private void revokeAccess() {
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // [START_EXCLUDE]
                        updateUI(false);
                        // [END_EXCLUDE]
                    }
                });
    }

    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // [START_EXCLUDE]
                        updateUI(false);
                        // [END_EXCLUDE]
                    }
                });
    }
    private void updateUI(boolean signedIn) {
        /**
        if (signedIn) {
            findViewById(R.id.sign_in_button).setVisibility(View.GONE);
            findViewById(R.id.btnLogout).setVisibility(View.VISIBLE);
            findViewById(R.id.btnDisconnect).setVisibility(View.VISIBLE);
        } else {
**/
            findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
            mGoogleApiClient.disconnect();
            findViewById(R.id.btnLogout).setVisibility(View.GONE);
            findViewById(R.id.btnDisconnect).setVisibility(View.GONE);

    }


    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btnLogout:
                signOut();
                break;

            case R.id.btnDisconnect:
                revokeAccess();
                break;


        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

}
