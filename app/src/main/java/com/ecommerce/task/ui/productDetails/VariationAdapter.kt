package com.ecommerce.task.ui.productDetails

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import androidx.annotation.LayoutRes
import com.ecommerce.task.data.model.Variation
import com.ecommerce.task.databinding.VariationItemBinding

class VariationAdapter(
    context: Context, @LayoutRes private val layoutResource: Int, cities: List<Variation>
) : ArrayAdapter<Variation>(context, layoutResource, cities) {
    private val variation: MutableList<Variation> = ArrayList(cities)
    private var allVariations: List<Variation> = ArrayList(cities)

    override fun getCount(): Int {
        return variation.size
    }

    override fun getItem(position: Int): Variation {
        return variation[position]
    }

    override fun getItemId(position: Int): Long {
        return variation[position].id.toLong()
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = LayoutInflater.from(context)
        val binding = VariationItemBinding.inflate(inflater, parent, false)
        try {
            val variation: Variation = getItem(position)
            binding.data = variation
            binding.executePendingBindings()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return binding.root
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun convertResultToString(resultValue: Any): String? {
                return (resultValue as Variation).title
            }

            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterResults = FilterResults()
                if (constraint != null) {
                    val variationSuggestion: MutableList<Variation> = ArrayList()
                    for (variation in allVariations) {
                        if (variation.title.contains(constraint.toString(), true)) {
                            variationSuggestion.add(variation)
                        }
                    }
                    filterResults.values = variationSuggestion
                    filterResults.count = variationSuggestion.size
                }
                return filterResults
            }

            override fun publishResults(
                constraint: CharSequence?, results: FilterResults
            ) {
                variation.clear()
                if (results.count > 0) {
                    for (result in results.values as List<*>) {
                        if (result is Variation) {
                            variation.add(result)
                        }
                    }
                    notifyDataSetChanged()
                } else if (constraint == null) {
                    variation.addAll(allVariations)
                    notifyDataSetInvalidated()
                }
            }
        }
    }
}