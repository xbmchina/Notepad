package com.project.note.android.zero.mynote;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;

import java.io.FileNotFoundException;

/**
 * Created by Administrator on 2016/8/13.
 *
 * ListView 显示图片缩略图和视频的截图还没有完成
 *
 */
public class MyNoteAdapter extends BaseAdapter {
    private Context context;
    private Cursor cursor;
    private LinearLayout layout;

    public MyNoteAdapter(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;
    }

    @Override
    public int getCount() {
        return cursor.getCount();
    }

    @Override
    public Object getItem(int position) {
        return cursor.getPosition();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

//        LayoutInflater inflater=LayoutInflater.from(context);
//        layout= (LinearLayout) inflater.inflate(R.layout.showlist,null);
//        TextView list_text= (TextView) layout.findViewById(R.id.list_text);
//        TextView list_time= (TextView) layout.findViewById(R.id.list_time);
//        ImageView list_image= (ImageView) layout.findViewById(R.id.list_image);
//        VideoView list_video= (VideoView) layout.findViewById(R.id.list_video);
//
//
//        cursor.moveToPosition(position);
//        String text=cursor.getString(cursor.getColumnIndex(NotedbHelper.CONTENT));
//        String time=cursor.getString(cursor.getColumnIndex(NotedbHelper.TIME));
//        String url=cursor.getString(cursor.getColumnIndex(NotedbHelper.IMAGE_PATH));
//        String video_url=cursor.getString(cursor.getColumnIndex(NotedbHelper.VIDEO_PATH));
//
//
//        list_text.setText(text);
//        list_time.setText(time);
//        list_image.setImageBitmap(setPic(url, 200, 200));
////        list_video.setVideoPath(video_url);
//
////        list_video.setImageBitmap(getVideoThumbnail(video_url,200,200, MediaStore.Images.Thumbnails.MICRO_KIND));
////        System.out.println(",,successful");
//
//        return layout;

//ListView的最佳适配方式，使用ViewHolder
        ViewHolder holder;
        if(convertView==null){
            convertView=LayoutInflater.from(context).inflate(R.layout.showlist,null);
            holder=new ViewHolder();
            holder.list_text= (TextView) convertView.findViewById(R.id.list_text);
            holder.list_time= (TextView) convertView.findViewById(R.id.list_time);
            holder.list_image= (ImageView) convertView.findViewById(R.id.list_image);
            holder.list_video= (VideoView) convertView.findViewById(R.id.list_video);

            cursor.moveToPosition(position);
            String text=cursor.getString(cursor.getColumnIndex(NotedbHelper.CONTENT));
            String time=cursor.getString(cursor.getColumnIndex(NotedbHelper.TIME));
            String url=cursor.getString(cursor.getColumnIndex(NotedbHelper.IMAGE_PATH));
            String video_url=cursor.getString(cursor.getColumnIndex(NotedbHelper.VIDEO_PATH));

            System.out.println("lll." + url);
            holder.list_text.setText(text);
            holder.list_time.setText(time);

            if(url.equals("null")){
                holder.list_image.setVisibility(View.VISIBLE);
                holder.list_image.setImageBitmap(setPic(url, 50, 50));
            }
//        list_video.setVideoPath(video_url);

//        list_video.setImageBitmap(getVideoThumbnail(video_url,200,200, MediaStore.Images.Thumbnails.MICRO_KIND));
//        System.out.println(",,successful");

            convertView.setTag(holder);//将ViewHolder存储在view中
        }else {
            holder= (ViewHolder) convertView.getTag();//重新加载ViewHolder,这样的做法就是当屏幕不够时，加载数据直接使用原来的View,不用再新建一个了。

            cursor.moveToPosition(position);
            String text=cursor.getString(cursor.getColumnIndex(NotedbHelper.CONTENT));
            String time=cursor.getString(cursor.getColumnIndex(NotedbHelper.TIME));
            String url=cursor.getString(cursor.getColumnIndex(NotedbHelper.IMAGE_PATH));
            String video_url=cursor.getString(cursor.getColumnIndex(NotedbHelper.VIDEO_PATH));

            holder.list_text.setText(text);//,为了ListView加载数据时不出现卡顿的现象。
            holder.list_time.setText(time);
            if(url.equals("null")){
                holder.list_image.setVisibility(View.VISIBLE);
                holder.list_image.setImageBitmap(setPic(url, 50, 50));
            }

        }

        return convertView;

    }


    public static class ViewHolder{
        TextView list_text;
        TextView list_time;
        ImageView list_image;
        VideoView list_video;

    }


    private Bitmap setPic(String uri,int targetW,int targetH ) {
        // Get the dimensions of the View
//        int targetW = mImageView.getWidth();
//        int targetH = mImageView.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(uri, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW / targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;
        Bitmap bitmap=null;
        bitmap = BitmapFactory.decodeFile(uri, bmOptions);
//            bitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(Uri.parse(uri)),null,bmOptions);

        System.out.println("///"+bitmap);
//        mImageView.setImageBitmap(bitmap);

        return bitmap;
    }



    public Bitmap getImageThumbnail(String uri, int width, int height) {
        Bitmap bitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        bitmap = BitmapFactory.decodeFile(uri, options);
        options.inJustDecodeBounds = false;
        int beWidth = options.outWidth / width;
        int beHeight = options.outHeight / height;
        int be = 1;
        if (beWidth < beHeight) {
            be = beWidth;
        } else {
            be = beHeight;
        }
        if (be <= 0) {
            be = 1;
        }
        options.inSampleSize = be;


        bitmap = BitmapFactory.decodeFile(uri, options);
//            bitmap=BitmapFactory.decodeStream(context.getContentResolver().openInputStream(Uri.parse(uri)),null,options);


        bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
                ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        return bitmap;
    }

    public Bitmap getVideoThumbnail(String uri,int width,int height,int kind){
        Bitmap bitmap=null;
        bitmap=ThumbnailUtils.createVideoThumbnail(uri, kind);
        bitmap=ThumbnailUtils.extractThumbnail(bitmap,width,height,ThumbnailUtils.OPTIONS_RECYCLE_INPUT);

        return bitmap;

    }

//private Bitmap createVideoThumbnail(String filePath){
//
//    Bitmap bitmap=null;
//    MediaMetadataRetriever retriever=new MediaMetadataRetriever();
//    retriever
//
//    return bitmap;
//}


}
