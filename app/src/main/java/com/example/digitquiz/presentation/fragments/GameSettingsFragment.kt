package com.example.digitquiz.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.digitquiz.R
import com.example.digitquiz.databinding.FragmentGameSettingsBinding
import com.example.digitquiz.domain.entity.Difficulty
import java.lang.RuntimeException

class GameSettingsFragment : Fragment() {

    private var _binding: FragmentGameSettingsBinding? = null
    private val binding : FragmentGameSettingsBinding
    get() = _binding ?: throw RuntimeException("null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGameSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnTest.setOnClickListener {
            launchGameFragment(Difficulty.TEST)
        }
        binding.btnEasy.setOnClickListener {
            launchGameFragment(Difficulty.EASY)
        }
        binding.btnNormal.setOnClickListener {
            launchGameFragment(Difficulty.NORMAL)
        }
        binding.btnHard.setOnClickListener {
            launchGameFragment(Difficulty.HARD)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun launchGameFragment(difficulty: Difficulty){

        findNavController().navigate(
            GameSettingsFragmentDirections.actionGameSettingsFragmentToGameFragment(difficulty)
        )
    }
}