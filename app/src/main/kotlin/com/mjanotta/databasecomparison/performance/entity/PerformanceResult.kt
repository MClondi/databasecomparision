package com.mjanotta.databasecomparison.performance.entity

import org.parceler.Parcel
import org.parceler.ParcelConstructor

@Parcel(Parcel.Serialization.BEAN)
data class PerformanceResult @ParcelConstructor constructor(var saveTime: Long, var readTime: Long, var deleteTime: Long)