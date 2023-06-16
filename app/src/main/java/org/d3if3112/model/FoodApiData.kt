package org.d3if3112.model

data class FoodApiData(
    val name: String,
    val calories: Double,
    val serving_size_g: Double,
    val fat_total_g: Double,
    val fat_saturated_g: Double,
    val protein_g: Double,
    val sodium_mg: Double,
    val potassium_mg: Double,
    val cholesterol_mg: Double,
    val carbohydrates_total_g: Double,
    val fiber_g: Double,
    val sugar_g: Double
)
