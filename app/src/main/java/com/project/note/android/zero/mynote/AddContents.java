package com.project.note.android.zero.mynote;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by Administrator on 2016/8/13.
 */
public class AddContents extends Activity implements View.OnClickListener {
    private Button savebtn,cancelbtn;
    private EditText editText;
    private ImageView imageView;
    private VideoView videoView;
    private NotedbHelper notedbHelper;
    private SQLiteDatabase notedb;
    private String intent_data;
    private File photofile,videofile;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addcontents);

        savebtn= (Button) findViewById(R.id.add_save);
        cancelbtn= (Button) findViewById(R.id.add_cancel);
        imageView= (ImageView) findViewById(R.id.add_image);
        videoView= (VideoView) findViewById(R.id.add_video);
        editText= (EditText) findViewById(R.id.add_text);
        notedbHelper=new NotedbHelper(this);
        notedb=notedbHelper.getWritableDatabase();
        intent_data=getIntent().getStringExtra("main");
        savebtn.setOnClickListener(this);
        cancelbtn.setOnClickListener(this);
        selectWay();

    }

    public void selectWay(){
        if(intent_data.equals("text")){
            imageView.setVisibility(View.GONE);
            videoView.setVisibility(View.GONE);
        }
        if(intent_data.equals("image")) {
            imageView.setVisibility(View.VISIBLE);
            videoView.setVisibility(View.GONE);

            Intent iimg = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File tempfile = new File(Environment.getExternalStorageDirectory()
                    .getAbsoluteFile() + "/note");

            if (!tempfile.exists()){
                tempfile.mkdir();
            }
            photofile=new File(tempfile.getAbsolutePath(),System.currentTimeMillis()+".jpg");//以时间命名图片

            iimg.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photofile));
            startActivityForResult(iimg, 1);
        }

//
//            photofile=new File(Environment.getExternalStorageDirectory(),getTime()+".jpg");
//            if(photofile.exists()){
//                photofile.delete();
//            }
//            try {
//                photofile.createNewFile();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            imageUri=Uri.fromFile(photofile);
//            Intent image_intent=new Intent("android.media.action.IMAGE_CAPTURE");
//            image_intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//            startActivityForResult(image_intent,1);//启动相机程序
//
//        }
        if(intent_data.equals("video")){
            imageView.setVisibility(View.GONE);
            videoView.setVisibility(View.VISIBLE);

            Intent video = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            File tempfile = new File(Environment.getExternalStorageDirectory()
                    .getAbsoluteFile() + "/note");
            if (!tempfile.exists()){
                tempfile.mkdir();
            }
            videofile=new File(tempfile.getAbsolutePath(),System.currentTimeMillis()+".mp4");//以时间命名图片



            video.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(videofile));
            startActivityForResult(video, 2);

        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_save:
                if (!editText.getText().toString().isEmpty()){
                    addNote();
                    finish();

                }
                else{
                    Toast.makeText(this,"输入文字不能为空",Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.add_cancel:
                finish();
                break;
        }


    }

    public void addNote(){
        ContentValues values=new ContentValues();
        values.put(NotedbHelper.CONTENT,editText.getText().toString());
        values.put(NotedbHelper.TIME,getTime()+"");
        values.put(NotedbHelper.IMAGE_PATH,photofile+"");
        values.put(NotedbHelper.VIDEO_PATH,videofile+"");
//        System.out.println("-->>"+photofile+"");
//        values.putExtra;

        notedb.insert(NotedbHelper.TABLE_NAME,null,values);

    }

    public String getTime(){
        SimpleDateFormat format=new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date date=new Date();
        String currentdate=format.format(date);
        return currentdate;

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

//        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
//            Bitmap bitmap = BitmapFactory.decodeFile(photofile
//                    .getAbsolutePath());
            Bitmap bitmap= null;
            try {
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(Uri.fromFile(photofile)));
                imageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
        if (requestCode == 2) {
            videoView.setVideoURI(Uri.fromFile(videofile));
            videoView.start();
        }
    }
}
