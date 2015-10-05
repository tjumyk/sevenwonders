package org.tjuscs.sevenwonders.gui;

import java.net.URL;
import java.util.HashMap;

import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import javafx.scene.text.Font;

public class ResManager {

	private static HashMap<String, Image> images = new HashMap<String, Image>();
	private static HashMap<String, Font> fonts = new HashMap<String, Font>();
	private static HashMap<String, AudioClip> audioClips = new HashMap<String, AudioClip>();
	private static ResManager rm;

	// public static void main(String[] args) {
	// loadAll();
	// }

	// public static void loadImages() {
	// }
	//
	// public static void loadFonts() {
	// }
	//
	// public static void loadAudioClips() {
	// }
	//
	// public static void loadAll() {
	// loadAudioClips();
	// loadFonts();
	// loadImages();
	// }
	public ResManager() {
		rm = this;
	}

	public static Image getImage(String key) {
		Image image;
		if ((image = images.get(key)) != null) {
			return image;
		} else {
			image = new Image(rm.getClass().getResourceAsStream("res/image/" + key));

			if (image != null) {
				images.put(key, image);
				return image;
			} else {
				return null;
			}
		}
	}

	public static Font getFont(String key, int size) {
		Font font;
		if ((font = fonts.get(key + size)) != null) {
			return font;
		} else {

			font = Font.loadFont(rm.getClass().getResourceAsStream("res/font/" + key), size);

			if (font != null) {
				fonts.put(key + size, font);
				return font;
			} else {
				return null;
			}
		}
	}

	public static AudioClip getAudio(String key) {
		AudioClip audio = audioClips.get(key);
		if (audio != null) {
			return audio;
		} else {
			URL url = rm.getClass().getResource("res/sound/" + key);
			audio = new AudioClip(url.toString());
			if (audio != null) {
				audioClips.put(key, audio);
				return audio;
			} else {
				return null;
			}
		}
	}
}
