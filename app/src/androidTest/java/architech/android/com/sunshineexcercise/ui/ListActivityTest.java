package architech.android.com.sunshineexcercise.ui;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import architech.android.com.sunshineexcercise.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class ListActivityTest {

    @Rule
    public ActivityTestRule<ListActivity> listActivityTestRule = new ActivityTestRule<>(ListActivity.class);

    private CountingIdlingResource mIdlingResource;


    @Before
    public void registerIdlingResource() {
        mIdlingResource = listActivityTestRule.getActivity().getIdlingResourceInTest();
        IdlingRegistry.getInstance().register(mIdlingResource);
    }

    @Test
    public void clickTest_LoadsMovieDetail() {
        onView(ViewMatchers.withId(R.id.weather_recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(0,
                click()));
    }

    @After
    public void unregister() {
        if(mIdlingResource != null) {
            IdlingRegistry.getInstance().unregister(mIdlingResource);
        }
    }


}