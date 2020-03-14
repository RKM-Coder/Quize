package com.ram.omniquriousquize.viewmodel;


import android.databinding.BaseObservable;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;


import com.ram.omniquriousquize.R;
import com.ram.omniquriousquize.application.EApplication;
import com.ram.omniquriousquize.event.DoneQuizesEvent;
import com.ram.omniquriousquize.event.PlayQuizAgainEvent;
import com.ram.omniquriousquize.session.GameSession;

import org.greenrobot.eventbus.EventBus;


/**
 * Created by RKM on 3/14/20.
 */
public class QuizResultsViewModel extends BaseObservable implements ViewModel, Parcelable{
    //Tag
    private static final String TAG = QuizResultsViewModel.class.getName();

    //Variables
    private int mTotalCorrect;
    private String mCategory;
    private int mTotalQuiz;


    /**
     * Constructor
     */
    public QuizResultsViewModel(){
        this.mTotalCorrect = GameSession.getInstance().getTotalCorrect();
        this.mCategory = GameSession.getInstance().getCategory();
        this.mTotalQuiz = GameSession.getInstance().getTotalQuizQuestions();
    }

    /**
     * Constructor for parcelable
     * @param in
     */
    protected QuizResultsViewModel(Parcel in) {
        mTotalCorrect = in.readInt();
        mCategory = in.readString();
        mTotalQuiz = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mTotalCorrect);
        dest.writeString(mCategory);
        dest.writeInt(mTotalQuiz);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<QuizResultsViewModel> CREATOR = new Creator<QuizResultsViewModel>() {
        @Override
        public QuizResultsViewModel createFromParcel(Parcel in) {
            return new QuizResultsViewModel(in);
        }

        @Override
        public QuizResultsViewModel[] newArray(int size) {
            return new QuizResultsViewModel[size];
        }
    };

    @Override
    public void destroy() {

    }

    public String getResultHeader(){
        if(mTotalCorrect < 3){
            return EApplication.getInstance().getString(R.string.quiz_results_header_good_try);
        }else if(mTotalCorrect < 6){
            return EApplication.getInstance().getString(R.string.quiz_results_header_good_job);
        }else if(mTotalCorrect < 10){
            return EApplication.getInstance().getString(R.string.quiz_results_header_well_done);
        }else{
            return EApplication.getInstance().getString(R.string.quiz_results_header_excellent);
        }
    }

    public String getSummary(){
        return String.format(EApplication.getInstance().getString(R.string.quiz_results_summary),
                mCategory,
                mTotalQuiz,
                mTotalCorrect);
    }

    public String getScore(){
        return String.format("%.0f", ((float)mTotalCorrect/mTotalQuiz) * 100) + "%";
    }

    /**
     * Click listener for done button
     */
    public void onDoneClicked(View v){
        EventBus.getDefault().post(new DoneQuizesEvent());
    }

    /**
     * Click listener for play again button
     */
    public void onPlayAgainClicked(View v){
        EventBus.getDefault().post(new PlayQuizAgainEvent());
    }
}
