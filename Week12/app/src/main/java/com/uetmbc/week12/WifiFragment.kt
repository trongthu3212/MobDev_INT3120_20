package com.uetmbc.week12

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.uetmbc.week12.databinding.FragmentWifiBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class WifiFragment : Fragment() {

    private var _binding: FragmentWifiBinding? = null
    private var _connectivity: ConnectivityManager? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentWifiBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _connectivity = context!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager;
        val networkCaps: NetworkCapabilities? = _connectivity!!.getNetworkCapabilities(_connectivity!!.activeNetwork);

        var isUsingWifi = false;
        if (networkCaps!!.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
            binding.tvConnectUsing.text = getString(R.string.connect_using_wifi);
        } else if (networkCaps!!.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
            binding.tvConnectUsing.text = getString(R.string.connect_using_mobile);
        } else {
            binding.tvConnectUsing.text = getString(R.string.connect_using_vpn);
        }

        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_WifiFragment_to_navigationFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}