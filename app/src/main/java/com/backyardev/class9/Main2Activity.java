package com.backyardev.class9;



import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {

    private String name,pass,phn,type,allIn, nameDel;
    final DBClass db = new DBClass( this );
    ArrayList<String> readList=new ArrayList<String>(  );
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main2 );
    listView=(ListView) findViewById( R.id.listOP );
        updateList();
        listView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String data = readList.get( position );
                String[] arr=data.split( "\n" );
                nameDel=arr[0];
                alertMethod();
            }
        } );
    }
    private void alertMethod(){



        AlertDialog.Builder alertbox= new AlertDialog.Builder( Main2Activity.this );
                alertbox.setTitle( "Delete Entry?" );
                alertbox.setMessage( "Are you sure you wanna delete the selected entry?" );
                alertbox.setPositiveButton( "Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.onDelete( nameDel );
                        readList.clear();
                        updateList();


                    }
                } );
                alertbox.setNegativeButton( "No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                } );
                AlertDialog alertDialog=alertbox.create();
                alertDialog.show();
    }

    private void updateList(){
        Cursor c = db.getData();

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


    public void btnLogout(View view) {
        Intent i = new Intent( Main2Activity.this,WelcomeActivity.class );
        startActivity( i );
        finish();

    }
}
