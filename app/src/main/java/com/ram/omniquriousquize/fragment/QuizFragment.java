package com.ram.omniquriousquize.fragment;


import android.databinding.DataBindingUtil;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



import com.ram.omniquriousquize.R;
import com.ram.omniquriousquize.databinding.FragmentQuizBinding;
import com.ram.omniquriousquize.event.NextQuizEvent;
import com.ram.omniquriousquize.intent.IntentUtil;
import com.ram.omniquriousquize.model.Quiz;
import com.ram.omniquriousquize.viewmodel.QuizViewModel;
import com.ram.omniquriousquize.viewmodel.SessionViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
/**
 * Created by RKM on 3/14/20.
 */
public class QuizFragment extends Fragment {
    //Tag
    private static final String TAG = QuizFragment.class.getName();

    //Variables
    private FragmentQuizBinding mBinding;
    private QuizViewModel mQuizViewModel;
    private SessionViewModel mSessionViewModel;
    private EventBus mEventBus;

    /**
     * Static constructor
     * @param quiz
     * @return
     */
    public static QuizFragment newInstance(Quiz quiz){
        QuizFragment fragment = new QuizFragment();

        //Supply arguments
        Bundle args = new Bundle();
        args.putParcelable(IntentUtil.QUIZ, quiz);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);

        mEventBus = EventBus.getDefault();
        mEventBus.register(this);

        Bundle arguments = getArguments();
        if(savedInstanceState != null &&
                savedInstanceState.containsKey(IntentUtil.QUIZ_VM)){
            mQuizViewModel = savedInstanceState.getParcelable(IntentUtil.QUIZ_VM);
        }else if(arguments != null) {
            //Check for parcelable from bundle
            Quiz quiz = arguments.getParcelable(IntentUtil.QUIZ);
            mQuizViewModel = new QuizViewModel(quiz);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(IntentUtil.QUIZ_VM, mQuizViewModel);
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Data binding
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_quiz, container, false);
        mSessionViewModel = new SessionViewModel();
        mBinding.setQuizViewModel(mQuizViewModel);
        mBinding.setSessionViewModel(mSessionViewModel); //Session VM is special as it binds only with singleton instance.

        return mBinding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mEventBus.unregister(this);

        mQuizViewModel.destroy();
        mSessionViewModel.destroy();
        mBinding.unbind();
    }

    /**
     * Click listener for next button
     */
    @Subscribe
    public void onNextQuizClicked(NextQuizEvent event){
        if(getActivity() != null &&
                getActivity() instanceof QuizNavigation){
            //Find shared element, the close button
            ((QuizNavigation)getActivity()).OnNextQuiz(mBinding.close, mBinding.topPanel);
        }
    }

    public interface QuizNavigation{
        void OnNextQuiz(View view1, View view2);
    }
}
