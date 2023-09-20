package com.uetmbc.donation

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.uetmbc.donation.databinding.FragmentDateTimeBinding
import java.util.*


class DateTimeFragment : Fragment() {
    private var _binding: FragmentDateTimeBinding? = null
    private val binding get() = _binding!!
    private var calender: Calendar = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDateTimeBinding.inflate(inflater, container, false)
        return binding.root;
    }

    private fun updateLabel()
    {
        binding.lblDateAndTime.text = calender.toString();
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val d: DatePickerDialog.OnDateSetListener = DatePickerDialog.OnDateSetListener()
        { _, year, monthOfYear, dayOfMonth ->
            calender.set(Calendar.YEAR, year);
            calender.set(Calendar.MONTH, monthOfYear);
            calender.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        };

        val t: TimePickerDialog.OnTimeSetListener = TimePickerDialog.OnTimeSetListener()
        { _, hourOfDay, minute ->
            calender.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calender.set(Calendar.MINUTE, minute);
            updateLabel();
        };

        binding.btnDate.setOnClickListener {
            this.context?.let { context ->
                DatePickerDialog(
                    context, d,
                    calender.get(Calendar.YEAR),
                    calender.get(Calendar.MONTH),
                    calender.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
        }

        binding.btnTime.setOnClickListener {
            this.context?.let { context ->
                TimePickerDialog(
                    context, t,
                    calender.get(Calendar.HOUR_OF_DAY),
                    calender.get(Calendar.MINUTE),
                    true
                ).show()
            }
        }

        updateLabel()
    }
}