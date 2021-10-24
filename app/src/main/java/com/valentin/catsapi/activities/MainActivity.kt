package com.valentin.catsapi.activities

import android.content.ContentValues
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.NavHostFragment
import com.bumptech.glide.Glide
import com.valentin.catsapi.R
import com.valentin.catsapi.databinding.ActivityMainBinding
import com.valentin.catsapi.fragments.CatFragmentListener
import com.valentin.catsapi.fragments.CatsFragmentDirections
import com.valentin.catsapi.models.Cat
import com.valentin.catsapi.utils.NotificationHelper
import kotlinx.coroutines.*
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class MainActivity : AppCompatActivity(), CatFragmentListener{
    private lateinit var binding: ActivityMainBinding
    private lateinit var scope: CoroutineScope

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        NotificationHelper.createNotificationChannel(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
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
        Log.d(TAG, "x: ${iv.x}, y: ${iv.y}")
        val extras = FragmentNavigatorExtras(iv to cat.id)
        val action = CatsFragmentDirections.actionCatsFragmentToDetailFragment(cat)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        navController.navigate(action, extras)
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
                NotificationHelper.showNotification(this@MainActivity, bitmap, url)
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

    private companion object {
        const val TAG = "ActivityMain"
    }
}
