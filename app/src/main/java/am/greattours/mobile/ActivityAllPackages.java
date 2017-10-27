package am.greattours.mobile;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ActivityAllPackages extends AppCompatActivity
{
   private String BaseUrl = "http://www.greattours.am";
   private String JsonUrl = "/mobile/all_tours_list.json";

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

        if(CheckJsonFile())
        {
            LoadList();
        }

    }

    private void LoadList()
    {
        try
        {
            File f_tours_json = new File(getCacheDir(),File.separator + "tours/tours.json");
            String JsonContent = LoadLocalJsonFile(f_tours_json);

            JSONArray jsonarray = new JSONArray(JsonContent);

            int JsonLen = jsonarray.length();

            List<Integer> TourIdList = new ArrayList<Integer>();
            List<String> TitleList = new ArrayList<String>();
            List<String> DaysList = new ArrayList<String>();
            List<String> NightsList = new ArrayList<String>();
            List<String> StartDateList = new ArrayList<String>();
            List<String> EndDateList = new ArrayList<String>();
            List<String> PriceList = new ArrayList<String>();
            List<String> ImageList = new ArrayList<String>();

            if(JsonLen > 0)
            {
                for(int i = 0; i < JsonLen; i++){
                    JSONObject obj = jsonarray.getJSONObject(i);

                    String end_date = obj.getString("end_date");

                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    Date EndDate = format.parse(end_date);

                    if(CompareDates(EndDate))
                    {
                        TourIdList.add(obj.getInt("tour_id"));
                        TitleList.add(obj.getString("title"));
                        DaysList.add(obj.getString("days"));
                        NightsList.add(obj.getString("nights"));
                        StartDateList.add(obj.getString("start_date"));
                        EndDateList.add(obj.getString("end_date"));
                        PriceList.add(obj.getString("price"));
                        ImageList.add(obj.getString("thema_image_377x218"));
                    }
                }

                Integer[] tour_id = new Integer[TourIdList.size()];
                String[] title = new String[TourIdList.size()];
                String[] days = new String[TourIdList.size()];
                String[] nights = new String[TourIdList.size()];
                String[] start_date = new String[TourIdList.size()];
                String[] end_date = new String[TourIdList.size()];
                String[] price = new String[TourIdList.size()];
                File[] image = new File[TourIdList.size()];

                for(int i = 0; i < ImageList.size(); i++)
                {
                    tour_id[i] = TourIdList.get(i);
                    title[i] = TitleList.get(i);
                    days[i] = DaysList.get(i);
                    nights[i] = NightsList.get(i);
                    start_date[i] = StartDateList.get(i);
                    end_date[i] = EndDateList.get(i);
                    price[i] = PriceList.get(i);
                    image[i] = new File(getCacheDir(),File.separator + "tours/" + ImageList.get(i));
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
            //ms.ShowMessageBox("Տեղի է ունեցել սխալ", "Տվյալները կարդալու ժամանակ տեղի է ունեցել սխալ" , MsgBox.MessageIcon.Error, ActivityAllPackages.this);
            ms.ShowMessageBox("Error", e.getMessage() , MsgBox.MessageIcon.Error, ActivityAllPackages.this);
        }
    }

    boolean CompareDates(Date EndDate)
    {
        Date CurrentDate = new Date(System.currentTimeMillis());

        if(!EndDate.before(CurrentDate ))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    boolean CheckJsonFile()
    {
        File f_tours_json = new File(getCacheDir(),File.separator + "tours/tours.json");

        if (f_tours_json.exists())
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    String LoadLocalJsonFile(File JsonFile)
    {
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

}