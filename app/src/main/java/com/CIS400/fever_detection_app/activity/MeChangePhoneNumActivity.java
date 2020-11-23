package com.CIS400.fever_detection_app.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import com.CIS400.fever_detection_app.R;
import com.CIS400.fever_detection_app.data.MyUser;
import com.CIS400.fever_detection_app.data.Symptoms;
import com.CIS400.fever_detection_app.fragments.meFragment;
import com.google.android.material.snackbar.Snackbar;

import com.CIS400.fever_detection_app.fragments.meFragment;

import org.w3c.dom.Text;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class MeChangePhoneNumActivity extends BaseActivity {

    //private Text currentNumber;
    private Fragment mFrag1;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me_change_phone_num);
        Bmob.initialize(this, "2de9dc3c787359faf54d36e92a2bbfb0");


        TextView currentNumber = (TextView) findViewById(R.id.current_Number);
        Button updateButton = (Button) findViewById(R.id.update_Number);
        EditText updateNumberText = (EditText) findViewById(R.id.update_Number_Text);
        String new_number = updateNumberText.getText().toString().trim();
        back = (ImageView) findViewById(R.id.back_phone);
        MyUser user = BmobUser.getCurrentUser(MyUser.class);
        String number = user.getPhoneNum();

        currentNumber.setText("Current Number: " + number);


        //user.setPhoneNum("909262");

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Base cases to catch any errors: if the text is empty, if the text is the users current number
                if(updateNumberText.getText().toString().matches("")) {
                    Toast.makeText(MeChangePhoneNumActivity.this, "Error: Please Enter A Valid Phone Number" , Toast.LENGTH_LONG).show();
                    return;
                }
                else if (updateNumberText.getText().toString().matches(number)) {
                    Toast.makeText(MeChangePhoneNumActivity.this, "Error: This Is Your Current Number, Please Enter A New One To Update" , Toast.LENGTH_LONG).show();
                    return;
                }


                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                mFrag1 = new meFragment();

                user.setPhoneNum(updateNumberText.getText().toString().trim());
                //Log.i("reached here", "yes");
                //Log.v("EditText", updateNumberText.getText().toString());
                user.update(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if(e == null){
                            Toast.makeText(MeChangePhoneNumActivity.this, "Phone Number Update Successful!", Toast.LENGTH_LONG).show();
                            startActivity(getIntent());
                            finish();
                        }else{
                            Toast.makeText(MeChangePhoneNumActivity.this, "Update failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });


            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed(){
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            fm.popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}