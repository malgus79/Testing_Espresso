package com.myespresso

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest  //para indicar que la prueba sera grande (se puede aplicar a una clase o metodo)
class MainActivityTest{
    //para tener acceso a la actividad en caso de requerir una modificacion interna
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    //test: verificar que la cantidad inicial sea "1" y luego de agregar "1", tenga "2"
    @Test
    fun setNewQuantity_sum_increasesTextField(){
        //cantidad inicial = 1
        onView(withId(R.id.etNewQuantity))
            .check(matches(withText("1")))

        //pulsar 1 vez en "+"
        onView(withId(R.id.ibSum))
            .perform(click())

        //nueva cantidad = 2
        onView(withId(R.id.etNewQuantity))
            .check(matches(withText("2")))
    }
}