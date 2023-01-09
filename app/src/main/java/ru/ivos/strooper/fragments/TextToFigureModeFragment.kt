package ru.ivos.strooper.fragments

import android.os.Bundle
import android.util.Log
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
import ru.ivos.strooper.databinding.FragmentTextToFigureModeBinding
import ru.ivos.strooper.databinding.FragmentTextToTextModeBinding
import java.text.FieldPosition
import kotlin.random.Random


class TextToFigureModeFragment : Fragment() {

    private var _binding: FragmentTextToFigureModeBinding? = null
    private val binding get() = _binding ?: throw java.lang.RuntimeException("Binding is empty")

    private val viewModel by viewModels<MainViewModel>()

    private val ibBack: ImageButton by lazy {
        binding.ibBackTtFMF
    }

    private val tvCountdownValueTtTMF: TextView by lazy {
        binding.tvCountdownValueTtFMF
    }

    private val piCountdown: CircularProgressIndicator by lazy {
        binding.piCountdownTtFMF
    }

    private var list = linkedMapOf<String, Int>()
    private var names = mutableListOf<String>()
    private var colors = mutableListOf<Int>()

    private var nameZero = ""
    private var colorZero = 0

    private var score = 0
    private var countOfCorrect = 0

    private var c1 = 0
    private var c2 = 0
    private var c3 = 0
    private var c4 = 0
    private var c5 = 0
    private var c6 = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTextToFigureModeBinding.inflate(inflater, container, false)
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
        colorZero = colors[0]
        fillButtons()
//        Log.d("tag", "$c1 $c2 $c3 $c4 $c5 $c6 $colorZero")

        ibBack.setOnClickListener {
            val fragment = HomeFragment()
            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit()
        }

        with(binding) {
            tOptionOneTtFMF.setOnClickListener {
//            list = viewModel.generate()
//            fillLists()
//            fillButtons()
//            viewModel.score(CountdownFragment.TEXT_TO_TEXT_MODE)
                onButtonPressed(c1)
            }
            tOptionTwoTtFMF.setOnClickListener {
                onButtonPressed(c2)
            }
            tOptionThreeTtFMF.setOnClickListener {
                onButtonPressed(c3)
            }
            tOptionFourTtFMF.setOnClickListener {
                onButtonPressed(c4)
            }
            tOptionFiveTtFMF.setOnClickListener {
                onButtonPressed(c5)
            }
            tOptionSixTtFMF.setOnClickListener {
                onButtonPressed(c6)
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onButtonPressed(position: Int) {

        if (position == colorZero) {
            viewModel.score(CountdownFragment.TEXT_TO_FIGURE_MODE)
            countOfCorrect++
        }
        list = viewModel.generate()
        names = list.keys.toMutableList()
        colors = list.values.toMutableList()
        nameZero = names[0]
        colorZero = colors[0]
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
                        val fragment = EndFragment.newInstance(countOfCorrect, score, CountdownFragment.TEXT_TO_FIGURE_MODE)
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
            binding.tvPointsValueTtFMF.text = score.toString()
        }
    }

    private fun fillButtons() {
        var name = ""
        var color = 0
        val lb = requireContext().getString(R.string.light_blue)
        val lg = requireContext().getString(R.string.light_green)

        with(binding) {
            color = colors[Random.nextInt(1, colors.size)]
            tvColorMainTtFMF.setTextColor(color)
            colors.remove(color)
            name = names[0]
            tvColorMainTtFMF.text = name
            names.remove(name)

            color = colors[Random.nextInt(colors.size)]
            tOptionOneTtFMF.setTextColor(color)
            tOptionOneTtFMF.background.setTint(color)
            c1 = color
            colors.remove(color)
            name = names[Random.nextInt(names.size)]
            if (name == lb || name == lg) {
                tOptionOneTtFMF.textSize = 10f
            } else {
                tOptionOneTtFMF.textSize = 10f
            }
            tOptionOneTtFMF.text = name
            names.remove(name)

            color = colors[Random.nextInt(colors.size)]
            tOptionTwoTtFMF.setTextColor(color)
            tOptionTwoTtFMF.background.setTint(color)
            c2 = color
            colors.remove(color)
            name = names[Random.nextInt(names.size)]
            if (name == lb || name == lg) {
                tOptionTwoTtFMF.textSize = 10f
            } else {
                tOptionTwoTtFMF.textSize = 10f
            }
            tOptionTwoTtFMF.text = name
            names.remove(name)

            color = colors[Random.nextInt(colors.size)]
            tOptionThreeTtFMF.setTextColor(color)
            tOptionThreeTtFMF.background.setTint(color)
            c3 = color
            colors.remove(color)
            name = names[Random.nextInt(names.size)]
            if (name == lb || name == lg) {
                tOptionThreeTtFMF.textSize = 10f
            } else {
                tOptionThreeTtFMF.textSize = 10f
            }
            tOptionThreeTtFMF.text = name
            names.remove(name)

            color = colors[Random.nextInt(colors.size)]
            tOptionFourTtFMF.setTextColor(color)
            tOptionFourTtFMF.background.setTint(color)
            c4 = color
            colors.remove(color)
            name = names[Random.nextInt(names.size)]
            if (name == lb || name == lg) {
                tOptionFourTtFMF.textSize = 10f
            } else {
                tOptionFourTtFMF.textSize = 10f
            }
            tOptionFourTtFMF.text = name
            names.remove(name)

            color = colors[Random.nextInt(colors.size)]
            tOptionFiveTtFMF.setTextColor(color)
            tOptionFiveTtFMF.background.setTint(color)
            c5 = color
            colors.remove(color)
            name = names[Random.nextInt(names.size)]
            if (name == lb || name == lg) {
                tOptionFiveTtFMF.textSize = 10f
            } else {
                tOptionFiveTtFMF.textSize = 10f
            }
            tOptionFiveTtFMF.text = name
            names.remove(name)

            color = colors[Random.nextInt(colors.size)]
            tOptionSixTtFMF.setTextColor(color)
            tOptionSixTtFMF.background.setTint(color)
            c6 = color
            colors.remove(color)
            name = names[Random.nextInt(names.size)]
            if (name == lb || name == lg) {
                tOptionSixTtFMF.textSize = 10f
            } else {
                tOptionSixTtFMF.textSize = 10f
            }
            tOptionSixTtFMF.text = name
            names.remove(name)
        }
    }
}