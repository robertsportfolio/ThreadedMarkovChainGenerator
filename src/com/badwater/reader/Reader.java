package com.badwater.reader;

import com.badwater.Markov.MarkovChain;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by irinix on 8/24/14.
 */
public class Reader implements Runnable {

	private File file;
	private MarkovChain mC;
	public Reader(File file, MarkovChain mC){
		this.file = file;
		this.mC = mC;
	}

	@Override public void run() {
		try {
			read();
		} catch (IOException e) {
			e.printStackTrace ();
		}
	}

	private void read() throws IOException {
		try(BufferedReader in = new BufferedReader ( new FileReader ( file ) {
			@Override public int read() throws IOException {
				return 0;
			}
		} )){
			String line = "";
			while ((line = in.readLine ()) != null){
				System.out.println("Thread " + Thread.currentThread ().getName () + " Is Sending Line \"" + line + "\" to mC.genChain()");
				mC.genChain ( parseForMCGen ( line ));

			}
		}
	}

	private String[] parseForMCGen(String line) {
		String tmp = line.replaceAll ( "[\\S]+://[\\S]+", "" );
		String[] retVal = tmp.replaceAll("\\d+", "").replaceAll ( "\\p{Punct}", "" ).split(" ");
		return retVal;
	}
}
