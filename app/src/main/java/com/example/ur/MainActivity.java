package com.example.ur;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import static android.support.v4.app.NotificationCompat.*;

public class MainActivity extends AppCompatActivity {

    private ProgressDialog mProgressDialog;
    private String url = "http://wezmidruknij.pl/strona.html";
    private ArrayList<String> dataZjazduList = new ArrayList<>();
    private ArrayList<String> planZjazduList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Description().execute();

        startService(new Intent(this, NotificationService.class));
    }

    private class Description extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(MainActivity.this);
            mProgressDialog.setTitle("Harmonogram zjazdów UR");
            mProgressDialog.setMessage("Ładowanie...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
         protected Void doInBackground(Void... params) {
            try {
                Document doc = Jsoup.connect(url).get();
                Elements elem = doc.select("p[class=zjazd]");
                int elemIlosc = elem.size();
                final int keep = elemIlosc;

                for (int i = 0; i < elemIlosc; i++) {
                    Elements mDataZjazdu = doc.select("p[class=zjazd]").eq(i);
                    String dataZjazdu = mDataZjazdu.text();

                    Elements mPlanZjazdu = doc.select("p[class=zjazd] > a[href]").eq(i);
                    String planZjazdu = mPlanZjazdu.attr("href");

                    dataZjazduList.add(dataZjazdu);
                    planZjazduList.add(planZjazdu);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            RecyclerView mRecyclerView = (RecyclerView)findViewById(R.id.act_recycler_view);

            DataAdapter mDataAdapter = new DataAdapter(MainActivity.this, dataZjazduList, planZjazduList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setAdapter(mDataAdapter);

            mProgressDialog.dismiss();
        }

    }
}