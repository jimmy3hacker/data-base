package com.backyardev.class9;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private EditText editName,editPass,editPhn;
    private RadioButton rdStu,rdIns;
    private Button btnSub;
    boolean sub=false;
    private String name,pass,phn,type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        btnSub=findViewById( R.id.btnSub );
        editName=findViewById( R.id.editName );
        editPass=findViewById( R.id.editPass );
        editPhn=findViewById( R.id.editPhn );
        rdIns=findViewById( R.id.rdIns );
        rdStu=findViewById( R.id.rdStu );

    }

    public void actionRegButton(View view) {
        name=editName.getText().toString();
        pass=editPass.getText().toString();
        phn=editPhn.getText().toString();

        if(TextUtils.isEmpty(name)){
            sub=false;
            editName.setError( "Required Field!" );
        }if(TextUtils.isEmpty(pass)){
            sub=false;
            editPass.setError( "Required Field!" );
        }if(TextUtils.isEmpty(phn)){
            sub=false;
            editPhn.setError( "Required Field!" );
        }
        if(sub) {

            final DBClass dbCls = new DBClass( this );
            boolean value = dbCls.onAddData( name, pass, phn, type );
            if (value) {
                Log.d( "regAct", "registered successfully" );
                Toast.makeText( MainActivity.this, "Registration Successful", Toast.LENGTH_SHORT ).show();
                Intent i = new Intent( MainActivity.this, WelcomeActivity.class );
                startActivity( i );
                finish();
            } else Toast.makeText( MainActivity.this ,"Something went wrong!",Toast.LENGTH_SHORT).show();
        }
        else Toast.makeText( MainActivity.this ,"Select a Type!",Toast.LENGTH_SHORT).show();
    }
    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch(view.getId()) {
            case R.id.rdIns:
                if (checked)
                    type="Instructor";
                    sub=true;
                    break;
            case R.id.rdStu:
                if (checked)
                    type="Student";
                    sub=true;
                    break;
        }
    }
}
