package vn.ttube.audio

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val padding = (24 * resources.displayMetrics.density).toInt()
        val box = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER_HORIZONTAL
            setPadding(padding, padding, padding, padding)
        }
        val heading = TextView(this).apply {
            text = "TTube Audio\nNghe audio trên Android Auto"
            textSize = 22f
            setPadding(0, 0, 0, padding)
        }
        val title = EditText(this).apply { hint = getString(R.string.source_title) }
        val url = EditText(this).apply {
            hint = getString(R.string.source_url)
            inputType = android.text.InputType.TYPE_TEXT_VARIATION_URI
        }
        val save = Button(this).apply { text = getString(R.string.save_source) }
        val youtubeMusic = Button(this).apply { text = getString(R.string.open_youtube_music) }
        box.addView(heading)
        box.addView(title, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        box.addView(url, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        box.addView(save, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        box.addView(youtubeMusic, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        setContentView(box)

        save.setOnClickListener {
            val name = title.text.toString().trim()
            val address = url.text.toString().trim()
            if (name.isEmpty() || !address.startsWith("http")) {
                Toast.makeText(this, "Hãy nhập tên và URL http(s) hợp lệ.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            getSharedPreferences(Catalog.PREFS, MODE_PRIVATE).edit()
                .putString(Catalog.KEY_TITLE, name)
                .putString(Catalog.KEY_URL, address)
                .apply()
            sendBroadcast(Intent(Catalog.ACTION_RELOAD).setPackage(packageName))
            Toast.makeText(this, getString(R.string.saved), Toast.LENGTH_LONG).show()
        }
        youtubeMusic.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://music.youtube.com")))
        }
    }
}
