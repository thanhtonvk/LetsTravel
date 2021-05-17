package com.tondz.letstravel.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.google.android.gms.maps.model.LatLng;
import com.tondz.letstravel.Model.Account;
import com.tondz.letstravel.Model.CommentNews;
import com.tondz.letstravel.Model.CommentStatus;
import com.tondz.letstravel.Model.News;
import com.tondz.letstravel.Model.Places;
import com.tondz.letstravel.Model.Status;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {

    public Database(@Nullable Context context) {
        super(context, "Database.sqlite", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table account(" +
                "username text primary key," +
                "password text not null," +
                "fullname text not null," +
                "dateofbirth datetime," +
                "email text not null," +
                "phonenumber char(10)," +
                "avatar blob," +
                "permission text)");

        db.execSQL("create table commentnews(" +
                "id text primary key," +
                "id_news text not null," +
                "username text not null," +
                "content text)");


        db.execSQL("create table commentstatus(" +
                "id text primary key," +
                "id_status text," +
                "username text," +
                "content text)");

        db.execSQL("create table news(" +
                "id text primary key," +
                "titile text," +
                "content text," +
                "id_places text," +
                "image blob)");

        db.execSQL("create table places(" +
                "id text primary key," +
                "name text," +
                "lat float not null," +
                "lng float not null)");


        db.execSQL("create table status(" +
                "id text primary key," +
                "content text," +
                "image blob," +
                "lat float," +
                "lng float," +
                "username text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists account");
        db.execSQL("drop table if exists commentnews");
        db.execSQL("drop table if exists commentstatus");
        db.execSQL("drop table if exists news");
        db.execSQL("drop table if exists places");
        db.execSQL("drop table if exists status");
    }

    public void addAccount(Account account) {
        //    String username, String password, String fullname, String dateofbirth, String email, String phonenumber, byte[] avatar, String permission
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", account.getUsername());
        values.put("password", account.getPassword());
        values.put("fullname", account.getFullname());
        values.put("dateofbirth", account.getDateofbirth());
        values.put("email", account.getEmail());
        values.put("phonenumber", account.getPhonenumber());
        values.put("avatar", account.getAvatar());
        values.put("permission", account.getPermission());
        database.insert("account", null, values);
        database.close();
    }

    public List<Account> getAllAccounts() {
        List<Account> accountList = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery("select * from account", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Account account = new Account(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getBlob(6), cursor.getString(7));
            accountList.add(account);
            cursor.moveToNext();
        }
        database.close();

        return accountList;
    }

    public void editAccount(Account account) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", account.getUsername());
        values.put("password", account.getPassword());
        values.put("fullname", account.getFullname());
        values.put("dateofbirth", account.getDateofbirth());
        values.put("email", account.getEmail());
        values.put("phonenumber", account.getPhonenumber());
        values.put("avatar", account.getAvatar());
        values.put("permission", account.getPermission());
        database.update("account", values, "username = ?", new String[]{account.getUsername()});
        database.close();
    }

    public void deleteAccount(Account account) {
        SQLiteDatabase database = getWritableDatabase();
        database.delete("account", "username = ?", new String[]{account.getUsername()});
        database.close();
    }

    public void addCommentNews(CommentNews commentNews) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        //        private String id,id_news,username,content;
        values.put("id", commentNews.getId());
        values.put("id_news", commentNews.getId_news());
        values.put("username", commentNews.getUsername());
        values.put("content", commentNews.getContent());
        database.insert("commentnews", null, values);
        database.close();
    }

    public List<CommentNews> getAllCommentNews() {
        List<CommentNews> commentNewsList = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery("select * from commentnews", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            CommentNews commentNews = new CommentNews(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
            commentNewsList.add(commentNews);
            cursor.moveToNext();
        }
        database.close();
  
        return commentNewsList;
    }

    public void editCommentNews(CommentNews commentNews) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        //        private String id,id_news,username,content;
        values.put("id", commentNews.getId());
        values.put("id_news", commentNews.getId_news());
        values.put("username", commentNews.getUsername());
        values.put("content", commentNews.getContent());
        database.update("commentnews", values, "id = ?", new String[]{commentNews.getId()});
        database.close();
    }

    public void deleteCommentNews(CommentNews commentNews) {
        SQLiteDatabase database = getWritableDatabase();
        database.delete("commentnews", "id = ?", new String[]{commentNews.getId()});
        database.close();
    }

    //        private String id,id_status,username,content;
    public void addCommentStatus(CommentStatus commentStatus) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        //        private String id,id_news,username,content;
        values.put("id", commentStatus.getId());
        values.put("id_status", commentStatus.getId_status());
        values.put("username", commentStatus.getUsername());
        values.put("content", commentStatus.getContent());
        database.insert("commentstatus", null, values);
        database.close();
    }

    public List<CommentStatus> getAllCommenStatuss() {
        List<CommentStatus> commentStatusList = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery("select * from commentstatus", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            CommentStatus commentStatus = new CommentStatus(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
            commentStatusList.add(commentStatus);
            cursor.moveToNext();
        }
        database.close();
        return commentStatusList;
    }

    public void editCommentStatus(CommentStatus commentStatus) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        //        private String id,id_news,username,content;
        values.put("id", commentStatus.getId());
        values.put("id_status", commentStatus.getId_status());
        values.put("username", commentStatus.getUsername());
        values.put("content", commentStatus.getContent());
        database.update("commentstatus", values, "id = ?", new String[]{commentStatus.getId()});
        database.close();
    }

    public void deleteCommentStatus(CommentStatus commentStatus) {
        SQLiteDatabase database = getWritableDatabase();
        database.delete("commentstatus", "id = ?", new String[]{commentStatus.getId()});
        database.close();
    }

    //  private String id,content,id_places;
    public void addNews(News news) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", news.getId());
        values.put("titile",news.getTitle());
        values.put("content", news.getContent());
        values.put("id_places", news.getId_places());
        values.put("image", news.getImage());
        database.insert("news", null, values);
        database.close();
    }

    public void editNews(News news) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", news.getId());
        values.put("titile",news.getTitle());
        values.put("content", news.getContent());
        values.put("id_places", news.getId_places());
        values.put("image", news.getImage());
        database.update("news", values, "id = ?",new  String[]{news.getId()});
        database.close();
    }

    public void deleteNews(News news) {
        SQLiteDatabase database = getWritableDatabase();
        database.delete("news", "id = ?", new String[]{news.getId()});
        database.close();
    }

    public List<News> getAllNews() {
        List<News> newsList = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery("select * from news", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            News news = new News(cursor.getString(0), cursor.getString(1), cursor.getString(2),cursor.getString(3), cursor.getBlob(4));
            newsList.add(news);
            cursor.moveToNext();
        }
        database.close();
        return newsList;
    }

    //        private String id,name;
//        private LatLng latLng;
    public List<Places> getAllPlaces() {
        List<Places> placesList = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery("select * from places", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Places places = new Places(cursor.getString(0), cursor.getString(1), new LatLng(cursor.getFloat(2), cursor.getFloat(3)));
            placesList.add(places);
            cursor.moveToNext();
        }
        database.close();
      
        return placesList;
    }

    public void addPlaces(Places places) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", places.getId());
        values.put("name", places.getName());
        values.put("lat", places.getLatLng().latitude);
        values.put("lng", places.getLatLng().longitude);
        database.insert("places", null, values);
        database.close();
    }

    public void editPlaces(Places places) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", places.getId());
        values.put("name", places.getName());
        values.put("lat", places.getLatLng().latitude);
        values.put("lng", places.getLatLng().longitude);
        database.update("places", values, "id = ?", new String[]{places.getId()});
        database.close();
    }

    public void deletePlaces(Places places) {
        SQLiteDatabase database = getWritableDatabase();
        database.delete("places", "id = ?", new String[]{places.getId()});
        database.close();
    }

    //        private String id,content;
    //      private byte[]image;
    public List<Status> getAllStatus() {
        List<Status> statusList = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery("select * from status", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Status status = new Status(cursor.getString(0), cursor.getString(1),cursor.getBlob(2),new LatLng(cursor.getFloat(3),cursor.getFloat(4)),cursor.getString(5));
            statusList.add(status);
            cursor.moveToNext();
        }
        database.close();
      
        return statusList;
    }
    public void addStatus(Status status){
        SQLiteDatabase database= getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id",status.getId());
        values.put("content",status.getContent());
        values.put("image",status.getImage());
        values.put("lat",status.getLatLng().latitude);
        values.put("lng",status.getLatLng().longitude);
        values.put("username",status.getUsername());
        database.insert("status",null,values);
        database.close();
    }
    public void deleteStatus(Status status){
        SQLiteDatabase database= getWritableDatabase();
        database.delete("status","id = ?",new String[]{status.getId()});
        database.close();
    }
    public void editStatus(Status status){
        SQLiteDatabase database= getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id",status.getId());
        values.put("content",status.getContent());
        values.put("image",status.getImage());
        values.put("lat",status.getLatLng().latitude);
        values.put("lng",status.getLatLng().longitude);
        values.put("username",status.getUsername());
        database.update("status",values,"id = ?",new String[]{status.getId()});
        database.close();
    }
}
