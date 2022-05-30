package com.aran.ocrapp.ui
import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.aran.ocrapp.databinding.ActivityNextStepBinding
import java.io.File

class NextStepActivity : AppCompatActivity() {

    private lateinit var binding : ActivityNextStepBinding

    private lateinit var file: File
    private lateinit var uri: Uri
    private lateinit var camIntent: Intent
    private lateinit var galIntent: Intent
    private lateinit var cropIntent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNextStepBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enablePermission()

        binding.btnCamera.setOnClickListener { openCamera() }
        binding.btnGallery.setOnClickListener { openGallery() }
    }

    private fun enablePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this@NextStepActivity, Manifest.permission.CAMERA
        )) {
            Toast.makeText(this@NextStepActivity, "Camera is allowed", Toast.LENGTH_SHORT).show()
        }
        else {
            ActivityCompat.requestPermissions(this@NextStepActivity, arrayOf(Manifest.permission.CAMERA), RequestPermissionCode)
        }
    }

    private fun openGallery() {
        galIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(Intent.createChooser(galIntent, "Select Image From Gallery"), 2)
    }

    private fun openCamera() {
        camIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        file = File(Environment.getExternalStorageDirectory(),
            "file"+System.currentTimeMillis().toString()+".jpg"
        )
        uri = Uri.fromFile(file)
        camIntent.putExtra(MediaStore.EXTRA_OUTPUT,uri)
        camIntent.putExtra("return-data",true)
        startActivityForResult(camIntent,0)
    }

    private fun cropImage() {

        try {
            cropIntent = Intent("com.android.camera.action.CROP")
            cropIntent.setDataAndType(uri, "image/*")
            cropIntent.putExtra("crop", true)
            cropIntent.putExtra("outputX", 270)
            cropIntent.putExtra("outputY", 180)
            cropIntent.putExtra("aspectX", 4)
            cropIntent.putExtra("aspectY", 4)
            cropIntent.putExtra("scaleUpIfNeeded", true)
            cropIntent.putExtra("return-data", true)

            startActivityForResult(cropIntent, 1)
        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && resultCode == RESULT_OK) {
            cropImage()
        } else if (requestCode == 2) {
            if (data != null) {
                uri = data.data!!
                cropImage()
            }
        } else if (requestCode == 1) {
            if (data != null) {
                val bundle = data.extras
                val bitmap = bundle!!.getParcelable<Bitmap>("data")
                binding.imgResult.setImageBitmap(bitmap)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            RequestPermissionCode ->
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this@NextStepActivity, "Permission Granted", Toast.LENGTH_SHORT).show()
                }
                else {
                    Toast.makeText(this@NextStepActivity, "Permission Denied", Toast.LENGTH_SHORT).show()
                }
        }
    }

    companion object {
        const val RequestPermissionCode = 111
    }
}