package com.example.wisataku.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.wisataku.R
import com.example.wisataku.data.model.Tour
import com.example.wisataku.ui.detailtour.DetailTourActivity

class TourAdapter(private val originalList: List<Tour>): RecyclerView.Adapter<TourAdapter.ListViewHolder>(), Filterable {
    private var filteredList: List<Tour> = originalList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_tour, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return filteredList.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val tour = filteredList[position]

        Glide.with(holder.itemView.context)
            .load(tour.photo)
            .apply(RequestOptions().override(495, 170))
            .into(holder.imgPhoto)

        holder.tvName.text = tour.name
        holder.tvAddress.text = tour.address

        holder.itemView.setOnClickListener{
            val intent = Intent(it.context, DetailTourActivity::class.java)
            intent.putExtra("TOUR_NAME", tour.name)
            intent.putExtra("TOUR_DESCRIPTION", tour.description)
            intent.putExtra("TOUR_PHOTO", tour.photo)
            intent.putExtra("TOUR_ADDRESS", tour.address)
            intent.putExtra("TOUR_RATING", tour.rating)
            it.context.startActivity(intent)
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterResults = FilterResults()
                if (constraint.isNullOrEmpty()) {
                    filterResults.values = originalList
                } else {
                    val filtered = originalList.filter {
                        it.name.contains(constraint.toString(), true)
                    }
                    filterResults.values = filtered
                }
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredList = results?.values as? List<Tour> ?: emptyList()
                notifyDataSetChanged()
            }
        }
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvName: TextView = itemView.findViewById(R.id.tvTitle)
        var tvAddress: TextView = itemView.findViewById(R.id.tvLocation)
        var imgPhoto: ImageView = itemView.findViewById(R.id.ivPhoto)
    }

}