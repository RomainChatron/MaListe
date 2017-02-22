package com.grenoble.miage.maliste;

import android.content.Context;
import android.content.SharedPreferences;

import com.grenoble.miage.maliste.persistence.StorageService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Romain on 20/02/2017.
 */

public class SharedPreferencesStorageServiceImpl implements StorageService {
    private final String PREF = "PREF";
    private SharedPreferences preferences ;

    @Override
    public List<String> store(Context context, List<String> articles) {
        preferences = context.getSharedPreferences(PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        // Range par ordre alphabétique
        Collections.sort(articles, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.trim().compareToIgnoreCase(o2.trim());
            }
        });
        editor.putStringSet("listArticles", new HashSet<>(articles));
        editor.commit();
        return articles;
    }

    @Override
    public List<String> restore(Context context) {
        Set<String> setArticles ;
        preferences = context.getSharedPreferences(PREF, Context.MODE_PRIVATE);

        setArticles = preferences.getStringSet("listArticles", new HashSet<String>());
        return new ArrayList<>(setArticles);
    }

    @Override
    public List<String> clear(Context context) {
        preferences = context.getSharedPreferences(PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.remove("listArticles");
        editor.commit();
        return new ArrayList<>();
    }

    @Override
    public void add(Context context, String article) {
        ArrayList<String> listArticles = (ArrayList<String>) restore(context);
        listArticles.add(article);
        store(context, listArticles);
    }

    public void add2(Context context, String article) {
        preferences = context.getSharedPreferences(PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString(article, article);
        editor.apply();
    }
}
