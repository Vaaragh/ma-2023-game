package game.slagalica.utils;

import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

public class FragmentSwitch {
    public static void to(Fragment newFragment, FragmentActivity activity, boolean addToBackstack, int layoutViewID)
    {
        Log.d("Usao", newFragment.toString());
        FragmentTransaction transaction = activity.getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(layoutViewID, newFragment);
        if(addToBackstack) transaction.addToBackStack(null);
        transaction.commit();
    }

}
