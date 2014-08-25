package com.badwater.Logger;

import java.io.*;
import java.util.Date;

/**
 * Created by irinix on 8/25/14.
 */
public class Logger {
	private String LogPath;
	private String dateStamp = new Date ().toString ().substring ( 0, 10 ).replace ( " ", "-" );

	public Logger(String LogPath) throws IOException {
		this.LogPath = LogPath;
		if ( !initLogger ( LogPath ) ) {
			System.out.println ( "Error Initializing Logger" );
		}
	}

	private boolean initLogger(String LogPath) throws IOException {
		boolean success = false;
		File file = new File ( LogPath );
		if ( !file.exists () ) {
			System.out.println ( "Logging Directory Does Not Exist.  Creating it." );
			success = file.mkdirs ();
		}
		log("=======New Run At: " + new Date().toString());
		return success;
	}

	public void log(String logMsg) throws IOException {
		String timeStamp = new Date ().toString ().substring ( 10, 19 ).trim ();
		logMsg = timeStamp + " :: " + logMsg;
		try (PrintWriter out = new PrintWriter (
			   new BufferedWriter ( new FileWriter ( LogPath + "/log-" + dateStamp, true ) ) )) {
			out.println ( logMsg );
		}
	}

}
