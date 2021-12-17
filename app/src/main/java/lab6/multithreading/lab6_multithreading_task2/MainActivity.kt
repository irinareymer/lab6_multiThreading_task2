package lab6.multithreading.lab6_multithreading_task2

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.graphics.BitmapFactory
import androidx.lifecycle.MutableLiveData
import lab6.multithreading.lab6_multithreading_task2.databinding.ActivityMainBinding
import java.net.URL
import java.util.concurrent.Future

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var myApplication: MyApplication
    private lateinit var future: Future<*>
    private val bitmap = MutableLiveData<Bitmap>()
    private val newURL = URL("https://www.aziko.ru/images/NRc0e18c39b2399be3a56b8c64240db6da.jpg")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        myApplication = application as MyApplication
        future = downloadImage(newURL)
        bitmap.observe(this) { value ->
            if (value != null) {
                binding.imageView.setImageBitmap(value)
            }
        }
    }

    private fun downloadImage(URL: URL): Future<*> {
        return myApplication.getExecutor().submit{
            val mIconVal = BitmapFactory.decodeStream(URL.openConnection().getInputStream())
            bitmap.postValue(mIconVal)
        }
    }

    override fun onDestroy() {
        future.cancel(true)
        super.onDestroy()
    }
}