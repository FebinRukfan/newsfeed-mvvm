package com.febinrukfan.newsfeed.newsfeed.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.febinrukfan.newsfeed.newsfeed.model.NewsFeedResponseItem
import com.febinrukfan.newsfeed.newsfeed.model.TypeAttributes
import com.febinrukfan.newsfeed.newsfeed.model.local.db.NewsFeedDatabase
import com.febinrukfan.newsfeed.newsfeed.model.repository.NewsFeedRepository
import com.google.common.truth.Truth.assertThat
import junit.framework.TestCase
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class NewsFeedViewModelTest : TestCase() {

    private lateinit var viewModel: NewsFeedViewModel

    lateinit var testTypeAttributesList: TypeAttributes

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    public override fun setUp() {
        super.setUp()

        val viewModelStore = ViewModelStore()
        val db = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), NewsFeedDatabase::class.java).allowMainThreadQueries().build()
        val newsFeedRepository = NewsFeedRepository(db)
        val viewModelProviderFactory = NewsFeedViewModelProviderFactory(ApplicationProvider.getApplicationContext(),newsFeedRepository)

        val viewModelProvider = ViewModelProvider(viewModelStore, viewModelProviderFactory)



        viewModel = viewModelProvider[NewsFeedViewModel::class.java]

        testTypeAttributesList = TypeAttributes("")

    }

    @Test
    fun `addAndRetrieveDataFromNewsFeedDBTest_False`(){

        viewModel.saveNewsFeedResponseItem(NewsFeedResponseItem(null, 1, "Evacuation Order Issued", "contentPackage", testTypeAttributesList, ""))
        viewModel.getAllNewsFeedResponseIem()

        val result = viewModel.getAllNewsFeedResponseIem().getOrAwaitValue().find {
            it.id == 1
        }

        assertThat(result != null).isFalse()

    }

    @Test
    fun `getNewsFeedTest_True`(){

        viewModel.saveNewsFeedResponseItem(NewsFeedResponseItem(null, 1, "Evacuation Order Issued", "contentPackage", testTypeAttributesList, ""))
        viewModel.getAllNewsFeedResponseIem()

        val result = viewModel.getAllNewsFeedResponseIem().getOrAwaitValue().find {
            it.id == 1
        }

        assertThat(result != null).isFalse()

    }


    @Test
    fun `internetConnectionTest_True`(){
        val result = viewModel.checkInternetConnection()

        assertThat(result != null).isTrue()

    }


}