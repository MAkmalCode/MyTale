package com.malbyte.mytale.ui.add_story

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.malbyte.mytale.R
import com.malbyte.mytale.data.factory.ViewModelFactory
import com.malbyte.mytale.databinding.ActivityAddStoryBinding
import com.malbyte.mytale.ui.add_story.state.AddStoryState

class AddStoryActivity : AppCompatActivity() {
    private val binding: ActivityAddStoryBinding by viewBinding()
    private val viewModel: AddStoryViewModel by viewModels {
        ViewModelFactory.getInstance(application)
    }
    private var selectedUri: Uri? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var latitude: Float? = null
    private var longitude: Float? = null
    private var switch: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_story)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (switch == true) {
                fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, CancellationTokenSource().token)
                    .addOnSuccessListener {
                        latitude = it.latitude.toFloat()
                        longitude = it.longitude.toFloat()
                    }
            }
        }

        binding.switchAddStoryLoaction.setOnClickListener {
            switch = !switch
            Log.d("Longitude",  longitude.toString())
            Log.d("Latitude",  latitude.toString())
        }

        binding.switchAddStoryLoaction.isChecked = switch

        viewModel.photo.observe(this) {
            binding.buttonAdd.setOnClickListener {
                val description = binding.edAddDescription.text.toString()

                if (it == null) {
                    Toast.makeText(
                        this@AddStoryActivity,
                        "Please select an image from your gallery",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }

                if (description.isEmpty()) {
                    binding.edAddDescription.error = "Please input this field"
                    return@setOnClickListener
                }
                viewModel.addStory(description, latitude, longitude)
            }
        }

        binding.btnAddImage.setOnClickListener {
            ImagePicker.with(this)
                .compress(1000)
                .setDismissListener {
                    Toast.makeText(this@AddStoryActivity, "Dismissed", Toast.LENGTH_SHORT).show()
                }
                .galleryMimeTypes(
                    arrayOf(
                        "image/*"
                    )
                )
                .createIntent {
                    startForProfileImageResult.launch(it)
                }
        }

        binding.loadingBar.isVisible = false
        viewModel.addStoryState.observe(this) {
            when (it) {
                is AddStoryState.Error -> {
                    binding.loadingBar.isVisible = false
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }

                is AddStoryState.Loading -> {
                    binding.loadingBar.isVisible = true
                }

                is AddStoryState.Success -> {
                    binding.loadingBar.isVisible = false
                    finish()
                }
            }
        }
    }

    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val data = result.data
            when (result.resultCode) {
                Activity.RESULT_OK -> {
                    selectedUri = data?.data!!
                    val imageBitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        ImageDecoder.decodeBitmap(
                            ImageDecoder.createSource(
                                this.contentResolver,
                                selectedUri!!
                            )
                        )
                    } else {
                        @Suppress("DEPRECATION")
                        MediaStore.Images.Media.getBitmap(this.contentResolver, selectedUri)
                    }

                    binding.ivAddStoryPreview.load(imageBitmap)
                    viewModel.setPhoto(imageBitmap)
                }

                ImagePicker.RESULT_ERROR -> {
                    Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
                }

                else -> {

                }
            }
        }
}