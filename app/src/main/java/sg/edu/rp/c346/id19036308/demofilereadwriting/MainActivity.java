package sg.edu.rp.c346.id19036308.demofilereadwriting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class MainActivity extends AppCompatActivity {

    String folderLocation, folderLocation_I;
    Button btnWrite, btnRead;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnRead = findViewById(R.id.btnRead);
        btnWrite = findViewById(R.id.btnWrite);
        tv = findViewById(R.id.tv);

        //Folder creation internal
        folderLocation_I = getFilesDir().getAbsolutePath() + "/Folder";

        File folder_I = new File(folderLocation_I);
        if (folder_I.exists() == false){
            boolean result = folder_I.mkdir();
            if (result == true){
                Log.d("File Read/Write", "Folder created");
            }
        }

        //Folder creation external storage
        folderLocation = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Folder";

        File folder = new File(folderLocation);
        if (folder.exists() == false){
            boolean result = folder.mkdir();
            if (result == true){
                Log.d("File Read/Write", "Folder created");
            }
        }

        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Code for file writing

                //File Creation and writing internal
                try {
                    String folderLocation_I = getFilesDir().getAbsolutePath() + "/Folder";
                    File targetFile_I = new File(folderLocation_I, "data.txt");
                    FileWriter writer_I = new FileWriter(targetFile_I, true);
                    writer_I.write("Hello World" + "\n");
                    writer_I.flush();
                    writer_I.close();
                } catch (Exception e){
                    Toast.makeText(MainActivity.this, "Failed to write!", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

                //File Creation and writing external
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
                if (checkPermission()){
                    try {
                        String folderLocation = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Folder";
                        File targetFile = new File(folderLocation, "data.txt");
                        FileWriter writer = new FileWriter(targetFile, true);
                        writer.write("Hello World" + "\n");
                        writer.flush();
                        writer.close();
                    } catch (Exception e){
                        Toast.makeText(MainActivity.this, "Failed to write!", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }

            }
        });

        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Code for file reading
                // Reading internal Storage
                String folderLocation_I = getFilesDir().getAbsolutePath() + "/Folder";
                File targetFile_I = new File(folderLocation_I,"data.txt");
                if (targetFile_I.exists() == true){
                    String data = "";
                    try{
                        FileReader reader = new FileReader(targetFile_I);
                        BufferedReader br = new BufferedReader(reader);
                        String line = br.readLine();
                        while (line != null){
                            data += line + "\n";
                            line = br.readLine();
                            tv.setText("Data: " + data );
                        }
                        br.close();
                        reader.close();
                    } catch (Exception e){
                        Toast.makeText(MainActivity.this, "Failed to read!", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                    Log.d("Content", data);
                }

                // Reading external Storage
                String folderLocation = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Folder";
                File targetFile = new File(folderLocation,"data.txt");
                if (targetFile.exists() == true){
                    String data = "";
                    try{
                        FileReader reader = new FileReader(targetFile);
                        BufferedReader br = new BufferedReader(reader);
                        String line = br.readLine();
                        while (line != null){
                            data += line + "\n";
                            line = br.readLine();

                        }
                        br.close();
                        reader.close();
                    } catch (Exception e){
                        Toast.makeText(MainActivity.this, "Failed to read!", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                    Log.d("Content", data);
                }

            }
        });
    }

    private boolean checkPermission() {
        int permissionCheck_Read = ContextCompat.checkSelfPermission(
                MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE
        );
        int permissionCheck_Write = ContextCompat.checkSelfPermission(
                MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE
        );
        if (permissionCheck_Read == PermissionChecker.PERMISSION_GRANTED || permissionCheck_Write == PermissionChecker.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }
}