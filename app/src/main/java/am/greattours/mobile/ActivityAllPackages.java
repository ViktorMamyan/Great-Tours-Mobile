package am.greattours.mobile;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;

public class ActivityAllPackages extends AppCompatActivity
{
    String BaseUrl = "http://www.greattours.am";
    String JsonUrl = "/mobile/all_tours_list.json";

    //ListView declaration
    ListView AllPackageListView;

    MsgBox ms = new MsgBox();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_packages);

        //back button
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //allow to use internet
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        AllPackageListView = (ListView)findViewById(R.id.AllPckView);

        //On Listview Click Event
        AllPackageListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                MsgBox ms = new MsgBox();
                ms.ShowMessageBox("OK", String.valueOf(position), MsgBox.MessageIcon.OK, ActivityAllPackages.this);
            }
        });

        //Check Json File
        if (!CheckJsonFile())
        {
            MsgBox ms = new MsgBox();
            ms.ShowMessageBox("Error", "File Error", MsgBox.MessageIcon.OK, ActivityAllPackages.this);
        }
        else
        {
            MsgBox ms = new MsgBox();
            ms.ShowMessageBox("OK", "OK", MsgBox.MessageIcon.OK, ActivityAllPackages.this);

            //geting json data from internet
            //LoadJsonData();

        }
    }

    private void LoadJsonData()
    {
        try
        {
            HttpReader HR = new HttpReader();
            JSONArray jsonarray = new JSONArray(HR.GetJsonData(BaseUrl + JsonUrl));

            int JsonLen = jsonarray.length();

            Integer[] tour_id = new Integer[JsonLen];
            String[] title = new String[JsonLen];
            String[] days = new String[JsonLen];
            String[] nights = new String[JsonLen];
            String[] start_date = new String[JsonLen];
            String[] end_date = new String[JsonLen];
            String[] price = new String[JsonLen];
            String[] image = new String[JsonLen];

            if(JsonLen > 0)
            {
                for(int i = 0; i < JsonLen - 1; i++){
                    JSONObject obj = jsonarray.getJSONObject(i);

                    tour_id[i] = obj.getInt("tour_id");
                    title[i] = obj.getString("title");
                    days[i] = obj.getString("days");
                    nights[i] = obj.getString("nights");
                    start_date[i] = obj.getString("start_date");
                    end_date[i] = obj.getString("end_date");
                    price[i] = obj.getString("price");
                    image[i] = BaseUrl + "/categories/tours/img_377_218/" + obj.getString("thema_image_377x218");

                    //if (i == 1 ) break;
                }

                CustomListAllPackages adapter = new CustomListAllPackages(ActivityAllPackages.this , title, image, start_date, price);
                AllPackageListView.setAdapter(adapter);
            }
            else
            {
                ms.ShowMessageBox("Տվյալները չեն ստացվել", "Հաղորդագրություն" , MsgBox.MessageIcon.Error, ActivityAllPackages.this);
            }

        }
        catch (Exception e) {
            //ms.ShowMessageBox("Տվյալները կաևդալու ժամանակ տեղի է ունեցել սխալ", "Տեղի է ունեցել սխալ" , MsgBox.MessageIcon.Error, ActivityAllPackages.this);

            String err = e.toString();
            ms.ShowMessageBox("Տեղի է ունեցել սխալ", err , MsgBox.MessageIcon.Error, ActivityAllPackages.this);
        }
    }

    boolean CheckJsonFile()
    {
        File f_tours_json = new File(getCacheDir(),File.separator + "tours/tours.json");
        if (!f_tours_json.exists())
        {
            return true;
        }
        else
        {
            return false;
        }
    }

}