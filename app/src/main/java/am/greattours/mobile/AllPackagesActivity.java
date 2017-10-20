package am.greattours.mobile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class AllPackagesActivity extends AppCompatActivity {

    ListView AllPackageListView;

    String[] LisTitles = {
            "title1"
            ,"title2"
            ,"title3"};

    Integer[] ListImages = {
             R.drawable.tourlist
            ,R.drawable.tourlist
            ,R.drawable.tourlist};

    String[] StartEndDate = {
            "Սկիզբ՝ 2017-01-01 Ավարտ՝ 2017-01-31"
            ,"Սկիզբ՝ 2017-01-01 Ավարտ՝ 2017-01-31"
            ,"Սկիզբ՝ 2017-01-01 Ավարտ՝ 2017-01-31"};

    String[] DurationPrice = {
            "7 Օր 6 Գիշեր   Արժեք՝ $ 850"
            ,"7 Օր 6 Գիշեր  Արժեք՝ $ 850"
            ,"7 Օր 6 Գիշեր  Արժեք՝ $ 850"};

    private void LoadListViewItems()
    {
        AllPackagesListCustom adapter = new AllPackagesListCustom(AllPackagesActivity.this, LisTitles, ListImages,
                                                                                            StartEndDate, DurationPrice);
        AllPackageListView = (ListView)findViewById(R.id.AllPckView);
        AllPackageListView.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_packages);

        AllPackageListView = (ListView)findViewById(R.id.AllPckView);
        AllPackageListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                MsgBox ms = new MsgBox();
                ms.ShowMessageBox("OK", String.valueOf(position), MsgBox.MessageIcon.OK, AllPackagesActivity.this);
            }
        });

        LoadListViewItems();
    }

}