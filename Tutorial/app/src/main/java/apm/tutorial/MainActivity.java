package apm.tutorial;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;


public class MainActivity extends ActionBarActivity {


    char[] neg;
    int count;
    String s, s1;
    public EditText editText, passkey;
    public TextView textView;
    public Button save, load;

    public String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/aaTutorial";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        passkey = (EditText) findViewById(R.id.passkey);
        editText = (EditText) findViewById(R.id.editText);
        textView = (TextView) findViewById(R.id.textView);
        save = (Button) findViewById(R.id.save);
        load = (Button) findViewById(R.id.load);

        File dir = new File(path);
        dir.mkdirs();

    }

    public void buttonSave(View view) {
        File file = new File(path + "/savedFile.txt");
        String saveText = editText.getText().toString();

        editText.setText("");

        Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();

        Save(file, saveText);
    }

    public void buttonLoad(View view) {


        File file = new File(path + "/savedFile.txt");
//        String [] loadText = Load(file);
//
//        String finalString = " ";

        String finalString = Load(file);

        /*for (int i = 0; i < loadText.length; i++)
        {
            finalString += loadText[i] + System.getProperty("line.separator");
        }
*/
        textView.setText(finalString);

    }

    public void decryptText(View view){

        File file = new File(path + "/savedFile.txt");
        try
        {
            FileInputStream fin=new FileInputStream(file);
            BufferedInputStream bin=new BufferedInputStream(fin);
            int i1,i2,i3=0;
            //neg=new char[bin.available()];
            s="";
            i3=0;
            while((i1=bin.read())!=-1)
            {
                if(i3==passkey.getText().length())
                    i3=0;
                if((char)bin.read()=='1')
                    i1=-i1;
                String fr = passkey.getText().toString();
                char[] fr1 = fr.toCharArray();

                i2=128-(i1+(int)fr1[i3]);
                s+=((char)i2);

                i3++;
            }

            bin.close();
            fin.close();
        }
        catch(Exception e1)
        {
            e1.printStackTrace();
        }

        textView.setText(s);

    }

    public static void Save(File file, String data) {

        try {
            FileWriter writer = new FileWriter(file);
            BufferedWriter buffer = new BufferedWriter(writer);
            buffer.write(data);
            buffer.close();
            writer.close();

        }
        catch (IOException e) {
            e.printStackTrace();
        }

      /*  FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            try {
                for (int i = 0; i < data.length; i++) {
                    fos.write(data[i].getBytes());
                    if (i < data.length - 1) {
                        fos.write("\n".getBytes());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
    }


    public String Load(File file) {

        FileInputStream fin = null;

        try {
            fin = new FileInputStream(file);
            BufferedInputStream bin = new BufferedInputStream(fin);
            int i1, i2;
            neg = new char[bin.available()];
            s = "";
            s1 = "";
            StringBuilder sb = new StringBuilder();
            count = 0;
            int i3 = 0;
            while ((i1 = bin.read()) != -1) {
                if (i3 == passkey.getText().length())
                i3 = 0;
                String fr = passkey.getText().toString();
                char[] fr1 = fr.toCharArray();
                i2 = 128 - (i1 + (int) fr1[i3]);
                if (i2 < 0)
                    neg[count] = '1';
                else
                    neg[count] = '0';
                sb.append(((char)Math.abs(i2)));
                sb.append(neg[count]);
                count++;
                i3++;
            }
            s = sb.toString();
            bin.close();
            fin.close();
            FileWriter writer = new FileWriter(file);
            BufferedWriter buffer = new BufferedWriter(writer);
            buffer.write(s);
            buffer.close();
            writer.close();

        } catch (Exception e1) {
            e1.printStackTrace();

        }

        return s;
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
