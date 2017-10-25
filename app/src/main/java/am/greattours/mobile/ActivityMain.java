package am.greattours.mobile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class ActivityMain extends AppCompatActivity
{

    private ProgressDialog pd = null;
    private Object data = null;

    ListView MainListView;

    String[] ListItems={
            "Փնտրել"
            ,"Փաթեթների Ցանկ"
            ,"Տաք Տուրեր"
            ,"Վաղ Ամրագրում"
            ,"Հայտ"
            ,"Ծանուցում"
            ,"Տեսարժան Վայր"
            ,"Հյուրանոց"
            ,"Նախընտրություններ"};

    Integer[] imageItems = {R.drawable.searchinpackage
            ,R.drawable.packagelist
            ,R.drawable.hottours
            ,R.drawable.earlybook
            ,R.drawable.book
            ,R.drawable.notification
            ,R.drawable.place
            ,R.drawable.hotel
            ,R.drawable.favorite};

    private void LoadListViewItems()
    {
        CustomListMain adapter = new CustomListMain(ActivityMain.this, ListItems, imageItems);
        MainListView=(ListView)findViewById(R.id.MainListView);
        MainListView.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainListView=(ListView)findViewById(R.id.MainListView);
        MainListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                if (position == 1)
                {
                    ActivityMain.this.pd = ProgressDialog.show(ActivityMain.this, "Կատարվում է գործողություն", "Բազայի թարմացում...", true, false);
                    new DownloadTask().execute("Any parameters my download task needs here");

                    Intent i = new Intent(ActivityMain.this, ActivityAllPackages.class);
                    startActivityForResult(i,1);
                }
            }
        });

        LoadListViewItems();

    }

    private class DownloadTask extends AsyncTask<String, Void, Object> {
        protected Object doInBackground(String... args) {
            //web json file url
            String BaseUrl = "http://www.greattours.am";
            String JsonUrl = "/mobile/all_tours_list.json";

            //local folder name
            File f_dir = new File(getCacheDir(),File.separator + "tours/");
            if (!f_dir.exists()) { f_dir.mkdirs(); }

            //local json file
            File f_tours_json = new File(getCacheDir(),File.separator + "tours/tours.json");
            if (f_tours_json.exists()) { f_tours_json.delete(); }

            //load json file
            LoadJsonFromInternet(BaseUrl + JsonUrl,f_tours_json);

            //read local json file & loade image files




            ///////////////////////////////////////////////////////////


            return "Գործողությունը կատարվեց";
        }

        void LoadJsonFromInternet(String source,File destination)
        {
            try
            {
                URL url = new URL(source);
                URLConnection conexion = url.openConnection();
                conexion.connect();

                InputStream input = new BufferedInputStream(url.openStream());
                OutputStream output = new FileOutputStream(destination);

                byte data[] = new byte[1024];
                long total = 0;
                int count;

                while ((count = input.read(data)) != -1)
                {
                    total += count;
                    output.write(data, 0, count);
                }

                output.flush();
                output.close();
                input.close();
            } catch (Exception e) {}
        }

        protected void onPostExecute(Object result) {
            // Pass the result data back to the main activity
            ActivityMain.this.data = result;

            if (ActivityMain.this.pd != null) {
                ActivityMain.this.pd.dismiss();
            }
        }
    }

}