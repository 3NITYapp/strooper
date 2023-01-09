package ru.ivos.strooper.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.progressindicator.CircularProgressIndicator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.ivos.strooper.MainViewModel
import ru.ivos.strooper.R
import ru.ivos.strooper.databinding.FragmentFigureToTextModeBinding
import ru.ivos.strooper.databinding.FragmentTextToTextModeBinding
import kotlin.random.Random

class FigureToTextModeFragment : Fragment() {

    private var _binding: FragmentFigureToTextModeBinding? = null
    private val binding get() = _binding ?: throw java.lang.RuntimeException("Binding is empty")

    private val viewModel by viewModels<MainViewModel>()

    private val ibBack: ImageButton by lazy {
        binding.ibBackFtTMF
    }

    private val tvCountdownValueTtTMF: TextView by lazy {
        binding.tvCountdownValueFtTMF
    }

    private val piCountdown: CircularProgressIndicator by lazy {
        binding.piCountdownFtTMF
    }

    private var list = linkedMapOf<String, Int>()
    private var names = mutableListOf<String>()
    private var colors = mutableListOf<Int>()

    private var nameZero = ""

    private var score = 0
    private var countOfCorrect = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFigureToTextModeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.timer()

        observeViewModel()

        list = viewModel.generate()
        names = list.keys.toMutableList()
        colors = list.values.toMutableList()
        nameZero = names[0]
        fillButtons()

        ibBack.setOnClickListener {
            val fragment = HomeFragment()
            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit()
        }

        with(binding) {
            tOptionOneFtTF.setOnClickListener {
//            list = viewModel.generate()
//            fillLists()
//            fillButtons()
//            viewModel.score(CountdownFragment.TEXT_TO_TEXT_MODE)
                onButtonPressed(tOptionOneFtTF.text.toString())
            }
            tOptionTwoFtTF.setOnClickListener {
                onButtonPressed(tOptionTwoFtTF.text.toString())
            }
            tOptionThreeFtTF.setOnClickListener {
                onButtonPressed(tOptionThreeFtTF.text.toString())
            }
            tOptionFourFtTF.setOnClickListener {
                onButtonPressed(tOptionFourFtTF.text.toString())
            }
            tOptionFiveFtTF.setOnClickListener {
                onButtonPressed(tOptionFiveFtTF.text.toString())
            }
            tOptionSixFtTF.setOnClickListener {
                onButtonPressed(tOptionSixFtTF.text.toString())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onButtonPressed(text: String) {

        if (text == nameZero) {
            viewModel.score(CountdownFragment.FIGURE_TO_TEXT_MODE)
            countOfCorrect++
        }
        list = viewModel.generate()
        names = list.keys.toMutableList()
        colors = list.values.toMutableList()
        nameZero = names[0]
        fillButtons()
    }

    private fun observeViewModel() {
        viewModel.timer.observe(viewLifecycleOwner) {
            viewLifecycleOwner.lifecycleScope.launch {
                tvCountdownValueTtTMF.text = it
                if (it == "01:00") {
                    piCountdown.progress = 60
                } else if (it == "Done!") {
                    tvCountdownValueTtTMF.text = requireContext().getString(R.string.finish)
                    piCountdown.progress = 0
                    withContext(Dispatchers.Main) {
                        delay(1000)
                        val fragment = EndFragment.newInstance(countOfCorrect, score, CountdownFragment.FIGURE_TO_TEXT_MODE)
                        requireActivity().supportFragmentManager.beginTransaction()
                            .replace(R.id.fragmentContainer, fragment)
                            .commit()
                    }
                } else {
                    piCountdown.progress = it.substring(3).toInt()
                }
            }
        }
        viewModel.newSetOfColorsAndNames.observe(viewLifecycleOwner) {
            list = it
        }
        viewModel.score.observe(viewLifecycleOwner) {
            score = it
            binding.tvPointsValueFtTF.text = score.toString()
        }
    }

    private fun fillButtons() {

        var name = ""
        var color = 0
        val lb = requireContext().getString(R.string.light_blue)
        val lg = requireContext().getString(R.string.light_green)

        with(binding) {
            color = colors[0]
            tvColorMainFtTF.setTextColor(color)
            tvColorMainFtTF.background.setTint(color)
            colors.remove(color)
            name = names[Random.nextInt(1, names.size)]
            tvColorMainFtTF.text = name
            names.remove(name)

            color = colors[Random.nextInt(colors.size)]
            tOptionOneFtTF.setTextColor(color)
            colors.remove(color)
            name = names[Random.nextInt(names.size)]
//            if (name == lb || name == lg) {
//                tOptionOneFtTF.textSize = 30f
//            } else {
//                tOptionOneFtTF.textSize = 50f
//            }
            tOptionOneFtTF.text = name
            names.remove(name)

            color = colors[Random.nextInt(colors.size)]
            tOptionTwoFtTF.setTextColor(color)
            colors.remove(color)
            name = names[Random.nextInt(names.size)]
//            if (name == lb || name == lg) {
//                tOptionTwoFtTF.textSize = 30f
//            } else {
//                tOptionTwoFtTF.textSize = 50f
//            }
            tOptionTwoFtTF.text = name
            names.remove(name)

            color = colors[Random.nextInt(colors.size)]
            tOptionThreeFtTF.setTextColor(color)
            colors.remove(color)
            name = names[Random.nextInt(names.size)]
//            if (name == lb || name == lg) {
//                tOptionThreeFtTF.textSize = 30f
//            } else {
//                tOptionThreeFtTF.textSize = 50f
//            }
            tOptionThreeFtTF.text = name
            names.remove(name)

            color = colors[Random.nextInt(colors.size)]
            tOptionFourFtTF.setTextColor(color)
            colors.remove(color)
            name = names[Random.nextInt(names.size)]
//            if (name == lb || name == lg) {
//                tOptionFourFtTF.textSize = 30f
//            } else {
//                tOptionFourFtTF.textSize = 50f
//            }
            tOptionFourFtTF.text = name
            names.remove(name)

            color = colors[Random.nextInt(colors.size)]
            tOptionFiveFtTF.setTextColor(color)
            colors.remove(color)
            name = names[Random.nextInt(names.size)]
//            if (name == lb || name == lg) {
//                tOptionFiveFtTF.textSize = 30f
//            } else {
//                tOptionFiveFtTF.textSize = 50f
//            }
            tOptionFiveFtTF.text = name
            names.remove(name)

            color = colors[Random.nextInt(colors.size)]
            tOptionSixFtTF.setTextColor(color)
            colors.remove(color)
            name = names[Random.nextInt(names.size)]
//            if (name == lb || name == lg) {
//                tOptionSixFtTF.textSize = 30f
//            } else {
//                tOptionSixFtTF.textSize = 50f
//            }
            tOptionSixFtTF.text = name
            names.remove(name)
        }
    }

}