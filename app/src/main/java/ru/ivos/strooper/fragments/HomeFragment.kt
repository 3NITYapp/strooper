package ru.ivos.strooper.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.ivos.strooper.R
import ru.ivos.strooper.data.PreferencesRepo
import ru.ivos.strooper.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding ?: throw java.lang.RuntimeException("Binding is empty")

    private lateinit var preferencesRepo: PreferencesRepo

    override fun onAttach(context: Context) {
        super.onAttach(context)
        preferencesRepo = PreferencesRepo(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvBestScoreValueHF.text = preferencesRepo.getHighScore().toString()

        binding.btnTextToTextModeHF.setOnClickListener {
            goToRulesFragment(CountdownFragment.TEXT_TO_TEXT_MODE)
        }
        binding.btnTextToFigureModeHF.setOnClickListener {
            goToRulesFragment(CountdownFragment.TEXT_TO_FIGURE_MODE)
        }
        binding.btnFigureToTextModeHF.setOnClickListener {
            goToRulesFragment(CountdownFragment.FIGURE_TO_TEXT_MODE)
        }
        binding.btnMixedModeHF.setOnClickListener {
            goToRulesFragment(CountdownFragment.MIXED_MODE)
        }
    }

    private fun goToRulesFragment(mode: String) {
        val fragment = RulesFragment.newInstance(mode)
        requireActivity().supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

    }

}