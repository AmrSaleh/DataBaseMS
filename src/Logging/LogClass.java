package Logging;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class LogClass {

	private static Logger log = Logger.getLogger(LogClass.class);

	static {
		initialize();
	}

//	public static void main(String[] args) {
//		initialize();
//		myMethod();
//
//	}

	public static void initialize() {
		Properties prop = new Properties();
		try {
			// load a properties file

			prop.load(new FileInputStream("log4j.properties"));

			PropertyConfigurator.configure("log4j.properties");
		} catch (IOException ex) {

			String props = "# Root logger option\r\nlog4j.rootLogger=INFO, file, stdout\r\n\r\n# Direct log messages to a log file\r\nlog4j.appender.file=org.apache.log4j.RollingFileAppender\r\nlog4j.appender.file.File=JDBC_logging.log\r\nlog4j.appender.file.MaxFileSize=1MB\r\nlog4j.appender.file.MaxBackupIndex=1\r\nlog4j.appender.file.layout=org.apache.log4j.PatternLayout\r\nlog4j.appender.file.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss} %-5p %c{1}:%L - %m%n\r\n\r\n# Direct log messages to stdout\r\nlog4j.appender.stdout=org.apache.log4j.ConsoleAppender\r\nlog4j.appender.stdout.Target=System.out\r\nlog4j.appender.stdout.layout=org.apache.log4j.PatternLayout\r\nlog4j.appender.stdout.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss} %-5p %c{1}:%L - %m%n";
			PrintWriter out;
			try {
				out = new PrintWriter("log4j.properties");
				out.println(props);
				out.close();

				PropertyConfigurator.configure("log4j.properties");
			} catch (FileNotFoundException e) {

				BasicConfigurator.configure();
			}

		}

		// log.trace("Trace");
		//
		// log.debug("Debug");
		//
		// log.info("Info");
		//
		// log.warn("Warn");
		//
		// log.error("Error");
		//
		// log.fatal("Fatal");
	}

	public static Logger log() {
		return log;
	}

//	private static void myMethod() {
//		try {
//			throw new Exception("My Exception");
//		} catch (Exception e) {
//			log.error("This is an exception", e);
//		}
//	}

}
