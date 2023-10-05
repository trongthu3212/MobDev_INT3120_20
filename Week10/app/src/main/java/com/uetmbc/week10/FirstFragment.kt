package com.uetmbc.week10

import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.uetmbc.week10.databinding.FragmentFirstBinding
import java.text.SimpleDateFormat
import java.util.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

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

    private fun updateImageFilesList(filter: String? = null) {
        var filter: String? = filter;

        if (filter == "") {
            filter = null;
        }

        var cursor = activity?.contentResolver?.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            arrayOf(MediaStore.Images.Media.DISPLAY_NAME, MediaStore.Images.Media.DATE_ADDED),
            if (filter != null) { MediaStore.Images.Media.DISPLAY_NAME + " LIKE ? " } else { null },
            if (filter != null) { arrayOf("%${filter}%") } else { null },
            MediaStore.Images.Media.DATE_ADDED + " DESC");

        var listFiles: ArrayList<ImageFileInfo> = ArrayList();

        if (cursor != null) {
            val indexName: Int = cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)
            val indexDt: Int = cursor.getColumnIndex(MediaStore.Images.Media.DATE_ADDED)
            //iterate over all words found
            while (cursor.moveToNext()) {
                //gets the value from the column.
                val word: String = cursor.getString(indexName)
                val dateAdded: Date = Date(cursor.getString(indexDt).toLong() * 1000);

                val format = SimpleDateFormat("dd/MM/yy")
                val dateAddedText: String = format.format(dateAdded)

                listFiles.add(ImageFileInfo(word, dateAddedText))
            }
        }

        if (binding.lvFiles.adapter != null) {
            var adapterImage = binding.lvFiles.adapter as ImageFileInfoAdapter;

            adapterImage.clear();
            adapterImage.addAll(listFiles)
            adapterImage.notifyDataSetInvalidated();
        } else {
            var adapter = context?.let { ImageFileInfoAdapter(it, R.layout.list_image_item, listFiles) }
            binding.lvFiles.adapter = adapter;
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateImageFilesList();

        binding.etSearch.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                updateImageFilesList(binding.etSearch.text.toString());
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}