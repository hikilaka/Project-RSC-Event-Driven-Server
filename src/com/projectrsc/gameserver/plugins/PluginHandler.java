package com.projectrsc.gameserver.plugins;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import com.projectrsc.gameserver.GameServer;
import com.projectrsc.gameserver.listeners.ClientMessageListener;
import com.projectrsc.gameserver.plugins.listeners.PingListener;
import com.projectrsc.gameserver.plugins.requests.LoginRequestListener;
import com.projectrsc.gameserver.plugins.requests.SessionRequestListener;

public final class PluginHandler {

	private static final PluginHandler INSTANCE = new PluginHandler();

	public static PluginHandler getInstance() {
		return INSTANCE;
	}

	public void loadPlugins() {
		//loadPacketListeners(); TODO: fix dis shit..
		GameServer.getInstance().getConnectionHandler().registerMessageListener(new SessionRequestListener());
		GameServer.getInstance().getConnectionHandler().registerMessageListener(new LoginRequestListener());
		GameServer.getInstance().getConnectionHandler().registerMessageListener(new PingListener());
	}

	private void loadPacketListeners() {
		try {
			List<Class<?>> listeners = loadClasses("com.projectrsc.gameserver.plugins.requests");
			for (Class<?> clazz : listeners) {
				Object instance = clazz.newInstance();

				if (instance instanceof ClientMessageListener) {
					ClientMessageListener listener = ClientMessageListener.class.cast(instance);
					GameServer.getInstance().getConnectionHandler().registerMessageListener(listener);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private List<Class<?>> loadClasses(String pckgname) throws ClassNotFoundException {
		List<Class<?>> classes = new ArrayList<Class<?>>();
		ArrayList<File> directories = new ArrayList<File>();
		try {
			ClassLoader cld = Thread.currentThread().getContextClassLoader();
			if (cld == null) {
				throw new ClassNotFoundException("Can't get class loader.");
			}
			Enumeration<URL> resources = cld.getResources(pckgname.replace('.', '/'));
			while (resources.hasMoreElements()) {
				URL res = resources.nextElement();
				if (res.getProtocol().equalsIgnoreCase("jar")) {
					JarURLConnection conn = (JarURLConnection) res.openConnection();
					JarFile jar = conn.getJarFile();
					for (JarEntry e : Collections.list(jar.entries())) {
						if (e.getName().startsWith(pckgname.replace('.', '/')) && e.getName().endsWith(".class") && !e.getName().contains("$")) {
							String className = e.getName().replace("/",".").substring(0,e.getName().length() - 6);
							classes.add(Class.forName(className));
						}
					}
				} else {
					directories.add(new File(URLDecoder.decode(res.getPath(), "UTF-8")));
				}
			}
		} catch (NullPointerException x) {
			throw new ClassNotFoundException(pckgname + " does not appear to be a valid package (Null pointer exception)");
		} catch (UnsupportedEncodingException encex) {
			throw new ClassNotFoundException(pckgname + " does not appear to be a valid package (Unsupported encoding)");
		} catch (IOException ioex) {
			throw new ClassNotFoundException("IOException was thrown when trying to get all resources for " + pckgname);
		}

		for (File directory : directories) {
			if (directory.exists()) {
				String[] files = directory.list();
				for (String file : files) {
					if (file.endsWith(".class")) {
						classes.add(Class.forName(pckgname + '.' + file.substring(0, file.length() - 6)));
					}
				}
			} else {
				throw new ClassNotFoundException(pckgname + " (" + directory.getPath() + ") does not appear to be a valid package");
			}
		}
		return classes;
	}

}
