package com.example.weatherapp.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherapp.data.db.entity.WeatherEntity
import com.example.weatherapp.databinding.CityItemBinding

class CitiesAdapter : ListAdapter<WeatherEntity, CitiesViewHolder>(DiffCallback){

    companion object DiffCallback : DiffUtil.ItemCallback<WeatherEntity>() {
        override fun areItemsTheSame(oldItem: WeatherEntity, newItem: WeatherEntity): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: WeatherEntity, newItem: WeatherEntity): Boolean {
            return oldItem.name == newItem.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): CitiesViewHolder {
        return CitiesViewHolder(CityItemBinding.inflate(LayoutInflater.from(parent.context))).apply {
            itemView.setOnClickListener {
                var navController: NavController? = null
                navController = Navigation.findNavController(itemView)
                navController.navigate(ListOfCitiesFragmentDirections.actionListOfCitiesFragmentToDetailFragment(getItem(adapterPosition).name))

            }
        }
    }

    override fun onBindViewHolder(holder: CitiesViewHolder, position: Int) {
        val item = getItem(position)

        if (item != null) {
            holder.bind(item)
        }
    }

//    class ClickListener(val clickListener: (name: String) -> Unit) {
//        fun onClick(name: String) = clickListener(name)
//    }

    interface OnClick{
        fun onClick(name: String)
    }
}

class CitiesViewHolder(private val binding: CityItemBinding):
    RecyclerView.ViewHolder(binding.root) {

    fun bind(weatherData: WeatherEntity) {
        binding.cityName.text = weatherData.name
        binding.temp.text = weatherData.temp
        Glide.with(itemView.context)
            .load("https://openweathermap.org/img/wn/${weatherData.icon}@2x.png")
            .into(binding.icon)
    }
}