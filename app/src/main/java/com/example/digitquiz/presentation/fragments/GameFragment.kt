package com.example.digitquiz.presentation.fragments

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.digitquiz.R
import com.example.digitquiz.databinding.FragmentGameBinding
import com.example.digitquiz.domain.entity.Difficulty
import com.example.digitquiz.domain.entity.GameResult
import com.example.digitquiz.domain.entity.GameSettings
import com.example.digitquiz.presentation.fragments.models.GameViewModel
import com.example.digitquiz.presentation.fragments.models.GameViewModelFactory

class GameFragment : Fragment() {
    private lateinit var difficulty : Difficulty
    private var _binding : FragmentGameBinding? = null
    private val  binding : FragmentGameBinding
        get() = _binding ?: throw RuntimeException("null")
    private val viewModel : GameViewModel by lazy {
        ViewModelProvider(this,
        GameViewModelFactory(requireActivity().application, difficulty)
        )[GameViewModel::class.java]
    }
    private val tvOptions by lazy {
        mutableListOf<TextView>().apply {
            add(binding.tvOption1)
            add(binding.tvOption2)
            add(binding.tvOption3)
            add(binding.tvOption4)
            add(binding.tvOption5)
            add(binding.tvOption6)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parsArgs()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeVM()
        setListenersOnOptions()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun parsArgs(){
        difficulty = requireArguments().getParcelable<Difficulty>(KEY_DIFFICULTY) as Difficulty
    }

    private fun observeVM(){
        viewModel.question.observe(viewLifecycleOwner){
            binding.tvSum.text = it.sum.toString()
            binding.tvNumber.text = it.visibleDigit.toString()
            for (i in 0 until tvOptions.size){
                tvOptions[i].text = it.options[i].toString()

            }
        }
        viewModel.percentOfRightAnswers.observe(viewLifecycleOwner){
            binding.progressBar.setProgress(it,true)
        }
        viewModel.isQtyEnough.observe(viewLifecycleOwner){
            binding.tvAnswersProgress.setTextColor(getColor(it))
        }
        viewModel.isPercentageEnough.observe(viewLifecycleOwner){
            binding.progressBar.progressTintList = ColorStateList.valueOf(getColor(it))
        }
        viewModel.formattedTime.observe(viewLifecycleOwner){
            binding.tvTimer.text = it
        }
        viewModel.minPercent.observe(viewLifecycleOwner){
            binding.progressBar.secondaryProgress = it
        }
        viewModel.result.observe(viewLifecycleOwner){
            launchNext(it)
        }
        viewModel.progress.observe(viewLifecycleOwner){
            binding.tvAnswersProgress.text = it
        }

    }

    private fun setListenersOnOptions(){
        for (option in tvOptions){
            option.setOnClickListener{
                viewModel.checkUserAnswer(option.text.toString().toInt())
            }
        }
    }

    private fun launchNext(result: GameResult){
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_container, GameFinishedFragment.newInstance(result))
            .addToBackStack(null)
            .commit()
    }

    private fun getColor(condition: Boolean):Int{
        var colorRes = if (condition){
            android.R.color.holo_green_light
        } else{
            android.R.color.holo_red_light
        }
        return ContextCompat.getColor(requireContext(), colorRes)
    }

    companion object{
        const val NAME = "GAME_FR"
        private const val KEY_DIFFICULTY = "difficulty"
        fun newInstance(difficulty: Difficulty) : GameFragment{

            return GameFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_DIFFICULTY, difficulty)
                }
            }
        }
    }
}