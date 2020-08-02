package com.tambolaonline.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.TextView
import com.tambolaonline.main.R
import com.tambolaonline.variations.VariationTypes

class VariationListAdapter (private val dataset:Array<VariationTypes>, context: Context): ArrayAdapter<VariationTypes>(context,
    R.layout.variationslist_item_view, dataset) {

    private class ViewHolder {
        lateinit var txtName: TextView
        lateinit var checked: CheckBox
    }

    override fun getCount(): Int {
        return dataset.size
    }

    override fun getItem(position: Int): VariationTypes {
        return dataset[position]
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val viewHolder : ViewHolder
        val result : View

        if(convertView == null){
            viewHolder = ViewHolder()
            convertView = LayoutInflater.from(parent.context).inflate(R.layout.variationslist_item_view, parent, false)

            viewHolder.txtName = convertView.findViewById(R.id.variationName)
            viewHolder.checked = convertView.findViewById(R.id.variationCheck)

            result=convertView
            convertView.tag = viewHolder
        }
        else
        {
            viewHolder=convertView.tag as ViewHolder
            result=convertView
        }

        val item : VariationTypes = dataset[position]
        viewHolder.txtName.text = item.variationname
        viewHolder.checked.isChecked = false

        return result
    }
}