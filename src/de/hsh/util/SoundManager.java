package de.hsh.util;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class SoundManager {
    private Clip virusChoppedClip;
    private Clip antivirusChoppedClip;
    private Clip gameOverClip;
    private Clip gameMusicClip;
    private Clip sliceClip;

    public SoundManager() {
        virusChoppedClip = loadClip("/de/hsh/audio/virus_chopped.wav");
        antivirusChoppedClip = loadClip("/de/hsh/audio/antivirus_chopped.wav");
        gameOverClip = loadClip("/de/hsh/audio/game_over.wav");
        gameMusicClip = loadClip("/de/hsh/audio/gamemusic.wav");
        sliceClip = loadClip("/de/hsh/audio/slicer.wav");
    }

    private Clip loadClip(String path) {
        try {
            URL url = getClass().getResource(path);
            if (url == null) {
                System.err.println("Audio file not found: " + path);
                return null;
            }
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            return clip;
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("Warning: Could not load audio file '" + path + "': " + e.getMessage());
            System.err.println("  -> Ensure the file is a standard 16-bit PCM WAV file.");
            return null;
        }
    }

    public void playVirusChopped() {
        playClip(virusChoppedClip);
    }

    public void playAntivirusChopped() {
        playClip(antivirusChoppedClip);
    }

    public void playGameOver() {
        stopBackgroundMusic();
        playClip(gameOverClip);
    }

    public void playBackgroundMusic() {
        if (gameMusicClip != null) {
            if (gameMusicClip.isRunning())
                return;
            gameMusicClip.setFramePosition(0);
            gameMusicClip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public void stopBackgroundMusic() {
        if (gameMusicClip != null && gameMusicClip.isRunning()) {
            gameMusicClip.stop();
        }
    }

    public void playSliceSound() {
        playClip(sliceClip);
    }

    private void playClip(Clip clip) {
        if (clip != null) {
            if (clip.isRunning()) {
                clip.stop();
            }
            clip.setFramePosition(0);
            clip.start();
        }
    }
}
