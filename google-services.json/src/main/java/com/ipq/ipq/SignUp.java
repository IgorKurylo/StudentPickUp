package com.ipq.ipq;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
public class SignUp extends ActionBarActivity {

    Bitmap bitmap = null;
    FrameLayout FrL;
    EditText ID, Email, Pass, FirstName, LastName;
    String  emailuser = "", passuser = "", IDuser = "", FullName = "";
    ImageButton Submit, TakePic;
    ImageView SignUpLogo;
    int status = 0;
    boolean Save = false;
//    private static final int CAMERA_CAPTURE = 1;
//    private static final int CAMERA_SUCCESS = -1;
   // Image CaptureImage = null;
    public int ImgID = 1;
    public String ImgName;
    CheckBox driverstatus;

    ImageView img;
    File file;
    private static android.support.v4.app.FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.signup);

        ID = (EditText) findViewById(R.id.ID);
        Email = (EditText) findViewById(R.id.Email);
        Pass = (EditText) findViewById(R.id.Pass);
        FirstName = (EditText) findViewById(R.id.Fn);
        LastName = (EditText) findViewById(R.id.Ln);
        Submit = (ImageButton) findViewById(R.id.submit);
        SignUpLogo = (ImageView) findViewById(R.id.signupimg);
        Intent signup = getIntent();
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    IDuser = ID.getText().toString();
                    FullName+=" "+ FirstName.getText().toString();
                    FullName+=" "+ LastName.getText().toString();
                    emailuser = Email.getText().toString();
                    passuser = Pass.getText().toString();
                    if(IDuser.equals("") && FullName.equals("")&& emailuser.equals("")&&passuser.equals(""))
                    {
                        Toast.makeText(v.getContext(),"Must Fill All Fields",Toast.LENGTH_LONG).show();
                    }
                    else
                    {

                        Bundle bd = new Bundle();

                        bd.putString("ID", IDuser);
                        bd.putString("Email", emailuser);
                        bd.putString("Pass", passuser);
                        bd.putString("FullName", FullName);


                        fragmentManager = getSupportFragmentManager();
                        FragmentSignUp Fs = new FragmentSignUp();
                        Fs.setArguments(bd);

                        fragmentManager.beginTransaction().replace(R.id.fragment_container, Fs).addToBackStack(null).commit();
                    }
                } catch (Exception e) {
                    Toast.makeText(v.getContext(), e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });
//        TakePic.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(captureIntent, CAMERA_CAPTURE);
//                Intent i=new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(i, 1);
//            }
//        });
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        try {
//            if (requestCode == CAMERA_CAPTURE && resultCode == CAMERA_SUCCESS) {
//                bitmap = (Bitmap) data.getExtras().get("data");
//                LayoutInflater inflater = getLayoutInflater();
//                View dialogLayout = inflater.inflate(R.layout.profileimage, null);
//                img = (ImageView) dialogLayout.findViewById(R.id.profileimg);
//                img.setImageBitmap(bitmap);
//                img.setScaleType(ImageView.ScaleType.CENTER_CROP);
//                Uri uri= data.getData();
//                String[]projection={MediaStore.Images.Media.DATA};
//
//                Cursor cursor=getContentResolver().query(uri, projection, null, null, null);
//                cursor.moveToFirst();
//
//                int columnIndex=cursor.getColumnIndex(projection[0]);
//                String filePath=cursor.getString(columnIndex);
//                cursor.close();
//                //Convert Bitmap to byte
//                Bitmap yourSelectedImage = BitmapFactory.decodeFile(filePath);
//                img.setImageBitmap(yourSelectedImage);
//                ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                yourSelectedImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
//                byte[] byteArray = stream.toByteArray();
//
//                if (bitmap != null) {
//
//                    ByteArrayOutputStream bs = new ByteArrayOutputStream();
//                    bitmap.compress(Bitmap.CompressFormat.PNG, 50, bs);
//                    //final byte[] byteArray = bs.toByteArray();
//                    SizeofImage = byteArray.length;
//                    ImageFile = Base64.encodeToString(byteArray, Base64.DEFAULT);
//                }
//
//                    AlertDialog.Builder ok = new AlertDialog.Builder(this);
//                    AlertDialog dialog;
//                    ok.setView(dialogLayout);
//                    ok.setPositiveButton("הגדר כתמונת פרופיל", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                try {
//                    ImgName = Email.getText().toString();
//                    int index = ImgName.indexOf("@");
//                    ImgName = ImgName.substring(0, index);
//                    CaptureImage = new Image(ImgID++, ImgName + "_" + ImgID, SizeofImage, ImageFile);
//                    Save = true;
//                    Toast.makeText(getApplicationContext(), "Imaged Saved", Toast.LENGTH_LONG).show();
//                } catch (Exception e) {
//                    Toast.makeText(getApplicationContext(), "Fill All Fields!!!", Toast.LENGTH_LONG).show();
//
//                }
//                            dialog.dismiss();
//            }
//                    });
//                    ok.setNegativeButton("לא ,תודה", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            Toast.makeText(getApplicationContext(), "Take Phono Again", Toast.LENGTH_LONG).show();
//                            dialog.cancel();
//                        }
//                    });
//                    dialog=ok.create();
//                    dialog.show();
//                }
//        } catch (Exception e) {
//            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
//        }
//
//    }



    }
    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:

                super.onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}









