package com.projectrsc.gameserver.plugins;

import java.io.File;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import com.projectrsc.gameserver.GameServer;
import com.projectrsc.gameserver.event.Event;
import com.projectrsc.gameserver.event.EventListener;
import com.projectrsc.gameserver.listeners.ClientMessageListener;

public final class PluginHandler {

	private static final PluginHandler INSTANCE = new PluginHandler();

	public static PluginHandler getInstance() {
		return INSTANCE;
	}

	/**
	 * Listeners that will be applied to every player upon logging in
	 */
	private final Set<EventListener<? extends Event>> playerEventListeners = new HashSet<>();

	public void loadPlugins() {
		loadPacketListeners();
		loadPlayerEventListeners();
	}

	public Set<EventListener<? extends Event>> getPlayerEventListeners() {
		return playerEventListeners;
	}

	private void loadPacketListeners() {
		try {
			List<Class<?>> requestListeners = loadClasses("com.projectrsc.gameserver.plugins.requests");
			List<Class<?>> actionListeners = loadClasses("com.projectrsc.gameserver.plugins.listeners");
			registerPacketListeners(requestListeners);
			registerPacketListeners(actionListeners);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void registerPacketListeners(List<Class<?>> listeners)
			throws Exception {
		for (Class<?> c : listeners) {
			Object instance = c.newInstance();

			if (instance instanceof ClientMessageListener) {
				ClientMessageListener listener = ClientMessageListener.class
						.cast(instance);
				GameServer.getInstance().getConnectionHandler()
						.registerMessageListener(listener);
			}
		}
	}

	private void loadPlayerEventListeners() {
		try {
			List<Class<?>> playerListeners = loadClasses("com.projectrsc.gameserver.plugins.listeners.player");
			for (Class<?> c : playerListeners) {
				Object instance = c.newInstance();

				if (instance instanceof EventListener) {
					EventListener<?> listener = EventListener.class
							.cast(instance);
					playerEventListeners.add(listener);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private List<Class<?>> loadClasses(String pckgname)
			throws ClassNotFoundException {
		List<Class<?>> classes = new ArrayList<Class<?>>();
		ArrayList<File> directories = new ArrayList<File>();
		try {
			ClassLoader classLoader = PluginHandler.class.getClassLoader();
			Enumeration<URL> resources = classLoader.getResources(pckgname
					.replace('.', '/'));

			while (resources.hasMoreElements()) {
				URL res = resources.nextElement();
				if (res.getProtocol().equalsIgnoreCase("jar")) {
					JarURLConnection conn = (JarURLConnection) res
							.openConnection();
					JarFile jar = conn.getJarFile();
					for (JarEntry e : Collections.list(jar.entries())) {
						if (e.getName().startsWith(pckgname.replace('.', '/'))
								&& e.getName().endsWith(".class")
								&& !e.getName().contains("$")) {
							String className = e.getName().replace("/", ".")
									.substring(0, e.getName().length() - 6);
							classes.add(Class.forName(className));
						}
					}
				} else {
					directories.add(new File(URLDecoder.decode(res.getPath(),
							"UTF-8")));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (File directory : directories) {
			if (directory.exists()) {
				String[] files = directory.list();
				for (String file : files) {
					if (file.endsWith(".class") && !file.contains("$")) {
						classes.add(Class.forName(pckgname + '.'
								+ file.substring(0, file.length() - 6)));
					}
				}
			} else {
				throw new ClassNotFoundException(pckgname + " ("
						+ directory.getPath()
						+ ") does not appear to be a valid package");
			}
		}
		return classes;
	}

}
