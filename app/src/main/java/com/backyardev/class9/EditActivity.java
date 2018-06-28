package com.backyardev.class9;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class EditActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<String> readList=new ArrayList<String>(  );
    final DBClass db = new DBClass( this );
    EditText editNewPass,reEditNewPass;
    private String name,pass,newPass,phn,type,nameChck,passChck,allIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_edit );


        listView=(ListView) findViewById( R.id.listData );
        editNewPass=findViewById( R.id.editNewPass );
        reEditNewPass=findViewById( R.id.reEditNewPass );
        nameChck=getIntent().getStringExtra( "name" );
        passChck=getIntent().getStringExtra( "pass" );
        Cursor c = db.getUserData(nameChck,passChck);
        if(c.moveToFirst()){
            do{
                name=c.getString( c.getColumnIndex( "Name" ) );
                pass=c.getString( c.getColumnIndex( "Password" ) );
                phn=c.getString( c.getColumnIndex( "Phone" ) );
                type=c.getString( c.getColumnIndex( "Type" ) );

                allIn=name+"\n"+pass+"\n"+phn+"\n"+type+"\n";
                readList.add( allIn );
            }while (c.moveToNext());
        }

        ArrayAdapter<String> adapter=new ArrayAdapter<String>( this,
                android.R.layout.simple_list_item_1,
                readList );
        listView.setAdapter( adapter );

    }

    public void delAction(View view) {
        db.onDelete( nameChck );
        Toast.makeText( EditActivity.this,"Deleted Successfully! Login to continue",Toast.LENGTH_SHORT).show();
        Intent i = new Intent( EditActivity.this,WelcomeActivity.class );
        startActivity( i );
        finish();

    }

    public void updAction(View view) {
        newPass = editNewPass.getText().toString();
        passChck = reEditNewPass.getText().toString();
        if(newPass.equals( passChck )) {
            db.onUpdate( name, pass, passChck );
        }  else Toast.makeText( EditActivity.this, "Passwords do not match", Toast.LENGTH_SHORT ).show();
        Toast.makeText( EditActivity.this, "Updated Successfully! Login to continue", Toast.LENGTH_SHORT ).show();
        Intent i = new Intent( EditActivity.this, WelcomeActivity.class );
        startActivity( i );
        finish();
    }
}
