package com.example.projectuts.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectuts.response.ProductResponse
import com.example.projectuts.R
import com.example.projectuts.adapter.ProductAdapter
import com.example.projectuts.databinding.FragmentProductListBinding
import com.example.projectuts.retrofit.RetrofitClient
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashboardFragment : Fragment() {

    private var _binding: FragmentProductListBinding? = null
    private val binding get() = _binding!!

    private lateinit var productAdapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductListBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        productAdapter = ProductAdapter()

        productAdapter.setOnItemClickCallback { product ->
            val bundle = Bundle().apply {
                putString(
                    DetailProductFragment.EXTRA_PRODUCT,
                    Gson().toJson(product)
                )
            }
            findNavController().navigate(R.id.action_dashboard_to_detail, bundle)
        }

        binding.rvProducts.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = productAdapter
        }

        binding.swipeRefresh.setOnRefreshListener {
            loadProducts()
        }

        loadProducts()
    }

    private fun loadProducts() {
        binding.progressBar.visibility = View.VISIBLE
        binding.tvError.visibility = View.GONE
        binding.swipeRefresh.isRefreshing = false

        RetrofitClient.instance.getProducts(25)
            .enqueue(object : Callback<ProductResponse> {

                override fun onResponse(
                    call: Call<ProductResponse>,
                    response: Response<ProductResponse>
                ) {
                    val currentBinding = _binding ?: return

                    currentBinding.progressBar.visibility = View.GONE
                    currentBinding.swipeRefresh.isRefreshing = false

                    if (response.isSuccessful) {
                        val products = response.body()?.products

                        if (products.isNullOrEmpty()) {
                            currentBinding.tvError.text =
                                "Data produk kosong."
                            currentBinding.tvError.visibility =
                                View.VISIBLE
                        } else {
                            productAdapter.submitList(products)
                            currentBinding.tvError.visibility =
                                View.GONE
                        }
                    } else {
                        currentBinding.tvError.text =
                            "Gagal mengambil data: ${response.code()}"
                        currentBinding.tvError.visibility =
                            View.VISIBLE
                    }
                }

                override fun onFailure(
                    call: Call<ProductResponse>,
                    t: Throwable
                ) {
                    val currentBinding = _binding ?: return

                    currentBinding.progressBar.visibility = View.GONE
                    currentBinding.swipeRefresh.isRefreshing = false
                    currentBinding.tvError.text =
                        "Koneksi gagal. Periksa internet."
                    currentBinding.tvError.visibility = View.VISIBLE
                }
            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}