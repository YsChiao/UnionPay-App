package com.example.yisongqiao.unionpayexchangerate;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class MainActivity extends AppCompatActivity {

    private TextView nodes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // locate the TextView and Button
        nodes = (TextView) findViewById(R.id.textView);
        Button button = (Button) findViewById(R.id.button);

        // capture botton click
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new UionPayRate().execute();
            }
        });
    }

    public class UionPayRate extends AsyncTask<Void, Void, Void> {
        private URL url;
        private Document doc;
        private ArrayList<String> list;
        private String string;
        private ProgressDialog progressDialog;

        //@Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setTitle("UnionPay Exchange Tool");
            progressDialog.setMessage("Loading... ");
            progressDialog.setIndeterminate(false);
            progressDialog.show();
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
            nodes.setText(string);
            progressDialog.dismiss();
        }
    }

}
