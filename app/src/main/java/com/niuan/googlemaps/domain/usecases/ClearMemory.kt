package com.niuan.googlemaps.domain.usecases

import com.niuan.googlemaps.domain.repositories.Repository

class ClearMemory(private val repository: Repository) {

    suspend operator fun invoke() = repository.clearMemory()
}