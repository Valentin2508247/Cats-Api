package com.valentin.catsapi.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.valentin.catsapi.adapters.CatAdapter
import com.valentin.catsapi.adapters.CatFragmentListener
import com.valentin.catsapi.adapters.CatListener
import com.valentin.catsapi.appComponent
import com.valentin.catsapi.databinding.FragmentCatsBinding
import com.valentin.catsapi.models.Cat
import com.valentin.catsapi.viewmodels.CatsViewModel
import com.valentin.catsapi.viewmodels.CatsViewModelFactory
import javax.inject.Inject


class CatsFragment : Fragment(), CatListener {

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
        viewModel = ViewModelProvider(this, viewModelFactory).get(CatsViewModel::class.java)
        viewModel.apply {
            cats.observe(viewLifecycleOwner) {
                Log.d(TAG, "Cats load observe")
                mAdapter.submitList(it)
            }

            state.observe(viewLifecycleOwner) {
                it.showState(mAdapter, mLayoutManager, pastVisibleItems)
            }
        }
    }

    private fun setupRecyclerView() {
        //mLayoutManager = LinearLayoutManager(context)
        mLayoutManager = GridLayoutManager(context, 2)
        binding.rvCat.apply {
            layoutManager = mLayoutManager
            adapter = mAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if (dy > 0) {
                        pastVisibleItems =
                            (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
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
        mListener.downloadImage(cat.url)
    }

    override fun showDetailed(cat: Cat, iv: View) {
        mListener.showDetailed(cat, iv)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private companion object {
        const val TAG = "CatFragment"
    }
}