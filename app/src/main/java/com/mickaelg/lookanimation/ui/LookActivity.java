package com.mickaelg.lookanimation.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mickaelg.lookanimation.R;

/**
 * Simple activity holding a LookFragment.
 */
public class LookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_look_fragment_container, LookFragment.newInstance())
                .commit();
    }
}
