package com.valentin.catsapi.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.valentin.catsapi.adapters.CatAdapter
import com.valentin.catsapi.adapters.CatListener
import com.valentin.catsapi.adapters.FavouriteFragmentListener
import com.valentin.catsapi.appComponent
import com.valentin.catsapi.database.AppDatabase
import com.valentin.catsapi.databinding.FragmentFavouriteBinding
import com.valentin.catsapi.models.Cat
import com.valentin.catsapi.viewmodels.FavouriteViewModel
import com.valentin.catsapi.viewmodels.FavouriteViewModelFactory
import javax.inject.Inject

class FavouriteFragment : Fragment(), CatListener {
    private var _binding: FragmentFavouriteBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FavouriteViewModel

    @Inject
    lateinit var viewModelFactory: FavouriteViewModelFactory
    private lateinit var mListener: FavouriteFragmentListener
    private val mAdapter = CatAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        requireContext().appComponent.inject(this)
        _binding = FragmentFavouriteBinding.inflate(inflater, container, false)
        setupViewModel()
        setupRecyclerView()
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mListener = context as FavouriteFragmentListener
    }

    private fun setupViewModel() {
        val database = AppDatabase.getDatabase(requireContext())
        viewModel = ViewModelProvider(this, viewModelFactory).get(FavouriteViewModel::class.java)
//        viewModel = ViewModelProvider(this, FavouriteViewModelFactory(CatsRepository(
//            ApiHelper(RetrofitBuilder.catApi),
//            database
//        ))).get(FavouriteViewModel::class.java)

        viewModel.apply {
            cats.observe(viewLifecycleOwner) {
                mAdapter.submitList(it)
            }
        }
    }

    private fun setupRecyclerView() {
        binding.rvCat.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun likeCat(cat: Cat) {
        viewModel.saveCat(cat)
        Toast.makeText(context, "Like cat: ${cat.id}", Toast.LENGTH_SHORT).show()
    }

    override fun downloadImage(cat: Cat) {
        Toast.makeText(context, "Download image: ${cat.url}", Toast.LENGTH_SHORT).show()
    }

    override fun showDetailed(cat: Cat, iv: View) {
        TODO("Not yet implemented")
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        const val TAG = "FavouriteFragment"
    }
}
