package com.badwater.reader;

import com.badwater.Logger.Logger;
import com.badwater.Markov.MarkovChain;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by irinix on 8/24/14.
 */
public class ReaderMgr {

	private File directory;
	private final int MAX_THREADS = 100;
	private final ExecutorService executor;
	private ArrayList<File> files = new ArrayList<File> ();
	private  MarkovChain mC;
	private Logger logger;
	public ReaderMgr(String path, Logger logger) throws IOException, ClassNotFoundException {
		//ctor:
		directory = new File ( path );
		this.logger = logger;
		//create a new MarkovChainGenerator
		mC = new MarkovChain (this.logger);
		//create a new executor service, and execute it.
		executor = Executors.newFixedThreadPool ( MAX_THREADS );
		getFiles(directory);
		execute ();
		//try to shutdown cleanly.
		executor.shutdown ();
		try {
			executor.awaitTermination ( 10, TimeUnit.MINUTES );
			mC.printChains ();
		} catch (InterruptedException e) {
			//if it doesn't, put it down forcefully.  (Should never happen, but you never know.)
			logger.log ( "Clean Shutdown failed due to timeout.  Forcing" );
			executor.shutdownNow ();
		}
		if(!mC.saveChains ()){
			logger.log("Unknown Error Saving Chains");
		}
		else{
			logger.log ( "Chains saved Successfully!" );
		}

	}

	private void getFiles(File directory) {
		//List all files in directory
		for ( File fileEntry : directory.listFiles () ) {
			//if we bump into a directoy, list those files, and add any txt files to our list.
			if ( fileEntry.isDirectory () ) {
				getFiles ( fileEntry );
			}
			else {
				//if the file is a txt file, add it to our files list, otherwise, ignore.
				if ( fileEntry.isFile () ) {
					String tmp = fileEntry.getName ();
					if ( tmp.substring ( tmp.lastIndexOf ( '.' ) + 1, tmp.length () )
					        .equalsIgnoreCase ( "txt" ) ) {
						files.add ( fileEntry );

					}
				}
			}
		}
	}
	private void execute(){
		//create a new reader for each file in our folder, and add them to the queue.
		for(File f : files){
			Runnable worker = new Reader(f, mC , logger );
			executor.execute ( worker );
		}
		//clear our list of files to ensure that if we call this class again, it's clear.  Yes, I know,
		//Its redundant, but I like to be sure.
		files.clear();
	}
}
