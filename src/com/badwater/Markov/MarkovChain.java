package com.badwater.Markov;

import com.badwater.Logger.Logger;

import java.io.*;
import java.util.HashMap;

/**
 * Created by irinix on 8/24/14.
 */
public class MarkovChain implements Serializable {
	private HashMap<String, HashMap<String, Integer>> chains = new HashMap<String, HashMap<String, Integer>> ();
	private Logger logger;

	public MarkovChain(Logger logger) throws IOException, ClassNotFoundException {
		this.logger = logger;
		loadChains ();
	}

	public synchronized void genChain(String[] msg) throws IOException {
		//iterate over the msg array setting both primary and next keys.
		for ( int i = 0; i < msg.length - 2; i++ ) {
			String priKey = msg[i];
			String nextKey = msg[i + 1];
			//if chains contains the primary key, check to see if it contains nextKey
			if ( chains.containsKey ( priKey ) ) {
				if ( chains.get ( priKey ).containsKey ( nextKey ) ) {
					//if it does, grab the integer Value of that key and add 1.  Then remove that key from
					//chains[priKey] and re add it with the new word count.
					int tmp = chains.get ( priKey ).get ( nextKey ) + 1;
					chains.get ( priKey ).put ( nextKey, tmp );
				}
				else {
					chains.get ( priKey ).put ( nextKey, 1 );
				}
			}
			//if chains does NOT contain the primary key
			else {
				//create a temporary hashmap to store our values in.
				HashMap<String, Integer> tmpMap = new HashMap<String, Integer> ();

				tmpMap.put ( nextKey, 1 );
				//put our tmpMap into chains[key]
				chains.put ( priKey, tmpMap );
			}
		}
	}
	public synchronized void printChains() {
		for ( String key : chains.keySet () ) {
			System.out.println ( key );
			for ( String key1 : chains.get ( key ).keySet () ) {
				System.out.println ( "\t" + key1 + "\t" + chains.get ( key ).get ( key1 ) );

			}
		}
	}

	public synchronized boolean saveChains() throws IOException {
		boolean success = false;
		File file = new File("./Markov");
		if(!file.exists ()){
			logger.log("Markov Directory does Not Exist.  Creating! " + file.mkdirs ());
		}

		else{
			try(FileOutputStream fos = new FileOutputStream ( file + "/Markov.Chains"  );
			    ObjectOutputStream chainSaver = new ObjectOutputStream ( fos )){
				logger.log("Saving Chains to: " + file + "Markov.Chains");
				chainSaver.writeObject ( chains );
				logger.log("Success!");
				success = true;
			}
		}
		return success;
	}

	private void loadChains() throws IOException, ClassNotFoundException {
		File file = new File ( "./Markov/Markov.Chains" );
		if ( !file.exists () ) {
			logger.log (
				   "Markov.Chains does not exist @: " + file.getPath () + " Please Call SaveChains to Create it" +
				   "." );
		}
		else {
			try (FileInputStream fis = new FileInputStream ( file );
			     ObjectInputStream chainLoader = new ObjectInputStream ( new BufferedInputStream ( fis ) )) {
				chains = (HashMap<String, HashMap<String, Integer>>) chainLoader.readObject ();
				logger.log ( "Chains Loaded Successfully from: " + file.getPath () );
			}
		}
	}


	public void generateNextLikelyWord(String word) {
	}
}