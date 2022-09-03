@file:OptIn(ExperimentalCoroutinesApi::class)

package com.nasa.gallary.domain.usecase

import NasaData
import com.nasa.gallary.app.common.Resource
import com.nasa.gallary.app.common.doOnError
import com.nasa.gallary.app.common.doOnSuccess
import com.nasa.gallary.domain.repository.NasaRepository
import com.nasa.gallary.domain.use_cases.GetNasaDataUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import testUtils.NasaDataReader
import java.util.concurrent.TimeUnit


internal class GetNasaDataUseCaseTest {
    @MockK
    private lateinit var fakeNasaDataRepo: NasaRepository
    private lateinit var getNasaDataUseCase: GetNasaDataUseCase

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        getNasaDataUseCase = GetNasaDataUseCase(fakeNasaDataRepo)
    }

    @Nested
    @DisplayName("getNasaData return success")
    @Timeout(value = 20, unit = TimeUnit.SECONDS)
    inner class NasDataApiSuccess {
        @Nested
        @DisplayName("NasaData is not empty")
        inner class NasaDataNotEmpty {

            @Test
            @DisplayName("the list should contain 5 items with 5 NasaData")
            fun containsFiveItems() {
                val fakeFeedLive = NasaDataReader.fakeFeedLiveModel("api/response/nasa_data_5_items.json")
                coEvery { fakeNasaDataRepo.getNasaData(true) } returns fakeFeedLive.provideFakeApiSuccessResult()

                runBlocking {
                    val result = getNasaDataUseCase.getNasaData(true)
                    result.doOnSuccess {
                        assertAll(
                            "nasaDataExist",
                            { assertNotNull(it) },
                            { assertEquals(5, fakeFeedLive.size) },
                            { assertEquals(5, it.size) }
                        )
                    }
                }
            }

            @Test
            @DisplayName("the list should contain 5 items with null copyright value")
            fun nasaDataDoNotContainCopyright() {
                val fakeFeedLive = NasaDataReader.fakeFeedLiveModel("api/response/nasa_data_no_copyright.json.json")
                coEvery { fakeNasaDataRepo.getNasaData(true) } returns fakeFeedLive.provideFakeApiSuccessResult()

                runBlocking {
                    val result = getNasaDataUseCase.getNasaData(true)
                    result.doOnSuccess {
                        assertAll(
                            "nasaDataNoCopyrightResult",
                            { assertNotNull(result) },
                            { assertEquals(5, fakeFeedLive.size) },
                            { assertEquals(null, it[0].copyright) },
                            { assertEquals(null, it[2].copyright) },
                            { assertEquals(null, it[3].copyright) },
                            { assertEquals(null, it[4].copyright) },
                            { assertEquals(null, it[5].copyright) },
                        )
                    }
                }
            }
        }

        @Nested
        @DisplayName("Nasa Data is empty")
        inner class NasaDataEmpty {
            @Test
            @DisplayName("the Nasa Data list should be empty")
            fun getEmptyResult() {
                val fakeFeedLive = NasaDataReader.fakeFeedLiveModel("api/response/nasa_data_no_items.json")
                coEvery { fakeNasaDataRepo.getNasaData(true) } returns fakeFeedLive.provideFakeApiSuccessResult()
                runBlocking {
                    val result = getNasaDataUseCase.getNasaData(true)
                    result.doOnSuccess {
                        assertAll(
                            "noItems",
                            { assertEquals(0, fakeFeedLive.size) },
                        )
                    }
                }
            }
        }
    }

    @Nested
    @DisplayName("getNasaData throws Exception")
    @Timeout(value = 20, unit = TimeUnit.SECONDS)
    inner class NasaDataThrowsException {
        @Test
        @DisplayName("the nasa data should be null")
        fun getNullResult() {
            coEvery { fakeNasaDataRepo.getNasaData(true) }.throws(Exception("No Data Found"))
            runBlocking {
                val result = getNasaDataUseCase.getNasaData(true)
                result.doOnSuccess {
                    assertAll(
                        "noDataFound",
                        { assertNull(result) }
                    )
                }.doOnError{
                    assertAll(
                        "received Exception",
                        { assertNotNull(it.message) },
                        { assertEquals(it.message, "No Data Found") }
                    )
                }
            }
        }
    }

    fun List<NasaData>.provideFakeApiSuccessResult() = Resource.Success(this).data
}