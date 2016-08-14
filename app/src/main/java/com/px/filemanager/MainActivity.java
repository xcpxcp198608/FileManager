package com.px.filemanager;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    private TextView tv_FilePath;
    private ListView ll_FileInfo;

    private String sdcardRootPath = Environment.getExternalStorageDirectory().getAbsolutePath();
    private List<FileInfo> mFileList = new ArrayList<>();
    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_FilePath = (TextView) findViewById(R.id.tv_FilePath);
        ll_FileInfo = (ListView) findViewById(R.id.ll_FileInfo);
        updateFileInfo(sdcardRootPath);
    }

    //get all File under current path
    private File [] getCurrentPathFiles (String filePath) {
        File file = new File(filePath);
        File [] files = file.listFiles();
        return files;
    }

    //update data by file path , and change adapter
    private List<FileInfo> updateFileInfo (String filePath) {
        tv_FilePath.setText(filePath);
        if(! mFileList.isEmpty()){
            mFileList.clear();
        }
        File[] currentPathFiles = getCurrentPathFiles(filePath);
        if(currentPathFiles == null){
            return null;
        }
        for (int i = 0; i < currentPathFiles.length; i++) {
//            if(currentPathFiles [i].isHidden())
//                continue;
            String currentFilePath = currentPathFiles[i].getAbsolutePath();
            String currentFileName = currentPathFiles[i].getName();
            boolean isDirectory = false;
            boolean isFolder = false;
            if(currentPathFiles[i].isDirectory()){
                isDirectory =true;
            }
            FileInfo fileInfo = new FileInfo(currentFilePath ,currentFileName , isDirectory);
            mFileList.add(fileInfo);
        }
        //按首2位字幕排序
        Collections.sort(mFileList, new Comparator<FileInfo>() {
            @Override
            public int compare(FileInfo lhs, FileInfo rhs) {
                if(lhs.isDirectory  && rhs.isDirectory){
                    return lhs.getFileName().compareToIgnoreCase(rhs.getFileName());
                }else if (lhs.isDirectory  && ! rhs.isDirectory) {
                    return -1;
                }else if (!lhs.isDirectory  &&  rhs.isDirectory) {
                    return 1;
                }else {
                    return lhs.getFileName().compareToIgnoreCase(rhs.getFileName());
                }
            }
        });
        adapter = new Adapter(MainActivity.this , mFileList);
        ll_FileInfo.setAdapter(adapter);
        ll_FileInfo.setOnItemClickListener(this);
        return mFileList;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        File file = new File (tv_FilePath.getText().toString().trim());
        File parentFile = file.getParentFile();
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if (parentFile== null) {
                startActivity(new Intent(MainActivity.this , ShowActivity.class));
            }else {
                List<FileInfo> fileList = updateFileInfo(parentFile.getAbsolutePath());
                Adapter adapter = new Adapter(MainActivity.this , fileList);
                ll_FileInfo.setAdapter(adapter);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(mFileList.get(position).isDirectory) {
            updateFileInfo(mFileList.get(position).getFileAbsolutePath());
        }else {
            String fileExtension = getFileExtension(mFileList.get(position).getFileName());
            Log.d("----test----" , fileExtension);
            if("apk".equals(fileExtension)){
                Intent intent = new Intent();
                intent.putExtra("apkPath" , mFileList.get(position).getFileAbsolutePath());
                intent.putExtra("apkName" , mFileList.get(position).getFileName());
                intent.putExtra("apkSize" , new File(mFileList.get(position).getFileAbsolutePath()).length());
                setResult(RESULT_OK , intent);
                finish();
            }else{

            }
        }

    }

    public String getFileExtension (String fileName) {
        if(fileName != null && fileName.length() >0) {
            int i  = fileName.lastIndexOf('.');
            if(i> -1 && i < fileName.length() -1) {
                return fileName.substring(i+1);
            }
        }
        return  fileName;
    }
}
