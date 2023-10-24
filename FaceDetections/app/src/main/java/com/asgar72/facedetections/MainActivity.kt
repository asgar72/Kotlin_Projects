package com.asgar72.facedetections

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.asgar72.facedetections.databinding.ActivityMainBinding
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetector
import com.google.mlkit.vision.face.FaceDetectorOptions

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()

        //open camera
        binding.btnCamera.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            if (intent.resolveActivity(packageManager) != null) {
                startActivityForResult(intent, 123)
            } else {
                Toast.makeText(this, "Oops something went wrong!", Toast.LENGTH_SHORT).show()
            }
        }
    }


    //then receive camera info (about photos)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 123 && resultCode == RESULT_OK) {
            val extras = data?.extras
            val bitmap = extras?.get("data") as? Bitmap
            if (bitmap != null) {
                detectFace(bitmap) //image in bitmap format
            }
        }
    }


    fun detectFace(bitmap: Bitmap) {
        // High-accuracy landmark detection and face classification
        val highAccuracyOpts = FaceDetectorOptions.Builder()
            .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
            .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
            .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
            .build()

        val detector = FaceDetection.getClient(highAccuracyOpts)
        val image = InputImage.fromBitmap(bitmap, 0)

        //images process
        val result = detector.process(image)
            .addOnSuccessListener { faces ->
                // Task completed successfully, our face is successfully detected.
                var resultText = ""
                var i = 1
                for (face in faces) {
                    resultText = "Face Number : $i" +
                            "\nSmile : ${face.smilingProbability?.times(100)}%" +
                            "\nLeft Eye open : ${face.leftEyeOpenProbability?.times(100)}%" +
                            "\nRight Eye open : ${face.rightEyeOpenProbability?.times(100)}%"
                    i++
                }
                if(faces.isEmpty()){
                    Toast.makeText(this,"Oops No face detected",Toast.LENGTH_SHORT).show()
                }
                // result show in dialog box
                else{
                    //Toast.makeText(this,resultText,Toast.LENGTH_LONG).show()
                    val builder = AlertDialog.Builder(this)
                    val dialogView = LayoutInflater.from(this).inflate(R.layout.fragment_resultdialog, null)

                    val dialogMessage = dialogView.findViewById<TextView>(R.id.result_text_view)
                    val okButton = dialogView.findViewById<Button>(R.id.result_ok_button)

                    dialogMessage.text = resultText

                    builder.setView(dialogView)
                    val dialog = builder.create()

                    okButton.setOnClickListener {
                        dialog.dismiss()
                    }
                    dialog.show()
                }
            }
            .addOnFailureListener { e ->
                // Task failed with an exception, face detection is failed.
                Toast.makeText(this,"Something Wrong",Toast.LENGTH_SHORT).show()

            }


    }
}













