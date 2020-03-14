package com.ram.omniquriousquize.fragment;


import android.databinding.DataBindingUtil;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



import com.ram.omniquriousquize.R;
import com.ram.omniquriousquize.databinding.FragmentDashboardBinding;
import com.ram.omniquriousquize.intent.IntentUtil;
import com.ram.omniquriousquize.viewmodel.DashboardViewModel;
import com.ram.omniquriousquize.viewmodel.SessionViewModel;
/**
 * Created by RKM on 3/14/20.
 */

public class DashboardFragment extends Fragment {
    //Tag
    private static final String TAG = DashboardFragment.class.getName();

    //Variables
    private FragmentDashboardBinding mBinding;
    private DashboardViewModel mDashboardViewModel; //Not really needed now as there are no real state info needed
    private SessionViewModel mSessionViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);

        if(savedInstanceState != null &&
                savedInstanceState.containsKey(IntentUtil.DASHBOARD_VM)){
            mDashboardViewModel = savedInstanceState.getParcelable(IntentUtil.DASHBOARD_VM);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(IntentUtil.DASHBOARD_VM, mDashboardViewModel);
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Data binding
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard, container, false);
        mDashboardViewModel = new DashboardViewModel();
        mSessionViewModel = new SessionViewModel();
        mBinding.setDashboardViewModel(mDashboardViewModel);
        mBinding.setSessionViewModel(mSessionViewModel);

        return mBinding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        mBinding.unbind();
    }

}
