package com.uetmbc.week12

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener2
import android.hardware.SensorManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.uetmbc.week12.databinding.FragmentSensorBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class SensorFragment : Fragment() {

    private var _binding: FragmentSensorBinding? = null
    private var _sensorManager: SensorManager? = null
    private var _currentListener: SensorEventListener2? = null;
    private var _isListening: Boolean = false;

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSensorBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onPause() {
        super.onPause()

        if (_isListening) {
            _sensorManager!!.unregisterListener(_currentListener);
        }
    }

    override fun onResume() {
        super.onResume()

        if (_isListening) {
            _sensorManager!!.registerListener(_currentListener, _sensorManager!!.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
                SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _sensorManager = activity!!.getSystemService(Context.SENSOR_SERVICE) as SensorManager;
        if (_sensorManager!!.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) != null) {
            binding.tvSensorMagneticInfo.text = getString(R.string.have_magnetic_info);
        } else {
            binding.tvSensorMagneticInfo.text = getString(R.string.no_magnetic_info);
        }

        _currentListener = object: SensorEventListener2 {
            override fun onSensorChanged(event: SensorEvent?) {
                binding.tvSensorData.text = String.format(getString(R.string.magnetic_field_value),
                    event!!.values[0], event!!.values[1], event!!.values[2]);
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
                binding.tvSensorAccChanged.visibility = View.VISIBLE;
                binding.tvSensorAccChanged.text = getString(R.string.magnetic_accuracy);

                view.postDelayed({
                     binding.tvSensorAccChanged.visibility = View.INVISIBLE;
                }, 2000);
            }

            override fun onFlushCompleted(sensor: Sensor?) {
            }
        }

        binding.tvSensorData.visibility = View.INVISIBLE;
        binding.tvSensorAccChanged.visibility = View.INVISIBLE;

        binding.btnSensorToggle.setOnClickListener {
            if (_isListening) {
                _sensorManager!!.unregisterListener(_currentListener);
                binding.tvSensorData.visibility = View.INVISIBLE;
                binding.tvSensorAccChanged.visibility = View.INVISIBLE;
            } else {
                _sensorManager!!.registerListener(_currentListener, _sensorManager!!.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
                    SensorManager.SENSOR_DELAY_NORMAL);

                binding.tvSensorData.visibility = View.VISIBLE;
                binding.tvSensorAccChanged.visibility = View.VISIBLE;
            }

            _isListening = !_isListening;
        }

        binding.btnToMenu.setOnClickListener {
            findNavController().navigate(R.id.action_SensorFragment_to_navigationFragment);
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}