package org.cis120.othello;

import java.io.*;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * FileLineIterator provides a useful wrapper around Java's provided
 * BufferedReader and provides practice with implementing an Iterator. Your
 * solution should not read the entire file into memory at once, instead reading
 * a line whenever the next() method is called.
 *<p>
 * Note: Any IOExceptions thrown by readers should be caught and handled
 * properly. Do not use the ready() method from BufferedReader.
 */

public class FileLineIterator implements Iterator<String> {

    // Add the fields needed to implement your FileLineIterator
    private final BufferedReader r;
    private int c = 0;
    private String state = null;

    /**
     * Creates a FileLineIterator for the reader. Fill out the constructor so
     * that a user can instantiate a FileLineIterator. Feel free to create and
     * instantiate any variables that your implementation requires here. See
     * recitation and lecture notes for guidance.
     *<p>
     * If an IOException is thrown by the BufferedReader, then hasNext should
     * return false.
     *<p>
     * The only method that should be called on BufferedReader is readLine() and
     * close(). You cannot call any other methods.
     *
     * @param reader - A reader to be turned to an Iterator
     * @throws IllegalArgumentException if reader is null
     */
    public FileLineIterator(BufferedReader reader) {
        if (reader == null) {
            throw new IllegalArgumentException();
        }
        this.r = reader;
        try {
            this.state = r.readLine();
        } catch (IOException e) {
            System.out.println("Error trying to read new line of constructor");
            this.c = -1;
        }
    }

    /**
     * Creates a FileLineIterator from a provided filePath by creating a
     * FileReader and BufferedReader for the file.
     *
     * @param filePath - a string representing the file
     * @throws IllegalArgumentException if filePath is null or if the file
     *                                  doesn't exist
     */
    public FileLineIterator(String filePath) {
        this(fileToReader(filePath));
    }

    /**
     * Takes in a filename and creates a BufferedReader.
     * See Java's documentation for BufferedReader to learn how to construct one
     * given a path to a file.
     *
     * @param filePath - the path to the CSV file to be turned to a
     *                 BufferedReader
     * @return a BufferedReader of the provided file contents
     * @throws IllegalArgumentException if filePath is null or if the file
     *                                  doesn't exist
     */
    public static BufferedReader fileToReader(String filePath) {
        // Instantiate the buffer reader
        Reader fin1;
        BufferedReader fin;
        // when is the null instantiation redundant and when is it needed?

        // attempt to create a bufferedreader of the filename given
        try {
            // check if the filename given is null
            if (filePath == null) {
                throw new IllegalArgumentException();
            }
            // if its not null, create readers for it
            fin1 = new FileReader(filePath);
            fin = new BufferedReader(fin1);

        } catch (FileNotFoundException e) {
            // if there is not file found print a string telling the error
            System.out.println("FileNotFoundException: File not found while trying to buffer");
            throw new IllegalArgumentException();
            // what should i be returning here?
            // check for any other types of errors in the processing
        }
        // Check for null (if the file was null or if there was an IO error

        // if there is an error throw an illegal argument exception
        return fin;
    }

    /**
     * Returns true if there are lines left to read in the file, and false
     * otherwise.
     *<p>
     * If there are no more lines left, this method should close the
     * BufferedReader.
     *
     * @return a boolean indicating whether the FileLineIterator can produce
     *         another line from the file
     */
    @Override
    public boolean hasNext() {
        // check if the current value exists
        // should I do c != null as well?
        boolean hasNext = (c != -1);
        // if the value does not exist, close the reader
        if (!hasNext) {
            try {
                r.close();
            } catch (IOException e) {
                System.out.println("File could not be closed in hasNext()");
            }
        }
        return hasNext;
    }

    /**
     * Returns the next line from the file, or throws a NoSuchElementException
     * if there are no more strings left to return (i.e. hasNext() is false).
     *<p>
     * This method also advances the iterator in preparation for another
     * invocation. If an IOException is thrown during a next() call, your
     * iterator should make note of this such that future calls of hasNext()
     * will return false and future calls of next() will throw a
     * NoSuchElementException
     *
     * @return the next line in the file
     * @throws NoSuchElementException if there is no more data in the
     *                                          file
     */
    @Override
    public String next() {

        // check if there is a next in the reader
        if (!this.hasNext()) {
            throw new NoSuchElementException();
        }

        // there is a next so return the line and move the state of the reader to the next line
        String fin = state;
        try {
            state = r.readLine();
            //System.out.println("state:" + state);

            // check if we are at the end of the read
            if (state == null) {
                c = -1;
            }

        } catch (IOException e) {
            System.out.println("Error trying to read new line of next method");
            this.c = -1;
        }

        return fin;
    }
}