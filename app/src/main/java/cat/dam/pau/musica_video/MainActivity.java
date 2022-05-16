package cat.dam.pau.musica_video;


import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.VideoView;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    MediaPlayer music;
    VideoView visor;

    ImageButton btn_music;
    Button btn_video_local;
    Button btn_video_remote;

    YouTubePlayerView youTubePlayerView;

    String youtubeVideId = "9TYzvfZIcf0";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        assignElements();

        music.start();

    }

    //Funció que carrega el vídeo local
    public void loadLocalVideo(){
        if(visor.getVisibility() == View.GONE){
            youTubePlayerView.setVisibility(View.GONE);
            visor.setVisibility(View.VISIBLE);
        }
        //Creem l'URI del video i el carreguem a la APP
        Uri video = Uri.parse("android.resource://" +
                getPackageName() + "/"
                + R.raw.mala_gestio);
        visor.setVideoURI(video);
        visor.setMediaController(new MediaController(this));
        if(music.isPlaying()) musicManagement();
        visor.start();

    }

    //Funció que carrega un video de youtube
    public void loadRemoteVideo(){
        if(youTubePlayerView.getVisibility() == View.GONE){
            if(music.isPlaying()) musicManagement();

            visor.setVisibility(View.GONE);
            youTubePlayerView.setVisibility(View.VISIBLE);
            youTubePlayerView.getYouTubePlayerWhenReady(youTubePlayer -> {
                youTubePlayer.loadVideo(youtubeVideId, 0f);
            });
        }
    }
    public void musicManagement(){
        if(music.isPlaying()){
            music.pause();
            btn_music.setImageResource(R.drawable.sound_off);
        }else{
            music.start();
            btn_music.setImageResource(R.drawable.sound_on);
        }
    }

    //Funció d'assignació dels components de la APP i crida dels listeners necessaris
    public void assignElements(){
        music = MediaPlayer.create(this, R.raw.son_interna);
        visor = (VideoView) findViewById(R.id.vv_video_local);
        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.video_remot);

        btn_music = (ImageButton) findViewById(R.id.btn_music);
        btn_video_local = (Button) findViewById(R.id.btn_video_local);
        btn_video_remote = (Button) findViewById(R.id.btn_video_remote);

        setListeners();
    }

    //Funció de setters dels listeners
    public void setListeners(){
        btn_music.setOnClickListener(this);
        btn_video_local.setOnClickListener(this);
        btn_video_remote.setOnClickListener(this);
    }

    //Funció que determina qué fa cada boto al ser clickat
    public void onClick(View view) {
        switch (view.getId()){
            case (R.id.btn_music): musicManagement(); break;
            case (R.id.btn_video_local): loadLocalVideo(); break;
            case (R.id.btn_video_remote): loadRemoteVideo(); break;
        }
    }
}