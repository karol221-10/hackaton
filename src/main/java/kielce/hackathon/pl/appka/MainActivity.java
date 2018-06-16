package kielce.hackathon.pl.appka;


import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private SectionsPagerAddapter mSectionsPagerAddapter;
    private TabLayout mTabLayout;
    private Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //tabs
        mViewPager = (ViewPager)findViewById(R.id.main_tabpager);
        mSectionsPagerAddapter = new SectionsPagerAddapter(getSupportFragmentManager());

        mViewPager.setAdapter(mSectionsPagerAddapter);

        mTabLayout = (TabLayout)findViewById(R.id.main_tabs);
        mTabLayout.setupWithViewPager(mViewPager);



    }
}
