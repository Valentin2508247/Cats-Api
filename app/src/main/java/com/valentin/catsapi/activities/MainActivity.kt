package com.valentin.catsapi.activities

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.NavHostFragment
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.valentin.catsapi.R
import com.valentin.catsapi.adapters.CatFragmentListener
import com.valentin.catsapi.adapters.FavouriteFragmentListener
import com.valentin.catsapi.adapters.ViewPagerAdapter
import com.valentin.catsapi.databinding.ActivityMainBinding
import com.valentin.catsapi.fragments.CatsFragmentDirections
import com.valentin.catsapi.models.Cat
import kotlinx.coroutines.*
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class MainActivity : AppCompatActivity(), CatFragmentListener, FavouriteFragmentListener {
    private lateinit var binding: ActivityMainBinding
    //private val pagerAdapter = ViewPagerAdapter(this)
    private lateinit var scope: CoroutineScope

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        createNotificationChannel()
        //appComponent.inject(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        binding.viewPager.adapter = pagerAdapter
//        binding.apply {
//            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
//                when (position) {
//                    0 -> {
//                        tab.text = "Cats"
//                        tab.setIcon(R.drawable.outline_pets_black_48)
//                    }
//                    1 -> {
//                        tab.text = "Favourite"
//                        tab.setIcon(R.drawable.ic_baseline_favorite_border_24)
//                    }
//                }
//            }.attach()
//        }
    }

    override fun onStart() {
        scope = CoroutineScope(Dispatchers.IO)
        super.onStart()
    }

    override fun onStop() {
        scope.cancel()
        super.onStop()
    }

    override fun showDetailed(cat: Cat, iv: View) {
        navigateToDetail(cat, iv)
    }

    override fun downloadImage(url: String) {
        scope.launch(Dispatchers.IO) {
            val bitmap: Bitmap = Glide
                .with(this@MainActivity)
                .asBitmap()
                .load(url)
                .submit()
                .get()
            saveMediaToStorage(bitmap)
            withContext(Dispatchers.Main) {
                showNotification(bitmap, url)
            }
        }
    }

    private fun saveMediaToStorage(bitmap: Bitmap) {
        val filename = "${System.currentTimeMillis()}.jpg"
        var fos: OutputStream? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            contentResolver?.also { resolver ->
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                }
                val imageUri: Uri? =
                    resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                fos = imageUri?.let { resolver.openOutputStream(it) }
            }
        } else {
            val imagesDir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val image = File(imagesDir, filename)
            fos = FileOutputStream(image)
        }
        fos?.use {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
        }
    }

    private fun showNotification(bitmap: Bitmap, name: String) {
        var builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Cat Api")
            .setContentText("Image '${name}' saved.")
            .setSmallIcon(R.drawable.ic_baseline_save_alt_24)
            .setLargeIcon(bitmap)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)) {
            // notificationId is a unique int for each notification that you must define
            notify(NOTIFICATION_ID, builder.build())
        }
    }

    private fun createNotificationChannel() {
        // api always >= 26

        val name = "Cat api"
        val descriptionText = "Cat api notifications"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }
        // Register the channel with the system
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private fun navigateToDetail(cat: Cat, iv: View) {

        // why zero??
        Log.d(TAG, "x: ${iv.x}, y: ${iv.y}")
        Log.d(TAG, "width: ${iv.width}, height: ${iv.height}")
        var view = View(this)
        view.layoutParams = ViewGroup.LayoutParams(iv.width, iv.height)
        view.x = 900f
        view.y = 900f
        view.transitionName = cat.id

        val extras = FragmentNavigatorExtras(view to cat.id)
        val action = CatsFragmentDirections.actionCatsFragmentToDetailFragment(cat)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        navController.navigate(action, extras)
    }

    private companion object {
        const val TAG = "ActivityMain"

        const val CHANNEL_ID = "cat_api"
        const val NOTIFICATION_ID = 2508
    }
}