package com.projectrsc.gameserver.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import com.google.gson.stream.JsonReader;

/**
 * Loads and holds all of the game's settings
 * 
 * @author Hikilaka
 * @version 1
 * @since 0.1
 */
public final class Settings {

	/**
	 * The name of the game
	 */
	public static String NAME = "RuneScape";

	/**
	 * The port number the GameServer will listen on
	 */
	public static int PORT = 43594;

	/**
	 * The current version of the game
	 */
	public static int VERSION = 0;

	/**
	 * Whether this is a members world or not
	 */
	public static boolean MEMBERS = false;

	/**
	 * The games world number (used for multiple worlds)
	 */
	public static int WORLD = 1;
	
	/**
	 * The number of cores Java Virtual Machine has available
	 */
	public static int CORES = Runtime.getRuntime().availableProcessors();

	/**
	 * Loads settings from the bootstrap
	 */
	public static void loadSettings() {
		try (JsonReader reader = new JsonReader(new InputStreamReader(new FileInputStream("bootstrap.json")))) {
			reader.beginObject();
			while (reader.hasNext()) {
				switch (reader.nextName()) {
				case "name":
					NAME = reader.nextString();
					break;
				case "port":
					PORT = reader.nextInt();
					break;
				case "version":
					VERSION = reader.nextInt();
					break;
				case "members":
					MEMBERS = reader.nextBoolean();
					break;
				case "world":
					WORLD = reader.nextInt();
					break;
				}
			}
			reader.endObject();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}