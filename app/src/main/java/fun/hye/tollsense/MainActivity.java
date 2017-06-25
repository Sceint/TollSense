package fun.hye.tollsense;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {

    EditText user, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = (EditText) findViewById(R.id.user);
        pass = (EditText) findViewById(R.id.pass);

        OfflineDBHelper offlineDBHelper = OfflineDBHelper.getInstance(this);
        offlineDBHelper.populateTable();
    }

    public void goTo(View v) {
        Intent intent;
        String usr = user.getText().toString();
        String password = pass.getText().toString();
        if (usr.equals("user") && password.equals("password")) {
            intent = new Intent(MainActivity.this, UserPage.class);
            startActivity(intent);
        } else if (usr.equals("toll") && password.equals("password")) {
            intent = new Intent(MainActivity.this, TollPage.class);
            startActivity(intent);
        } else if (usr.equals("test")) {
            intent = new Intent(MainActivity.this, UpdateDB.class);
            startActivity(intent);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Invalid Details");
            builder.setMessage("Please Enter a Valid Username and password")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }
}
