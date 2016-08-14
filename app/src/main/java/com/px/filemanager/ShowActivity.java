package com.px.filemanager;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by Administrator on 2016/8/13.
 */
public class ShowActivity extends BaseActivity {

    private long backExitTime = 0;
    private static final int RESULT_CODE_1 = 1;

    private ImageButton bt_Select;
    private TextView tv_ApkName, tv_ApkPackageName , tv_ApkSize , tv_ApkVersion , tv_VersionCode ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        bt_Select = (ImageButton) findViewById(R.id.ibt_select);
        tv_ApkName = (TextView) findViewById(R.id.tv_apkName);
        tv_ApkPackageName = (TextView) findViewById(R.id.tv_packageName);
        tv_ApkSize = (TextView) findViewById(R.id.tv_ApkSize);
        tv_ApkVersion = (TextView) findViewById(R.id.tv_appVersion);
        tv_VersionCode = (TextView) findViewById(R.id.tv_versionCode);

        if(savedInstanceState != null) {
            tv_ApkName.setText(savedInstanceState.getString("apkName"));
            tv_ApkPackageName.setText(savedInstanceState.getString("apkPackageName"));
            tv_ApkSize.setText(savedInstanceState.getString("apkSize"));
            tv_ApkVersion.setText(savedInstanceState.getString("apkVersion"));
            tv_VersionCode.setText(savedInstanceState.getString("versionCode"));
        }

        bt_Select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(ShowActivity.this , MainActivity.class) , RESULT_CODE_1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String apkPath = data.getStringExtra("apkPath");
        Log.d("----test----", data.getLongExtra("apkSize" , 0)+"");
        tv_ApkName.setText(data.getStringExtra("apkName"));
        tv_ApkPackageName.setText(getApkPackageName(this , apkPath));
        tv_ApkSize.setText(getApkSize(data.getLongExtra("apkSize" , 0)));
        tv_ApkVersion.setText(getApkVersion(this , apkPath));
        tv_VersionCode.setText(getApkVersionCode(this , apkPath)+"");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("apkName" ,tv_ApkName.getText().toString().trim());
        outState.putString("apkPackageName" ,tv_ApkPackageName.getText().toString().trim());
        outState.putString("apkSize" ,tv_ApkSize.getText().toString().trim());
        outState.putString("apkVersion" ,tv_ApkVersion.getText().toString().trim());
        outState.putString("versionCode" ,tv_VersionCode.getText().toString().trim());
    }

    private String getApkPackageName (Context context , String apkPath) {
        ApplicationInfo applicationInfo = null;
        PackageInfo packageInfo= null;
        String packageName = null;
        PackageManager packageManager = context.getPackageManager();
        packageInfo = packageManager.getPackageArchiveInfo(apkPath , PackageManager.GET_ACTIVITIES);
        if(packageInfo != null){
            applicationInfo = packageInfo.applicationInfo;
            packageName = applicationInfo.packageName;
        }else{
            packageName = "";
        }
        return packageName;
    }

    private String getApkVersion (Context context ,String apkPath) {
        ApplicationInfo applicationInfo = null;
        PackageInfo packageInfo= null;
        String version = null;
        PackageManager packageManager = context.getPackageManager();
        packageInfo = packageManager.getPackageArchiveInfo(apkPath , PackageManager.GET_ACTIVITIES);
        if(packageInfo != null){
            version = packageInfo.versionName;
        }else{
            version = "";
        }
        return version;
    }

    private int getApkVersionCode (Context context ,String apkPath) {
        ApplicationInfo applicationInfo = null;
        PackageInfo packageInfo= null;
        int versionCode = 0;
        PackageManager packageManager = context.getPackageManager();
        packageInfo = packageManager.getPackageArchiveInfo(apkPath , PackageManager.GET_ACTIVITIES);
        if(packageInfo != null){
            versionCode = packageInfo.versionCode;
        }else{
            versionCode = 0;
        }
        return versionCode;
    }

    private String getApkSize (long apkLength) {
        return new DecimalFormat("#.00").format(apkLength*0.01d/1024/1024*100);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - backExitTime) > 2000) {
                Toast.makeText(ShowActivity.this, "Press back again to exit", Toast.LENGTH_SHORT).show();
                backExitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
