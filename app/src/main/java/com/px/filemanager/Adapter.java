package com.px.filemanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2016/8/12.
 */
public class Adapter extends BaseAdapter {

    private Context context ;
    private List<FileInfo> dataList;
    private LayoutInflater layoutInflater;

    public Adapter(Context context, List<FileInfo> dataList) {
        this.context = context;
        this.dataList = dataList;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();
        if(convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_file_info , null);
            viewHolder.tv_FileName = (TextView) convertView.findViewById(R.id.tv_FileName);
            viewHolder.iv_FileIcon = (ImageView) convertView.findViewById(R.id.iv_fileIcon);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if(dataList.get(position).isDirectory){
            viewHolder.iv_FileIcon.setImageResource(R.drawable.folder_72);
        }else {
            viewHolder.iv_FileIcon.setImageResource(R.mipmap.ic_launcher);
        }
        viewHolder.tv_FileName.setText(dataList.get(position).getFileName());

        return convertView;
    }

}

class ViewHolder {
    public TextView tv_FileName;
    public ImageView iv_FileIcon;
}
