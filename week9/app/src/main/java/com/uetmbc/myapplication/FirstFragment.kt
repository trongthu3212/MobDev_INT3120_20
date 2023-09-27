package com.uetmbc.myapplication

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SimpleCursorAdapter
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.uetmbc.myapplication.databinding.FragmentFirstBinding
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private var databaseHelper: FeedReaderDbHelper? = null

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

    private fun updateSilentModeStatus()
    {
        val sharedPrefs: SharedPreferences? = context?.getSharedPreferences("bai9", 0);
        binding.swSilentMode.isChecked = sharedPrefs?.getBoolean("silentMode", false) == true;
    }

    private fun editSilentModeStatus(modeOn: Boolean)
    {
        val sharedPrefs: SharedPreferences? = context?.getSharedPreferences("bai9", 0);
        var sharedPrefsEdit = sharedPrefs?.edit();

        sharedPrefsEdit?.putBoolean("silentMode", modeOn);
        sharedPrefsEdit?.commit();
    }

    private fun getSavedFileContent() : String?
    {
        try {
            var fis : FileInputStream? = context?.openFileInput("hello.txt") ?: return null;
            var isr = InputStreamReader(fis);
            var bufferedReader = BufferedReader(isr);
            var sb = StringBuilder();
            var line: String? = null;

            while (true) {
                line = bufferedReader.readLine() ?: break;
                sb.append(line);
            }

            fis?.close();

            return sb.toString();
        } catch (e: Exception)
        {
            return null;
        }
    }

    private fun saveFileContent(value: String)
    {
        var fos = context?.openFileOutput("hello.txt", 0)  ?: return;
        fos.write(value.toByteArray());
        fos.close();
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.swSilentMode.setOnCheckedChangeListener { _, isChecked -> editSilentModeStatus(isChecked) }
        binding.etContent.setText(getSavedFileContent() ?: "");
        binding.etContent.addTextChangedListener {
            saveFileContent(binding.etContent.text.toString());
        }

        /*
        databaseHelper = FeedReaderDbHelper(context);
        binding.rvDbList.adapter = SimpleCursorAdapter(
            context,
            R.layout.list_view_item,
            databaseHelper?.readableDatabase?.query(FeedReaderContract.FeedEntry.TABLE_NAME, arrayOf("*"), null, null, null, null, null),
                arrayOf("_id", "title", "subtitle"),
                IntArray(3) { R.id.tvId; R.id.tvTitle; R.id.tvSubtitle }
        )*/

        updateSilentModeStatus();
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}