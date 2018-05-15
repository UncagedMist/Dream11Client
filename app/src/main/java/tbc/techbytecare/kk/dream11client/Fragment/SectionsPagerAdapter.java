package tbc.techbytecare.kk.dream11client.Fragment;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch(position) {
            case 0:
                FixtureFragment fixtureFragment = new FixtureFragment();
                return fixtureFragment;

            case 1:
                LiveFragment liveFragment = new LiveFragment();
                return  liveFragment;

            case 2:
                ResultFragment resultFragment = new ResultFragment();
                return resultFragment;

            default:
                return  null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

         switch (position) {
            case 0:
                return "FIXTURES";

            case 1:
                return "LIVE";

            case 2:
                return "RESULTS";

            default:
                return null;
        }
    }
}
