package me.jamesstevenson.onelist;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {


        //This makes it so that the keyboard doesn't show up automatically.
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        ;


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get reference of widgets from XML layout
        final ListView lv = (ListView) findViewById(R.id.lv);

        // Initializing a new String Array
        String[] listItems = new String[] {};



        // Create a List from String Array elements
        final List<String> dir_list = new ArrayList<String>(Arrays.asList(listItems));

        // Create an ArrayAdapter from List
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, dir_list);

        // DataBind ListView with items from ArrayAdapter
        lv.setAdapter(arrayAdapter);


        //Loops through files in the directory and adds them to the list
        File dir = new File(Environment.getExternalStorageDirectory()+"/"+ "One_List_Notes");
        if (dir.exists()) {
            File[] files = dir.listFiles();
            if (files != null) {
                for (int i = 0; i < files.length; ++i) {
                    File file = files[i];
                    dir_list.add(file.getName()); //Adds names of files to the list
                    // do something here with the file
                }

            }

            arrayAdapter.notifyDataSetChanged(); //Updates array
        }



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        1);


                // Checks if the permission to write to external storage is set
                if (ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

                    // Permission is not granted
                    // Requests permission

                    Snackbar.make(view, "Please Try Again...", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            1);

                } else {

                    String folder_main = "One_List_Notes";
                    EditText textValue = (EditText) findViewById(R.id.editText);

                    //Checks if the directory has already been created, if not it creates it.
                    File f = new File(Environment.getExternalStorageDirectory(), folder_main);
                    if (!f.exists()) {

                        f.mkdirs();
                    }

                    // Uses the text in the text box to create a new file with the name of the text. The files are all empty and just the names are used.
                    File newFile = new File(Environment.getExternalStorageDirectory() + "/" + folder_main + "/" + textValue.getText().toString());
                    if (!newFile.exists()) {
                        try {
                            Snackbar.make(view, "New item: " + textValue.getText().toString(), Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            newFile.createNewFile(); //Creates file with name of text.
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    // Clears the array and sets the list view to equal the now updates items
                    arrayAdapter.clear();
                    arrayAdapter.notifyDataSetChanged();
                    File dir = new File(Environment.getExternalStorageDirectory() + "/" + folder_main);
                    if (dir.exists()) {
                        File[] files = dir.listFiles();
                        if (files != null) {
                            for (int i = 0; i < files.length; ++i) {
                                File file = files[i];
                                dir_list.add(file.getName());
                                // do something here with the file
                            }
                        }

                        arrayAdapter.notifyDataSetChanged();
                    }

                    // Resets the text box to empty
                    textValue.setText("");

                }




            }
        });


        FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Snackbar.make(view, "Updating Items", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                // Clears the array so that it is not appended to.
                arrayAdapter.clear();
                arrayAdapter.notifyDataSetChanged();

                //dir_list.add("test");
                //arrayAdapter.notifyDataSetChanged();

                // Checks if the permission to write to external storage is set
                if (ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

                    // Permission is not granted
                    // Requests permission

                    Snackbar.make(view, "Please Try Again...", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            1);



                } else {
                    //Peermission is set

                    //Checks if folder exists, if it doesn't it creates one.
                    String folder_main = "One_List_Notes";

                    File f = new File(Environment.getExternalStorageDirectory(), folder_main);

                    if (!f.exists()) {
                        f.mkdirs();

                    }

                    //Clears array
                    arrayAdapter.clear();
                    arrayAdapter.notifyDataSetChanged();

                    //Reads files in the filepath and adds to list/ array
                    File dir = new File(Environment.getExternalStorageDirectory()+"/"+ folder_main);
                    if (dir.exists()) {
                        File[] files = dir.listFiles();
                        for (int i = 0; i < files.length; ++i) {
                            File file = files[i];
                            dir_list.add(file.getName());
                            // do something here with the file
                        }

                        arrayAdapter.notifyDataSetChanged();
                    }
                }








            }
        });

        // if run when someone clicks on a list item and deletes that file and item
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final String selectedItem = (String) parent.getItemAtPosition(position);

                Snackbar.make(view, "Deleted item: "+selectedItem, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                //Deletes the file with the name of the clicked item.
                File dir = new File(Environment.getExternalStorageDirectory()+"/One_List_Notes/"+ selectedItem);
                dir.delete();

                //Clears array
                arrayAdapter.clear();
                arrayAdapter.notifyDataSetChanged();

                //Updates array from files
                dir = new File(Environment.getExternalStorageDirectory()+"/"+ "One_List_Notes");
                if (dir.exists()) {
                    File[] files = dir.listFiles();
                    for (int i = 0; i < files.length; ++i) {
                        File file = files[i];
                        dir_list.add(file.getName());
                        // do something here with the file
                    }

                    arrayAdapter.notifyDataSetChanged();
                }


            }



        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
