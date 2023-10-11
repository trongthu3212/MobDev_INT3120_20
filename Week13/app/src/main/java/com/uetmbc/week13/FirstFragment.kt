package com.uetmbc.week13

import android.content.pm.PackageManager
import android.location.Location
import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.Task
import com.uetmbc.week13.databinding.FragmentFirstBinding


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private var _mediaPlayer: MediaPlayer? = null;

    private val fusedLocationClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(context)
    }

    private var cancellationTokenSource = CancellationTokenSource()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun updateLocation() {
        if (ActivityCompat.checkSelfPermission(
                context!!,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) ==
            PackageManager.PERMISSION_GRANTED
        ) {

            val currentLocationTask: Task<Location> = fusedLocationClient.getCurrentLocation(
                PRIORITY_BALANCED_POWER_ACCURACY,
                cancellationTokenSource.token
            )

            currentLocationTask.addOnCompleteListener { task: Task<Location> ->
                val result = if (task.isSuccessful) {
                    val result: Location = task.result
                    binding.txtLocation.text = String.format(
                        getString(R.string.current_location),
                        result.latitude,
                        result.longitude
                    );
                } else {
                    val exception = task.exception
                    binding.txtLocation.text =
                        getString(R.string.location_get_failed) + exception.toString();
                }
            }
        } else {
            ActivityCompat.requestPermissions(activity!!, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 1);
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnPlayMusic.setOnClickListener {
            if (_mediaPlayer == null) {
                _mediaPlayer = MediaPlayer.create(context, R.raw.sample);
                _mediaPlayer!!.start();

                binding.btnPlayMusic.text = getString(R.string.stop_music);
            } else {
                _mediaPlayer!!.stop();
                _mediaPlayer = null;

                binding.btnPlayMusic.text = getString(R.string.play_music);
            }
        }

        binding.btnUpdateLoc.setOnClickListener {
            updateLocation();
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}