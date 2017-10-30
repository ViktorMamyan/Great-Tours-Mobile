package am.greattours.mobile;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

public class ActivitySingleTour extends AppCompatActivity {

    Button btncall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_tour);

        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btncall = (Button) findViewById(R.id.btncall);
        btncall.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:077001882"));

                if (callIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(callIntent);
                }

                //startActivity(callIntent);
            }
        });

        Bundle extras=getIntent().getExtras();
        if(extras != null)
        {
            TextView txtTitle = (TextView)findViewById(R.id.single_title);
            TextView txtDate = (TextView)findViewById(R.id.single_startenddate);
            TextView txtDurPrice = (TextView)findViewById(R.id.single_durationprice);
            ImageView img = (ImageView)findViewById(R.id.single_tourlistitem);
            WebView wb = (WebView)findViewById(R.id.webbrowser);

            String Val = extras.getString("Title");

            if (Val != null)
            {
                txtTitle.setText(extras.getString("Title"));
                txtDate.setText("Ամսաթիվ` " + extras.getString("StartDate") + " - " + extras.getString("EndDate"));
                txtDurPrice.setText(extras.getString("Days") +" " + "Օր" + " / " + extras.getString("Nights") + " Գիշեր" + " | Արժեք` " + extras.getString("Price"));

                File f = new File(getCacheDir(),File.separator + "tours/" + extras.getString("Image"));
                Bitmap B = BitmapFactory.decodeFile(f.getAbsolutePath());
                img.setImageBitmap(B);

                wb.getSettings().setJavaScriptEnabled(true);
                wb.loadDataWithBaseURL("", extras.getString("Content"), "text/html", "UTF-8", "");

            }
            else
            {
                txtTitle.setText("");
                txtDate.setText("");
                txtDurPrice.setText("");
            }

            //add buttons for call

        }

    }

}