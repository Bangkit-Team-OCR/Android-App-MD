package com.aran.ocrapp.view

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.service.controls.ControlsProviderService
import android.util.Base64
import android.util.Base64.encodeToString
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import com.aran.ocrapp.R
import com.aran.ocrapp.api.ApiConfig
import com.aran.ocrapp.databinding.ActivitySecondStepBinding
import com.aran.ocrapp.helper.PostResponse
import com.aran.ocrapp.helper.Responses
import com.aran.ocrapp.helper.SharedViewModel
import com.bumptech.glide.Glide
import com.yalantis.ucrop.UCrop
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.*
import java.lang.ref.WeakReference
import java.util.*

class SecondStepActivity : AppCompatActivity() {

    lateinit var binding: ActivitySecondStepBinding

    lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    lateinit var uri: Uri

    private lateinit var uploadId: SharedViewModel

    var imgBase64: String? = null

    private val GALLERY_REQUEST_CODE = 1234
    private val WRITE_EXTERNAL_STORAGE_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_second_step)

        checkPermission()
        requestPermission()

        binding.btnGallery.setOnClickListener {
            if (checkPermission()) {
                pickFromGallery()
            } else {
                Toast.makeText(this, "Allow all permissions", Toast.LENGTH_SHORT).show()
                requestPermission()
            }
        }

        binding.btnCamera.setOnClickListener {
            if (checkPermission()) {
                pickFromCamera()
            } else {
                Toast.makeText(this, "Allow all permissions", Toast.LENGTH_SHORT).show()
                requestPermission()
            }
        }

        binding.btnUpload.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                    val permission = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    requestPermissions(permission,WRITE_EXTERNAL_STORAGE_CODE)
                }
                else {
                    uploadPhoto()
                }
            }
        }

        binding.btnCancel.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setMessage("DO YOU WANT TO CANCEL?")
            builder.setPositiveButton("YES") { dialog, which ->

                binding.btnCancel.visibility=View.GONE
                binding.btnUpload.visibility=View.GONE
                binding.imgResult.visibility=View.VISIBLE
                binding.btnCamera.visibility=View.VISIBLE
                binding.btnGallery.visibility=View.VISIBLE
            }

            builder.setNegativeButton("NO") { dialog, which -> }

            val alertDialog = builder.create()
            alertDialog.window?.setGravity(Gravity.BOTTOM)
            alertDialog.show()
        }

        activityResultLauncher  =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                if (result.resultCode == RESULT_OK) {
                    val extras: Bundle? = result.data?.extras
                    val imageUri: Uri
                    val imageBitmap = extras?.get("data") as Bitmap
                    val imageResult: WeakReference<Bitmap> = WeakReference(
                        Bitmap.createScaledBitmap(
                            imageBitmap, imageBitmap.width, imageBitmap.height, false
                        ).copy(
                            Bitmap.Config.RGB_565, true
                        )
                    )
                    val bm = imageResult.get()
                    imageUri = saveImage(bm, this)
                    launchImageCrop(imageUri)
                }
            }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode) {

            WRITE_EXTERNAL_STORAGE_CODE -> {

                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    Toast.makeText(this, "Enable permissions", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun uploadPhoto() {
        val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
        val stream = ByteArrayOutputStream()

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)

        val bytes = stream.toByteArray()
        val imgBase64 = encodeToString(bytes, Base64.DEFAULT)

        uploadPhotoToServer(imgBase64)

        Log.d("Converting . . .", "Base64: $imgBase64")
    }

    private fun uploadPhotoToServer(imgBase64: String) {
        showLoading(true)

        val client = ApiConfig.getApiService().postID(imgBase64)
        client.enqueue(object: Callback<PostResponse> {
            override fun onResponse(
                call: Call<PostResponse>,
                response: Response<PostResponse>
            ) {
                showLoading(false)
                val responseBody = response.body()
                Log.d(ControlsProviderService.TAG, "onResponse: $responseBody")
                if(response.isSuccessful && responseBody?.message == "load model successfully") {

                    Toast.makeText(this@SecondStepActivity, getString(R.string.upload_success), Toast.LENGTH_SHORT).show()
                    Log.d("data", responseBody.data.nik)

                    val nik = responseBody.data.nik
                    val nama = responseBody.data.nama
                    val alamat = responseBody.data.alamat
                    val provinsi = responseBody.data.provinsi
                    val kabupaten = responseBody.data.kabupaten
                    val agama = responseBody.data.agama
                    val jk = responseBody.data.jk
                    val kecamatan = responseBody.data.kecamatan
                    val kelurahan = responseBody.data.kelurahan
                    val pekerjaan = responseBody.data.pekerjaan
                    val rt = responseBody.data.rt
                    val ttl = responseBody.data.ttl
                    val kewarganegaraan = responseBody.data.kewarganegaraan

                    val i = Intent(this@SecondStepActivity, DetailDataActivity::class.java)
                    i.putExtra("nik", nik)
                    i.putExtra("nama", nama)
                    i.putExtra("alamat", alamat)
                    i.putExtra("provinsi", provinsi)
                    i.putExtra("kabupaten", kabupaten)
                    i.putExtra("agama", agama)
                    i.putExtra("jk", jk)
                    i.putExtra("kecamatan", kecamatan)
                    i.putExtra("kelurahan", kelurahan)
                    i.putExtra("pekerjaan", pekerjaan)
                    i.putExtra("rt", rt)
                    i.putExtra("ttl", ttl)
                    i.putExtra("kewarganegaraan", kewarganegaraan)

                    startActivity(i)

                } else {
                    Log.e(ContentValues.TAG, "onFailure1: ${response.message()}")
                    Toast.makeText(this@SecondStepActivity, getString(R.string.upload_success), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                showLoading(false)
                Log.e(ControlsProviderService.TAG, "onFailure2: ${t.message}")
                Toast.makeText(this@SecondStepActivity, getString(R.string.login_fail), Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun uploadToServer(bitmap: Bitmap) {
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
            Toast.makeText(this, "Saved to Photos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveImage(image: Bitmap?, context: Context): Uri {
        val imageFolder = File(context.cacheDir,"images")
        var uri: Uri? = null
        try {

            imageFolder.mkdirs()
            val file: File = File(imageFolder,"captured_image.png")
            val stream: FileOutputStream = FileOutputStream(file)
            image?.compress(Bitmap.CompressFormat.JPEG,100, stream)
            stream.flush()
            stream.close()
            uri = FileProvider.getUriForFile(context.applicationContext,"com.aran.ocrapp"+".provider", file)

        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return uri!!
    }

    @SuppressLint("IntentReset")
    private fun pickFromGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        val mimeTypes = arrayOf("image/jpeg", "image/png", "image/jpg")
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
        intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        startActivityForResult(intent, GALLERY_REQUEST_CODE)
    }

    private fun pickFromCamera(){
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        activityResultLauncher.launch(intent)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            GALLERY_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    data?.data?.let { uri ->
                        launchImageCrop(uri)
                    }
                }
            }
        }

        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            val resultUri :Uri ?= UCrop.getOutput(data!!)

            setImage(resultUri!!)
            uri = resultUri

            binding.imgResult.visibility= View.VISIBLE
            binding.btnUpload.visibility= View.VISIBLE
            binding.btnCancel.visibility= View.VISIBLE
            binding.btnCamera.visibility= View.GONE
            binding.btnGallery.visibility= View.GONE
        }
    }

    private fun launchImageCrop(uri: Uri) {
        val destination:String=StringBuilder(UUID.randomUUID().toString()).toString()
        val options:UCrop.Options=UCrop.Options()
        UCrop.of(Uri.parse(uri.toString()), Uri.fromFile(File(cacheDir,destination)))
            .withOptions(options)
            .withAspectRatio(0F, 0F)
            .useSourceImageAspectRatio()
            .withMaxResultSize(1000, 1000)
            .start(this)
        options.setCompressionQuality(100)
        options.setMaxBitmapSize(10000)
    }

    private fun setImage(uri: Uri){
        Glide.with(this)
            .load(uri)
            .into(binding.imgResult)
    }

    private fun checkPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
            ),
            100
        )
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}