package com.nidhin.customerapp.data.remote.dto

import com.nidhin.customerapp.domain.model.Section

data class SectionDto(
    val catIds: List<Int>,
    val section: String
) {
    fun toSection(): Section {
        return Section(section = section, catIds = catIds)
    }
}