package com.uetmbc.myapplication

import android.app.Activity
import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.uetmbc.myapplication.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val greetingLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
    {
        if (it.resultCode == Activity.RESULT_OK)
        {
            val feedback = it.data?.getStringExtra("feedback");
            binding.tvFeedback.text = feedback;
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun searchInternet()
    {
        var keyword = binding.etSearchKeyword.text;
        var intent = Intent(Intent.ACTION_WEB_SEARCH);
        intent.putExtra(SearchManager.QUERY, keyword);

        startActivity(intent);
    }

    private fun sendMessage()
    {
        val fullName = binding.editTextFullName.text.toString();
        val message = getString(R.string.hello_msg);

        var intent = Intent(context, NextActivity::class.java);
        intent.putExtra("fullName", fullName);
        intent.putExtra("message", message);

        greetingLauncher.launch(intent);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSendMsg.setOnClickListener {
             sendMessage();
        }

        binding.btnSearch.setOnClickListener {
            searchInternet();
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}