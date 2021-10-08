package com.valentin.catsapi.fragments

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.valentin.catsapi.R
import com.valentin.catsapi.adapters.CatFragmentListener
import com.valentin.catsapi.appComponent
import com.valentin.catsapi.databinding.FragmentDetailBinding
import com.valentin.catsapi.viewmodels.CatsViewModel
import com.valentin.catsapi.viewmodels.CatsViewModelFactory
import javax.inject.Inject

class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val args: DetailFragmentArgs by navArgs()
    private lateinit var mListener: CatFragmentListener

    @Inject
    lateinit var viewModelFactory: CatsViewModelFactory
    private lateinit var viewModel: CatsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        requireContext().appComponent.inject(this)
        _binding = FragmentDetailBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this, viewModelFactory).get(CatsViewModel::class.java)

        binding.ivDownload.setOnClickListener {
            mListener.downloadImage(args.cat.url)
        }

        binding.ivLike.setOnClickListener {
            viewModel.saveCat(args.cat)
        }

        //initViews()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setSharedElementTransitionOnEnter()
        postponeEnterTransition()
        binding.ivCat.transitionName = args.cat.id


        startEnterTransitionAfterLoadingImage(args.cat.url, binding.ivCat)
    }

    private fun setSharedElementTransitionOnEnter() {
        sharedElementEnterTransition = TransitionInflater.from(context)
            .inflateTransition(R.transition.image_transition)
    }

    private fun startEnterTransitionAfterLoadingImage(imageAddress: String, imageView: ImageView) {
        Glide.with(this)
            .load(imageAddress)
            .apply(
                RequestOptions().dontTransform() // this line
            )
            .dontAnimate()
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    startPostponedEnterTransition()
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable,
                    model: Any,
                    target: Target<Drawable>,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    startPostponedEnterTransition()
                    return false
                }
            })
            .into(imageView)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mListener = context as CatFragmentListener
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private companion object {
        const val TAG = "DetailFragment"
    }
}
