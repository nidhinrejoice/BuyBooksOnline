package com.forthcode.customerapp.home.data

import com.forthcode.customerapp.api.ApiService
import com.forthcode.customerapp.home.domain.IHomeRepository
import com.forthcode.customerapp.models.Category
import com.forthcode.customerapp.persistance.SharedPrefsHelper
import com.forthcode.customerapp.persistance.db.InventoryRepoDao
import com.google.gson.Gson
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val apiService: ApiService,
    private val sharedPrefsHelper: SharedPrefsHelper,
    private val inventoryRepoDao: InventoryRepoDao
) : IHomeRepository {

    var job: CompletableJob? = null
    val gson: Gson = Gson()


    fun cancelJobs() {
        job?.cancel()
    }

    override suspend fun getInventoryCategories(): Flow<List<Category>> {
        return flow {
            if (inventoryRepoDao.isItemCacheAvailable() == 0) {
                val pinCode = sharedPrefsHelper.get("pincode", "673001")
                val res = (apiService.refreshMenu(pinCode))
                if (res.success == true) {
                    inventoryRepoDao.saveItems(res.items)
                    inventoryRepoDao.saveCategory(res.categories)
                }
            }
            emit(inventoryRepoDao.allCategories())
        }
    }

    override suspend fun getPincode(): Flow<String?> {
        return flow {
//            if(sharedPrefsHelper.hasKey("pincode")){
            emit(sharedPrefsHelper.get("pincode", "673010"))
//            }else{
//                throw Exception("No pincode set")
//            }
        }
    }
}