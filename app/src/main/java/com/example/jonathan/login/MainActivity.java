package com.example.jonathan.login;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
//import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;
import android.graphics.Bitmap;

import javax.net.ssl.HttpsURLConnection;


public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {

    private static final String TAG = "IdTokenActivity";
    private static final int RC_GET_TOKEN = 9002;

    private GoogleApiClient mGoogleApiClient;
    String personName;
    String personEmail;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Button click listeners
        findViewById(R.id.sign_in_button).setOnClickListener(this);



        //Create the Google Sign-In Object
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("309697773397-fa7iqgs04i6650svo1qhgu3rhqcpr2oe.apps.googleusercontent.com")
                .requestEmail()
                .build();

        // Build GoogleAPIClient with the Google Sign-In API and the above options.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    private void getIdToken()
    {
        // Show an account picker to let the user choose a Google account from the device.
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_GET_TOKEN);
    }

    private void refreshIdToken()
    {
        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);

        if (opr.isDone())
        {
            // Users cached credentials are valid, GoogleSignInResult containing ID token
            // is available immediately. This likely means the current ID token is already
            // fresh and can be sent to your server.
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        }
        else
        {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently and get a valid
            // ID token. Cross-device single sign on will occur in this branch.
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(@NonNull GoogleSignInResult result) {
                    handleSignInResult(result);
                }
            });
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {

        if (result.isSuccess())
        {
            String idToken = result.getSignInAccount().getIdToken();
            updateUI(true);

        } else
        {
            updateUI(false);
        }

        //test getRequest by going to homepage URL after sign in
        /*
        HashMap<String,String> tester = new HashMap<String, String>();
        tester.put("id", "5");
        tester.put("cat", "3");
        App.getRequest("http://www.heartbeats.ml", tester, ); //not sure what to put as the callback function parameter here
        */

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);  //call the super code as required

        if (requestCode == RC_GET_TOKEN)                        //if we need to get a token
        {
            final GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            GoogleSignInAccount acct = result.getSignInAccount();
            personName = acct.getDisplayName();
            personEmail = acct.getEmail();

            if(result.isSuccess())  //if we logged in correctly
            {
                App.myToken = result.getSignInAccount().getIdToken().toString();    //store the idToken for future reference

                StringRequest myRequest = new StringRequest(
                        Request.Method.POST,
                        App.apiURL + "/googlesignin",                       //send the request to our server at /googlesiginin
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response)
                            {
                                //response
                            }
                        },

                        new Response.ErrorListener()
                        {
                            @Override
                            public void onErrorResponse(VolleyError error)
                            {
                                //error
                            }
                        })  {
                    @Override
                    protected Map<String, String> getParams()   //Pass a Map of parameters which will be appended to the URL
                    {
                        HashMap<String,String> myParams = new HashMap<String,String>();
                        myParams.put("idToken", App.myToken);
                        return myParams;
                    }
                };
                App.getInstance().addToRequestQueue(myRequest);   //add the request to the queue

                handleSignInResult(result);                 //call the method to handle the sign in
            }
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

    private void updateUI(boolean signedIn)
    {
        if (signedIn)
        {

            findViewById(R.id.sign_in_button).setVisibility(View.GONE);
            Intent intent = new Intent(MainActivity.this,HomeScreen.class);

            //pass the gmail username and email
            intent.putExtra("getinfo", personName);
            intent.putExtra("getemail", personEmail);
            startActivity(intent);
        }
        else
        {
            findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                getIdToken();
                break;
        }
    }






}