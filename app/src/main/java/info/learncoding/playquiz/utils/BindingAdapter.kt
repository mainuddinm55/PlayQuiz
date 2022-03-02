package info.learncoding.playquiz.utils

import android.content.res.ColorStateList
import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.button.MaterialButton

@BindingAdapter("app:answer_stroke_color")
fun bindStrokeColor(button: MaterialButton, color: String?) {
    button.strokeColor = ColorStateList.valueOf(Color.parseColor(color ?: "#FFFFFF"))
}

@BindingAdapter("app:image_url")
fun bindImageUrl(imageView: ImageView, url: String?) {
    Log.d(imageView::class.java.simpleName, "bindImageUrl: $url")
    Glide.with(imageView)
        .load(url)
        .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
        .into(imageView)
}

@BindingAdapter("app:view_visibility")
fun bindButtonVisibility(view: View, content: String?) {
    view.visibility = if (content.isNullOrEmpty()) View.GONE else View.VISIBLE
}