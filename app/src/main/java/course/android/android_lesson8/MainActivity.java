package course.android.android_lesson8;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    boolean SDModeOn;
    Button song1Button;
    Button song2Button;
    Button saveButton;
    Button clearButton;
    EditText songDescription;
    Switch switch1;
    int numberOfTheLastSongClicked = 1;

    String billieJeanText = "She was more like a beauty queen from a movie scene\n" +
            "I said don't mind, but what do you mean, I am the one\n" +
            "Who will dance on the floor in the round\n" +
            "She said I am the one, who will dance on the floor in the round\n" +
            "She told me her name was Billie Jean, as she caused a scene\n" +
            "Then every head turned with eyes that dreamed of being the one\n" +
            "Who will dance on the floor in the round\n" +
            "People always told me be careful of what you do\n" +
            "And don't go around breaking young girls' hearts\n" +
            "And mother always told me be careful of who you love\n" +
            "And be careful of what you do 'cause the lie becomes the truth\n" +
            "Billie Jean is not my lover\n" +
            "She's just a girl who claims that I am the one\n" +
            "But the kid is not my son\n" +
            "She says I am the one, but the kid is not my son\n" +
            "For forty days and forty nights\n" +
            "The law was on her side\n" +
            "But who can stand when she's in demand\n" +
            "Her schemes and plans\n" +
            "'Cause we danced on the floor in the round\n" +
            "So take my strong advice, just remember to always think twice\n" +
            "(Don't think twice, don't think twice)\n" +
            "She told my baby we'd danced till three, then she looked at me\n" +
            "Then showed a photo my baby cried his eyes were like mine (oh, no!)\n" +
            "'Cause we danced on the floor in the round, baby\n" +
            "People always told me be careful of what you do\n" +
            "And don't go around breaking young girls' hearts\n" +
            "She came and stood right by me\n" +
            "Just the smell of sweet perfume\n" +
            "This happened much too soon\n" +
            "She called me to her room";
    String stayingAliveText = "\"Stayin' Alive\"\n" +
            "\n" +
            "Well, you can tell by the way I use my walk,\n" +
            "I'm a woman's man: no time to talk.\n" +
            "Music loud and women warm, I've been kicked around\n" +
            "Since I was born.\n" +
            "And now it's all right. It's OK.\n" +
            "And you may look the other way.\n" +
            "We can try to understand\n" +
            "The New York Times' effect on man.\n" +
            "\n" +
            "Whether you're a brother or whether you're a mother,\n" +
            "You're stayin' alive, stayin' alive.\n" +
            "Feel the city breakin' and everybody shakin',\n" +
            "And we're stayin' alive, stayin' alive.\n" +
            "Ah, ha, ha, ha, stayin' alive, stayin' alive.\n" +
            "Ah, ha, ha, ha, stayin' alive.\n" +
            "\n" +
            "Well now, I get low and I get high,\n" +
            "And if I can't get either, I really try.\n" +
            "Got the wings of heaven on my shoes.\n" +
            "I'm a dancin' man and I just can't lose.\n" +
            "You know it's all right. It's OK.\n" +
            "I'll live to see another day.\n" +
            "We can try to understand\n" +
            "The New York Times' effect on man.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        song1Button = (Button) findViewById(R.id.buttonSong1);
        song2Button = (Button) findViewById(R.id.buttonSong2);
        saveButton = (Button) findViewById(R.id.buttonSong2);
        clearButton = (Button) findViewById(R.id.buttonSong2);
        songDescription = (EditText) findViewById(R.id.editText1);
        switch1 = (Switch) findViewById(R.id.switch1);
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {
                    SDModeOn = true;
                } else {
                    SDModeOn = false;
                }
            }
        });

        initFiles();

    }


    public void readSong1Description(View view) {
        try {
            loadTextFromFile("song1Description.txt");
            numberOfTheLastSongClicked = 1;
        } catch (FileNotFoundException e) {
            songDescription.setText("File not found.");
        }
    }

    public void readSong2Description(View view) {
        try {
            loadTextFromFile("song2Description.txt");
            numberOfTheLastSongClicked = 2;
        } catch (FileNotFoundException e) {
            songDescription.setText("File not found.");
        }
    }

    public void save(View view) {
        if (SDModeOn) {
            saveToSD();
        } else {
            saveToInternalStorage();
        }
    }

    public void clear(View view) {
        songDescription.setText("");
        //no i plik ustaw na pusty , ale to póóźniej TODO


    }

    public void saveToSD() {
        File sdcard = Environment.getExternalStorageDirectory();
        File dir = new File(sdcard.getAbsolutePath() + "/MojePliki/");
        dir.mkdir();
        File file = new File(dir, "song"+numberOfTheLastSongClicked+"Description.txt");
        String text = songDescription.getText().toString();
        try {
            FileOutputStream os = new FileOutputStream(file);
            os.write(text.getBytes());
            os.close();
            Toast.makeText(this, "Text Saved !", Toast.LENGTH_LONG).show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveToInternalStorage() {
        try {
            OutputStreamWriter out = new
                    OutputStreamWriter(openFileOutput("song"+numberOfTheLastSongClicked+"Description.txt", MODE_PRIVATE));
            String text = songDescription.getText().toString();
            out.write(text);
            out.close();
            Toast.makeText(this, "Text Saved !", Toast.LENGTH_LONG).show();
        } catch (java.io.IOException e) {
            Toast.makeText(this, "Sorry Text could't be added", Toast.LENGTH_LONG).show();
        }
    }

    public void loadFromSD(String textFileName) {
        File sdcard = Environment.getExternalStorageDirectory();
        File dir = new File(sdcard.getAbsolutePath() + "/MojePliki/");
        File file = new File(dir, textFileName);
        int length = (int) file.length();
        byte[] bytes = new byte[length];
        FileInputStream in;
        try {
            in = new FileInputStream(file);
            in.read(bytes);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String contents = new String(bytes);
        songDescription.setText(contents);
    }

    public void loadFromInternalStorage(String textFileName) {
        StringBuilder text = new StringBuilder();
        try {
            InputStream instream = openFileInput(textFileName);
            if (instream != null) {
                InputStreamReader inputreader = new InputStreamReader(instream);
                BufferedReader buffreader = new BufferedReader(inputreader);
                String line = null;
                while ((line = buffreader.readLine()) != null) {
                    text.append(line);
                    text.append('\n');
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        songDescription.setText(text.toString());
    }

    public void loadTextFromFile(String textFileName) throws FileNotFoundException {
        if (SDModeOn) {
            loadFromSD(textFileName);
        } else {
            loadFromInternalStorage(textFileName);
        }

    }

    public void initFiles() {
        try {
            OutputStreamWriter out = new
                    OutputStreamWriter(openFileOutput("song1Description.txt", MODE_APPEND));
            out.write(stayingAliveText);
            out.close();

            out = new
                    OutputStreamWriter(openFileOutput("song2Description.txt", MODE_APPEND));
            out.write(billieJeanText);
            out.close();
        } catch (java.io.IOException e) {
            Toast.makeText(this, "Sorry, an error occured during file creation", Toast.LENGTH_LONG).show();
        }


        File sdcard = Environment.getExternalStorageDirectory();
        File dir = new File(sdcard.getAbsolutePath() + "/MojePliki/");
        dir.mkdir();
        File file = new File(dir, "song1Description.txt");
        try {
            FileOutputStream os = new FileOutputStream(file);
            os.write(stayingAliveText.getBytes());
            os.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        file = new File(dir, "song2Description.txt");

        try {
            FileOutputStream os = new FileOutputStream(file);
            os.write(billieJeanText.getBytes());
            os.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
