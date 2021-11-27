package lab6.multithreading.lab6_multithreading_task2

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.graphics.BitmapFactory
import androidx.lifecycle.MutableLiveData
import lab6.multithreading.lab6_multithreading_task2.databinding.ActivityMainBinding
import java.net.URL
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {
    private lateinit var executor: ExecutorService
    private lateinit var binding: ActivityMainBinding
    private val bitmap = MutableLiveData<Bitmap>()
    private val newURL = URL("https://www.aziko.ru/images/NRc0e18c39b2399be3a56b8c64240db6da.jpg")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        downloadImage(newURL)
        bitmap.observe(this) { value ->
            if (value != null) {
                binding.imageView.setImageBitmap(value)
            }
        }
    }

    private fun downloadImage(URL: URL) {
        executor = Executors.newFixedThreadPool(1)
        executor.execute {
            val mIconVal = BitmapFactory.decodeStream(URL.openConnection().getInputStream())
            bitmap.postValue(mIconVal)
        }
    }

    override fun onDestroy() {
        executor.shutdown()
        super.onDestroy()
    }
}