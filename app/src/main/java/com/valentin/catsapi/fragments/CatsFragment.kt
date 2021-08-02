package com.valentin.catsapi.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.valentin.catsapi.adapters.CatAdapter
import com.valentin.catsapi.adapters.CatListener
import com.valentin.catsapi.api.ApiHelper
import com.valentin.catsapi.api.RetrofitBuilder
import com.valentin.catsapi.databinding.FragmentCatsBinding
import com.valentin.catsapi.models.Cat
import com.valentin.catsapi.repositories.CatsRepository
import com.valentin.catsapi.viewmodels.CatsViewModel
import com.valentin.catsapi.viewmodels.CatsViewModelFactory


class CatsFragment : Fragment(), CatListener {
    private val TAG = "CatFragment"

    private var _binding: FragmentCatsBinding? = null
    private val binding get() = _binding!!
    private val mAdapter = CatAdapter(this)

//    private var loading = true
//    var pastVisibleItems = 0
//    var visibleItemCount:Int = 0
//    var totalItemCount:Int = 0

    // TODO: Dependency injection
    private val viewModel: CatsViewModel by viewModels { CatsViewModelFactory(CatsRepository(
        ApiHelper(RetrofitBuilder.catApi)
    )) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCatsBinding.inflate(inflater, container, false)
        setupViewModel()
        setupListeners()
        setupRecyclerView()
        return binding.root
    }

    private fun setupListeners() {
        binding.fab.setOnClickListener {
            viewModel.loadCats()
        }
    }

    private fun setupViewModel() {
        viewModel.apply {
            cats.observe(viewLifecycleOwner){
                Toast.makeText(context, "Cats loaded", Toast.LENGTH_LONG).show()
                mAdapter.submitList(it)
            }

            state.observe(viewLifecycleOwner) {
                Toast.makeText(context, "State observe", Toast.LENGTH_LONG).show()
                it.showState(binding)
            }
        }
    }

    private fun setupRecyclerView() {
        binding.rvCat.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
//            addOnScrollListener(object : RecyclerView.OnScrollListener() {
//                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                    if (dy > 0) { //check for scroll down
//                        visibleItemCount = (layoutManager as LinearLayoutManager).childCount
//                        totalItemCount = (layoutManager as LinearLayoutManager).itemCount
//                        pastVisibleItems = (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
//                        if (loading) {
//                            if (visibleItemCount + pastVisibleItems >= totalItemCount) {
//                                loading = false
//                                Log.v("...", "Last Item Wow !")
//                                Toast.makeText(context, "Do pagination", Toast.LENGTH_SHORT).show()
//                                //viewModel.loadCats()
//                                // Do pagination.. i.e. fetch new data
//                                loading = true
//                            }
//                        }
//                    }
//                }
//            })
        }
    }


    override fun likeCat(cat: Cat) {
        Toast.makeText(context, "Like cat: ${cat.id}", Toast.LENGTH_SHORT).show()
    }

    override fun downloadImage(cat: Cat) {
        Toast.makeText(context, "Download image: ${cat.url}", Toast.LENGTH_SHORT).show()
    }

    override fun onCatBind(pos: Int) {
        Log.d(TAG, "Bind cat at pos $pos")
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}