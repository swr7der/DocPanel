package net.aaditech.docpanel;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by ways on 14/3/16.
 */
public class PasswordActivity extends ActionBarActivity {
    private static final int REQUEST_LOGIN = 1;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);

        final TextView login = (TextView) findViewById(R.id.login_page);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivityForResult(intent, REQUEST_LOGIN);
            }
        });
    }
}
