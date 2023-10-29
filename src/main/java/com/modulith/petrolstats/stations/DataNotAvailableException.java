package com.modulith.petrolstats.stations;


/**
 * This exception is thrown when the data isn't available yet. In general a retry after some seconds should fix it.
 */
public class DataNotAvailableException extends RuntimeException {
}
