package dev.mrviper111.utils;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class MediaPlayer {

    public enum Sound {

        HIT ("src/main/java/dev/mrviper111/game/audio/hit.wav"),
        MISS ("src/main/java/dev/mrviper111/game/audio/miss.wav"),
        EXPLODE ("src/main/java/dev/mrviper111/game/audio/explode.wav"),
        DISTANT_BOMBS ("src/main/java/dev/mrviper111/game/audio/bombs.wav"),
        FIRE ("src/main/java/dev/mrviper111/game/audio/fire.wav"),
        DING ("src/main/java/dev/mrviper111/game/audio/ding.wav"),
        WIN ("src/main/java/dev/mrviper111/game/audio/win.wav"),
        LOSE ("src/main/java/dev/mrviper111/game/audio/lose.wav");

        private final String filePath;

        Sound(String filePath) {
            this.filePath = filePath;
        }

        public String getPath() {
            return this.filePath;
        }

    }

    public static void playSound(Sound sound) {
        File audioFile = new File(System.getProperty("user.dir"), sound.getPath());

        try {
            Clip audioClip = AudioSystem.getClip();

            audioClip.open(AudioSystem.getAudioInputStream(audioFile.getAbsoluteFile()));
            audioClip.start();
        } catch (Exception error) {
            error.printStackTrace();
        }

    }

}
