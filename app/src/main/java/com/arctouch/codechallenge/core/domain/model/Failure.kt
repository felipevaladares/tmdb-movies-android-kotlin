package com.arctouch.codechallenge.core.domain.model

/**
 * Base Class for handling errors/failures/exceptions.
 * Every feature specific failure should extend [FeatureFailure] class.
 */
sealed class Failure(val errorMessage: String) {
    class NetworkConnection: Failure("Verify your internet connection and try again!")
    class RequestError: Failure("Error loading data!")
    class ServerError: Failure("Server error!")
    class NoDataAvailable: Failure("No data available, probably you reach the end of the line!")

    /** * Extend this class for feature specific failures.*/
    abstract class FeatureFailure(errorMessage: String): Failure(errorMessage)
}
