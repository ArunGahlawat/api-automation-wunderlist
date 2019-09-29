package com.nagarro.utils;

import com.aventstack.extentreports.Status;
import com.nagarro.utils.report.ExtentTestManager;
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
		if (LOGGER.isInfoEnabled()) {
			try {
				ExtentTestManager.getTest().log(Status.INFO, message);
			} catch (Exception ignore) {
			}
		}
	}

	/**
	 * method to display debug messages in console logs
	 *
	 * @param message message to be displayed
	 */
	public static void debug(String message) {
		LOGGER.debug(message);
		if (LOGGER.isDebugEnabled()) {
			try {
				ExtentTestManager.getTest().log(Status.DEBUG, message);
			} catch (Exception ignore) {
			}
		}
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
		if (LOGGER.isErrorEnabled()) {
			try {
				ExtentTestManager.getTest().log(Status.ERROR, "In class : " + className + ", method: " + methodName);
				ExtentTestManager.getTest().log(Status.ERROR, exception);
			} catch (Exception ignore) {
			}
		}
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
