package com.example.yisongqiao.unionpayexchangerate;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private TextView textRate;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        // hide actionbar
//        if(getSupportActionBar() != null) {
//            getSupportActionBar().hide();
//        }
        new UnionPayRate().execute();

        //locate the TextView and Button
        textView = (TextView) findViewById(R.id.textView);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new UnionPayRate().execute();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    // Menu
    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.contact_item:
                Toast.makeText(this, "Contact", Toast.LENGTH_SHORT).show();
                break;
            case R.id.about_item:
                Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent);
                //Toast.makeText(this, "About", Toast.LENGTH_SHORT).show();
                break;
            default:
        }
        return true;
    }

    // logic WebContent load
    public class UnionPayRate extends AsyncTask<Void, Void, Void> {
        private URL url;
        private Document doc;
        private ArrayList<String> list;
        private String string;

        //@Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                url = new URL("http://www.kuaiyilicai.com/huilv/c-eur.html");
                doc = Jsoup.parse(url, 3000);
                Element table = doc.select("table").get(1);
                Elements rows = table.select("tr");
                Elements cols = rows.get(1).select("td");
                list = new ArrayList<String>();
                for (int i = 0; i < cols.size(); i++)
                {
                    String col = cols.get(i).text();
                    list.add(col);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            string  = list.get(0) + list.get(1) + list.get(2) + "  " + list.get(3) + "  " + list.get(4);
            String temp = "1€=¥" + list.get(3);
            textView.setText(temp);
        }
    }

}
