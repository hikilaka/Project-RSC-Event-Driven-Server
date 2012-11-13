package com.projectrsc.gameserver.util;

import java.io.FileInputStream;
import java.io.IOException;

import com.projectrsc.shared.xml.XmlNode;
import com.projectrsc.shared.xml.XmlParser;

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

	public static void loadSettings() {
		try {
			FileInputStream fis = new FileInputStream("bootstrap.xml");
			XmlParser parser = new XmlParser();
			XmlNode root = parser.parse(fis);

			if (!root.getName().equals("Settings")) {
				throw new IOException("The root node name is not 'Settings'");
			}
			XmlNode node = root.getChild("name");
			NAME = node.getValue();

			node = root.getChild("port");
			PORT = Integer.parseInt(node.getValue());

			node = root.getChild("version");
			VERSION = Integer.parseInt(node.getValue());

			node = root.getChild("members");
			MEMBERS = Boolean.parseBoolean(node.getValue());

			node = root.getChild("world");
			WORLD = Integer.parseInt(node.getValue());
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}