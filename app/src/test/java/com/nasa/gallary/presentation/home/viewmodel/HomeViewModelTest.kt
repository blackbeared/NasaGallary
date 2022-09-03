@file:OptIn(ExperimentalCoroutinesApi::class)

package com.nasa.gallary.presentation.home.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.nasa.gallary.app.common.Resource
import com.nasa.gallary.domain.use_cases.GetNasaDataUseCase
import com.nasa.gallary.presentation.home.item_model.NasaModel
import com.nasa.gallary.presentation.home.item_model.toNasaModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import testUtils.InstantExecutorExtension
import testUtils.NasaDataReader
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@ExtendWith(InstantExecutorExtension::class)
class HomeViewModelTest {

    val dispatcher = TestCoroutineDispatcher()

    @MockK
    private lateinit var useCase: GetNasaDataUseCase

    private lateinit var myViewModel: HomeViewModel

    @MockK
    private var nasaDataContents = MutableLiveData<List<NasaModel>>()

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        myViewModel = HomeViewModel(useCase)
        Dispatchers.setMain(dispatcher)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun loadNasaData() {
        runBlocking {
            coEvery { useCase.getNasaData(true) } answers {
                flow {
                    emit(Resource.Loading)
                    val repositories = NasaDataReader.fakeFeedLiveModel("api/response/nasa_data_5_items.json")
                    emit(Resource.Success(repositories))
                }.catch {
                    emit(Resource.Error(it))
                }
            }

            var allNasaData: List<NasaModel>? = ArrayList()
            coEvery { nasaDataContents.value?.get(0) } returns NasaDataReader.fakeNasaModel("api/response/nasa_data.json")
                ?.toNasaModel()
            val liveData = myViewModel.initTestingData()
            val latch = CountDownLatch(1)
            liveData.observeForever(object : Observer<List<NasaModel>> {
                override fun onChanged(t: List<NasaModel>?) {
                    allNasaData = t
                    assertEquals(allNasaData?.size, 5)
                    latch.countDown()
                    liveData.removeObserver(this)
                }
            })
            latch.await(10, TimeUnit.SECONDS)
            assertNotNull(allNasaData)
        }
    }
}