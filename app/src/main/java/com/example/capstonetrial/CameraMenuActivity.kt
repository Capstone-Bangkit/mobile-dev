package com.example.capstonetrial

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import androidx.lifecycle.ViewModelProvider
import com.example.capstonetrial.databinding.ActivityCameraMenuBinding
import com.example.capstonetrial.detail.DetailActivity
import com.example.capstonetrial.detail.DetailViewModel
import com.example.capstonetrial.ml.Model
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.File


class CameraMenuActivity : AppCompatActivity() {

    private lateinit var binding:ActivityCameraMenuBinding
    private var getFile: File? = null
    private lateinit var bitmap: Bitmap
    private lateinit var gambar: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityCameraMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val extra = intent.extras
        if(extra!=null){
            val image = File(extra.getString("preview"))
            getFile = image
            binding.previewGambar.setImageBitmap(BitmapFactory.decodeFile(image.path))

            bitmap = BitmapFactory.decodeFile(image.path)
        }
        binding.buttonScan.setOnClickListener{
            imageClassification()
        }

        supportActionBar?.setTitle("Scan Your Equipment")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }

        return super.onOptionsItemSelected(item)
    }

    fun imageClassification(){
        if (getFile!=null){

            var imageProcessor = ImageProcessor.Builder().add(ResizeOp(256,256,ResizeOp.ResizeMethod.BILINEAR)).build()

            var tensorImage = TensorImage(DataType.FLOAT32)
            tensorImage.load(bitmap)

            tensorImage = imageProcessor.process(tensorImage)

            val model = Model.newInstance(this)

            // Creates inputs for reference.
            val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 256, 256, 3), DataType.FLOAT32)
            inputFeature0.loadBuffer(tensorImage.buffer)

            // Runs model inference and gets result.
            val outputs = model.process(inputFeature0)
            val outputFeature0 = outputs.outputFeature0AsTensorBuffer.floatArray

            var prediksi = 0
            outputFeature0.forEachIndexed { index, fl ->
                if(outputFeature0[prediksi] < fl){
                    prediksi = index
                }
            }

            val viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
                DetailViewModel::class.java)
            viewModel.getEquipmentDetail(prediksi)

            viewModel.isLoading.observe(this){
                if(it){
                    binding.progressBar.visibility = View.VISIBLE
                }else{
                    binding.progressBar.visibility = View.GONE
                }
            }
            viewModel.listEquipment.observe(this){equipment ->
                val data = equipment.payload
                binding.hasilScan.text = data[0].name?.replace("_", " ")?.uppercase()
                binding.buttonHow.setOnClickListener{
                    val intent = Intent(this, DetailActivity::class.java)
                    intent.putExtra("id", prediksi)
                    startActivity(intent)
                }
                binding.buttonHow.visibility = View.VISIBLE
                Log.d("test aja", equipment.payload.toString())
            }


            // Releases model resources if no longer used.
            model.close()

        }
    }
}