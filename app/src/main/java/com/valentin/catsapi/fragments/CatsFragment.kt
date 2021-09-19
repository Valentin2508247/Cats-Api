package com.valentin.catsapi.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.valentin.catsapi.adapters.CatAdapter
import com.valentin.catsapi.adapters.CatFragmentListener
import com.valentin.catsapi.adapters.CatListener
import com.valentin.catsapi.api.ApiHelper
import com.valentin.catsapi.api.RetrofitBuilder
import com.valentin.catsapi.appComponent
import com.valentin.catsapi.database.AppDatabase
import com.valentin.catsapi.databinding.FragmentCatsBinding
import com.valentin.catsapi.models.Cat
import com.valentin.catsapi.repositories.CatsRepository
import com.valentin.catsapi.viewmodels.CatsViewModel
import com.valentin.catsapi.viewmodels.CatsViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


class CatsFragment : Fragment(), CatListener {
    private val TAG = "CatFragment"

    private var _binding: FragmentCatsBinding? = null
    private val binding get() = _binding!!
    private val mAdapter = CatAdapter(this)
    private lateinit var mListener: CatFragmentListener
    private lateinit var mLayoutManager: LinearLayoutManager
//    private var loading = true
    var pastVisibleItems = 0
//    var visibleItemCount:Int = 0
//    var totalItemCount:Int = 0
    @Inject
    lateinit var viewModelFactory: CatsViewModelFactory
    private lateinit var viewModel: CatsViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        requireContext().appComponent.inject(this)
        _binding = FragmentCatsBinding.inflate(inflater, container, false)
        setupViewModel()
        setupListeners()
        setupRecyclerView()

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mListener = context as CatFragmentListener
    }

    private fun setupListeners() {
        binding.fab.setOnClickListener {
            viewModel.loadCats()
        }
    }

    private fun setupViewModel() {
//        val database = AppDatabase.getDatabase(requireContext())
//        viewModel = ViewModelProvider(this, CatsViewModelFactory(CatsRepository(
//            ApiHelper(RetrofitBuilder.catApi),
//            database
//        ))).get(CatsViewModel::class.java)
        viewModel = ViewModelProvider(this, viewModelFactory).get(CatsViewModel::class.java)
        viewModel.apply {
            cats.observe(viewLifecycleOwner){
                Toast.makeText(context, "Cats loaded", Toast.LENGTH_LONG).show()
                Log.d(TAG, "Cats load observe")
                mAdapter.submitList(it)
            }

            state.observe(viewLifecycleOwner) {
                it.showState(mAdapter, mLayoutManager, pastVisibleItems)
            }
        }
    }

    private fun setupRecyclerView() {
        mLayoutManager = LinearLayoutManager(context)
        binding.rvCat.apply {
            layoutManager = mLayoutManager
            adapter = mAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if (dy > 0) {
                        pastVisibleItems = (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                    }
                }
            })
        }
    }


    override fun likeCat(cat: Cat) {
        viewModel.saveCat(cat)
        Toast.makeText(context, "Like cat: ${cat.id}", Toast.LENGTH_SHORT).show()
    }

    override fun downloadImage(cat: Cat) {
        Toast.makeText(context, "Download image: ${cat.url}", Toast.LENGTH_SHORT).show()
        mListener.downloadImage(cat.url)
    }



    override fun onCatBind(pos: Int) {
        Log.d(TAG, "Bind cat at pos $pos")
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}