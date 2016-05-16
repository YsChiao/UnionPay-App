package com.example.yisongqiao.unionpayexchangerate;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Locale;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private TextView base_currency_view;
    private TextView currency_view;
    private ProgressDialog progressDialog;
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
    private Button button_clear;

    private double textDouble1;
    private double textDouble2;
    private double rate;
    private boolean point_bool = false;
    private boolean timeout_bool = false;

    public BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int internetStatus = NetworkUtil.getConnectivityStatus(context);
            String status = NetworkUtil.getConnectivityStatusString(context);
            Log.v("BroadcastReceiver", status);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
            //progressBar.setVisibility(View.VISIBLE);
        }

        progressDialog = new ProgressDialog(MainActivity.this);
//        progressDialog.setTitle("Internet connecting");
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        // hide actionbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        //progressBar.setVisibility(View.VISIBLE);
        new UnionPayRate().execute();


        //locate the TextView and Button
        textView = (TextView) findViewById(R.id.textView);
        base_currency_view = (TextView) findViewById(R.id.base_currency);
        currency_view = (TextView) findViewById(R.id.currency);

        // swipe refresh
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
        button_clear = (Button) findViewById(R.id.button_clear);

        // set input soft keyboard always hide
        editText1.setInputType(InputType.TYPE_NULL);
        editText2.setInputType(InputType.TYPE_NULL);

        // set editText1 focus as default
        editText1.requestFocus();
        base_currency_view.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorSelected));
        editText1.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorSelected));

        button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText1.hasFocus()) {
                    editText1.setText(editText1.getText().insert(editText1.getText().length(), "0"));
                    String string = editText1.getText().toString();
                    try {
                        NumberFormat.getNumberInstance(Locale.US).parse(string);
                        Number number = NumberFormat.getNumberInstance(Locale.US).parse(string);
                        textDouble1 = number.doubleValue();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    // calculate textDouble2 value and show
                    Double data2 = textDouble1 / rate;
                    NumberFormat nm = NumberFormat.getNumberInstance();
                    nm.setMaximumFractionDigits(2);
                    nm.setMinimumFractionDigits(2);
                    editText2.setText(nm.format(data2));
                } else if (editText2.hasFocus()) {
                    editText2.setText(editText2.getText().insert(editText2.getText().length(), "0"));
                    String string = editText2.getText().toString();
                    try {
                        NumberFormat.getNumberInstance(Locale.US).parse(string);
                        Number number = NumberFormat.getNumberInstance(Locale.US).parse(string);
                        textDouble2 = number.doubleValue();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    // calculate textDouble2 value and show
                    Double data1 = textDouble2 * rate;
                    NumberFormat nm = NumberFormat.getNumberInstance();
                    nm.setMaximumFractionDigits(2);
                    nm.setMinimumFractionDigits(2);
                    editText1.setText(nm.format(data1));
                }
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText1.hasFocus()) {
                    editText1.setText(editText1.getText().insert(editText1.getText().length(), "1"));
                    String string = editText1.getText().toString();
                    try {
                        NumberFormat.getNumberInstance(Locale.US).parse(string);
                        Number number = NumberFormat.getNumberInstance(Locale.US).parse(string);
                        textDouble1 = number.doubleValue();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    // calculate textDouble2 value and show
                    Double data2 = textDouble1 / rate;
                    NumberFormat nm = NumberFormat.getNumberInstance(Locale.US);
                    nm.setMaximumFractionDigits(2);
                    nm.setMinimumFractionDigits(2);
                    editText2.setText(nm.format(data2));
                } else if (editText2.hasFocus()) {
                    editText2.setText(editText2.getText().insert(editText2.getText().length(), "1"));
                    String string = editText2.getText().toString();
                    try {
                        NumberFormat.getNumberInstance(Locale.US).parse(string);
                        Number number = NumberFormat.getNumberInstance(Locale.US).parse(string);
                        textDouble2 = number.doubleValue();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    // calculate textDouble2 value and show
                    Double data1 = textDouble2 * rate;
                    NumberFormat nm = NumberFormat.getNumberInstance();
                    nm.setMaximumFractionDigits(2);
                    nm.setMinimumFractionDigits(2);
                    editText1.setText(nm.format(data1));
                }
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText1.hasFocus()) {
                    editText1.setText(editText1.getText().insert(editText1.getText().length(), "2"));
                    String string = editText1.getText().toString();
                    try {
                        NumberFormat.getNumberInstance(Locale.US).parse(string);
                        Number number = NumberFormat.getNumberInstance(Locale.US).parse(string);
                        textDouble1 = number.doubleValue();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    // calculate textDouble2 value and show
                    Double data2 = textDouble1 / rate;
                    NumberFormat nm = NumberFormat.getNumberInstance();
                    nm.setMaximumFractionDigits(2);
                    nm.setMinimumFractionDigits(2);
                    editText2.setText(nm.format(data2));
                } else if (editText2.hasFocus()) {
                    editText2.setText(editText2.getText().insert(editText2.getText().length(), "2"));
                    String string = editText2.getText().toString();
                    try {
                        NumberFormat.getNumberInstance(Locale.US).parse(string);
                        Number number = NumberFormat.getNumberInstance(Locale.US).parse(string);
                        textDouble2 = number.doubleValue();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    // calculate textDouble2 value and show
                    Double data1 = textDouble2 * rate;
                    NumberFormat nm = NumberFormat.getNumberInstance();
                    nm.setMaximumFractionDigits(2);
                    nm.setMinimumFractionDigits(2);
                    editText1.setText(nm.format(data1));
                }
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText1.hasFocus()) {
                    editText1.setText(editText1.getText().insert(editText1.getText().length(), "3"));
                    String string = editText1.getText().toString();
                    try {
                        NumberFormat.getNumberInstance(Locale.US).parse(string);
                        Number number = NumberFormat.getNumberInstance(Locale.US).parse(string);
                        textDouble1 = number.doubleValue();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    // calculate textDouble2 value and show
                    Double data2 = textDouble1 / rate;
                    NumberFormat nm = NumberFormat.getNumberInstance();
                    nm.setMaximumFractionDigits(2);
                    nm.setMinimumFractionDigits(2);
                    editText2.setText(nm.format(data2));
                } else if (editText2.hasFocus()) {
                    editText2.setText(editText2.getText().insert(editText2.getText().length(), "3"));
                    String string = editText2.getText().toString();
                    try {
                        NumberFormat.getNumberInstance(Locale.US).parse(string);
                        Number number = NumberFormat.getNumberInstance(Locale.US).parse(string);
                        textDouble2 = number.doubleValue();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    // calculate textDouble2 value and show
                    Double data1 = textDouble2 * rate;
                    NumberFormat nm = NumberFormat.getNumberInstance();
                    nm.setMaximumFractionDigits(2);
                    nm.setMinimumFractionDigits(2);
                    editText1.setText(nm.format(data1));
                }
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText1.hasFocus()) {
                    editText1.setText(editText1.getText().insert(editText1.getText().length(), "4"));
                    String string = editText1.getText().toString();
                    try {
                        NumberFormat.getNumberInstance(Locale.US).parse(string);
                        Number number = NumberFormat.getNumberInstance(Locale.US).parse(string);
                        textDouble1 = number.doubleValue();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    // calculate textDouble2 value and show
                    Double data2 = textDouble1 / rate;
                    NumberFormat nm = NumberFormat.getNumberInstance();
                    nm.setMaximumFractionDigits(2);
                    nm.setMinimumFractionDigits(2);
                    editText2.setText(nm.format(data2));
                } else if (editText2.hasFocus()) {
                    editText2.setText(editText2.getText().insert(editText2.getText().length(), "4"));
                    String string = editText2.getText().toString();
                    try {
                        NumberFormat.getNumberInstance(Locale.US).parse(string);
                        Number number = NumberFormat.getNumberInstance(Locale.US).parse(string);
                        textDouble2 = number.doubleValue();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    // calculate textDouble2 value and show
                    Double data1 = textDouble2 * rate;
                    NumberFormat nm = NumberFormat.getNumberInstance();
                    nm.setMaximumFractionDigits(2);
                    nm.setMinimumFractionDigits(2);
                    editText1.setText(nm.format(data1));
                }
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText1.hasFocus()) {
                    editText1.setText(editText1.getText().insert(editText1.getText().length(), "5"));
                    String string = editText1.getText().toString();
                    try {
                        NumberFormat.getNumberInstance(Locale.US).parse(string);
                        Number number = NumberFormat.getNumberInstance(Locale.US).parse(string);
                        textDouble1 = number.doubleValue();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    // calculate textDouble2 value and show
                    Double data2 = textDouble1 / rate;
                    NumberFormat nm = NumberFormat.getNumberInstance();
                    nm.setMaximumFractionDigits(2);
                    nm.setMinimumFractionDigits(2);
                    editText2.setText(nm.format(data2));
                } else if (editText2.hasFocus()) {
                    editText2.setText(editText2.getText().insert(editText2.getText().length(), "5"));
                    String string = editText2.getText().toString();
                    try {
                        NumberFormat.getNumberInstance(Locale.US).parse(string);
                        Number number = NumberFormat.getNumberInstance(Locale.US).parse(string);
                        textDouble2 = number.doubleValue();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    // calculate textDouble2 value and show
                    Double data1 = textDouble2 * rate;
                    NumberFormat nm = NumberFormat.getNumberInstance();
                    nm.setMaximumFractionDigits(2);
                    nm.setMinimumFractionDigits(2);
                    editText1.setText(nm.format(data1));
                }
            }
        });

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText1.hasFocus()) {
                    editText1.setText(editText1.getText().insert(editText1.getText().length(), "6"));
                    String string = editText1.getText().toString();
                    try {
                        NumberFormat.getNumberInstance(Locale.US).parse(string);
                        Number number = NumberFormat.getNumberInstance(Locale.US).parse(string);
                        textDouble1 = number.doubleValue();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    // calculate textDouble2 value and show
                    Double data2 = textDouble1 / rate;
                    NumberFormat nm = NumberFormat.getNumberInstance();
                    nm.setMaximumFractionDigits(2);
                    nm.setMinimumFractionDigits(2);
                    editText2.setText(nm.format(data2));
                } else if (editText2.hasFocus()) {
                    editText2.setText(editText2.getText().insert(editText2.getText().length(), "6"));
                    String string = editText2.getText().toString();
                    try {
                        NumberFormat.getNumberInstance(Locale.US).parse(string);
                        Number number = NumberFormat.getNumberInstance(Locale.US).parse(string);
                        textDouble2 = number.doubleValue();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    // calculate textDouble2 value and show
                    Double data1 = textDouble2 * rate;
                    NumberFormat nm = NumberFormat.getNumberInstance();
                    nm.setMaximumFractionDigits(2);
                    nm.setMinimumFractionDigits(2);
                    editText1.setText(nm.format(data1));
                }
            }
        });

        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText1.hasFocus()) {
                    editText1.setText(editText1.getText().insert(editText1.getText().length(), "7"));
                    String string = editText1.getText().toString();
                    try {
                        NumberFormat.getNumberInstance(Locale.US).parse(string);
                        Number number = NumberFormat.getNumberInstance(Locale.US).parse(string);
                        textDouble1 = number.doubleValue();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    // calculate textDouble2 value and show
                    Double data2 = textDouble1 / rate;
                    NumberFormat nm = NumberFormat.getNumberInstance();
                    nm.setMaximumFractionDigits(2);
                    nm.setMinimumFractionDigits(2);
                    editText2.setText(nm.format(data2));
                } else if (editText2.hasFocus()) {
                    editText2.setText(editText2.getText().insert(editText2.getText().length(), "7"));
                    String string = editText2.getText().toString();
                    try {
                        NumberFormat.getNumberInstance(Locale.US).parse(string);
                        Number number = NumberFormat.getNumberInstance(Locale.US).parse(string);
                        textDouble2 = number.doubleValue();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    // calculate textDouble2 value and show
                    Double data1 = textDouble2 * rate;
                    NumberFormat nm = NumberFormat.getNumberInstance();
                    nm.setMaximumFractionDigits(2);
                    nm.setMinimumFractionDigits(2);
                    editText1.setText(nm.format(data1));
                }
            }
        });

        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText1.hasFocus()) {
                    editText1.setText(editText1.getText().insert(editText1.getText().length(), "8"));
                    String string = editText1.getText().toString();
                    try {
                        NumberFormat.getNumberInstance(Locale.US).parse(string);
                        Number number = NumberFormat.getNumberInstance(Locale.US).parse(string);
                        textDouble1 = number.doubleValue();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    // calculate textDouble2 value and show
                    Double data2 = textDouble1 / rate;
                    NumberFormat nm = NumberFormat.getNumberInstance();
                    nm.setMaximumFractionDigits(2);
                    nm.setMinimumFractionDigits(2);
                    editText2.setText(nm.format(data2));
                } else if (editText2.hasFocus()) {
                    editText2.setText(editText2.getText().insert(editText2.getText().length(), "8"));
                    String string = editText2.getText().toString();
                    try {
                        NumberFormat.getNumberInstance(Locale.US).parse(string);
                        Number number = NumberFormat.getNumberInstance(Locale.US).parse(string);
                        textDouble2 = number.doubleValue();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    // calculate textDouble2 value and show
                    Double data1 = textDouble2 * rate;
                    NumberFormat nm = NumberFormat.getNumberInstance();
                    nm.setMaximumFractionDigits(2);
                    nm.setMinimumFractionDigits(2);
                    editText1.setText(nm.format(data1));
                }
            }
        });

        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText1.hasFocus()) {
                    editText1.setText(editText1.getText().insert(editText1.getText().length(), "9"));
                    String string = editText1.getText().toString();
                    try {
                        NumberFormat.getNumberInstance(Locale.US).parse(string);
                        Number number = NumberFormat.getNumberInstance(Locale.US).parse(string);
                        textDouble1 = number.doubleValue();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    // calculate textDouble2 value and show
                    Double data2 = textDouble1 / rate;
                    NumberFormat nm = NumberFormat.getNumberInstance();
                    nm.setMaximumFractionDigits(2);
                    nm.setMinimumFractionDigits(2);
                    editText2.setText(nm.format(data2));
                } else if (editText2.hasFocus()) {
                    editText2.setText(editText2.getText().insert(editText2.getText().length(), "9"));
                    String string = editText2.getText().toString();
                    try {
                        NumberFormat.getNumberInstance(Locale.US).parse(string);
                        Number number = NumberFormat.getNumberInstance(Locale.US).parse(string);
                        textDouble2 = number.doubleValue();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    // calculate textDouble2 value and show
                    Double data1 = textDouble2 * rate;
                    NumberFormat nm = NumberFormat.getNumberInstance();
                    nm.setMaximumFractionDigits(2);
                    nm.setMinimumFractionDigits(2);
                    editText1.setText(nm.format(data1));
                }
            }
        });

        button_point.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!point_bool) {
                    point_bool = true;
                    if (editText1.hasFocus()) {
                        editText1.setText(editText1.getText().insert(editText1.getText().length(), "."));
                        String string = editText1.getText().toString();
                        try {
                            NumberFormat.getNumberInstance(Locale.US).parse(string);
                            Number number = NumberFormat.getNumberInstance(Locale.US).parse(string);
                            textDouble1 = number.doubleValue();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        // calculate textDouble2 value and show
                        Double data2 = textDouble1 / rate;
                        NumberFormat nm = NumberFormat.getNumberInstance();
                        nm.setMaximumFractionDigits(2);
                        nm.setMinimumFractionDigits(2);
                        editText2.setText(nm.format(data2));
                    } else if (editText2.hasFocus()) {
                        editText2.setText(editText2.getText().insert(editText2.getText().length(), "."));
                        String string = editText2.getText().toString();
                        try {
                            NumberFormat.getNumberInstance(Locale.US).parse(string);
                            Number number = NumberFormat.getNumberInstance(Locale.US).parse(string);
                            textDouble2 = number.doubleValue();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        // calculate textDouble2 value and show
                        Double data1 = textDouble2 * rate;
                        NumberFormat nm = NumberFormat.getNumberInstance();
                        nm.setMaximumFractionDigits(2);
                        nm.setMinimumFractionDigits(2);
                        editText1.setText(nm.format(data1));
                    }
                }
            }
        });


        button_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // all editText clear
                editText1.setText("");
                editText2.setText("");
                textDouble1 = 0;
                textDouble2 = 0;
                point_bool = false;
            }
        });

        base_currency_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText1.requestFocus();
                Log.v("base_currency_view", "focus");
            }
        });

        currency_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText2.requestFocus();
                Log.v("currency_view", "focus");
            }
        });

        editText1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    base_currency_view.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorSelected));
                    editText1.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorSelected));
                    Log.v("editText1", "selected");
                } else {
                    base_currency_view.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorUnSelected));
                    editText1.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorUnSelected));
                    Log.v("editText1", "unselected");
                }
            }
        });

        editText2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    currency_view.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorSelected));
                    editText2.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorSelected));
                    Log.v("editText2", "selected");
                } else {
                    currency_view.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorUnSelected));
                    editText2.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorUnSelected));
                    Log.v("editText2", "unselected");
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        // BroadcastReceiver register
        registerReceiver(receiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    public void onPause() {
        super.onPause();
        // BroadcastReceiver unregister
        unregisterReceiver(receiver);
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

    // AlertDialog
    private void InternetConnectionDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(R.string.Internet_No);
        builder.setCancelable(false);

        builder.setPositiveButton(
                R.string.Internet_retry,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //if (0 == internetStatus)
                        progressDialog.show();
                        new UnionPayRate().execute();
                    }
                });

        builder.setNegativeButton(
                R.string.Exit,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MainActivity.this.finish();
                    }
                });
        builder.show();
    }

    // logic WebContent load
    public class UnionPayRate extends AsyncTask<Void, Void, Void> {
        private URL url;
        private Document doc;
        private ArrayList<String> list;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                url = new URL("http://www.kuaiyilicai.com/huilv/c-eur.html");
                try {
                    doc = Jsoup.connect(url.toString()).timeout(3 * 1000).get();
                    Log.v("Jsoup", "success");
                } catch (Exception e) {
                    Log.v("Jsoup", "failed");
                    timeout_bool = true;
                    return null;
                }
                Element table = doc.select("table").get(1);
                Elements rows = table.select("tr");
                Elements cols = rows.get(1).select("td");
                list = new ArrayList<>();
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

            // timeout exception
            if (timeout_bool) {
                timeout_bool = false;
                progressDialog.dismiss();
                InternetConnectionDialog(MainActivity.this);
                return;
            }
            String temp = "€ 1 = ¥ " + list.get(3);
            rate = Double.parseDouble(list.get(3));
            textView.setText(temp);
            progressDialog.dismiss();
        }
    }
}
