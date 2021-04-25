package ru.listtask.utils.managers;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import static ru.listtask.utils.managers.ConfProperties.getConfProperties;
import static ru.listtask.utils.PropertiesConstant.*;

public class GongPlayer {
    private Clip gongSound;
    
    public void play() {
		gongSound = null;
		try {
			File f = new File(getConfProperties().getProperty(FILE_NAME_MUSIC));
			AudioFileFormat aff = AudioSystem.getAudioFileFormat(f);
			AudioFormat af = aff.getFormat();
			DataLine.Info info = new DataLine.Info(Clip.class, af);
			if(AudioSystem.isLineSupported(info)) {
				gongSound = (Clip) AudioSystem.getLine(info);
				AudioInputStream ais = AudioSystem.getAudioInputStream(f);
				gongSound.open(ais);
				gongSound.start();
				Thread.sleep(1500);
				gongSound.stop();
				gongSound.close();
			} else {
				System.out.println("Формат музыкального файла не поддерживается!");
			}
		} catch (IOException | InterruptedException | LineUnavailableException
                        | UnsupportedAudioFileException e) {
			System.out.println("Ошибка востпроизведения звука!");
		}
	}
}
