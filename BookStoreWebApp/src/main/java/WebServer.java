
import java.io.File;
import java.lang.management.ManagementFactory;
import java.net.URL;
import org.eclipse.jetty.jmx.MBeanContainer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AllowSymLinkAliasChecker;
import org.eclipse.jetty.webapp.WebAppContext;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author cpu02453-local
 */
public class WebServer {

	private static final String WEBAPP_RESOURCES_LOCATION = "webapp";

	public static void main(String[] args) throws Exception {
		Server server = new Server(8080);

		WebAppContext root = new WebAppContext();
		root.setContextPath("/");
		File warFile = new File(
				"../../jetty-distribution/target/distribution/test/webapps/test/");
		root.setWar(warFile.getAbsolutePath());
		root.addAliasCheck(new AllowSymLinkAliasChecker());

		root.setDescriptor(WEBAPP_RESOURCES_LOCATION + "/WEB-INF/web.xml");
		URL webAppDir = Thread.currentThread().getContextClassLoader().getResource(WEBAPP_RESOURCES_LOCATION);
		if (webAppDir == null) {
			throw new RuntimeException(String.format("No %s directory was found into the JAR file", WEBAPP_RESOURCES_LOCATION));
		}
		root.setResourceBase(webAppDir.toURI().toString());
		root.setParentLoaderPriority(true);

		server.setHandler(root);

		server.start();

		server.dumpStdErr();

		server.join();
	}
}
