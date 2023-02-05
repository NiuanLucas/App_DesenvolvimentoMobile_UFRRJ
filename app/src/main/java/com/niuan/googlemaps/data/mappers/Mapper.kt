package com.niuan.googlemaps.data.mappers

interface Mapper<I, O> {
    fun map(input : I) : O
}