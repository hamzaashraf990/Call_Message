package project.sialkot.task2;

import android.Manifest;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView mlv;
    UserAdapter oUserAapter;
    ArrayList<User> oListUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Init();
    }

    private void Init() {
        mlv=(ListView)findViewById(R.id.listview);

        oListUsers=new ArrayList<User>();
           getContactList();


        oUserAapter=new UserAdapter(getApplicationContext(),oListUsers);
        mlv.setAdapter(oUserAapter);



            }
    private void getContactList(){
        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        if ((cur != null ? cur.getCount() : 0) > 0) {
            while (cur != null && cur.moveToNext()) {
                String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                 final String name = cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME));

                if (cur.getInt(cur.getColumnIndex(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    final Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                         final String phoneNo = pCur.getString(pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));

                        User user=new User();
                         user.setName(name);
                         user.setContact(phoneNo);
                         oListUsers.add(user);

                        mlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                User Ouser=oListUsers.get(position);
                                AlertDialog.Builder dialog1=new AlertDialog.Builder(MainActivity.this);
                                dialog1.setMessage("Exit");
                                dialog1.setCancelable(false);
                                dialog1.setPositiveButton(
                                        "Call",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                Intent callIntent = new Intent(Intent.ACTION_CALL);
                                                callIntent.setData(Uri.parse("tel:" +phoneNo));

                                                if (ActivityCompat.checkSelfPermission(MainActivity.this,
                                                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                                    return;
                                                }
                                                startActivity(callIntent);

                                            }
                                        });

                                dialog1.setNegativeButton(
                                        "Message",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                                                sendIntent.putExtra("" ,"");
                                                sendIntent.setType("vnd.android-dir/mms-sms");
                                                startActivity(sendIntent);
                                            }
                                        });

                                AlertDialog alert11 = dialog1.create();
                                alert11.show();

                            }
                        });






                    }
                    pCur.close();
                }
            }
        }
        if(cur!=null){
            cur.close();
        }
    }

}
