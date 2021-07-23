package com.example.android.architecture.blueprints.todoapp.statistics

import com.example.android.architecture.blueprints.todoapp.data.Task
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.*
import org.junit.Test

class StatisticsUtilsTest {

    @Test
    fun tasks_non_completate(){

        val tasks = listOf<Task>(
                Task("titolo", "descrizione", false)
        )
        val risultato = getActiveAndCompletedStats(tasks)

        assertThat(risultato.activeTasksPercent, `is` (100f))
        assertThat(risultato.completedTasksPercent, `is` (0f))
    }

    @Test
    fun tre_su_cinque_completati(){

        val tasks = listOf<Task>(
                Task("titolo", "descrizione", false),
                Task("titolo", "descrizione", false),
                Task("titolo", "descrizione", true),
                Task("titolo", "descrizione", true),
                Task("titolo", "descrizione", true)
        )
        val risultato = getActiveAndCompletedStats(tasks)

        assertThat(risultato.activeTasksPercent, `is` (40f))
        assertThat(risultato.completedTasksPercent, `is` (60f))
    }

    @Test
    fun uno_su_dieci_completati(){

        val tasks = listOf<Task>(
                Task("titolo", "descrizione", false),
                Task("titolo", "descrizione", false),
                Task("titolo", "descrizione", false),
                Task("titolo", "descrizione", false),
                Task("titolo", "descrizione", false),
                Task("titolo", "descrizione", false),
                Task("titolo", "descrizione", false),
                Task("titolo", "descrizione", false),
                Task("titolo", "descrizione", false),
                Task("titolo", "descrizione", true)
        )
        val risultato = getActiveAndCompletedStats(tasks)

        assertThat(risultato.activeTasksPercent,`is` (90f))
        assertThat(risultato.completedTasksPercent, `is` (10f))
    }

    @Test
    fun nessuno_su_dieci_completati(){

        val tasks = listOf<Task>(
                Task("titolo", "descrizione", false),
                Task("titolo", "descrizione", false),
                Task("titolo", "descrizione", false),
                Task("titolo", "descrizione", false),
                Task("titolo", "descrizione", false),
                Task("titolo", "descrizione", false),
                Task("titolo", "descrizione", false),
                Task("titolo", "descrizione", false),
                Task("titolo", "descrizione", false),
                Task("titolo", "descrizione", false)
        )
        val risultato = getActiveAndCompletedStats(tasks)

        assertThat(risultato.activeTasksPercent,`is` (100f))
        assertThat(risultato.completedTasksPercent,`is` (0f))
    }

    @Test
    fun una_task_completata(){

        val tasks = listOf<Task>(
                Task("titolo", "descrizione", true)
        )
        val risultato = getActiveAndCompletedStats(tasks)

        assertThat(risultato.activeTasksPercent, `is`(0f))
        assertThat(risultato.completedTasksPercent, `is`(100f))
    }

    @Test
    fun zero_task(){

        val tasks = emptyList<Task>()

        val risultato = getActiveAndCompletedStats(tasks)

        assertThat(risultato.activeTasksPercent, `is`(0f))
        assertThat(risultato.completedTasksPercent, `is` (0f))
    }

    @Test
    fun task_nulla(){

        val tasks = null

        val risultato = getActiveAndCompletedStats(tasks)

        assertThat(risultato.activeTasksPercent, `is`(0f))
        assertThat(risultato.completedTasksPercent, `is`(0f))
    }


}