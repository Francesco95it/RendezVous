package com.unitn.francesco.rendezvous;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.MultiSelectListPreference;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import util.Constants;
import util.FileUtils;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.ServerResponse;
import net.gotev.uploadservice.UploadInfo;
import net.gotev.uploadservice.UploadNotificationConfig;
import net.gotev.uploadservice.UploadStatusDelegate;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import static com.unitn.francesco.rendezvous.Event.EVENTS;
import static com.unitn.francesco.rendezvous.Event.EVENTS_COMM;


public class CreateEvent extends AppCompatActivity {

    //Declaring views
    private Button btnChoose;
    private Button btnSubmit;

    private TextView imgpath;

    private EditText input_event_name;
    private EditText input_event_tags;
    private EditText input_event_position;
    private EditText input_event_desc;

    DatePicker datePicker;
    TimePicker timePicker;
    private String date;
    private int hours;
    private int minutes;

    private RadioButton input_radio_pers;
    private RadioButton input_radio_comm;

    private static final String TAG = "CreateEventActivity";

    //Image request code
    private int PICK_IMAGE_REQUEST = 1;

    //storage permission code
    private static final int STORAGE_PERMISSION_CODE = 123;

    //Bitmap to get image from gallery
    private Bitmap bitmap;

    //Uri to store the image uri
    private Uri filePath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        requestStoragePermissions();

        //inputs and button association with related resources

        input_event_name = (EditText)findViewById(R.id.input_event_name);
        input_event_tags = (EditText)findViewById(R.id.input_event_tag);
        input_event_position = (EditText)findViewById(R.id.input_event_place);
        input_event_desc = (EditText)findViewById(R.id.input_event_description);

        input_radio_pers = (RadioButton)findViewById(R.id.persRadio);
        input_radio_comm = (RadioButton)findViewById(R.id.commRadio);

        datePicker = (DatePicker)findViewById(R.id.datePicker);
        timePicker = (TimePicker)findViewById(R.id.timePicker);
        timePicker.setIs24HourView(true);

        btnChoose = (Button)findViewById(R.id.btn_choose_up);
        btnSubmit = (Button)findViewById(R.id.submit_event_create);

        imgpath = (TextView)findViewById(R.id.img_path);

        //radio_pers radio button checked by default
        input_radio_pers.setChecked(true);

        //listeners declaration

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });

        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });

        input_radio_pers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input_radio_pers.setChecked(true);
                input_radio_comm.setChecked(false);
            }
        });

        input_radio_comm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input_radio_pers.setChecked(false);
                input_radio_comm.setChecked(true);
            }
        });

    }

    public void submit() {
        Log.d(TAG, "Submitting");

        if (!validate()) {
            onFailed();
            return;
        }

        btnSubmit.setEnabled(false);

        String event_name = input_event_name.getText().toString();
        String event_place = input_event_position.getText().toString();
        String event_tags = input_event_tags.getText().toString();
        String event_type;
        if(input_radio_pers.isChecked()) event_type="pers";
        else event_type="comm";
        String event_desc = input_event_desc.getText().toString();


        uploadMultipart(event_name, event_tags,event_type,event_place);

    }

    public boolean validate() {
        boolean valid = true;

        String event_name = input_event_name.getText().toString();
        String event_tags = input_event_tags.getText().toString();
        String event_position = input_event_position.getText().toString();
        String img_path = imgpath.getText().toString();

        if (event_name.isEmpty()) {
            input_event_name.setError("Inserisci un nome");
            valid = false;
        } else {
            input_event_name.setError(null);
        }

        if (event_position.isEmpty()) {
            input_event_position.setError("Inserisci la location");
            valid = false;
        } else {
            input_event_position.setError(null);
        }

        if (event_tags.isEmpty()) {
            input_event_tags.setError("Inserisci uno o più tag");
            valid = false;
        } else {
            input_event_tags.setError(null);
        }

        if (img_path.isEmpty()) {
            imgpath.setText("Inserisci una immagine!");
            valid = false;
        } else {
            imgpath.setText(null);
        }
        return valid;
    }




    /*
    * This is the method responsible for image upload
    * We need the full image path and the name for the image in this method
    * */
    public void uploadMultipart(final String name, final String tags, final String type, final String place) {

        //getting the actual path of the image
        String path = FileUtils.getPath(this, filePath);
        final ProgressDialog mPbar = new ProgressDialog(this);
        mPbar.setTitle("Loading");
        mPbar.setMessage("Uploading..");
        mPbar.setCancelable(false); // disable dismiss by tapping outside of the dialog
        mPbar.show();
        //Uploading code
        try {
            String uploadId = UUID.randomUUID().toString();

            Log.d(TAG, "Uploading img at path " + path + ", with UUID "+ uploadId);
            Log.d(TAG, "uploading at: "+Constants.UPLOAD_URL);
            //Creating a multi part request
            new MultipartUploadRequest(this, uploadId, Constants.UPLOAD_URL)
                    .addFileToUpload(path, "image") //Adding file
                    .addParameter("uuid", uploadId) //Adding text parameter to the request
                    .setMaxRetries(2)
                    .setDelegate(new UploadStatusDelegate() {
                        @Override
                        public void onProgress(Context context, UploadInfo uploadInfo) {
                            Log.d(TAG, "onProgress!");
                        }

                        @Override
                        public void onError(Context context, UploadInfo uploadInfo, ServerResponse serverResponse, Exception exception) {
                            mPbar.dismiss();
                            Toast.makeText(getBaseContext(), "Evento aggiunto con successo", Toast.LENGTH_LONG).show();
                            onSuccess();
                            Log.d(TAG, "onError!");
                        }

                        @Override
                        public void onCompleted(Context context, UploadInfo uploadInfo, ServerResponse serverResponse) {
                            //infoUpload(name, tags, type, place);
                            mPbar.dismiss();
                            Toast.makeText(getBaseContext(), "Evento aggiunto con successo", Toast.LENGTH_LONG).show();
                            onSuccess();
                            Log.d(TAG, "onCompleted!");
                        }

                        @Override
                        public void onCancelled(Context context, UploadInfo uploadInfo) {
                            Log.d(TAG, "onCancelled!");
                        }
                    })
                    .startUpload(); //Starting the upload

        } catch (Exception exc) {
            Log.d(TAG, exc.getMessage());
            Toast.makeText(this, exc.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
/*

    public void infoUpload(String name,String tags,String type,String place) {
        try {
             final String objinfo = "{" +
                    "'name': '" + name +
                    "', 'tags': '" + tags +
                    "', 'type': '" + type +
                    "', 'place': '" + place +
                    "'}";
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    HttpURLConnection con = null;
                    try {
                        con = (HttpURLConnection) (new URL(Constants.UPLOAD_URL)).openConnection();
                        con.setRequestMethod("POST");
                        con.setRequestProperty("Accept", "application/json");
                        con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                        con.setDoInput(true);
                        con.setDoOutput(true);
                        con.connect();
                        con.getOutputStream().write(objinfo.getBytes("UTF-8"));
                        con.getOutputStream().flush();
                        con.getOutputStream().close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
*/


    //method to show file chooser
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Seleziona un'immagine"), PICK_IMAGE_REQUEST);
    }

    //handling the image chooser activity result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //setting textView with imgpath
                imgpath.setText("Image selected!");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    //Requesting permission
    private void requestStoragePermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }


    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(this, "Hai il permesso di accedere alla memoria", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops, ci hai negato l'accesso alla memoria!", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }

    public void onSuccess() {
        String event_name = input_event_name.getText().toString();
        String event_tags = input_event_tags.getText().toString();
        String event_position = input_event_position.getText().toString();
        String event_desc = input_event_desc.getText().toString();
        String image_path = imgpath.getText().toString();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            hours = timePicker.getHour();
            minutes = timePicker.getMinute();
        }
        date = datePicker.getDayOfMonth()+"/"+(datePicker.getMonth()+1)+"/"+datePicker.getYear();


        String event_type;
        if(input_radio_pers.isChecked()){
            event_type="pers";
            EVENTS.add(new Event(new Random().nextInt(1000), event_name, event_tags, event_position, event_type, event_desc, date, hours, minutes, bitmap));
        }
        else {
            event_type="comm";
            EVENTS_COMM.add(new Event(new Random().nextInt(1000), event_name, event_tags, event_position, event_type, event_desc, date, hours, minutes, bitmap));
        }

        btnSubmit.setEnabled(true);
        finish();
    }

    public void onFailed() {
        Toast.makeText(getBaseContext(), "Invio fallito, riprova", Toast.LENGTH_LONG).show();

        btnSubmit.setEnabled(true);
    }

}