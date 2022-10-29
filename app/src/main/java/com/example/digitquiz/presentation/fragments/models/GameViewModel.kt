package com.example.digitquiz.presentation.fragments.models

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.digitquiz.R
import com.example.digitquiz.common.Constants.MILLS_IN_SECS
import com.example.digitquiz.common.Constants.SECS_IN_MINS
import com.example.digitquiz.data.GameRepositoryImpl
import com.example.digitquiz.domain.entity.Difficulty
import com.example.digitquiz.domain.entity.GameResult
import com.example.digitquiz.domain.entity.GameSettings
import com.example.digitquiz.domain.entity.Question
import com.example.digitquiz.domain.repo.GameRepository
import com.example.digitquiz.domain.usecases.GenerateQuestionUseCase
import com.example.digitquiz.domain.usecases.GetGameSettingsUseCase

class GameViewModel (private val app:Application, private val difficulty: Difficulty) : ViewModel() {
    private var repository: GameRepository = GameRepositoryImpl
    private lateinit var gameSettings: GameSettings
    private var timer :CountDownTimer? = null
    // make it simple int?
    private var _numOfRightAnswers: Int = 0
    // make it simple int?
    private var _numOfTotalAnswers : Int = 0
    private var _formattedTime = MutableLiveData<String>()
    val formattedTime: LiveData<String>
        get() = _formattedTime
    private var _progress = MutableLiveData<String>()
    val progress: LiveData<String>
        get() = _progress
    private var _question = MutableLiveData<Question>()
    val question : LiveData<Question>
        get() = _question
    private var _percentOfRightAnswers = MutableLiveData<Int>()
    val percentOfRightAnswers : LiveData<Int>
        get() = _percentOfRightAnswers
    private var _isQtyEnough = MutableLiveData<Boolean>()
    val isQtyEnough : LiveData<Boolean>
        get() = _isQtyEnough
    private var _isPercentageEnough = MutableLiveData<Boolean>()
    val isPercentageEnough : LiveData<Boolean>
        get() = _isPercentageEnough
    private var _minPercent = MutableLiveData<Int>()
    val minPercent : LiveData<Int>
        get() = _minPercent
    private var _result = MutableLiveData<GameResult>()
    val result : LiveData<GameResult>
        get() = _result
    private var generateQuestionUseCase = GenerateQuestionUseCase(repository)
    private var getGameSettingsUseCase = GetGameSettingsUseCase(repository)

    init {
        startGame()
    }

   private fun startGame(){
        setUpGameSettings()
        startTimer()
        generateQuestion()

    }

    fun generateQuestion(){
        _question.value =generateQuestionUseCase(gameSettings.maxValue)
    }

    fun checkUserAnswer(num : Int){

        val rightAnswer = _question.value?.rightAnser
        _numOfTotalAnswers++
        if (num == rightAnswer){
            _numOfRightAnswers++
        }
        updateProgress()
        generateQuestion()
    }

    private fun setUpGameSettings(){
        this.gameSettings = getGameSettingsUseCase(difficulty)
        _minPercent.value = gameSettings.minPercentageOfRightAnswers
    }

    private fun startTimer(){
         timer = object :CountDownTimer(gameSettings.timeInSeconds*MILLS_IN_SECS,
            MILLS_IN_SECS) {
            override fun onTick(p0: Long) {
                _formattedTime.postValue(formatTime(p0))
            }

            override fun onFinish() {
                finishGame()
            }
        }
        timer?.start()
    }
    private fun finishGame() {
        _result.value = GameResult(
            hasWon = _isQtyEnough.value == true && _isPercentageEnough.value ==true,
            qtyOfRightAnswers = _numOfRightAnswers,
            totalAnswers = _numOfTotalAnswers,
            gameSettings = gameSettings
        )
    }

    private fun formatTime(p0: Long): String{
        val seconds = p0 / MILLS_IN_SECS
        val mins = seconds / SECS_IN_MINS
        val leftSecs = seconds- (mins* SECS_IN_MINS)
        return String.format("%02d:%02d", mins, leftSecs)
    }

    private fun calculatePercentageOfRightAnswers() : Int{
        if (_numOfTotalAnswers==0){
            return 0
        }
        return ((_numOfRightAnswers/_numOfTotalAnswers.toDouble())*100).toInt()
    }

    private fun updateProgress(){
        _percentOfRightAnswers.value = calculatePercentageOfRightAnswers()
        _progress.value = String.format(
            app.resources.getString(R.string.progress_answers),
            _numOfRightAnswers,
            gameSettings.minQuantityOfRightAswers
        )
        _isQtyEnough.value = (_numOfRightAnswers >= gameSettings.minQuantityOfRightAswers)
        _isPercentageEnough.value = (_percentOfRightAnswers.value!! >= gameSettings.minPercentageOfRightAnswers)
    }


    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }
  }


