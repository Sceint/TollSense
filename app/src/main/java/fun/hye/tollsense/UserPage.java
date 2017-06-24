package fun.hye.tollsense;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;

public class UserPage extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ImageView imageView;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_user);

        imageView = (ImageView) findViewById(R.id.imageView);
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String message = spinner.getSelectedItem().toString();
        if (message.equals("Select")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Select User");
            builder.setMessage("Please Select a user from drop down")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        } else {
            if(message.contains("1"))
                imageView.setImageDrawable(getDrawable(R.drawable.user1));
            else if(message.contains("2"))
                imageView.setImageDrawable(getDrawable(R.drawable.user2));
            else if(message.contains("3"))
                imageView.setImageDrawable(getDrawable(R.drawable.user3));
            else if(message.contains("4"))
                imageView.setImageDrawable(getDrawable(R.drawable.user4));
            Cursor cursor = OfflineDBHelper.getInstance(this).getUserInfo(message);
            cursor.moveToNext();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

