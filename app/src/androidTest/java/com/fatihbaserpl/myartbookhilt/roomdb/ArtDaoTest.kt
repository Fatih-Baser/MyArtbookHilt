package com.fatihbaserpl.myartbookhilt.roomdb

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import com.fatihbaserpl.myartbookhilt.getOrAwaitValue
import dagger.hilt.android.testing.HiltAndroidRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before

import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named
import com.google.common.truth.Truth.assertThat
@SmallTest
@ExperimentalCoroutinesApi
class ArtDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("testDatabase")
    lateinit var database : ArtDatabase

    private lateinit var dao : ArtDao

    @Before
    fun setup() {

        database = Room.inMemoryDatabaseBuilder(// database de saklamıyorda özel bir yerde saklıyor
            ApplicationProvider.getApplicationContext(),ArtDatabase::class.java)
            .allowMainThreadQueries() //this is a test case, we don't want other thread pools
            .build()


        //hiltRule.inject()
        dao = database.artDao()
    }

    @After
    fun teardown() {
        database.close()
    }


  @Test
    fun insertArtTesting() = runBlockingTest { //runblocing corutein scope açıyor sıra sıra devam ediyor bitince devam ediyor
        val exampleArt = Art("Mona Lisa","Da Vinci",1700,"test.com",1)
        dao.insertArt(exampleArt)

        val list = dao.observeArts().getOrAwaitValue()
        assertThat(list).contains(exampleArt)

    }

   /* @Test
    fun deleteArtTesting() = runBlockingTest {
        val exampleArt = Art("Mona Lisa","Da Vinci",1700,"test.com",1)
        dao.insertArt(exampleArt)
        dao.deleteArt(exampleArt)

        val list = dao.observeArts().getOrAwaitValue()
        assertThat(list).doesNotContain(exampleArt)

    }*/

}