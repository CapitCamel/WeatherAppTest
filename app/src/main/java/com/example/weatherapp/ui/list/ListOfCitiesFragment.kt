package com.example.weatherapp.ui.list

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentListOfCitiesBinding
import com.example.weatherapp.utils.ResultWrapper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListOfCitiesFragment : Fragment() {
    private var _binding: FragmentListOfCitiesBinding? = null
    private val binding get() = _binding!!

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val viewModel: CitiesViewModel by activityViewModels()

    private val rvAdapter: CitiesAdapter = CitiesAdapter(CitiesAdapter.ClickListener{
        viewModel.displayPropertyDetails(it)
    })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        _binding = FragmentListOfCitiesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setHasOptionsMenu(true)

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ){
            val permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION)
            ActivityCompat.requestPermissions(requireActivity(), permissions,0)
        }

        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(context, 1)
            adapter = rvAdapter
        }

        viewModel.weather.observe(viewLifecycleOwner, Observer {
            if(it.isEmpty()){
                Log.d("dfdf", viewModel.weather.value?.size.toString())
                obtieneLocalizacion()
            }
            Log.d("QWQWeee", it.toString())
            rvAdapter.submitList(it)
        })

        viewModel.navigateToSelectedProperty.observe(viewLifecycleOwner, Observer {
            if(null != it){
                this.findNavController().navigate(ListOfCitiesFragmentDirections.actionListOfCitiesFragmentToDetailFragment(it))
                viewModel.displayPropertyDetailsComplete()
            }
        })
    }

    fun getWeather(city: String){
        viewModel.getWeather(city, "9105c08fd7019d9fa51cc2e3c79e6f76", "metric")
        viewModel.res.observe(viewLifecycleOwner, Observer {
            when(it){
                is ResultWrapper.Success -> {
                }
                is ResultWrapper.Error -> {
                    Toast.makeText(
                        requireContext(),
                        "invalid name",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)
    }

    //dialog для добавления города
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val inputEditTextField = EditText(requireActivity())
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("New city")
            .setMessage("Enter a name")
            .setView(inputEditTextField)
            .setPositiveButton("OK") { _, _ ->
                val editTextInput = inputEditTextField .text.toString()
                getWeather(editTextInput)
            }
            .setNegativeButton("Cancel", null)
            .create()
        dialog.show()
        return super.onOptionsItemSelected(item)
    }

    //запрос по последнему местоположению
    @SuppressLint("MissingPermission")
    private fun obtieneLocalizacion(){
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                var latitude = location?.latitude
                var longitude = location?.longitude

                if (latitude != null && longitude != null) {
                    viewModel.insertCityLatLon(latitude, longitude, "9105c08fd7019d9fa51cc2e3c79e6f76", "metric")
                }
            }
    }
}