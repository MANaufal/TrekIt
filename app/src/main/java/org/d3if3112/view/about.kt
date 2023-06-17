package org.d3if3112.view

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import org.d3if3112.R
import org.d3if3112.databinding.FragmentAboutBinding
import java.util.concurrent.Executors

class about : Fragment() {
    private lateinit var binding: FragmentAboutBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAboutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val executor = Executors.newSingleThreadExecutor()

        val handler = Handler(Looper.getMainLooper())

        var image: Bitmap? = null

        executor.execute {
            val imageUrl = "https://i.ytimg.com/vi/xBf5G4XSTa8/mqdefault.jpg"

            try {
                val enterImage = java.net.URL(imageUrl).openStream()

                image = BitmapFactory.decodeStream(enterImage)

                handler.post {
                    binding.urlImage.setImageBitmap(image)
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
    }
}
