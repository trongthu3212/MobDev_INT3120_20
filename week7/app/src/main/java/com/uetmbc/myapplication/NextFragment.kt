package com.uetmbc.myapplication

import MyBroadcastReceiver
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.uetmbc.myapplication.databinding.FragmentNextBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class NextFragment : Fragment(), MessageListener {

    private var _binding: FragmentNextBinding? = null
    private var receiver: MyBroadcastReceiver? = null;

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var fullName: String = "";
    private var message: String = "";

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentNextBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            activity?.onBackPressedDispatcher?.onBackPressed();
        }

        var intent = activity?.intent;
        this.fullName = intent?.getStringExtra("fullName").toString();
        this.message = intent?.getStringExtra("message").toString();

        binding.textView2.setText(this.message);

        receiver = MyBroadcastReceiver(this);
        context?.registerReceiver(receiver, IntentFilter("com.example.ACTION_MY_EVENT"));

        var testIntent = Intent("com.example.ACTION_MY_EVENT");
        testIntent.putExtra("message", "This is a test message! From feedback sender");

        context?.sendBroadcast(testIntent);
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
        context?.unregisterReceiver(receiver)
    }

    override fun OnMessageIncoming(message: String) {
        binding.tvFeedbackReceived.text = getString(R.string.feedback_received) + message;
    }
}