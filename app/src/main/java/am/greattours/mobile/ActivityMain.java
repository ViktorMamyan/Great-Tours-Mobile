package am.greattours.mobile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ActivityMain extends AppCompatActivity
{
    private String BaseUrl = "http://www.greattours.am";
    private String JsonUrl = "/mobile/all_tours_list.json";
    private String ImgUrl = "/categories/tours/img_377_218/";

    private ProgressDialog pd = null;
    private Object data = null;

    MsgBox ms = new MsgBox();

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
            ,"Թարմացնել Բազան"
            ,"Ջնջել Քեշը"};

    Integer[] imageItems = {R.drawable.searchinpackage
            ,R.drawable.packagelist
            ,R.drawable.hottours
            ,R.drawable.earlybook
            ,R.drawable.book
            ,R.drawable.notification
            ,R.drawable.place
            ,R.drawable.hotel
            ,R.drawable.update
            ,R.drawable.clean};

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

                if (position == 0)          //search tours
                {
                    File f_tours_json = new File(getCacheDir(),File.separator + "tours/tours.json");
                    if (!f_tours_json.exists())
                    {
                        ms.ShowMessageBox("Գործողությունը դադարեցվել է", "Անհրաժեշտ է բազայի թարմացում", MsgBox.MessageIcon.OK, ActivityMain.this);
                    }
                    else
                    {
                        Intent i = new Intent(ActivityMain.this, ActivitySearch.class);
                        startActivity(i);
                    }
                }
                else if (position == 1)     //tours
                {
                    File f_tours_json = new File(getCacheDir(),File.separator + "tours/tours.json");
                    if (!f_tours_json.exists())
                    {
                        ms.ShowMessageBox("Գործողությունը դադարեցվել է", "Անհրաժեշտ է բազայի թարմացում", MsgBox.MessageIcon.OK, ActivityMain.this);
                    }
                    else
                    {
                        Intent i = new Intent(ActivityMain.this, ActivityAllPackages.class);
                        startActivity(i);
                    }
                }
                else if (position == 2)     //hot tours
                {
                    File f_tours_json = new File(getCacheDir(),File.separator + "tours/tours.json");
                    if (!f_tours_json.exists())
                    {
                        ms.ShowMessageBox("Գործողությունը դադարեցվել է", "Անհրաժեշտ է բազայի թարմացում", MsgBox.MessageIcon.OK, ActivityMain.this);
                    }
                    else
                    {
                        Intent i = new Intent(ActivityMain.this, ActivityAllHotTours.class);
                        startActivity(i);
                    }
                }
                else if (position == 3)     //early tours
                {
                    File f_tours_json = new File(getCacheDir(),File.separator + "tours/tours.json");
                    if (!f_tours_json.exists())
                    {
                        ms.ShowMessageBox("Գործողությունը դադարեցվել է", "Անհրաժեշտ է բազայի թարմացում", MsgBox.MessageIcon.OK, ActivityMain.this);
                    }
                    else
                    {
                        Intent i = new Intent(ActivityMain.this, ActivityAllEarlyTours.class);
                        startActivity(i);
                    }
                }




                else if (position == 8)     //update
                {
                    ActivityMain.this.pd = ProgressDialog.show(ActivityMain.this, "Կատարվում է ...", "Բազայի թարմացում", true, false);
                    new UpdateTask().execute("Any parameters download task needs here");
                }
                else if (position == 9)     //clean
                {
                    ActivityMain.this.pd = ProgressDialog.show(ActivityMain.this, "Կատարվում է ...", "Քեշի մաքրում", true, false);
                    new CleanTask().execute("Any parameters download task needs here");
                }


            }
        });

        LoadListViewItems();

    }

    private class UpdateTask extends AsyncTask<String, Void, Object> {

        protected Object doInBackground(String... args) {
            File f_dir = new File(getCacheDir(),File.separator + "tours/");
            File f_tours_json = new File(getCacheDir(),File.separator + "tours/tours.json");

            if (!f_dir.exists()) { f_dir.mkdirs(); }
            if (f_tours_json.exists()) { f_tours_json.delete(); }

            if(!SaveFileFromInternetToLocal(BaseUrl + JsonUrl,f_tours_json))
            {
                return "";
            }

            String[] image = ReadJsonFromLocal();
            if (image == null)
            {
                return "";
            }

            for (int i = 0; i < image.length; i++)
            {
                File img_file = new File(getCacheDir(),File.separator + "tours/" + image[i]);
                if (!img_file.exists())
                {
                    SaveFileFromInternetToLocal(BaseUrl + ImgUrl + image[i],img_file);
                }
            }

            return "Գործողությունը կատարվեց";
        }

        boolean SaveFileFromInternetToLocal(String source,File destination) {
            try
            {
                URL url = new URL(source);
                URLConnection connection = url.openConnection();
                connection.connect();

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

                return true;
            } catch (Exception e) { return false; }
        }

        String LoadLocalJsonFile(File JsonFile) {
            String json = null;
            try {

                StringBuilder text = new StringBuilder();
                BufferedReader br = new BufferedReader(new FileReader(JsonFile));
                String line;

                while ((line = br.readLine()) != null) {
                    text.append(line);
                    text.append('\n');
                }
                br.close();

                json = text.toString();

                return json;
            }
            catch (Exception e)
            {
                return e.getMessage();
            }
        }

        String[] ReadJsonFromLocal() {
            File f_tours_json = new File(getCacheDir(),File.separator + "tours/tours.json");
            String JsonContent = LoadLocalJsonFile(f_tours_json);

            if (JsonContent == null) return null;

            try
            {
                JSONArray jsonarray = new JSONArray(JsonContent);

                int JsonLen = jsonarray.length();

                List<String> ImageList = new ArrayList<String>();

                if(JsonLen > 0)
                {
                    for(int i = 0; i < JsonLen; i++)
                    {
                        JSONObject obj = jsonarray.getJSONObject(i);

                        String end_date = obj.getString("end_date");

                        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        Date EndDate = format.parse(end_date);

                        if(CompareDates(EndDate))
                        {
                            ImageList.add(obj.getString("thema_image_377x218"));
                        }
                    }

                    String[] image = new String[ImageList.size()];

                    for(int i = 0; i < ImageList.size(); i++)
                    {
                        image[i] = ImageList.get(i);
                    }

                    return image;
                }
                else
                {
                    return null;
                }

            }
            catch (Exception e)
            {
                return null;
            }
        }

        boolean CompareDates(Date EndDate) {
            Date CurrentDate = new Date(System.currentTimeMillis());

            if(!EndDate.before(CurrentDate))
            {
                return true;
            }
            else
            {
                return false;
            }
        }

        protected void onPostExecute(Object result) {
            // Pass the result data back to the main activity
            ActivityMain.this.data = result;

            Toast.makeText(ActivityMain.this, "Թարմացումը ավարտվել է", Toast.LENGTH_LONG).show();

            if (ActivityMain.this.pd != null) {
                ActivityMain.this.pd.dismiss();
            }
        }

    }

    private class CleanTask extends AsyncTask<String, Void, Object> {

        protected Object doInBackground(String... args) {
            File f_dir = new File(getCacheDir(),File.separator + "tours/");

            if (f_dir.exists())
            {
                try {
                    FileUtils.deleteDirectory(f_dir);
                } catch (IOException e) {
                }
            }

            return "Գործողությունը կատարվեց";
        }

        protected void onPostExecute(Object result) {
            // Pass the result data back to the main activity
            ActivityMain.this.data = result;

            Toast.makeText(ActivityMain.this, "Քեշը ջնջվեց", Toast.LENGTH_LONG).show();

            if (ActivityMain.this.pd != null) {
                ActivityMain.this.pd.dismiss();
            }
        }

    }
















}