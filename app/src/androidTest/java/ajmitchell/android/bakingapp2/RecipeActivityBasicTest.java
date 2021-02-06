package ajmitchell.android.bakingapp2;


import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class RecipeActivityBasicTest {

    public ActivityScenarioRule<MainActivity> activityScenarioRule =
            new ActivityScenarioRule<MainActivity>(MainActivity.class);

//    @Before
//    public void init() {
//        activityScenarioRule.getScenario().onActivity()
//    }
    @Rule public void testRecipeItem() {
        onView(withId(R.id.recipe_list))
                .perform(click());


    }
}
// TODO (1) Add annotation to specify AndroidJUnitRunner class as the default test runner
//@RunWith(AndroidJUnit4ClassRunner.class)
//public class MenuActivityScreenTest {
//
//    // TODO (2) Add the rule that provides functional testing of a single activity
//    public static final String TEA_NAME = "Green Tea";
//
//    @Rule
//    public ActivityTestRule<MenuActivity> mActivityTestRule =
//            new ActivityTestRule<>(MenuActivity.class);
//    // TODO (3) Finish writing this test which will click on a gridView Tea item and verify that
//    // the OrderActivity opens up with the correct tea name displayed.
//    @Test
//    public void clickGridViewItem_OpensOrderActivity() {
//        onData(anything()).inAdapterView(withId(R.id.tea_grid_view)).atPosition(1).perform(click());
//        onView(withId(R.id.tea_name_text_view)).check(matches(withText(TEA_NAME)));
//    }