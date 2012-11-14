package com.projectrsc.gameserver;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

import com.projectrsc.gameserver.core.GameEngine;
import com.projectrsc.gameserver.network.RSCConnectionHandler;
import com.projectrsc.gameserver.network.RSCPipelineFactory;
import com.projectrsc.gameserver.plugins.PluginHandler;
import com.projectrsc.gameserver.util.Settings;
import com.projectrsc.shared.Constants;

/**
 * TODO: needs description
 * 
 * @author Hikilaka
 */
public final class GameServer {

	/**
	 * The main entry point to the application
	 * 
	 * @param args
	 *            arguments passed into the program
	 */
	public static void main(String[] args) {
		Constants.printLogo();
		Settings.loadSettings();

		System.out.println("Starting up " + Settings.NAME + " version " + Settings.VERSION + " on port " + Settings.PORT + "..");

		System.out.print("Loading plugins.. ");
		PluginHandler.getInstance().loadPlugins();
		System.out.println("Complete!");

		System.out.println(Settings.NAME + " is now online and ready for connections!");
	}

	/**
	 * The singleton object of this class
	 */
	private static final GameServer INSTANCE = new GameServer();

	/**
	 * @return This class' singleton object
	 */
	public static GameServer getInstance() {
		return INSTANCE;
	}

	private final Channel channel;

	private final ChannelFactory channelFactory;

	private final RSCConnectionHandler connectionHandler = new RSCConnectionHandler();

	private final GameEngine gameEngine = new GameEngine();

	private GameServer() {
		ExecutorService executorService = Executors.newFixedThreadPool(Settings.CORES * 2);
		channelFactory = new NioServerSocketChannelFactory(executorService, executorService);

		ServerBootstrap bootstrap = new ServerBootstrap(channelFactory);

		bootstrap.setPipelineFactory(new RSCPipelineFactory(connectionHandler));
		bootstrap.setOption("sendBufferSize", 5000);
		bootstrap.setOption("receiveBufferSize", 5000);
		bootstrap.setOption("child.tcpNoDelay", true);
		bootstrap.setOption("child.keepAlive", false);

		channel = bootstrap.bind(new InetSocketAddress(Settings.PORT));
	}

	public void closeChannel() {
		channel.close();
	}

	public RSCConnectionHandler getConnectionHandler() {
		return connectionHandler;
	}

	public GameEngine getGameEngine() {
		return gameEngine;
	}

}