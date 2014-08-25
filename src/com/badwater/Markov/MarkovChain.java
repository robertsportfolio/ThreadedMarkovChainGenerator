package com.badwater.Markov;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by irinix on 8/24/14.
 */
public class MarkovChain implements Serializable {
	private HashMap<String, HashMap<String, Integer>> chains = new HashMap<String, HashMap<String, Integer>> ();
	private ArrayList<String[]> queue = new ArrayList<String[]> ();
	private boolean queueProcessing = false;

	public MarkovChain() {
	}

	public synchronized void genChain(String[] msg) {
		//iterate over the msg array setting both primary and next keys.
		for ( int i = 0; i < msg.length - 2; i++ ) {
			String priKey = msg[i];
			String nextKey = msg[i + 1];
			//if chains contains the primary key, check to see if it contains nextKey
			if ( chains.containsKey ( priKey ) ) {
				if ( chains.get ( priKey ).containsKey ( nextKey ) ) {
					//if it does, grab the integer Value of that key and add 1. Then remove that key from
					//chains[priKey] and re add it with the new word count.
					int tmp = chains.get ( priKey ).get ( nextKey ) + 1;
					chains.get ( priKey ).remove ( nextKey );
					chains.get ( priKey ).put ( nextKey, tmp );
				}
			}
			//if chains does NOT contain the primary key
			else if ( !chains.containsKey ( priKey ) ) {
				//create a temporary hashmap to store our values in.
				HashMap<String, Integer> tmpMap = new HashMap<String, Integer> ();
				tmpMap.put ( nextKey, 1 );
				//put our tmpMap int chains[key]
				chains.put ( priKey, tmpMap );
			}
		}
	}

	public synchronized void printChains() {
		for ( String key : chains.keySet () ) {
			System.out.println ( key );
			for ( String key1 : chains.get ( key ).keySet () ) {
				System.out.println ( "\t" + key1 + "\t" + chains.get ( key ).get ( key1 ));
				
			}
		}
	}

	public void generateNextLikelyWord(String word) {
	}
}