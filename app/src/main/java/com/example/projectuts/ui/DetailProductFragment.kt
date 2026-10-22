package com.example.projectuts.ui

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.projectuts.model.Product
import com.example.projectuts.model.Review
import com.example.projectuts.databinding.FragmentDetailProductBinding
import com.google.gson.Gson
import java.util.Locale

class DetailProductFragment : Fragment() {

    private var _binding: FragmentDetailProductBinding? = null
    private val binding get() = _binding!!

    companion object {
        const val EXTRA_PRODUCT = "extra_product"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val json = arguments?.getString(EXTRA_PRODUCT) ?: return
        val product = Gson().fromJson(json, Product::class.java)

        tampilkanGambar(product)

        val hargaAkhir = product.price * (100 - product.discountPercentage) / 100

        binding.tvTitle.text = product.title
        binding.tvPrice.text = "\$%.2f".format(Locale.US, hargaAkhir)
        binding.tvId.text = product.id.toString()
        binding.tvBrand.text = product.brand ?: "-"
        binding.tvCategory.text = product.category
        binding.tvTags.text = product.tags?.joinToString(", ") ?: "-"

        val d = product.dimensions
        binding.tvDimensions.text =
            if (d != null) "${d.width} x ${d.height} x ${d.depth}" else "-"

        binding.tvDiscount.text = "${product.discountPercentage}%"
        binding.tvRating.text = product.rating.toString()
        binding.tvStock.text = product.stock.toString()
        binding.tvAvailability.text = product.availabilityStatus ?: "-"
        binding.tvWarranty.text = product.warrantyInformation ?: "-"
        binding.tvShipping.text = product.shippingInformation ?: "-"
        binding.tvReturn.text = product.returnPolicy ?: "-"
        binding.tvDescription.text = product.description

        tampilkanReview(product.reviews)
    }

    private fun tampilkanGambar(product: Product) {
        val density = resources.displayMetrics.density
        val ukuran = (220 * density).toInt()
        val jarak = (8 * density).toInt()

        val daftarGambar =
            if (product.images.isNotEmpty()) product.images else listOf(product.thumbnail)

        for (url in daftarGambar) {
            val imageView = ImageView(requireContext()).apply {
                layoutParams = LinearLayout.LayoutParams(ukuran, ukuran).apply {
                    setMargins(jarak, 0, jarak, 0)
                }
                scaleType = ImageView.ScaleType.CENTER_INSIDE
            }
            Glide.with(this)
                .load(url)
                .placeholder(R.drawable.ic_menu_gallery)
                .error(R.drawable.ic_menu_report_image)
                .into(imageView)
            binding.llImages.addView(imageView)
        }
    }

    private fun tampilkanReview(reviews: List<Review>?) {
        val density = resources.displayMetrics.density
        val jarak = (12 * density).toInt()

        binding.llReviews.removeAllViews()

        if (reviews.isNullOrEmpty()) {
            binding.llReviews.addView(TextView(requireContext()).apply {
                text = "Belum ada ulasan."
            })
            return
        }

        for (r in reviews) {
            val tv = TextView(requireContext()).apply {
                text = "★ ${r.rating ?: "-"}  •  ${r.reviewerName ?: "-"}\n" +
                        "${r.comment ?: ""}\n" +
                        "${r.date?.take(10) ?: ""}"
                setPadding(0, 0, 0, jarak)
            }
            binding.llReviews.addView(tv)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}