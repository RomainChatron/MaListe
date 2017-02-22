package com.grenoble.miage.maliste;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.grenoble.miage.maliste.persistence.StorageService;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int KEY_NEW_VALUE = 1 ;
    public static final String NEW_VALUE = "NEW_VALUE";
    private FloatingActionButton fab;
    private ListView lvArticle ;
    private  ArrayAdapter<String> adapter ;
    private MyApplication app ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addIntent = new Intent(MainActivity.this, AddActivity.class);
                startActivityForResult(addIntent, KEY_NEW_VALUE);
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });

        lvArticle = (ListView) findViewById(R.id.listViewArticle);
        // Récupère liste article dans les ressources
        //String[] stringArray = getResources().getStringArray(R.array.article_names);
        //ArrayList<String> listArticle = new ArrayList<>(Arrays.asList(stringArray));
        // Récupère liste article stocké en shared preferences
        app = (MyApplication) getApplication();
        StorageService storageS = app.getStorageService();
        List<String> listArticleSP = storageS.restore(this);
        adapter = new ArrayAdapter<String>(this, R.layout.listview_article,
                listArticleSP);
        lvArticle.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_vider) {
            app.getStorageService().clear(this);
            adapter.clear();
            adapter.notifyDataSetChanged();
            Snackbar.make(lvArticle, R.string.deleteMessage, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == KEY_NEW_VALUE) {
            if(resultCode == RESULT_OK) {
                String value = data.getStringExtra(NEW_VALUE);
                adapter.add(value);
                adapter.notifyDataSetChanged();
                app.getStorageService().add(this, value);
                Snackbar.make(lvArticle, R.string.successMessage, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }
    }
}
