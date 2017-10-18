package com.example.greattours.greattours;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    //menu items
    String[] ListItems={
            "Փնտրել"
            ,"Փաթեթների Ցանկ"
            ,"Տաք Տուրեր"
            ,"Վաղ Ամրագրում"
            ,"Հայտ"
            ,"Ծանուցում"
            ,"Տեսարժան Վայր"
            ,"Հյուրանոց"
            ,"Նախընտրություններ"

    };

    Integer[] imageId = {R.drawable.searchinpackage
            ,R.drawable.packagelist
            ,R.drawable.hottours
            ,R.drawable.earlybook
            ,R.drawable.book
            ,R.drawable.notification
            ,R.drawable.place
            ,R.drawable.hotel
            ,R.drawable.favorite
    };

    //main menu variable
    ListView MainListView;

    //load listview items
    private void LoadListViewItems()
    {
        //load listview items
        CustomList adapter = new CustomList(MainActivity.this, ListItems, imageId);
        MainListView=(ListView)findViewById(R.id.MainListView);
        MainListView.setAdapter(adapter);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //define listview
        MainListView=(ListView)findViewById(R.id.MainListView);
        MainListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                //show query window
                if (ListItems[position].toString() == "Փնտրել Փաթեթ")
                {
                    //Intent i = new Intent(MainActivity.this, Query.class);
                    //i.putExtra("UserID", Integer.toString(UserID));
                    //startActivityForResult(i, 1);

                    MsgBox ms = new MsgBox();
                    ms.ShowMessageBox("OK", "Փնտրել Փաթեթ", MsgBox.MessageIcon.OK, MainActivity.this);

                }
                else if (ListItems[position].toString() == "Փաթեթների Ցանկ")
                {
                    //Intent i = new Intent(MainActivity.this, OpenTasks.class);
                    //i.putExtra("UserID", UserID);
                    //startActivityForResult(i, 1);

                    MsgBox ms = new MsgBox();
                    ms.ShowMessageBox("OK", "Փաթեթների Ցանկ", MsgBox.MessageIcon.OK, MainActivity.this);

                }
                else if (ListItems[position].toString() == "Տաք Տուրեր")
                {
                    //Intent i = new Intent(MainActivity.this, ClosedTasks.class);
                    //i.putExtra("UserID", UserID);
                    //startActivityForResult(i, 1);

                    MsgBox ms = new MsgBox();
                    ms.ShowMessageBox("OK", "Տաք Տուրեր", MsgBox.MessageIcon.OK, MainActivity.this);

                }
                else if (ListItems[position].toString() == "Վաղ Ամրագրում")
                {
                    //Intent i = new Intent(MainActivity.this, MessageSender.class);
                    //i.putExtra("UserID", UserID);
                    //startActivityForResult(i, 1);

                    MsgBox ms = new MsgBox();
                    ms.ShowMessageBox("OK", "Վաղ Ամրագրում", MsgBox.MessageIcon.OK, MainActivity.this);

                }
                else if (ListItems[position].toString() == "Ծանուցում")
                {
                    //msgbox ms2 = new msgbox();
                    //try {
                     //   String versionName  = MainActivity.this.getPackageManager().getPackageInfo(MainActivity.this.getPackageName(), 0).versionName;

                     //   ms2.ShowMessageBox("Ծրագիր","Տարբերակ " +  versionName , msgbox.MessageIcon.OK, MainActivity.this);
                    //} catch (PackageManager.NameNotFoundException e) {
                    //    ms2.ShowMessageBox("Սխալ", "Տեղի է ունեցել սխալ", msgbox.MessageIcon.Error, MainActivity.this);
                   // }

                    MsgBox ms = new MsgBox();
                    ms.ShowMessageBox("OK", "Ծանուցում", MsgBox.MessageIcon.OK, MainActivity.this);

                }
                else if (ListItems[position].toString() == "Նախընտրություններ")
                {
                    MsgBox ms = new MsgBox();
                    ms.ShowMessageBox("OK", "Նախընտրություններ", MsgBox.MessageIcon.OK, MainActivity.this);
                }
                else if (ListItems[position].toString() == "Հայտ")
                {
                    MsgBox ms = new MsgBox();
                    ms.ShowMessageBox("OK", "Հայտ", MsgBox.MessageIcon.OK, MainActivity.this);
                }
                else
                {
                    MsgBox ms = new MsgBox();
                    ms.ShowMessageBox("OK", "Անորոշ հրաման", MsgBox.MessageIcon.OK, MainActivity.this);
                }


            }
        });


        //load list items
        LoadListViewItems();

    }



}