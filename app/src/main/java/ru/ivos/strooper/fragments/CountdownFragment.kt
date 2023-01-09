package ru.ivos.strooper.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.ivos.strooper.R
import ru.ivos.strooper.databinding.FragmentCountdownBinding
import ru.ivos.strooper.databinding.FragmentHomeBinding
import ru.ivos.strooper.databinding.FragmentRulesBinding

class CountdownFragment : Fragment() {

    private var _binding: FragmentCountdownBinding? = null
    private val binding get() = _binding ?: throw java.lang.RuntimeException("Binding is empty")

    private var mode: String? = null

    private val tvCountdown: TextView by lazy {
        binding.tvCountdown
    }

    private var job: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mode = it.getString(KEY)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCountdownBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        job = viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            for (i in 3 downTo 1) {
                tvCountdown.text = i.toString()
                delay(1000)
            }
            tvCountdown.textSize = 150f
            tvCountdown.text = requireContext().getString(R.string.go)
            delay(1000)
            startGame(mode!!)
            job?.cancel()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun startGame(mode: String) {
        if (job!!.isActive) {
            when (mode) {
                TEXT_TO_TEXT_MODE -> getMode(TextToTextModeFragment())
                TEXT_TO_FIGURE_MODE -> getMode(TextToFigureModeFragment())
                FIGURE_TO_TEXT_MODE -> getMode(FigureToTextModeFragment())
                MIXED_MODE -> getMode(MixedModeFragment())
            }
        }
        job?.cancel()
    }

    private fun getMode(fragment: Fragment) {
        requireActivity().supportFragmentManager.popBackStack()
        requireActivity().supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    companion object {
        private const val KEY = "key"

        const val TEXT_TO_TEXT_MODE = "text_to_text_mode"
        const val TEXT_TO_FIGURE_MODE = "text_to_figure_mode"
        const val FIGURE_TO_TEXT_MODE = "figure_to_text_mode"
        const val MIXED_MODE = "mixed_mode"

        fun newInstance(mode: String) =
            CountdownFragment().apply {
                arguments = Bundle().apply {
                    putString(CountdownFragment.KEY, mode)
                }
            }
    }
}