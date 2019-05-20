package com.arctouch.codechallenge

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.arctouch.codechallenge.core.domain.model.Either
import com.arctouch.codechallenge.core.domain.model.Failure
import com.arctouch.codechallenge.core.domain.model.Movie
import com.arctouch.codechallenge.core.domain.model.map
import com.arctouch.codechallenge.core.extensions.runTesting
import com.arctouch.codechallenge.home.data.repository.GenresRepository
import com.arctouch.codechallenge.home.data.repository.MoviesRepository
import com.arctouch.codechallenge.home.domain.HomeMoviesUseCase
import com.arctouch.codechallenge.home.presentation.HomeViewModel
import com.nhaarman.mockitokotlin2.whenever
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import kotlin.test.assertTrue

class HomeViewModelTest {

    @Rule
    @JvmField
    val rule: TestRule = InstantTaskExecutorRule()

    lateinit var homeViewModel: HomeViewModel

    lateinit var homeMoviesUseCase: HomeMoviesUseCase

    @Mock
    lateinit var moviesRepository: MoviesRepository

    @Mock
    lateinit var genresRepository: GenresRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        homeMoviesUseCase = HomeMoviesUseCase(genresRepository, moviesRepository)
        homeViewModel = HomeViewModel(homeMoviesUseCase)
    }

    @Test
    fun `test if viewmodel is notifying movies`() = runTesting {
        val movies = listOf(Mockito.mock(Movie::class.java))
        whenever(moviesRepository.getUpcomingMoviesFromApi(1)).thenReturn(Either.Right(movies))
        whenever(homeMoviesUseCase.getUpcoming(1, false) {
            it.map { result ->
                homeViewModel.handleSuccess(result)
            }
        }).then { }

        homeViewModel.movies.observeForever {
            it?.size shouldEqual 1
        }

        homeViewModel.loadMovies(false)
    }

    @Test
    fun `test if viewmodel is notifying failure`() = runTesting {
        val failure = Mockito.mock(Failure.RequestError::class.java)
        whenever(moviesRepository.getUpcomingMoviesFromApi(1)).thenReturn(Either.Left(failure))
        whenever(homeMoviesUseCase.getUpcoming(1, false) {
            homeViewModel.handleFailure(failure)
        }).then { }

        homeViewModel.failure.observeForever{
            assertTrue(it is Failure.RequestError)
        }

        homeViewModel.loadMovies()
    }
}
