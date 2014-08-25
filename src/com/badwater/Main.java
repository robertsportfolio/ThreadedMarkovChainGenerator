package com.badwater;

import com.badwater.Logger.Logger;
import com.badwater.reader.ReaderMgr;

import java.io.IOException;

public class Main {

	private static final String LOG_PATH = "./logs";

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		/**TODO: Generate a Threadpool to manage upto 100 GUTENBERGREADERS
		 *       Give Each Thread a New GUTENBERGREADER(FILE)
		 *       Fill A String Queue With Each Line Of Text from Each Reader
		 *       When Queue >= 100 flush it to the markov chain generator
		 *       If Queue still has contents when all threads are finished, Flush Queue to generator
		 *
		 */
		Logger logger = new Logger (LOG_PATH);
		ReaderMgr rMgr = new ReaderMgr ( "./gtbtmp", logger );


	}
}
