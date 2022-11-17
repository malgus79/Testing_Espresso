package com.myespresso

import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
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

    //test: que no se pueda incrementar mas la cantidad al llegar al limite disponible
    @Test
    fun setNewQuantity_sumLimit_noIncreasesTextField(){
        val scenario = activityRule.scenario
        scenario.moveToState(Lifecycle.State.RESUMED)
        scenario.onActivity { activity ->
            activity.selectedProduct.quantity = 1  //limitar cantidad para agregar/disminuir cantidad
        }

        onView(withId(R.id.etNewQuantity))
            .check(matches(withText("1")))

        onView(withId(R.id.ibSum))
            .perform(click())

        onView(withId(R.id.etNewQuantity))
            .check(matches(withText("1")))  //como ya se limito la cantidad -> deberia seguir mostrando "1"
    }

    //test: reemplazar texto
    @Test
    fun setNewQuantity_sub_reducesTextField(){
        onView(withId(R.id.etNewQuantity))
            .perform(ViewActions.replaceText("11"))  //simular que ponemos el numero "11" de forma manual en el campo cantidad

        onView(withId(R.id.ibSub))
            .perform(click())

        onView(withId(R.id.etNewQuantity))
            .check(matches(withText("10")))
    }

    //test: teniendo el valor "1" en cantidad, no se pueda disminuir el valor
    @Test
    fun setNewQuantity_subLimit_noReducesTextField(){
        onView(withId(R.id.etNewQuantity))
            .check(matches(withText("1")))

        onView(withId(R.id.ibSub))
            .perform(click())

        onView(withId(R.id.etNewQuantity))
            .check(matches(withText("1")))
    }
}