package com.example.hakob.notepad;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class UserActivity extends AppCompatActivity implements Datable{

    EditText edTitle;
    EditText edScrol;
    TextView tDate;
    Button delButton;
    Button saveButton;

    DatabaseHelper sqlHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    long userId=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        edTitle = (EditText) findViewById(R.id.editTitle);
        edScrol = (EditText) findViewById(R.id.editTextScroll);
        tDate=findViewById(R.id.textDate);
        delButton = (Button) findViewById(R.id.btnDel);
        //saveButton = (Button) findViewById(R.id.btnSave);

        sqlHelper = new DatabaseHelper(this);
        db = sqlHelper.getWritableDatabase();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userId = extras.getLong("id");
        }

        String str = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(new Date());
        tDate.setText(str);

        // если 0, то добавление
        if (userId > 0) {
            // получаем элемент по id из бд
           /* userCursor = db.rawQuery("select * from " + DatabaseHelper.TABLE + " where " +
                    DatabaseHelper.COLUMN_ID + "=?", new String[]{String.valueOf(userId)});*/
           /*String[] columns=new String[]{DatabaseHelper.COLUMTITLE,DatabaseHelper.COLUMDATE};
            String selection=DatabaseHelper.COLUMID + "=?";
            String[] selectionArgs=new String[]{String.valueOf(userId)};*/
            userCursor=db.query(DatabaseHelper.TABLE,null,null,null,null,null,null);
            userCursor.moveToFirst();
            edTitle.setText(userCursor.getString(1));
            tDate.setText(userCursor.getString(2));
            edScrol.setText(userCursor.getString(3));
            userCursor.close();
        } else {
            // скрываем кнопку удаления
              delButton.setVisibility(View.GONE);
        }
    }

    public void save(View view){
        if( TextUtils.isEmpty(edTitle.getText())){

            edTitle.setError( "Title is required!" );

        }else {
            ContentValues cv = new ContentValues();
            cv.put(DatabaseHelper.COLUMTITLE, edTitle.getText().toString());
            cv.put(DatabaseHelper.COLUMDATE, tDate.getText().toString());
            cv.put(DatabaseHelper.COLUMTEXT, edScrol.getText().toString());
            if (userId > 0) {
                db.update(DatabaseHelper.TABLE, cv, DatabaseHelper.COLUMID + "=" + String.valueOf(userId), null);
            } else {
                db.insert(DatabaseHelper.TABLE, null, cv);
            }
            goHome();
        }
    }


    public void delete(View view){

        String selectedId =String.valueOf(userId);
        CustomDialogFragment dialog = new CustomDialogFragment();
        Bundle args = new Bundle();
        args.putString("userid", selectedId);
        dialog.setArguments(args);
        dialog.show(getSupportFragmentManager(), "custom");

    }


    private void goHome(){
        // закрываем подключение
        db.close();
        // переход к главной activity
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }


    @Override
    public void remove(String idName) {
        db.delete(DatabaseHelper.TABLE, "_id = ?", new String[]{idName});
        goHome();
    }



}