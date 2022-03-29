package com.nidhin.customerapp.domain.usecases.homescreen

import com.nidhin.customerapp.commons.Resource
import com.nidhin.customerapp.data.remote.dto.InventoryData
import com.nidhin.customerapp.data.remote.dto.toItem
import com.nidhin.customerapp.domain.repository.HomeRepository
import com.nidhin.customerapp.presentation.homescreen.HomeScreenState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PopulateInventory @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(): Flow<Resource<HomeScreenState>> = flow {
        try {
            emit(Resource.Loading())
            var data: InventoryData = homeRepository.getLatestInventory()
            val items = data.items.map { it.toItem() }
            val categories = data.categories.map { it.toCategory() }
            val subCategories = data.subCategories.map { it.toSubCategory() }
            val sections = data.sections.map { it.toSection() }
            val razorpay = data.razorpay
            val store = data.store
            homeRepository.saveItems(items)
            homeRepository.saveCategories(categories)
            homeRepository.saveSubCategories(subCategories)
            homeRepository.saveSections(sections)
            homeRepository.saveRazorpay(razorpay)
            homeRepository.saveStore(store)
            val state = HomeScreenState(
                items = items,
                categories = categories,
                subcategories = subCategories,
                sections = sections,
                store = store,
                user = homeRepository.getUser(),
                bestSellingItems = homeRepository.getBestSellingItems(),
                loading = false,
                loadingMessage = ""
            )
            emit(Resource.Success(state))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Something went wrong"))
        } catch (e: IOException) {
            emit(Resource.Error("Internet not available"))
        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))
        }
    }
}

