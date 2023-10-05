package com.uetmbc.week10

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView


class ImageFileInfoAdapter(context: Context, resource: Int, objects: MutableList<ImageFileInfo>) :
    ArrayAdapter<ImageFileInfo>(context, resource, objects) {
    class ViewHolder {
        public var tvImageName: TextView? = null;
        public var tvDateAdded: TextView? = null;
    };

    private var inflater: LayoutInflater;

    init {
        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val holder: ViewHolder
        var convertView: View? = convertView

        if (convertView == null) {
            holder = ViewHolder()
            convertView = inflater.inflate(com.uetmbc.week10.R.layout.list_image_item, null);
            convertView.tag = holder

            holder.tvDateAdded = convertView.findViewById(com.uetmbc.week10.R.id.tvImageDateAdded);
            holder.tvImageName = convertView.findViewById(com.uetmbc.week10.R.id.tvImageName);
        } else {
            holder = convertView.tag as ViewHolder
        }

        holder.tvDateAdded?.text = super.getItem(position)?.dateAdded;
        holder.tvImageName?.text = super.getItem(position)?.name;

        return convertView!!;
    }
}