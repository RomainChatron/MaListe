package com.grenoble.miage.maliste;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.grenoble.miage.maliste.persistence.StorageService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Romain on 20/02/2017.
 */

public class SQLiteStorageServiceImpl implements StorageService {


    @Override
    public List<String> store(Context context, List<String> articles) {
        ArticleOpenHelper db = new ArticleOpenHelper(context);
        Collections.sort(articles, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.trim().compareToIgnoreCase(o2.trim());
            }
        });
        ContentValues value = new ContentValues();
        for(String article : articles) {
            value.put(ArticleOpenHelper.ARTICLE_COL_NAME, article);
        }
        db.getWritableDatabase().insert(ArticleOpenHelper.ARTICLE_TABLE_NAME,
                null, value);
        return articles;
    }

    @Override
    public List<String> restore(Context context) {
        List<String> listArticles = new ArrayList<>();
        ArticleOpenHelper db = new ArticleOpenHelper(context);
        String sql = "SELECT " + ArticleOpenHelper.ARTICLE_COL_NAME +
                " FROM " + ArticleOpenHelper.ARTICLE_TABLE_NAME ;
        Cursor c = db.getReadableDatabase().rawQuery(sql, null);
        while(c.moveToNext()) {
            listArticles.add(c.getString(0));
        }
        return listArticles;
    }

    @Override
    public List<String> clear(Context context) {
        ArticleOpenHelper db = new ArticleOpenHelper(context);
        db.getWritableDatabase().delete(ArticleOpenHelper.ARTICLE_TABLE_NAME, null, null);
        return new ArrayList<>();
    }

    @Override
    public void add(Context context, String article) {
        ArticleOpenHelper db = new ArticleOpenHelper(context);
        ContentValues value = new ContentValues();
        value.put(ArticleOpenHelper.ARTICLE_COL_NAME, article);
        db.getWritableDatabase().insert(ArticleOpenHelper.ARTICLE_TABLE_NAME,
                null, value);
    }
}
