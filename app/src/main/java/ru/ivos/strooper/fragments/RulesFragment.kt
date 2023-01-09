package ru.ivos.strooper.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import ru.ivos.strooper.R
import ru.ivos.strooper.databinding.FragmentRulesBinding


class RulesFragment : Fragment() {

    private var _binding: FragmentRulesBinding? = null
    private val binding get() = _binding ?: throw java.lang.RuntimeException("Binding is empty")

    private val btnBack: ImageButton by lazy {
        binding.ibBackRF
    }

    private val btnLetsGo: AppCompatButton by lazy {
        binding.btnLetsgoRF
    }

    private val tvRulesBody: TextView by lazy {
        binding.tvRulesBodyRF
    }

    private var mode: String? = null

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
        _binding = FragmentRulesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
            val fragment = HomeFragment()
            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit()
        }

        when (mode) {
            CountdownFragment.TEXT_TO_TEXT_MODE -> {
                binding.ivCardOfModeRF.setImageDrawable(
                    requireActivity().getDrawable(R.drawable.card_text_to_text)
                )
                binding.tvRulesHeaderRF.text = requireActivity().getString(R.string.text_to_text)
                tvRulesBody.text = requireActivity().getString(R.string.rules_text_to_text)
            }
            CountdownFragment.TEXT_TO_FIGURE_MODE -> {
                binding.ivCardOfModeRF.setImageDrawable(
                    requireActivity().getDrawable(R.drawable.card_text_to_figure)
                )
                binding.tvRulesHeaderRF.text = requireActivity().getString(R.string.text_to_figure)
                tvRulesBody.text = requireActivity().getString(R.string.rules_text_to_figure)
            }
            CountdownFragment.FIGURE_TO_TEXT_MODE -> {
                binding.ivCardOfModeRF.setImageDrawable(
                    requireActivity().getDrawable(R.drawable.card_figure_to_text)
                )
                binding.tvRulesHeaderRF.text = requireActivity().getString(R.string.figure_to_text)
                tvRulesBody.text = requireActivity().getString(R.string.rules_figure_to_text)
            }
            else -> {
                binding.ivCardOfModeRF.setImageDrawable(
                    requireActivity().getDrawable(R.drawable.card_mixed_mode)
                )
                binding.tvRulesHeaderRF.text = requireActivity().getString(R.string.mixed_mode)
                tvRulesBody.text = requireActivity().getString(R.string.rules_mixed_mode)
            }
        }



        btnLetsGo.setOnClickListener {
            val fragment = CountdownFragment.newInstance(mode!!)
            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val KEY = "key"

        fun newInstance(mode: String) =
            RulesFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY, mode)
                }
            }
    }
}