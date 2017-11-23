package am.greattours.mobile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.os.StrictMode;
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

public class ActivityAllHotTours extends AppCompatActivity {

    List<Integer> TourIdList = new ArrayList<Integer>();
    List<String> TitleList = new ArrayList<String>();
    List<String> DaysList = new ArrayList<String>();
    List<String> NightsList = new ArrayList<String>();
    List<Integer> EarlyBookList = new ArrayList<Integer>();
    List<Integer> HotLIst = new ArrayList<Integer>();
    List<String> StartDateList = new ArrayList<String>();
    List<String> EndDateList = new ArrayList<String>();
    List<String> PriceList = new ArrayList<String>();
    List<String> ImageList = new ArrayList<String>();
    List<String> ContentList = new ArrayList<String>();

    //ListView declaration
    ListView AllPackageListView;

    MsgBox ms = new MsgBox();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_hot_tours);

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
                //MsgBox ms = new MsgBox();
                //ms.ShowMessageBox("OK", TitleList.get(position), MsgBox.MessageIcon.OK, ActivityAllPackages.this);

                Intent i = new Intent(ActivityAllHotTours.this, ActivitySingleHotTour.class);

                i.putExtra("TourId", TourIdList.get(position));
                i.putExtra("Title", TitleList.get(position));
                i.putExtra("Days", DaysList.get(position));
                i.putExtra("Nights", NightsList.get(position));
                i.putExtra("Early", EarlyBookList.get(position));
                i.putExtra("Hot", HotLIst.get(position));
                i.putExtra("StartDate", StartDateList.get(position));
                i.putExtra("EndDate", EndDateList.get(position));
                i.putExtra("Price", PriceList.get(position));
                i.putExtra("Image", ImageList.get(position));
                i.putExtra("Content", ContentList.get(position));

                //startActivityForResult(i, 1);
                startActivity(i);

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

            TourIdList.clear();
            TitleList.clear();
            DaysList.clear();
            NightsList.clear();
            EarlyBookList.clear();
            HotLIst.clear();
            StartDateList.clear();
            EndDateList.clear();
            PriceList.clear();
            ImageList.clear();
            ContentList.clear();

            if(JsonLen > 0)
            {
                for(int i = 0; i < JsonLen; i++){
                    JSONObject obj = jsonarray.getJSONObject(i);

                    String end_date = obj.getString("end_date");

                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    Date EndDate = format.parse(end_date);

                    if(CompareDates(EndDate) && obj.getInt("hot") == 1)
                    {
                        TourIdList.add(obj.getInt("tour_id"));
                        TitleList.add(obj.getString("title"));
                        DaysList.add(obj.getString("days"));
                        NightsList.add(obj.getString("nights"));
                        StartDateList.add(obj.getString("start_date"));
                        EndDateList.add(obj.getString("end_date"));
                        PriceList.add(obj.getString("price"));
                        ImageList.add(obj.getString("thema_image_377x218"));
                        EarlyBookList.add(obj.getInt("early_book"));
                        HotLIst.add(obj.getInt("hot"));
                        ContentList.add(obj.getString("content"));
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
                Integer[] earlybook = new Integer[TourIdList.size()];
                Integer[] hot = new Integer[TourIdList.size()];
                String[] content = new String[TourIdList.size()];

                String[] startenddates = new String[TourIdList.size()];

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
                    earlybook[i] = EarlyBookList.get(i);
                    hot[i] = HotLIst.get(i);
                    content[i] = ContentList.get(i);

                    startenddates[i] =start_date[i] + " - " + end_date[i];
                }

                CustomListAllPackages adapter = new CustomListAllPackages(ActivityAllHotTours.this ,
                        title, image, startenddates, price);
                AllPackageListView.setAdapter(adapter);
            }
            else
            {
                ms.ShowMessageBox("Հաղորդագրություն", "Տվյալները չեն ստացվել" , MsgBox.MessageIcon.Error, ActivityAllHotTours.this);
            }

        }
        catch (Exception e) {
            ms.ShowMessageBox("Տեղի է ունեցել սխալ", "Տվյալները կարդալու ժամանակ տեղի է ունեցել սխալ" , MsgBox.MessageIcon.Error, ActivityAllHotTours.this);
            //ms.ShowMessageBox("Error", e.getMessage() , MsgBox.MessageIcon.Error, ActivityAllPackages.this);
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
