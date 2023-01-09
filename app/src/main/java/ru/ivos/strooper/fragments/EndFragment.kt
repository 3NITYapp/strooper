package ru.ivos.strooper.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.ivos.strooper.R
import ru.ivos.strooper.data.PreferencesRepo
import ru.ivos.strooper.databinding.FragmentEndBinding

class EndFragment : Fragment() {

    private var _binding: FragmentEndBinding? = null
    private val binding get() = _binding ?: throw java.lang.RuntimeException("Binding is empty")

    private var countOfCorrect = 0
    private var score = 0
    private var mode: String? = ""

    private lateinit var preferencesRepo: PreferencesRepo

    override fun onAttach(context: Context) {
        super.onAttach(context)
        preferencesRepo = PreferencesRepo(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            countOfCorrect = it.getInt(COUNT_CORRECT_ANSWERS)
            score = it.getInt(SCORE)
            mode = it.getString(MODE)
            Log.d("tag", "${mode}")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEndBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            btnTryAgainEF.setOnClickListener {
                val fragment = CountdownFragment.newInstance(mode!!)
                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, fragment)
                    .commit()
            }
            btnBackToHomeEF.setOnClickListener {
                val fragment = HomeFragment()
                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, fragment)
                    .commit()
            }

            tvCorrectAnswersCountEF.text = countOfCorrect.toString()
            tvScoreCountEF.text = score.toString()
            tvRecordCountEF.text = preferencesRepo.getHighScore().toString()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        private const val COUNT_CORRECT_ANSWERS = "CountOfCorrectAnswers"
        private const val SCORE = "score"
        private const val MODE = "mode"

        @JvmStatic
        fun newInstance(param1: Int, param2: Int, param3: String?) =
            EndFragment().apply {
                arguments = Bundle().apply {
                    putInt(COUNT_CORRECT_ANSWERS, param1)
                    putInt(SCORE, param2)
                    putString(MODE, param3)
                }
            }

    }
}