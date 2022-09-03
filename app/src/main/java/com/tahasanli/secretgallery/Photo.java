package com.tahasanli.secretgallery;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;

public class Photo {
    public int ID;
    public Bitmap photo;
    public String name;
    public String date;

    public Photo(){}
    public Photo(Bitmap p, String n, String d){
        this.photo = p;
        this.name = n;
        this.date = d;
    }

    public static ArrayList<Photo> GetAllPhotos(){
        ArrayList<Photo> retArr = new ArrayList<Photo>();

        try{
            Cursor cur = MainActivity.instance.db.rawQuery("SELECT * FROM " + MainActivity.DBTableName, null );

            int IDIndex = cur.getColumnIndex("id");
            int NameIndex = cur.getColumnIndex("name");
            int DateIndex = cur.getColumnIndex("date");
            int ImageIndex = cur.getColumnIndex("image");

            while(cur.moveToNext()){
                Photo tmp = new Photo();
                tmp.ID = cur.getInt(IDIndex);
                tmp.name = cur.getString(NameIndex);
                tmp.date = cur.getString(DateIndex);
                byte[] byteArr = cur.getBlob(ImageIndex);
                tmp.photo = BitmapFactory.decodeByteArray(byteArr, 0, byteArr.length);
                retArr.add(tmp);
            }

            cur.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return retArr;

    }

    public void InsertPhoto(){
        byte[] photoBinDump;
        photo = MakeSmallerBitmaps(photo, 300);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.PNG, 50, outputStream);
        photoBinDump = outputStream.toByteArray();

        try {
            String sqlCom = "INSERT INTO " + MainActivity.DBTableName + " (name, date, image) VALUES(?, ?, ?)";
            SQLiteStatement statement = MainActivity.instance.db.compileStatement(sqlCom);
            statement.bindString(1, name);
            statement.bindString(2, date);
            statement.bindBlob(3, photoBinDump);
            statement.execute();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    private Bitmap MakeSmallerBitmaps(Bitmap bm, int MaximumEdge){
        int width = bm.getWidth();
        int height = bm.getHeight();

        float AspectRatio = (float)width / (float)height;

        if(AspectRatio > 1){
            width = MaximumEdge;
            height = (int)(MaximumEdge / AspectRatio);
        }
        else {
            height = MaximumEdge;
            width = (int)(MaximumEdge * AspectRatio);
        }

        return bm.createScaledBitmap(bm, width, height, true);
    }
}
