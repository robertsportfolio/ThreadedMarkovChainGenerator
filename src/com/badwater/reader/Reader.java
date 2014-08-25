package com.badwater.reader;

import com.badwater.Logger.Logger;
import com.badwater.Markov.MarkovChain;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by irinix on 8/24/14.
 */
public class Reader implements Runnable {
	private Logger logger;
	private File file;
	private MarkovChain mC;

	public Reader(File file, MarkovChain mC, Logger logger) {
		this.file = file;
		this.mC = mC;
		this.logger = logger;
	}

	@Override public void run() {
		try {
			System.out.println ( "Reader: " + Thread.currentThread ().getName () + " Started!" );
			logger.log ( "Thread: " + Thread.currentThread ().getName () + " Has Started Processing File: "
			             + file.getPath () );
			read ();
			System.out.println ( "Reader: " + Thread.currentThread ().getName () + " Finished!" );
			logger.log ( "Thread: " + Thread.currentThread ().getName () + " Has Finished Processing File: "
			             + file.getPath () );
		} catch (IOException e) {
			e.printStackTrace ();
		}
	}

	private void read() throws IOException {
		try (BufferedReader in = new BufferedReader ( new FileReader ( file ) {
			@Override public int read() throws IOException {
				return 0;
			}
		} )) {
			String line = "";

			while ( ( ( line = in.readLine () ) != null ) ) {
				mC.genChain ( parseForMCGen ( line ) );

			}
		}
	}

	private String[] parseForMCGen(String line) {
		String tmp = line.replaceAll ( "[\\S]+://[\\S]+", "" );
		String[] retVal = tmp.replaceAll ( "\\d+", "" ).replaceAll ( "\\p{Punct}", "" ).split ( " " );
		return retVal;
	}
}
