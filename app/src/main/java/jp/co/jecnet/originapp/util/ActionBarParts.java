package jp.co.jecnet.originapp.util;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;

import com.beardedhen.androidbootstrap.BootstrapButton;

import jp.co.jecnet.originapp.R;

public class ActionBarParts extends AppCompatActivity  {
    private View mActionBarView;
    public BootstrapButton btn_add;

    public void initActionBar(ActionBar mActionBar, LayoutInflater mInflater) {
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        mActionBarView= mInflater.inflate(R.layout.actionbar_main, null);
        btn_add = mActionBarView.findViewById(R.id.actionbar_main_btn_add);
        mActionBar.setCustomView(mActionBarView);
        mActionBar.setDisplayShowCustomEnabled(true);
        ((Toolbar) mActionBarView.getParent()).setContentInsetsAbsolute(0, 0);
    }
}
