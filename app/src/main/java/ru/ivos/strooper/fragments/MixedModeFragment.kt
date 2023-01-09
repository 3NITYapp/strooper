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
import ru.ivos.strooper.databinding.FragmentMixedModeBinding
import kotlin.random.Random

class MixedModeFragment : Fragment() {

    private var _binding: FragmentMixedModeBinding? = null
    private val binding get() = _binding ?: throw java.lang.RuntimeException("Binding is empty")

    private val viewModel by viewModels<MainViewModel>()

    private val ibBack: ImageButton by lazy {
        binding.ibBackMMF
    }

    private val tvCountdownValueTtTMF: TextView by lazy {
        binding.tvCountdownValueMMF
    }

    private val piCountdown: CircularProgressIndicator by lazy {
        binding.piCountdownMMF
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
        _binding = FragmentMixedModeBinding.inflate(inflater, container, false)
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
            tOptionOneMMF.setOnClickListener {
//            list = viewModel.generate()
//            fillLists()
//            fillButtons()
//            viewModel.score(CountdownFragment.TEXT_TO_TEXT_MODE)
                onButtonPressed(tOptionOneMMF.text.toString())
            }
            tOptionTwoMMF.setOnClickListener {
                onButtonPressed(tOptionTwoMMF.text.toString())
            }
            tOptionThreeMMF.setOnClickListener {
                onButtonPressed(tOptionThreeMMF.text.toString())
            }
            tOptionFourMMF.setOnClickListener {
                onButtonPressed(tOptionFourMMF.text.toString())
            }
            tOptionFiveMMF.setOnClickListener {
                onButtonPressed(tOptionFiveMMF.text.toString())
            }
            tOptionSixMMF.setOnClickListener {
                onButtonPressed(tOptionSixMMF.text.toString())
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onButtonPressed(text: String) {

        if (text == nameZero) {
            viewModel.score(CountdownFragment.MIXED_MODE)
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
                        val fragment = EndFragment.newInstance(countOfCorrect, score, CountdownFragment.MIXED_MODE)
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
            binding.tvPointsValueMMF.text = score.toString()
        }
    }

    private fun fillButtons() {

        var name = ""
        var color = 0
        val lb = requireContext().getString(R.string.light_blue)
        val lg = requireContext().getString(R.string.light_green)

        with(binding) {
            color = colors[0]
            tvColorMainMMF.setTextColor(color)
            tvColorMainMMF.background.setTint(colors[5])
            colors.remove(color)
            name = names[Random.nextInt(1, names.size)]
            tvColorMainMMF.text = name
            names.remove(name)

            color = colors[Random.nextInt(colors.size)]
            tOptionOneMMF.setTextColor(color)
            colors.remove(color)
            name = names[Random.nextInt(names.size)]
//            if (name == lb || name == lg) {
//                tOptionOneMMF.textSize = 30f
//            } else {
//                tOptionOneMMF.textSize = 50f
//            }
            tOptionOneMMF.text = name
            names.remove(name)

            color = colors[Random.nextInt(colors.size)]
            tOptionTwoMMF.setTextColor(color)
            colors.remove(color)
            name = names[Random.nextInt(names.size)]
//            if (name == lb || name == lg) {
//                tOptionTwoMMF.textSize = 30f
//            } else {
//                tOptionTwoMMF.textSize = 50f
//            }
            tOptionTwoMMF.text = name
            names.remove(name)

            color = colors[Random.nextInt(colors.size)]
            tOptionThreeMMF.setTextColor(color)
            colors.remove(color)
            name = names[Random.nextInt(names.size)]
//            if (name == lb || name == lg) {
//                tOptionThreeMMF.textSize = 30f
//            } else {
//                tOptionThreeMMF.textSize = 50f
//            }
            tOptionThreeMMF.text = name
            names.remove(name)

            color = colors[Random.nextInt(colors.size)]
            tOptionFourMMF.setTextColor(color)
            colors.remove(color)
            name = names[Random.nextInt(names.size)]
//            if (name == lb || name == lg) {
//                tOptionFourMMF.textSize = 30f
//            } else {
//                tOptionFourMMF.textSize = 50f
//            }
            tOptionFourMMF.text = name
            names.remove(name)

            color = colors[Random.nextInt(colors.size)]
            tOptionFiveMMF.setTextColor(color)
            colors.remove(color)
            name = names[Random.nextInt(names.size)]
//            if (name == lb || name == lg) {
//                tOptionFiveMMF.textSize = 30f
//            } else {
//                tOptionFiveMMF.textSize = 50f
//            }
            tOptionFiveMMF.text = name
            names.remove(name)

            color = colors[Random.nextInt(colors.size)]
            tOptionSixMMF.setTextColor(color)
            colors.remove(color)
            name = names[Random.nextInt(names.size)]
//            if (name == lb || name == lg) {
//                tOptionSixMMF.textSize = 30f
//            } else {
//                tOptionSixMMF.textSize = 50f
//            }
            tOptionSixMMF.text = name
            names.remove(name)
        }
    }

}