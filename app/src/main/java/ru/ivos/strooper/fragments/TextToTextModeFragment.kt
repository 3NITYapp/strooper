package ru.ivos.strooper.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.progressindicator.CircularProgressIndicator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.ivos.strooper.MainViewModel
import ru.ivos.strooper.R
import ru.ivos.strooper.databinding.FragmentTextToTextModeBinding
import kotlin.random.Random


class TextToTextModeFragment : Fragment() {

    private var _binding: FragmentTextToTextModeBinding? = null
    private val binding get() = _binding ?: throw java.lang.RuntimeException("Binding is empty")

    private val viewModel by viewModels<MainViewModel>()

    private val ibBack: ImageButton by lazy {
        binding.ibBackTtTMF
    }

    private val tvCountdownValueTtTMF: TextView by lazy {
        binding.tvCountdownValueTtTMF
    }

    private val piCountdown: CircularProgressIndicator by lazy {
        binding.piCountdownTtTMF
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
        _binding = FragmentTextToTextModeBinding.inflate(inflater, container, false)
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
            tOptionOneTtTF.setOnClickListener {
                onButtonPressed(tOptionOneTtTF.text.toString())
            }
            tOptionTwoTtTF.setOnClickListener {
                onButtonPressed(tOptionTwoTtTF.text.toString())
            }
            tOptionThreeTtTF.setOnClickListener {
                onButtonPressed(tOptionThreeTtTF.text.toString())
            }
            tOptionFourTtTF.setOnClickListener {
                onButtonPressed(tOptionFourTtTF.text.toString())
            }
            tOptionFiveTtTF.setOnClickListener {
                onButtonPressed(tOptionFiveTtTF.text.toString())
            }
            tOptionSixTtTF.setOnClickListener {
                onButtonPressed(tOptionSixTtTF.text.toString())
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onButtonPressed(text: String) {

        if (text == nameZero) {
            viewModel.score(CountdownFragment.TEXT_TO_TEXT_MODE)
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
                        requireActivity().supportFragmentManager.popBackStack()
                        val fragment = EndFragment.newInstance(
                            countOfCorrect,
                            score,
                            CountdownFragment.TEXT_TO_TEXT_MODE
                        )
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
            binding.tvPointsValueTtTF.text = score.toString()
        }
    }

    private fun fillButtons() {

        var name = ""
        var color = 0

        with(binding) {
            color = colors[0]
            tvColorMainTtTF.setTextColor(color)
            colors.remove(color)
            name = names[Random.nextInt(1, names.size)]
            tvColorMainTtTF.text = name
            names.remove(name)

            color = colors[Random.nextInt(colors.size)]
            tOptionOneTtTF.setTextColor(color)
            colors.remove(color)
            name = names[Random.nextInt(names.size)]
            tOptionOneTtTF.text = name
            names.remove(name)

            color = colors[Random.nextInt(colors.size)]
            tOptionTwoTtTF.setTextColor(color)
            colors.remove(color)
            name = names[Random.nextInt(names.size)]
            tOptionTwoTtTF.text = name
            names.remove(name)

            color = colors[Random.nextInt(colors.size)]
            tOptionThreeTtTF.setTextColor(color)
            colors.remove(color)
            name = names[Random.nextInt(names.size)]
            tOptionThreeTtTF.text = name
            names.remove(name)

            color = colors[Random.nextInt(colors.size)]
            tOptionFourTtTF.setTextColor(color)
            colors.remove(color)
            name = names[Random.nextInt(names.size)]
            tOptionFourTtTF.text = name
            names.remove(name)

            color = colors[Random.nextInt(colors.size)]
            tOptionFiveTtTF.setTextColor(color)
            colors.remove(color)
            name = names[Random.nextInt(names.size)]
            tOptionFiveTtTF.text = name
            names.remove(name)

            color = colors[Random.nextInt(colors.size)]
            tOptionSixTtTF.setTextColor(color)
            colors.remove(color)
            name = names[Random.nextInt(names.size)]
            tOptionSixTtTF.text = name
            names.remove(name)
        }
    }
}