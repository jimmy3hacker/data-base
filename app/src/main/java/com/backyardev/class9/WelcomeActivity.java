package com.backyardev.class9;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class WelcomeActivity extends AppCompatActivity {

    Button btnLogin;
    DBClass db=new DBClass( this );
    boolean sub=true;
    ArrayList readList= new ArrayList(  );
    EditText editNameLogin,editPassLogin;
    String name,pass,nameChck,passChck,type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_welcome );
        btnLogin=findViewById( R.id.btnLogin );
        editNameLogin=findViewById( R.id.editNameLogin );
        editPassLogin=findViewById( R.id.editPassLogin );
        btnLogin.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name=editNameLogin.getText().toString();
                pass=editPassLogin.getText().toString();
                if(TextUtils.isEmpty( name )){
                    sub=false;
                    editNameLogin.setError( "Required Field!" );
                }if(TextUtils.isEmpty( pass )){
                    sub=false;
                    editPassLogin.setError( "Required Field!" );
                }if(sub){
                   Cursor c = db.loginLogic(name,pass);
                    if(c.moveToFirst()){
                        do{
                            nameChck=c.getString( c.getColumnIndex( "Name" ) );
                            passChck=c.getString( c.getColumnIndex( "Password" ) );
                            type=c.getString( c.getColumnIndex( "Type" ) );
                        }while (c.moveToNext());
                    }
                    if(name.equals( nameChck )&&pass.equals( passChck )){
                        if(type.equals( "Instructor" )) {
                            Intent i = new Intent( WelcomeActivity.this, Main2Activity.class );
                            i.putExtra( "name", name );
                            i.putExtra( "pass", pass );
                            startActivity( i );
                            finish();
                        }
                        else if(type.equals( "Student" )) {
                            Intent i = new Intent( WelcomeActivity.this, EditActivity.class );
                            i.putExtra( "name", name );
                            i.putExtra( "pass", pass );
                            startActivity( i );
                            finish();
                        }
                        }
                    else Toast.makeText( WelcomeActivity.this,
                            "Invalid Credentials!",Toast.LENGTH_SHORT ).show();
                }
            }
        } );

    }



    public void lblAction(View view) {

        Intent i= new Intent( WelcomeActivity.this,MainActivity.class );
        startActivity( i );
    }
}
