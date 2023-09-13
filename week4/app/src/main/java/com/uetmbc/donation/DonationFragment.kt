package com.uetmbc.donation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import com.uetmbc.donation.databinding.FragmentDonationBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class DonationFragment : Fragment() {

    private var _binding: FragmentDonationBinding? = null
    private var totalDonated: Int = 0

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val donationGoal get() = _binding!!.npWheel.value;

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentDonationBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.npWheel.minValue = 1;
        binding.npWheel.maxValue = 999;
        binding.npWheel.value = 500;

        binding.pbDonate.max = donationGoal;
        binding.pbDonate.progress = 0;

        binding.tvTotal.text = "${getString(R.string.total_donate)} \$0";

        binding.npWheel.setOnValueChangedListener { _: NumberPicker, _: Int, newGoal: Int ->
            binding.pbDonate.max = newGoal
        }

        binding.btnDonate.setOnClickListener {
            totalDonated += Integer.parseInt(binding.etAmount.text.toString())
            binding.tvTotal.text = "${getString(R.string.total_donate)} \$${totalDonated}";
            binding.pbDonate.progress = totalDonated;
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}