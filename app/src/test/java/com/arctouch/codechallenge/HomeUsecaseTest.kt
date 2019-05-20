package com.arctouch.codechallenge

import com.arctouch.codechallenge.core.domain.model.Either
import com.arctouch.codechallenge.core.domain.model.Failure
import com.arctouch.codechallenge.core.domain.model.Movie
import com.arctouch.codechallenge.core.domain.model.map
import com.arctouch.codechallenge.core.extensions.runTesting
import com.arctouch.codechallenge.home.data.repository.GenresRepository
import com.arctouch.codechallenge.home.data.repository.MoviesRepository
import com.arctouch.codechallenge.home.domain.HomeMoviesUseCase
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import kotlin.test.assertTrue

class HomeUsecaseTest {

    lateinit var homeMoviesUseCase: HomeMoviesUseCase

    @Mock
    lateinit var moviesRepository: MoviesRepository

    @Mock
    lateinit var genresRepository: GenresRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        homeMoviesUseCase = HomeMoviesUseCase(genresRepository, moviesRepository)
    }

    @Test
    fun `test if usecase is notifying error from repository`() = runTesting {
        whenever(moviesRepository.getUpcomingMoviesFromApi(1)).thenReturn(Either.Left(Failure.RequestError()))
        homeMoviesUseCase.getUpcoming(1, true) { result ->
            assertTrue(result.isLeft)
        }
    }

    @Test
    fun `test if usecase is returning movies from api only`() = runTesting {
        val movieFromApi = Mockito.mock(Movie::class.java)
        val movieFromCache = Mockito.mock(Movie::class.java)
        whenever(moviesRepository.getUpcomingMoviesFromCache()).thenReturn(listOf(movieFromCache))
        whenever(moviesRepository.getUpcomingMoviesFromApi(1)).thenReturn(Either.Right(listOf(movieFromApi)))

        homeMoviesUseCase.getUpcoming(1, false) { result ->
            result.map {
                it.size shouldEqual 1
                it[0] shouldEqual movieFromApi
            }

            verify(moviesRepository, times(0)).getUpcomingMoviesFromCache()
        }
    }

    @Test
    fun `test if usecase is returning movies from cache and api`() = runTesting {
        val movieFromApi = Mockito.mock(Movie::class.java)
        val movieFromCache = Mockito.mock(Movie::class.java)
        whenever(moviesRepository.getUpcomingMoviesFromCache()).thenReturn(listOf(movieFromCache))
        whenever(moviesRepository.getUpcomingMoviesFromApi(1)).thenReturn(Either.Right(listOf(movieFromApi)))

        homeMoviesUseCase.getUpcoming(1, true) { result ->
            result.map {
                it.size shouldEqual 2
                it[0] shouldEqual movieFromCache
                it[1] shouldEqual movieFromApi
            }
        }
    }

    @Test
    fun `test if usecase is returning movies from cache and notifying error from api on the same call`() = runTesting {

        /**
         * Since the usecase is callback based it´s possible to have error and result on the same call
         * Example: the user have movies on the cache, the orientation changes and the phone lost connection, the use case get a call to load movies,
         * in this case the usecase must return the movies from the cache and also notify the user about internet connection if he wants to load more
         */

        val movie = Mockito.mock(Movie::class.java)
        whenever(moviesRepository.getUpcomingMoviesFromCache()).thenReturn(listOf(movie))
        whenever(moviesRepository.getUpcomingMoviesFromApi(1)).thenReturn(Either.Left(Failure.RequestError()))

        homeMoviesUseCase.getUpcoming(1, true) { result ->
            if (result.isRight) {
                verify(moviesRepository, times(1)).getUpcomingMoviesFromCache()
                result.map {
                    it.size shouldEqual 1
                    it[0] shouldEqual movie
                }
            } else {
                result is Failure
            }
        }
    }
}
