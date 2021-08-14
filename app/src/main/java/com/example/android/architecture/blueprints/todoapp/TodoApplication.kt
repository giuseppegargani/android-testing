/*
 * Copyright (C) 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.architecture.blueprints.todoapp

import android.app.Application
import timber.log.Timber
import timber.log.Timber.DebugTree

/**
 * An application that lazily provides a repository. Note that this Service Locator pattern is
 * used to simplify the sample. Consider a Dependency Injection framework.
 *
 * Also, sets up Timber in the DEBUG BuildConfig. Read Timber's documentation for production setups.
 */

/* Crea diversi Tests per Statistiche varie - fun statisticheAttiveEComplete_parzialmenteCompletate_restituisciPercentuale()
    Vai su StatisticsUtils e con il pulsante destro, clicca su generate e su Test ed accertati che sia JUnit4 e scrivi un test che riguardi alcune Tasks
            (la prima è val perchè la lista di Task è immutabile)
            testImplementation "org.hamcrest:hamcrest-all:$hamcrestVersion"

    @Test
    fun getActiveAndCompletedStats_partiallyCompleted_returnsPercentage() {

        // Create an active tasks (the false makes this active)
        val tasks = listOf<Task>(
            Task("title", "desc", isCompleted = false)
        )
        // Call our function
        val result = getActiveAndCompletedStats(tasks)

        // Check the result
        assertEquals(result.completedTasksPercent, 0f)
        assertEquals(result.activeTasksPercent, 100f)
    }

 */

/*1 - Crea un Test per Tasks nulle e con errori e che restituisce Completed Zero (e chiamale come vuoi)
    modifica la funzione in modo StatisticUtils in modo corrispondente!!!  e non preoccuparti per messaggi di errore durante la scrittura
    Nomi: getActiveAndCompletedStats_error_returnZeros getActiveAndCompletedStats_empty_returnZeros

    aggiustamenti all'inizio della funzione  (si usa il metodo .isEmpty())
    return if (tasks == null || tasks.isEmpty()) {
        StatsResult(0f, 0f)
    } else {

    //ma come valore di tasks si mette emptyList o null (direttamente come argomento per istanziare la variabile result

 */

/*2 - Rendi leggibili i tests con Hamcrest
    dipendenza:
    testImplementation "org.hamcrest:hamcrest-all:$hamcrestVersion"
    e in questo modo diventa:
    assertThat(result.completedTasksPercent, `is`(0f))
    VEDI APPROFONDIMENTI SUL SITO DI UDACITY
 */

/*MODIFICA LE DIPENDENZE IN MODO DA INCLUDERE RISORSE O MENO E VEDI RIEPILOGO GENERALE DEI CONCETTI
https://developer.android.com/training/testing/fundamentals
 */

/*3 - Crea un Unit Test che crea un test per un metodo di ViewModel con LiveData
    Di solito la logica di ViewModel si testa in locale (test) perchè non ha bisogno di framework o sistemi operativi
    SE E' UN VIEWMODEL NORMALE (E NON ANDROID VIEWMODEL) è sufficiente creare una istanza         val taskViewModel = TaskViewModel()
    Se invece hai bisogno di ApplicationContext (o di una classe di android) devi caricare Roboelectric e AndroidX e aggiungere un testrunner AndroidJUnit4
    dipendenze:
    // AndroidX Test - JVM testing
    testImplementation "androidx.test:core-ktx:$androidXTestCoreVersion"
    testImplementation "org.robolectric:robolectric:$robolectricVersion"
    testImplementation "androidx.test.ext:junit-ktx:$androidXTestExtKotlinRunnerVersion"
    come argomento della classe specifica di ViewModel si mette ApplicationProvider.getApplicationContext() !!!!
    in cima alla classe si mette         @RunWith(AndroidJUnit4::class)

    @RunWith(AndroidJUnit4::class)
    class TasksViewModelTest {

    @Test
    fun addNewTask_setsNewTaskEvent() {

        // Given a fresh ViewModel
        val tasksViewModel = TasksViewModel(ApplicationProvider.getApplicationContext())

        // When adding a new task
        tasksViewModel.addNewTask()
    }
 */

/* Modifica la dipendenze per gestire eventuali errori sul Manifest e installa se possibile Java 9 o compila a SDK 28
    // Always show the result of every unit test when running via command line, even if it passes.
    testOptions.unitTests {
        includeAndroidResources = true

        // ...
    }
 */

/*(4) Fai il test su LiveData
    InstantExecutorRule permette di eseguire le righe di codice in modo sincrono e ripetibile e la sua dipendenza è
    testImplementation "androidx.arch.core:core-testing:$archTestingVersion"
    si deve instanziare una variabile con una annotazione @get:Rule
    Il LIVEDATA VIENE OSSERVATO PERENNEMENTE E SI DEVE TOGLIERE MANUALMENTE (l'osservazione non viene azionata al modificarsi ma è continua)
    prima si crea una istanza dell'osserver DI TIPO CORRISPONDENTE (generico)  val observer = Observer<Event<Unit>> {}
    e poi si collega manualmente al LiveData (es. newTaskEvent di TaskViewModel)  taskViewModel.newTaskEvent.observeForever(observer)
    poi si compie una qualche azione (es. invocare un metodo di viewModel   come addNewTask())
    poi istanzio una variabile con il valore del LiveData, ne prendo il valore e lo confronto (cioè non dovrebbe essere null)
    assertThat(value.getContentIfNotHandled(), (not(nullValue())))
 */

/*4 - Usando LiveDataTestUtil
    instanzia una variabile con il metodo di getOrAwaitValue e controlla che il valore non sia null

    val value = tasksViewModel.newTaskEvent.getOrAwaitValue()
    assertThat(value.getContentIfNotHandled(), (not(nullValue())))
 */

/*5 - Scrivi un test (setFilterAllTasks_tasksAddViewVisible) - Quando il filterTask è su All Tasks allora TaskAddViewVisibile è true
        // When the filter type is ALL_TASKS
        tasksViewModel.setFiltering(TasksFilterType.ALL_TASKS)
        // Then the "Add task" action is visible
        assertThat(tasksViewModel.tasksAddViewVisible.getOrAwaitValue(), `is`(true))}
 */

/*6 - Sposta l'istanza di ViewModel all'esterno e prima di ogni singolo test - OGNI TEST DOVREBBE AVERE UNA NUOVA ISTANZA DEL VIEWMODEL
    // Subject under test
    private lateinit var tasksViewModel: TasksViewModel
    @Before
    fun setupViewModel() {
        tasksViewModel = TasksViewModel(ApplicationProvider.getApplicationContext())}
 */

/*SINTESI:    usando LiveDataTestUtil si può con due righe di codice testare il LiveData di un VieWmodel qualsiasi (anche AndroidViewModel)
    Il metodo invocato può contenere qualsiasi parametro e il LiveData può essere testato per qualsiasi valore!!!!!
    TEST PER QUALSIASI VALORE DI LIVEDATA
    NON SI DEVE AVERE UNA SOLA UNICA ISTANZA DEL VIEWMODEL PER OGNI TEST, ma si deve creare ogni volta una nuova
 */

/*CODICE FINALE DI TASKVIEWMODELTEST
@RunWith(AndroidJUnit4::class)
class TasksViewModelTest {

    // Subject under test
    private lateinit var tasksViewModel: TasksViewModel

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setupViewModel() {
        tasksViewModel = TasksViewModel(ApplicationProvider.getApplicationContext())
    }

    @Test
    fun addNewTask_setsNewTaskEvent() {
        // When adding a new task
        tasksViewModel.addNewTask()
        // Then the new task event is triggered
        val value = tasksViewModel.newTaskEvent.awaitNextValue()
        assertThat(
            value?.getContentIfNotHandled(), (not(nullValue()))
        )
    }

    @Test
    fun getTasksAddViewVisible() {
        // When the filter type is ALL_TASKS
        tasksViewModel.setFiltering(TasksFilterType.ALL_TASKS)
        // Then the "Add task" action is visible
        assertThat(tasksViewModel.tasksAddViewVisible.awaitNextValue(), `is`(true))
    }
}
 */

/*Funzione LiveDataTestUtil
    Una spiegazione completa di questa funzione la trovi sul post del blog!!! Link sul sito di Udacity
    DA METTERE UN PO' DI COMMENTI SUL CODICE!!!
    getOrAwaitValue   ottiene un valore oppure aspetta due secondi e restituisci null!!

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException


@VisibleForTesting(otherwise = VisibleForTesting.NONE)
fun <T> LiveData<T>.getOrAwaitValue(
    time: Long = 2,
    timeUnit: TimeUnit = TimeUnit.SECONDS,
    afterObserve: () -> Unit = {}
): T {
    var data: T? = null
    val latch = CountDownLatch(1)
    val observer = object : Observer<T> {
        override fun onChanged(o: T?) {
            data = o
            latch.countDown()
            this@getOrAwaitValue.removeObserver(this)
        }
    }
    this.observeForever(observer)

    try {
        afterObserve.invoke()

        // Don't wait indefinitely if the LiveData is not set.
        if (!latch.await(time, timeUnit)) {
            throw TimeoutException("LiveData value was never set.")
        }

    } finally {
        this.removeObserver(observer)
    }

    @Suppress("UNCHECKED_CAST")
    return data as T
}

 */

class TodoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(DebugTree())
    }
}
