package am.greattours.mobile;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ActivityTourRequest extends AppCompatActivity {

    EditText dtStartDate;
    EditText dtEndDate;

    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;

    private SimpleDateFormat dateFormatter;

    MsgBox ms = new MsgBox();

    Button btnSend;

    EditText txtName;
    EditText txtEmail;
    EditText txtTel;
    EditText txtPlace;
    EditText txtPersonCount;
    EditText txtChildCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_request);

        dtStartDate = (EditText) findViewById(R.id.dtStartDate);
        dtEndDate = (EditText) findViewById(R.id.dtEndDate);

        //date chooser
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        setDateTimeField();

        txtName = (EditText)findViewById(R.id.txtName);
        txtEmail = (EditText)findViewById(R.id.txtEmail);
        txtTel = (EditText)findViewById(R.id.txtTel);
        txtPlace = (EditText)findViewById(R.id.txtPlace);
        txtPersonCount = (EditText)findViewById(R.id.txtPersonCount);
        txtChildCount = (EditText)findViewById(R.id.txtChildCount);


        btnSend = (Button)findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(txtName.getText().toString().equals(""))
                {
                    ms.ShowMessageBox("Հարցումը ամբողջական չէ", "Անուն ազգանունը գրված չէ", MsgBox.MessageIcon.OK, ActivityTourRequest.this);
                    return;
                }
                if(txtEmail.getText().toString().equals(""))
                {
                    ms.ShowMessageBox("Հարցումը ամբողջական չէ", "Էլ հասցեն գրված չէ", MsgBox.MessageIcon.OK, ActivityTourRequest.this);
                    return;
                }
                if(txtTel.getText().toString().equals(""))
                {
                    ms.ShowMessageBox("Հարցումը ամբողջական չէ", "Հեռախոսահամարը գրված չէ", MsgBox.MessageIcon.OK, ActivityTourRequest.this);
                    return;
                }
                if(txtPlace.getText().toString().equals(""))
                {
                    ms.ShowMessageBox("Հարցումը ամբողջական չէ", "Հանգստյան վայրը գրված չէ", MsgBox.MessageIcon.OK, ActivityTourRequest.this);
                    return;
                }
                if(txtPersonCount.getText().toString().equals(""))
                {
                    ms.ShowMessageBox("Հարցումը ամբողջական չէ", "Անձանց քանակը գրված չէ", MsgBox.MessageIcon.OK, ActivityTourRequest.this);
                    return;
                }
                if(txtChildCount.getText().toString().equals(""))
                {
                    ms.ShowMessageBox("Հարցումը ամբողջական չէ", "Երեխաների քանակը գրված չէ", MsgBox.MessageIcon.OK, ActivityTourRequest.this);
                    return;
                }
                if(dtStartDate.getText().toString().equals(""))
                {
                    ms.ShowMessageBox("Հարցումը ամբողջական չէ", "Տուրի սկիզբը գրված չէ", MsgBox.MessageIcon.OK, ActivityTourRequest.this);
                    return;
                }
                if(dtEndDate.getText().toString().equals(""))
                {
                    ms.ShowMessageBox("Հարցումը ամբողջական չէ", "Տուրի ավարտը գրված չէ", MsgBox.MessageIcon.OK, ActivityTourRequest.this);
                    return;
                }


                //Intent i = new Intent(Intent.ACTION_SEND);
                //i.setType("message/rfc822");
                //i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"info@greattours.am"});
                //i.putExtra(Intent.EXTRA_SUBJECT, "Tour Request From Mobile Application");
                //i.putExtra(Intent.EXTRA_TEXT   , "test email");
                //try {
                //    startActivity(Intent.createChooser(i, "Send mail..."));
                //} catch (android.content.ActivityNotFoundException ex) {
                //    ms.ShowMessageBox("Սխալ", ex.getMessage().toString(), MsgBox.MessageIcon.OK, ActivityTourRequest.this);
                //}

                ms.ShowMessageBox("OK", "OK", MsgBox.MessageIcon.OK, ActivityTourRequest.this);
            }
        });

    }

    private void setDateTimeField() {
        dtStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fromDatePickerDialog.show();
            }
        });
        dtEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toDatePickerDialog.show();
            }
        });

        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                dtStartDate.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        toDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                dtEndDate.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

}