package com.nagarro.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class Log {
	public static final Logger LOGGER = LoggerFactory.getLogger(Log.class);
	private static PrintStream myPrintStream;

	/**
	 * method to display information in console logs only
	 *
	 * @param message message to be displayed
	 */
	public static void info(String message) {
		LOGGER.info(message);
	}

	/**
	 * method to display debug messages in console logs
	 *
	 * @param message message to be displayed
	 */
	public static void debug(String message) {
		LOGGER.debug(message);
	}

	/**
	 * method to display errors in console logs
	 *
	 * @param className  name of class in which error occurred.
	 * @param methodName name of method in which error occurred.
	 * @param exception  stack trace of exception
	 */
	public static void error(String className, String methodName, String exception) {
		LOGGER.error("ClassName : " + className);
		LOGGER.error("MethodName : " + methodName);
		LOGGER.error("==========================================================");
		LOGGER.error("Exception : " + exception);
		LOGGER.error("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	}

	/**
	 * @return printStream
	 */
	public static PrintStream getPrintStream()
	{
		if ( myPrintStream == null )
		{
			OutputStream output = new OutputStream()
			{
				private StringBuilder myStringBuilder = new StringBuilder();

				@Override
				public void write(int b) throws IOException
				{
					this.myStringBuilder.append((char) b );
				}

				/**
				 * @see java.io.OutputStream#flush()
				 */
				@Override
				public void flush()
				{
					debug( this.myStringBuilder.toString() );
					myStringBuilder = new StringBuilder();
				}
			};
			myPrintStream = new PrintStream( output, true );  // true: autoflush must be set!
		}
		return myPrintStream;
	}
}
