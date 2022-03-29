package com.forthcode.customerapp.home.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.forthcode.customerapp.databinding.ActivityHomeBinding
import com.forthcode.customerapp.models.Category
import com.forthcode.customerapp.utils.ToastUtils
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_home.*
import javax.inject.Inject

class HomeActivity : AppCompatActivity(), LifecycleOwner {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: HomeViewModel by viewModels { viewModelFactory }
    private lateinit var binding: ActivityHomeBinding
    private lateinit var categoryAdapter: CategoryAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.pincode.observe(this, this::setPincode)
        viewModel.categories.observe(this, this::showCategories)
        viewModel.getPincode()
    }

    private fun showCategories(categories: List<Category>) {
        categoryAdapter = CategoryAdapter(applicationContext, categories)
        categoryAdapter.onItemClick = this::onCategoryClicked
        categories_grid_view.adapter = categoryAdapter;
        val layoutManager =
            LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
        layoutManager.scrollToPosition(0)
        categories_grid_view.layoutManager = layoutManager
    }

    private fun setPincode(pincode: String) {
        binding.pincode.setText("673001")
    }

    private fun onCategoryClicked(category: Category) {
        ToastUtils.makeToast(applicationContext, category.catName)
    }
}