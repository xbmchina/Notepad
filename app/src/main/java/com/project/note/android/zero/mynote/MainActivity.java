package com.project.note.android.zero.mynote;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
/*ListView显示缩略图还没实现，还需要添加闹钟以及通知栏提示。
1、需要知识ListView ,Adapter,SQLiteOpenHelper,SQLiteDatabase,Intent,
2、启动系统相机拍照和视频，保存图片以及使用时间命名System.currentTimeMillis()方式

3、获取缩略图，显示在ListView上。

*
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button textbtn,imagebtn,videobtn;
    private ListView list_content;
    private Intent intent;
    private MyNoteAdapter myAdpter;
    private NotedbHelper notedbHelper;
    private SQLiteDatabase noteDb;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textbtn= (Button) findViewById(R.id.start_text);
        imagebtn= (Button) findViewById(R.id.start_image);
        videobtn= (Button) findViewById(R.id.start_video);
        list_content= (ListView) findViewById(R.id.list_content);
        notedbHelper=new NotedbHelper(this);
        noteDb=notedbHelper.getReadableDatabase();

        textbtn.setOnClickListener(this);
        imagebtn.setOnClickListener(this);
        videobtn.setOnClickListener(this);

        list_content.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cursor.moveToPosition(position);
                Intent i=new Intent(MainActivity.this,LookItemContents.class);
                i.putExtra(NotedbHelper.ID,cursor.getInt(cursor.getColumnIndex(NotedbHelper.ID)));
                i.putExtra(NotedbHelper.CONTENT,cursor.getString(cursor.getColumnIndex(NotedbHelper.CONTENT)));
                i.putExtra(NotedbHelper.IMAGE_PATH,cursor.getString(cursor.getColumnIndex(NotedbHelper.IMAGE_PATH)));
                i.putExtra(NotedbHelper.VIDEO_PATH,cursor.getString(cursor.getColumnIndex(NotedbHelper.VIDEO_PATH)));

                startActivity(i);

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        listAllContent();
    }

    @Override
    public void onClick(View v) {
        intent=new Intent(this,AddContents.class);
        switch (v.getId()){
            case R.id.start_text:
                intent.putExtra("main","text");
                startActivity(intent);
                break;
            case R.id.start_image:
                intent.putExtra("main","image");
                startActivity(intent);
                break;
            case R.id.start_video:
                intent.putExtra("main","video");
                startActivity(intent);
                break;
        }

    }

    public void listAllContent(){
        cursor=noteDb.query(NotedbHelper.TABLE_NAME,null,null,null,null,null,null);

        myAdpter=new MyNoteAdapter(this,cursor);
        list_content.setAdapter(myAdpter);

    }

}
