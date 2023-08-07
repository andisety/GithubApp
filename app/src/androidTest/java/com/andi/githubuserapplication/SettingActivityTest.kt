package com.andi.githubuserapplication

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.andi.githubuserapplication.setting.SettingActivity
import org.junit.Before
import org.junit.Test

class SettingActivityTest{
    @Before
    fun setup(){
        ActivityScenario.launch(SettingActivity::class.java)
    }

    @Test
    fun inputSearch(){
        onView(withId(R.id.switch_theme)).perform(click())
    }
}