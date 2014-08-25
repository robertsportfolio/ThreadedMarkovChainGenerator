package com.badwater;

import com.badwater.reader.ReaderMgr;

public class Main {

	public static void main(String[] args) {
		/**TODO: Generate a Threadpool to manage upto 100 GUTENBERGREADERS
		 *       Give Each Thread a New GUTENBERGREADER(FILE)
		 *       Fill A String Queue With Each Line Of Text from Each Reader
		 *       When Queue >= 100 flush it to the markov chain generator
		 *       If Queue still has contents when all threads are finished, Flush Queue to generator
		 *
		 */
		ReaderMgr rMgr = new ReaderMgr ( "./gtbtmp" );

	}
}
