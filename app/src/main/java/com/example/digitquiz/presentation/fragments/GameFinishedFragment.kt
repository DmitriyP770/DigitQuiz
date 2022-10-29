package com.example.digitquiz.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.digitquiz.R
import com.example.digitquiz.databinding.FragmentGameBinding
import com.example.digitquiz.databinding.FragmentGameFinishedBinding
import com.example.digitquiz.domain.entity.GameResult
import com.example.digitquiz.presentation.fragments.models.GameViewModel

class GameFinishedFragment : Fragment() {

    val args by navArgs<GameFinishedFragmentArgs>()

    private var _binding: FragmentGameFinishedBinding? = null
    private val binding: FragmentGameFinishedBinding
        get() = _binding ?: throw RuntimeException("null")
    private lateinit var result: GameResult


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGameFinishedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews()
        binding.buttonRetry.setOnClickListener {
            retryGame()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parsArgs()
    }

    private fun parsArgs() {
        result = args.result
    }


    private fun calculatePercentage(total: Int, right: Int): String {
        return ((right / total.toDouble()) * 100).toInt().toString()
    }

    private fun bindViews() {
        with(binding) {
            tvRequiredAnswers.text = String.format(
                getString(R.string.required_score),
                result.gameSettings.minQuantityOfRightAswers
            )
            tvRequiredPercentage.text = String.format(
                getString(R.string.required_percentage),
                result.gameSettings.minPercentageOfRightAnswers
            )
            tvScoreAnswers.text = String.format(
                getString(R.string.score_answers),
                result.qtyOfRightAnswers
            )
            tvScorePercentage.text = String.format(
                getString(R.string.score_percentage),
                calculatePercentage(result.totalAnswers, result.qtyOfRightAnswers)
            )
            emojiResult.setImageResource(defineEmoji(result))
        }

    }

    private fun defineEmoji(result: GameResult): Int {
        if (result.hasWon) {
            return R.drawable.ic_happy
        } else return R.drawable.ic_sad
    }

    private fun retryGame() {
        findNavController().popBackStack()

    }
}