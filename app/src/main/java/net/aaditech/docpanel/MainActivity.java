package net.aaditech.docpanel;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceActivity;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainActivity extends ActionBarActivity {
    private static final int REQUEST_SIGNUP = 1;
    private static final int REQUEST_FORGOT_PASSWORD = 1;
    private static final String URL = "http://docpanel.in";
    private StringRequest request;
    private RequestQueue requestQueue;
    private String username;
    private String password;

    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        /*if(isNetworkAvailable())
        {
            Toast.makeText(this,"Network Availble",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "Turn On your network", Toast.LENGTH_SHORT).show();
        }*/
        //setContentView(R.layout.activity_main);
        setContentView(R.layout.login);

        requestQueue = Volley.newRequestQueue(this);




        final TextInputLayout usernameWrapper = (TextInputLayout) findViewById(R.id.usernameWrapper);
        final TextInputLayout passwordWrapper = (TextInputLayout) findViewById(R.id.passwordWrapper);
        final Button login = (Button) findViewById(R.id.login);
        final TextView signup = (TextView) findViewById(R.id.signup);
        final TextView forgotpassword = (TextView) findViewById(R.id.forgotpassword);


        usernameWrapper.setHint("Username");
        passwordWrapper.setHint("Password");

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // STUB
                hideKeyboard();
                username = usernameWrapper.getEditText().getText().toString();
                password = usernameWrapper.getEditText().getText().toString();
                if (!validateEmail(username)) {
                    usernameWrapper.setError("Not a valid email address!");
                } else if (!validatePassword(password)) {
                    passwordWrapper.setError("Not a valid password!");
                } else {
                    usernameWrapper.setErrorEnabled(false);
                    passwordWrapper.setErrorEnabled(false);
                    doLogin();
                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // STUB
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });

        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // STUB
                Intent intent = new Intent(getApplicationContext(), PasswordActivity.class);
                startActivityForResult(intent, REQUEST_FORGOT_PASSWORD);
            }
        });
    }

    /*public boolean isNetworkAvailable()
    {
        ConnectivityManager cm = (ConnectivityManager) mContext .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
        {
            Log.e("Network Testing", "***Available***");
            return true;
        }
        Log.e("Network Testing", "***Not Available***");
        return false;
    }*/


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
    private Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    private Matcher matcher;

    public boolean validateEmail(String email) {
        matcher = pattern.matcher(email);
        return matcher.matches();
    }
    public boolean validatePassword(String password) {
        return password.length() > 5;
    }

    public void doLogin() {
        Toast.makeText(getApplicationContext(), "OK! I'm performing login.", Toast.LENGTH_SHORT).show();

        request = new StringRequest(Request.Method.POST, URL + "/auth/login", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String json="";
                json = new String(response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject myResponse = jsonObject.getJSONObject("data");
                    Toast.makeText(getApplicationContext(),jsonObject.getString("status"),Toast.LENGTH_LONG).show();
                    if(jsonObject.getString("status")=="error")
                    {
                        Toast.makeText(getApplicationContext(),myResponse.getString("message"),Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), myResponse.getString("message"), Toast.LENGTH_LONG).show();
                    }

                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivityForResult(intent, 0);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(getApplicationContext(),"Didn't worked",Toast.LENGTH_LONG).show();
                NetworkResponse response = error.networkResponse;
                String json="";
                if(response != null)//&& response.data != null
                {
                    switch(response.statusCode)
                    {
                        case 412:
                            json = new String(response.data);

                            try {
                                /*JSONObject jObject = new JSONObject(json);
                                String messages = jObject.getString("message");*/

                                JSONObject jsonObject = new JSONObject(json);
                                /*JSONObject myResponse = jsonObject.getJSONObject("message");
                                JSONArray tsmresponse = (JSONArray) myResponse.get("");

                                ArrayList<String> list = new ArrayList<String>();

                                for(int i=0; i<tsmresponse.length(); i++){
                                    list.add(tsmresponse.getJSONObject(i).getString("name"));
                                }

                                System.out.println(list);*/
                                Log.d("tag", "Response message : " + jsonObject.getString("message"));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            Log.d("tag", "Response data : "+json);
                            break;
                        case 500:
                            Toast.makeText(getApplicationContext(),"Something went wrong . Please try after sometime", Toast.LENGTH_LONG).show();
                            break;
                    }
                }
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError{
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("email",username);
                hashMap.put("password",password);
                return hashMap;
            }
        };

        requestQueue.add(request);
    }

    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }











    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
