package org.tjuscs.sevenwonders;

import java.io.PrintWriter;
import java.io.StringWriter;

import javafx.application.Application;
import javafx.application.ConditionalFeature;
import javafx.application.Platform;
import javafx.application.Preloader.PreloaderNotification;
import javafx.stage.Stage;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.tjuscs.sevenwonders.gui.ErrorDialog;
import org.tjuscs.sevenwonders.gui.GUIManager;
import org.tjuscs.sevenwonders.kernel.KernelManager;
import org.tjuscs.sevenwonders.net.NetManager;

public class Manager extends Application {

	private final static Logger log = Logger.getLogger(Manager.class.getName());
	private static Manager manager;
	private static KernelManager km;
	private static NetManager nm;
	private static GUIManager gm;
	private static Object finalReport;
	public static boolean isApplet;
	private static int warningCount;
	private static int errorCount;
	private static int exitCode;

	public static Manager getManager() {
		return manager;
	}

	public static GUIManager getGUI() {
		return gm;
	}

	public static NetManager getNet() {
		return nm;
	}

	public static KernelManager getKernel() {
		return km;
	}

	@Override
	public void init() throws Exception {
		super.init();
		log.setLevel(Level.DEBUG);
		log.info("Log system initialized");
		km = KernelManager.getManager();
		nm = NetManager.getManager();
		gm = GUIManager.getManager();
		log.info("All moduels initialized");
		if (Platform.isSupported(ConditionalFeature.EFFECT))
			log.info("Check Effect support...Yes!");
		else
			log.warn("Check Effect support...No!");
		if (Platform.isSupported(ConditionalFeature.INPUT_METHOD))
			log.info("Check Input Method support...Yes!");
		else
			log.warn("Check Input Method support...No!");
		if (Platform.isSupported(ConditionalFeature.SCENE3D))
			log.info("Check 3D Scene support...Yes!");
		else {
			log.warn("Check 3D Scene support...No!");
			log.warn("Turn down Effect-Level automatically...");
			GUIManager.enableReflectionEffect = false;
			GUIManager.enableSmooth = false;
		}
		if (Platform.isSupported(ConditionalFeature.SHAPE_CLIP))
			log.info("Check Shape Clip support...Yes!");
		else
			log.warn("Check Shape Clip support...No!");
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Manager.manager = this;
		netscape.javascript.JSObject browser = getHostServices().getWebContext();
		isApplet = browser != null;
		if (isApplet) {
			GUIManager.width = (int) primaryStage.getWidth();
			GUIManager.height = (int) primaryStage.getHeight();
			log.info("Starting running as an Applet...");
		} else
			log.info("Start running as an Application...");
		log.info("Now Loading GUI Moduel...");
		gm.start(primaryStage);
	}

	public static void hidePreloader() {
		manager.notifyPreloader(new PreloaderNotification() {
		});
	}

	@Override
	public void stop() throws Exception {
		super.stop();
		if (errorCount <= 0)
			if (warningCount <= 0)
				log.info("Exit successfully![" + exitCode + "]");
			else
				log.warn("Exit with " + warningCount + " waring(s)![" + exitCode + "]");
		else
			log.error("Exit with " + errorCount + " error(s)![" + exitCode + "]");
	}

	public static void info(Object o) {
		log.info(o);
	}

	public static void debug(Object o) {
		StringBuilder os = new StringBuilder(o.toString());
		if (os.length() < 60 && os.charAt(os.length() - 1) == '\n')
			os.deleteCharAt(os.length() - 1);
		for (int i = os.length(); i < 60; i++)
			os.append('-');
		log.debug(os);
	}

	public static void warn(Object o) {
		StringBuilder os = new StringBuilder(o.toString());
		if (os.length() < 60 && os.charAt(os.length() - 1) == '\n')
			os.deleteCharAt(os.length() - 1);
		for (int i = os.length(); i < 60; i++)
			os.append('=');
		log.warn(os);
		warningCount++;
	}

	public static void error(Object o) {
		StringBuilder os = new StringBuilder(o.toString());
		// if(os.length() < 60 && os.charAt(os.length()-1) == '\n')
		// os.deleteCharAt(os.length()-1);
		// for(int i = os.length() ; i< 60; i++)
		// os.append('#');
		if (o instanceof Throwable) {
			StringWriter str = new StringWriter();
			Throwable e = (Throwable) o;
			e.printStackTrace(new PrintWriter(str));
			log.error(str.toString());
			ErrorDialog.report((Throwable) o);
		} else {
			log.error(o.toString());
		}

		errorCount++;

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}

	public static void exit(int exitCode) {
		Manager.exitCode = exitCode;
		if (Manager.isApplet)
			Manager.getManager().getHostServices().getWebContext().eval("close()");
		else
			Platform.exit();

	}

}
