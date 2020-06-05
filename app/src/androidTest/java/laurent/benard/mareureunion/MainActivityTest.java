package laurent.benard.mareureunion;


import android.app.TimePickerDialog;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import androidx.test.espresso.matcher.ViewMatchers;


import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import laurent.benard.mareureunion.controler.DI;
import laurent.benard.mareureunion.controler.InterfaceReunionApiServices;
import laurent.benard.mareureunion.model.Reunion;
import laurent.benard.mareureunion.view.MainActivity;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.actionWithAssertions;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;

import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    private MainActivity activity;

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    /**
     * Création d'une réunion pour sujet Laurent
     */
    public void initReunionLaurent(){
        onView(withId(R.id.floatingActionButton)).perform(click());

        onView(withId(R.id.txt_input_sujet)).perform(typeText("laurent"));
        onView(withId(R.id.spinner)).perform(click());
        onData(anything()).atPosition(0).perform(click());
        onView(withId(R.id.spinner)).check(matches(withSpinnerText(containsString("mario"))));
        onView(withId(R.id.txt_input_date)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2020, 6, 14));
        onView(allOf(withId(android.R.id.button1), withText("OK"))).perform(click());
        onView(withId(R.id.txt_input_heure)).perform(click());
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName()))).perform(PickerActions.setTime(9, 30));
        onView(allOf(withId(android.R.id.button1), withText("OK"))).perform(click());
        closeSoftKeyboard();
        onView(withId(R.id.txt_input_participants)).perform(scrollTo(), replaceText("laurent@gail.com"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.button_addReunion)).perform(click());
    }

    /**
     * Création d'une réunion pour sujet Aurélie
     */
    public void initReunionAurelie(){
        onView(withId(R.id.floatingActionButton)).perform(click());

        onView(withId(R.id.txt_input_sujet)).perform(typeText("aurelie"));
        onView(withId(R.id.spinner)).perform(click());
        onData(anything()).atPosition(1).perform(click());
        onView(withId(R.id.spinner)).check(matches(withSpinnerText(containsString("luigi"))));
        onView(withId(R.id.txt_input_date)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2020, 7, 20));
        onView(allOf(withId(android.R.id.button1), withText("OK"))).perform(click());
        onView(withId(R.id.txt_input_heure)).perform(click());
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName()))).perform(PickerActions.setTime(9, 30));
        onView(allOf(withId(android.R.id.button1), withText("OK"))).perform(click());
        closeSoftKeyboard();
        onView(withId(R.id.txt_input_participants)).perform(replaceText("aurelie@gmail.com"));
        closeSoftKeyboard();
        onView(withId(R.id.button_addReunion)).perform(click());
    }

    @Before
    public void setUp(){ activity = activityRule.getActivity(); }

    /**
     * Affichage de la liste à zéro
     */
    @Test
    public void listItemIsDisplayed(){
        onView(allOf(ViewMatchers.withId(R.id.fragment_list_items),
                isDisplayed()));
        onView(withId(R.id.fragment_list_items)).check(new RecyclerViewItemCountAssertion(0));
    }

    /**
     * Affichage de l'activité addActivity
     */
    @Test
    public void addActivityIsDisplayed(){
        onView(withId(R.id.floatingActionButton)).perform(click());
        onView(allOf(ViewMatchers.withId(R.id.add_activity),
                isDisplayed()));
    }

    /**
     * Création d'une réunion
     * Vérification du nombre de réunions affichées
     * Suppression d'une réunion
     * Vérification du nombre de réunions affichées
     */
    @Test
    public void addReunionTest(){
        initReunionLaurent();

        onView(allOf(ViewMatchers.withId(R.id.fragment_list_items), isDisplayed()));
        onView(withId(R.id.txt_fragment_sujet)).check(matches(withText("laurent")));
        onView(withId(R.id.txt_fragment_horaire)).check(matches(withText("09:30")));
        onView(withId(R.id.txt_fragment_lieu)).check(matches(withText("mario")));
        onView(withId(R.id.txt_fragment_participants)).check(matches(withText("laurent@gail.com")));
        onView(withId(R.id.fragment_item_img_circle)).equals(R.drawable.ic_fiber_manual_record_red_24dp);
        onView(withId(R.id.fragment_list_items)).check(new RecyclerViewItemCountAssertion(1));
        onView(withId(R.id.but_fragment_delete)).perform(click());
        onView(withId(R.id.fragment_list_items)).check(new RecyclerViewItemCountAssertion(0));
    }

    /**
     * Test des filtres par salle puis date
     * Vérification du nombre de réunions après chaque filtre
     */
    @Test
    public void filterReunions(){
        initReunionLaurent();
        initReunionAurelie();
        onView(withId(R.id.fragment_list_items)).check(new RecyclerViewItemCountAssertion(2));
        onView(withId(R.id.menu_filtre)).perform(click());
        onData(anything()).atPosition(0).perform(click());
        onView(withId(R.id.search_src_text)).perform(typeText("ma"));
        onView(withId(R.id.fragment_list_items)).check(new RecyclerViewItemCountAssertion(1));
        onView(withId(R.id.txt_fragment_lieu)).check(matches(withText("mario")));

        onView(withId(R.id.menu_filtre)).perform(click());
        onData(anything()).atPosition(1).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2020, 7, 20));
        onView(allOf(withId(android.R.id.button1), withText("OK"))).perform(click());
        onView(withId(R.id.txt_fragment_lieu)).check(matches(withText("luigi")));
        onView(withId(R.id.fragment_list_items)).check(new RecyclerViewItemCountAssertion(1));
    }

    /**
     * Vefification du nombre de réunions à la rotation de l'écran
     */
    @Test
    public void rotationDeleteItemTest(){
        initReunionLaurent();
        onView(withId(R.id.fragment_list_items)).check(new RecyclerViewItemCountAssertion(1));
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        onView(withId(R.id.fragment_list_items)).check(new RecyclerViewItemCountAssertion(0));
    }

}
