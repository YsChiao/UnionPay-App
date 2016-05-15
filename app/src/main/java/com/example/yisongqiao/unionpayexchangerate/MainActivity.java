package com.example.yisongqiao.unionpayexchangerate;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuView;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private EditText editText1;
    private EditText editText2;
    private Button button0;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Button button6;
    private Button button7;
    private Button button8;
    private Button button9;
    private Button button_point;
    //private Button button_back;
    private Button button_clear;
    //private Button button_done;

    private double textDouble1;
    private double textDouble2;
    private double rate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }


        // hide actionbar
//        if(getSupportActionBar() != null) {
//            getSupportActionBar().hide();
//        }
        //progressBar.setVisibility(View.VISIBLE);
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

        //locate EditText
        editText1 = (EditText) findViewById(R.id.base_currency_input);
        editText2 = (EditText) findViewById(R.id.currency_input);
        button0 = (Button) findViewById(R.id.button_0);
        button1 = (Button) findViewById(R.id.button_1);
        button2 = (Button) findViewById(R.id.button_2);
        button3 = (Button) findViewById(R.id.button_3);
        button4 = (Button) findViewById(R.id.button_4);
        button5 = (Button) findViewById(R.id.button_5);
        button6 = (Button) findViewById(R.id.button_6);
        button7 = (Button) findViewById(R.id.button_7);
        button8 = (Button) findViewById(R.id.button_8);
        button9 = (Button) findViewById(R.id.button_9);
        button_point = (Button) findViewById(R.id.button_point);
        //button_back = (Button) findViewById(R.id.button_back);
        button_clear = (Button) findViewById(R.id.button_clear);
        //button_done = (Button) findViewById(R.id.button_done);

        // set input soft keyboard always hide
        editText1.setInputType(InputType.TYPE_NULL);
        editText2.setInputType(InputType.TYPE_NULL);

        // set editText1 focus as default
        editText1.requestFocus();


        button0.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           if (editText1.hasFocus()) {
                                               editText1.setText(editText1.getText().insert(editText1.getText().length(), "0"));
                                               String string = editText1.getText().toString();
                                               textDouble1 = Double.parseDouble(string);

                                               // calculate textDouble2 value and show
                                               Double data2 = textDouble1 / rate;
                                               NumberFormat nm = NumberFormat.getNumberInstance();
                                               nm.setMaximumFractionDigits(2);
                                               nm.setMinimumFractionDigits(2);
                                               editText2.setText(nm.format(data2));

                                           } else if (editText2.hasFocus())

                                           {
                                               editText2.setText(editText2.getText().insert(editText2.getText().length(), "0"));
                                               String string = editText2.getText().toString();
                                               textDouble2 = Double.parseDouble(string);

                                               // calculate textDouble2 value and show
                                               Double data1 = textDouble2 * rate;
                                               NumberFormat nm = NumberFormat.getNumberInstance();
                                               nm.setMaximumFractionDigits(2);
                                               nm.setMinimumFractionDigits(2);
                                               editText1.setText(nm.format(data1));
                                           }
                                       }
                                   }

        );

        button1.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           if (editText1.hasFocus()) {
                                               editText1.setText(editText1.getText().insert(editText1.getText().length(), "1"));
                                               String string = editText1.getText().toString();
                                               textDouble1 = Double.parseDouble(string);

                                               // calculate textDouble2 value and show
                                               Double data2 = textDouble1 / rate;
                                               NumberFormat nm = NumberFormat.getNumberInstance();
                                               nm.setMaximumFractionDigits(2);
                                               nm.setMinimumFractionDigits(2);
                                               editText2.setText(nm.format(data2));

                                           } else if (editText2.hasFocus()) {
                                               editText2.setText(editText2.getText().insert(editText2.getText().length(), "1"));
                                               String string = editText2.getText().toString();
                                               textDouble2 = Double.parseDouble(string);

                                               // calculate textDouble2 value and show
                                               Double data1 = textDouble2 * rate;
                                               NumberFormat nm = NumberFormat.getNumberInstance();
                                               nm.setMaximumFractionDigits(2);
                                               nm.setMinimumFractionDigits(2);
                                               editText1.setText(nm.format(data1));
                                           }
                                       }
                                   }

        );

        button2.setOnClickListener(new View.OnClickListener()

                                   {
                                       @Override
                                       public void onClick(View v) {
                                           if (editText1.hasFocus()) {
                                               editText1.setText(editText1.getText().insert(editText1.getText().length(), "2"));
                                               String string = editText1.getText().toString();
                                               textDouble1 = Double.parseDouble(string);

                                               // calculate textDouble2 value and show
                                               Double data2 = textDouble1 / rate;
                                               NumberFormat nm = NumberFormat.getNumberInstance();
                                               nm.setMaximumFractionDigits(2);
                                               nm.setMinimumFractionDigits(2);
                                               editText2.setText(nm.format(data2));
                                           } else if (editText2.hasFocus()) {
                                               editText2.setText(editText2.getText().insert(editText2.getText().length(), "2"));
                                               String string = editText2.getText().toString();
                                               textDouble2 = Double.parseDouble(string);

                                               // calculate textDouble2 value and show
                                               Double data1 = textDouble2 * rate;
                                               NumberFormat nm = NumberFormat.getNumberInstance();
                                               nm.setMaximumFractionDigits(2);
                                               nm.setMinimumFractionDigits(2);
                                               editText1.setText(nm.format(data1));
                                           }
                                       }
                                   }

        );

        button3.setOnClickListener(new View.OnClickListener()

                                   {
                                       @Override
                                       public void onClick(View v) {
                                           if (editText1.hasFocus()) {
                                               editText1.setText(editText1.getText().insert(editText1.getText().length(), "3"));
                                               String string = editText1.getText().toString();
                                               textDouble1 = Double.parseDouble(string);

                                               // calculate textDouble2 value and show
                                               Double data2 = textDouble1 / rate;
                                               NumberFormat nm = NumberFormat.getNumberInstance();
                                               nm.setMaximumFractionDigits(2);
                                               nm.setMinimumFractionDigits(2);
                                               editText2.setText(nm.format(data2));
                                           } else if (editText2.hasFocus()) {
                                               editText2.setText(editText2.getText().insert(editText2.getText().length(), "3"));
                                               String string = editText2.getText().toString();
                                               textDouble2 = Double.parseDouble(string);

                                               // calculate textDouble2 value and show
                                               Double data1 = textDouble2 * rate;
                                               NumberFormat nm = NumberFormat.getNumberInstance();
                                               nm.setMaximumFractionDigits(2);
                                               nm.setMinimumFractionDigits(2);
                                               editText1.setText(nm.format(data1));
                                           }
                                       }
                                   }

        );

        button4.setOnClickListener(new View.OnClickListener()

                                   {
                                       @Override
                                       public void onClick(View v) {
                                           if (editText1.hasFocus()) {
                                               editText1.setText(editText1.getText().insert(editText1.getText().length(), "4"));
                                               String string = editText1.getText().toString();
                                               textDouble1 = Double.parseDouble(string);

                                               // calculate textDouble2 value and show
                                               Double data2 = textDouble1 / rate;
                                               NumberFormat nm = NumberFormat.getNumberInstance();
                                               nm.setMaximumFractionDigits(2);
                                               nm.setMinimumFractionDigits(2);
                                               editText2.setText(nm.format(data2));
                                           } else if (editText2.hasFocus()) {
                                               editText2.setText(editText2.getText().insert(editText2.getText().length(), "4"));
                                               String string = editText2.getText().toString();
                                               textDouble2 = Double.parseDouble(string);

                                               // calculate textDouble2 value and show
                                               Double data1 = textDouble2 * rate;
                                               NumberFormat nm = NumberFormat.getNumberInstance();
                                               nm.setMaximumFractionDigits(2);
                                               nm.setMinimumFractionDigits(2);
                                               editText1.setText(nm.format(data1));
                                           }
                                       }
                                   }

        );

        button5.setOnClickListener(new View.OnClickListener()

                                   {
                                       @Override
                                       public void onClick(View v) {
                                           if (editText1.hasFocus()) {
                                               editText1.setText(editText1.getText().insert(editText1.getText().length(), "5"));
                                               String string = editText1.getText().toString();
                                               textDouble1 = Double.parseDouble(string);

                                               // calculate textDouble2 value and show
                                               Double data2 = textDouble1 / rate;
                                               NumberFormat nm = NumberFormat.getNumberInstance();
                                               nm.setMaximumFractionDigits(2);
                                               nm.setMinimumFractionDigits(2);
                                               editText2.setText(nm.format(data2));
                                           } else if (editText2.hasFocus()) {
                                               editText2.setText(editText2.getText().insert(editText2.getText().length(), "5"));
                                               String string = editText2.getText().toString();
                                               textDouble2 = Double.parseDouble(string);

                                               // calculate textDouble2 value and show
                                               Double data1 = textDouble2 * rate;
                                               NumberFormat nm = NumberFormat.getNumberInstance();
                                               nm.setMaximumFractionDigits(2);
                                               nm.setMinimumFractionDigits(2);
                                               editText1.setText(nm.format(data1));
                                           }
                                       }
                                   }

        );

        button6.setOnClickListener(new View.OnClickListener()

                                   {
                                       @Override
                                       public void onClick(View v) {
                                           if (editText1.hasFocus()) {
                                               editText1.setText(editText1.getText().insert(editText1.getText().length(), "6"));
                                               String string = editText1.getText().toString();
                                               textDouble1 = Double.parseDouble(string);

                                               // calculate textDouble2 value and show
                                               Double data2 = textDouble1 / rate;
                                               NumberFormat nm = NumberFormat.getNumberInstance();
                                               nm.setMaximumFractionDigits(2);
                                               nm.setMinimumFractionDigits(2);
                                               editText2.setText(nm.format(data2));
                                           } else if (editText2.hasFocus()) {
                                               editText2.setText(editText2.getText().insert(editText2.getText().length(), "6"));
                                               String string = editText2.getText().toString();
                                               textDouble2 = Double.parseDouble(string);

                                               // calculate textDouble2 value and show
                                               Double data1 = textDouble2 * rate;
                                               NumberFormat nm = NumberFormat.getNumberInstance();
                                               nm.setMaximumFractionDigits(2);
                                               nm.setMinimumFractionDigits(2);
                                               editText1.setText(nm.format(data1));
                                           }
                                       }
                                   }

        );

        button7.setOnClickListener(new View.OnClickListener()

                                   {
                                       @Override
                                       public void onClick(View v) {
                                           if (editText1.hasFocus()) {
                                               editText1.setText(editText1.getText().insert(editText1.getText().length(), "7"));
                                               String string = editText1.getText().toString();
                                               textDouble1 = Double.parseDouble(string);

                                               // calculate textDouble2 value and show
                                               Double data2 = textDouble1 / rate;
                                               NumberFormat nm = NumberFormat.getNumberInstance();
                                               nm.setMaximumFractionDigits(2);
                                               nm.setMinimumFractionDigits(2);
                                               editText2.setText(nm.format(data2));
                                           } else if (editText2.hasFocus()) {
                                               editText2.setText(editText2.getText().insert(editText2.getText().length(), "7"));
                                               String string = editText2.getText().toString();
                                               textDouble2 = Double.parseDouble(string);

                                               // calculate textDouble2 value and show
                                               Double data1 = textDouble2 * rate;
                                               NumberFormat nm = NumberFormat.getNumberInstance();
                                               nm.setMaximumFractionDigits(2);
                                               nm.setMinimumFractionDigits(2);
                                               editText1.setText(nm.format(data1));
                                           }
                                       }
                                   }

        );

        button8.setOnClickListener(new View.OnClickListener()

                                   {
                                       @Override
                                       public void onClick(View v) {
                                           if (editText1.hasFocus()) {
                                               editText1.setText(editText1.getText().insert(editText1.getText().length(), "8"));
                                               String string = editText1.getText().toString();
                                               textDouble1 = Double.parseDouble(string);

                                               // calculate textDouble2 value and show
                                               Double data2 = textDouble1 / rate;
                                               NumberFormat nm = NumberFormat.getNumberInstance();
                                               nm.setMaximumFractionDigits(2);
                                               nm.setMinimumFractionDigits(2);
                                               editText2.setText(nm.format(data2));
                                           } else if (editText2.hasFocus()) {
                                               editText2.setText(editText2.getText().insert(editText2.getText().length(), "8"));
                                               String string = editText2.getText().toString();
                                               textDouble2 = Double.parseDouble(string);

                                               // calculate textDouble2 value and show
                                               Double data1 = textDouble2 * rate;
                                               NumberFormat nm = NumberFormat.getNumberInstance();
                                               nm.setMaximumFractionDigits(2);
                                               nm.setMinimumFractionDigits(2);
                                               editText1.setText(nm.format(data1));
                                           }
                                       }
                                   }

        );

        button9.setOnClickListener(new View.OnClickListener()

                                   {
                                       @Override
                                       public void onClick(View v) {
                                           if (editText1.hasFocus()) {
                                               editText1.setText(editText1.getText().insert(editText1.getText().length(), "9"));
                                               String string = editText1.getText().toString();
                                               textDouble1 = Double.parseDouble(string);

                                               // calculate textDouble2 value and show
                                               Double data2 = textDouble1 / rate;
                                               NumberFormat nm = NumberFormat.getNumberInstance();
                                               nm.setMaximumFractionDigits(2);
                                               nm.setMinimumFractionDigits(2);
                                               editText2.setText(nm.format(data2));
                                           } else if (editText2.hasFocus()) {
                                               editText2.setText(editText2.getText().insert(editText2.getText().length(), "9"));
                                               String string = editText2.getText().toString();
                                               textDouble2 = Double.parseDouble(string);

                                               // calculate textDouble2 value and show
                                               Double data1 = textDouble2 * rate;
                                               NumberFormat nm = NumberFormat.getNumberInstance();
                                               nm.setMaximumFractionDigits(2);
                                               nm.setMinimumFractionDigits(2);
                                               editText1.setText(nm.format(data1));
                                           }
                                       }
                                   }

        );

        button_point.setOnClickListener(new View.OnClickListener()

                                        {
                                            @Override
                                            public void onClick(View v) {
                                                if (editText1.hasFocus()) {
                                                    editText1.setText(editText1.getText().insert(editText1.getText().length(), "."));
                                                    String string = editText1.getText().toString();
                                                    textDouble1 = Double.parseDouble(string);
                                                } else if (editText2.hasFocus()) {
                                                    editText2.setText(editText2.getText().insert(editText2.getText().length(), "."));
                                                    String string = editText2.getText().toString();
                                                    textDouble2 = Double.parseDouble(string);
                                                }
                                            }
                                        }

        );

//        button_back.setOnClickListener(new View.OnClickListener()
//
//                                       {
//                                           @Override
//                                           public void onClick(View v) {
//                                               if (editText1.hasFocus()) {
//                                                   if (0 == editText1.length()) {
//                                                       return;
//                                                   } else {
//                                                       editText1.setText(editText1.getText().delete(editText1.getText().length() - 1, editText1.getText().length()));
//                                                       String string = editText1.getText().toString();
//                                                       textDouble1 = Double.parseDouble(string);
//
//                                                       // calculate textDouble2 value and show
//                                                       Double data2 = textDouble1 / rate;
//                                                       NumberFormat nm = NumberFormat.getNumberInstance();
//                                                       nm.setMaximumFractionDigits(2);
//                                                       nm.setMinimumFractionDigits(2);
//                                                       editText2.setText(nm.format(data2));
//                                                   }
//                                               } else if (editText2.hasFocus()) {
//                                                   if (0 == editText2.length()) {
//                                                       return;
//                                                   } else {
//                                                       editText2.setText(editText2.getText().delete(editText2.getText().length() - 1, editText2.getText().length()));
//                                                       String string = editText2.getText().toString();
//                                                       textDouble2 = Double.parseDouble(string);
//
//                                                       // calculate textDouble2 value and show
//                                                       Double data1 = textDouble2 * rate;
//                                                       NumberFormat nm = NumberFormat.getNumberInstance();
//                                                       nm.setMaximumFractionDigits(2);
//                                                       nm.setMinimumFractionDigits(2);
//                                                       editText1.setText(nm.format(data1));
//                                                   }
//                                               }
//                                           }
//                                       }
//
//        );

        button_clear.setOnClickListener(new View.OnClickListener()

                                        {
                                            @Override
                                            public void onClick(View v) {
                                                // all editText clear
                                                editText1.setText("");
                                                editText2.setText("");
                                                textDouble1 = 0;
                                                textDouble2 = 0;
                                            }
                                        }

        );

//        button_done.setOnClickListener(new View.OnClickListener()
//
//                                       {
//                                           @Override
//                                           public void onClick(View v) {
//                                               if (editText1.hasFocus()) {
//                                                   if (0 == editText1.length()) {
//                                                       return;
//                                                   } else {
//                                                       String string1 = editText1.getText().toString();
//                                                       textDouble1 = Double.parseDouble(string1);
//                                                       // set value in editText2
//                                                       Double data2 = textDouble1 / rate;
//                                                       NumberFormat nm = NumberFormat.getNumberInstance();
//                                                       nm.setMaximumFractionDigits(2);
//                                                       nm.setMinimumFractionDigits(2);
//                                                       editText2.setText(nm.format(data2));
//                                                   }
//                                               } else if (editText2.hasFocus()) {
//                                                   if (0 == editText2.length()) {
//                                                       return;
//                                                   } else {
//                                                       String string2 = editText2.getText().toString();
//                                                       textDouble2 = Double.parseDouble(string2);
//                                                       // set value in editText1
//                                                       Double data1 = textDouble2 * rate;
//                                                       NumberFormat nm = NumberFormat.getNumberInstance();
//                                                       nm.setMaximumFractionDigits(2);
//                                                       nm.setMinimumFractionDigits(2);
//                                                       editText1.setText(nm.format(data1));
//                                                   }
//                                               }
//                                           }
//
//                                       }
//
//        );

//        editText1.setOnEditorActionListener(new EditText.OnEditorActionListener()
//
//                                            {
//                                                @Override
//                                                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                                                    if (actionId == EditorInfo.IME_ACTION_DONE) {
//                                                        button_done.performClick();
//                                                        return true;
//                                                    }
//                                                    return false;
//                                                }
//                                            }
//
//        );

    }

    // Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
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

        //@Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                url = new URL("http://www.kuaiyilicai.com/huilv/c-eur.html");
                doc = Jsoup.parse(url, 10000);
                Element table = doc.select("table").get(1);
                Elements rows = table.select("tr");
                Elements cols = rows.get(1).select("td");
                list = new ArrayList<String>();
                for (int i = 0; i < cols.size(); i++) {
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
            // String string = list.get(0) + list.get(1) + list.get(2) + "  " + list.get(3) + "  " + list.get(4);
            String temp = "1€=¥" + list.get(3);
            rate = Double.parseDouble(list.get(3));
            textView.setText(temp);
            progressBar.setVisibility(View.GONE);
        }
    }

}
