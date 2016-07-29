package me.dan.testproj;

import java.io.File;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.huami.watch.companion.device.DeviceManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();

                //toGlideTest();
                //toUnzip();
                toRetrieveDeviceInfo(MainActivity.this);
            }
        });

        //Glide.with(this.getApplicationContext())
        //        .load(R.drawable.ic_discovery_banner_loading)
                //.downloadOnly(400, 400)
                //.preload();
    }

    private void toGlideTest() {
        Intent intent = new Intent(this, SettingsProviderTestActivity.class);
        startActivity(intent);
    }

    private void toUnzip() {
        File file = new File(Environment.getExternalStorageDirectory() + "/agps_data");
        ZipUtil.unzip(file);
    }

    private void toRetrieveDeviceInfo(Context context) {
        //DeviceUtil.retrieveAndroidDeviceId(context);
        //DeviceUtil.retrieveCpuID();
        DeviceManager.getManager().getDeviceInfoJson(context);
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
