package ro.pub.cs.systems.eim.lab04.contactsmanager;

import android.content.ContentValues;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Visibility;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class ContactsManagerActivity extends AppCompatActivity {
    Button operateFields;
    Button saveButton;
    Button cancelButton;
    EditText nameText;
    EditText phoneText;
    EditText emailText;
    EditText addressText;

    EditText jobText;
    EditText websiteText;
    EditText IMText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_manager);

        operateFields = (Button) findViewById(R.id.show_button);
        saveButton = (Button) findViewById(R.id.save_button);
        cancelButton = (Button) findViewById(R.id.cancel_button);
        MyListener listener = new MyListener();
        operateFields.setOnClickListener(listener);
        saveButton.setOnClickListener(listener);
        cancelButton.setOnClickListener(listener);
        nameText = (EditText) findViewById(R.id.name_text);
        phoneText = (EditText) findViewById(R.id.phone_text);
        emailText = (EditText) findViewById(R.id.email_text);
        addressText = (EditText) findViewById(R.id.address_text);
        jobText = (EditText) findViewById(R.id.job_text);
        websiteText = (EditText) findViewById(R.id.website_text);
        IMText = (EditText) findViewById(R.id.IM_text);
    }

    private class MyListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.show_button:
                    final LinearLayout hiddenLayout = (LinearLayout) findViewById(R.id.hidden_layout);
                    int visibility = hiddenLayout.getVisibility();
                    String message;
                    if (visibility == View.GONE) {
                        visibility = View.VISIBLE;
                        message = "Hide details";
                    } else {
                        visibility = View.GONE;
                        message = "Show details";
                    }
                    hiddenLayout.setVisibility(visibility);
                    operateFields.setText(message);
                    break;
                case R.id.save_button:
                    Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
                    intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
                    if (nameText != null) {
                        intent.putExtra(ContactsContract.Intents.Insert.NAME, nameText.getText().toString());
                    }
                    if (phoneText != null) {
                        intent.putExtra(ContactsContract.Intents.Insert.PHONE, phoneText.getText().toString());
                    }
                    if (emailText != null) {
                        intent.putExtra(ContactsContract.Intents.Insert.EMAIL, emailText.getText().toString());
                    }
                    if (addressText != null) {
                        intent.putExtra(ContactsContract.Intents.Insert.POSTAL, addressText.getText().toString());
                    }
                    if (jobText!= null) {
                        intent.putExtra(ContactsContract.Intents.Insert.JOB_TITLE, jobText.getText().toString());
                    }

                    ArrayList<ContentValues> contactData = new ArrayList<ContentValues>();
                    if (websiteText != null) {
                        ContentValues websiteRow = new ContentValues();
                        websiteRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE);
                        websiteRow.put(ContactsContract.CommonDataKinds.Website.URL, websiteText.getText().toString());
                        contactData.add(websiteRow);
                    }
                    if (IMText != null) {
                        ContentValues imRow = new ContentValues();
                        imRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE);
                        imRow.put(ContactsContract.CommonDataKinds.Im.DATA, IMText.getText().toString());
                        contactData.add(imRow);
                    }
                    intent.putParcelableArrayListExtra(ContactsContract.Intents.Insert.DATA, contactData);
                    startActivity(intent);
                    break;
                case R.id.cancel_button:
                    finish();
                    break;
            }
        }
    }
}
