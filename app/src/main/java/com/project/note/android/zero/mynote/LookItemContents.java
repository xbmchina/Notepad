package com.project.note.android.zero.mynote;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;
/*
* 点击每一项查看详情页。
* */
public class LookItemContents extends AppCompatActivity implements View.OnClickListener {
    private Button delData,backMain;
    private ImageView showImage;
    private VideoView showVideo;
    private EditText showText;
    private NotedbHelper helper;
    private SQLiteDatabase NoteDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_allcontents);
//        System.out.println("..."+getIntent().getIntExtra(NotedbHelper.ID,0));
        delData= (Button) findViewById(R.id.show_delete);
        backMain= (Button) findViewById(R.id.show_back);
        showImage= (ImageView) findViewById(R.id.image);
        showText= (EditText) findViewById(R.id.text);
        showVideo= (VideoView) findViewById(R.id.video);
        helper=new NotedbHelper(this);
        NoteDb=helper.getWritableDatabase();
        delData.setOnClickListener(this);
        backMain.setOnClickListener(this);
        showView();



    }
    private void showView(){
        if(getIntent().getStringExtra(NotedbHelper.IMAGE_PATH).equals("null")){
            showImage.setVisibility(View.GONE);
        }else{
            showImage.setVisibility(View.VISIBLE);
        }
        if (getIntent().getStringExtra(NotedbHelper.VIDEO_PATH).equals("null")){
            showVideo.setVisibility(View.GONE);
        }else{
            showVideo.setVisibility(View.VISIBLE);
        }
        showText.setText(getIntent().getStringExtra(NotedbHelper.CONTENT));
        Bitmap bitmap= BitmapFactory.decodeFile(getIntent().getStringExtra(NotedbHelper.IMAGE_PATH));
        showImage.setImageBitmap(bitmap);

        showVideo.setVideoURI(Uri.parse(getIntent().getStringExtra(NotedbHelper.VIDEO_PATH)));
        showVideo.start();

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.show_delete:
                deleteData();
                finish();

                break;

            case R.id.show_back:
                finish();

                break;
        }

    }

    public void  deleteData(){
        NoteDb.delete(NotedbHelper.TABLE_NAME,"_id="+getIntent().getIntExtra(NotedbHelper.ID,0),null);
    }
}
