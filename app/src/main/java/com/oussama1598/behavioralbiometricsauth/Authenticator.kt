package com.oussama1598.behavioralbiometricsauth

import com.github.kittinunf.fuel.Fuel
import org.json.JSONObject

class Authenticator {
    private val orientationHistogram: Array<Double> = Array<Double>(4) { 0.0 }
    private val orientationTimeHistogram: Array<Double> = Array<Double>(4) { 0.0 }
    private val keystrokesDurationHistogram: Array<Double> = Array<Double>(52) { 0.0 }
    private var keystrokeLatency: Double = 0.0
    private val distributionOfActionsOnTheScreenHistogram: Array<Double> = Array<Double>(9) { 0.0 }
    private val movementDirectionHistogram: Array<Double> = Array<Double>(8) { 0.0 }
    private val averageSpeedPerMovementDirectionHistogram: Array<Double> = Array<Double>(8) { 0.0 }
    private val travelDistanceHistogram: Array<Double> = Array<Double>(9) { 0.0 }
    private val extremeMovementSpeedRelativeToTravelDistanceHistogram: Array<Double> =
        Array<Double>(9) { 0.0 }
    private val movementElapsedTimeHistogram: Array<Double> = Array<Double>(9) { 0.0 }

    private val averageMovementSpeedRelativeToTravelDistance: Array<Double> =
        Array<Double>(9) { 0.0 }

    fun setOrientationHistogramData(list: List<Double>) {
        for ((index, value) in list.withIndex()) {
            orientationHistogram[index] = value
        }
    }

    fun setOrientationTimeHistogramData(list: List<Double>) {
        for ((index, value) in list.withIndex()) {
            orientationTimeHistogram[index] = value
        }
    }

    fun setKeystrokesDurationHistogramData(list: List<Double>) {
        for ((index, value) in list.withIndex()) {
            keystrokesDurationHistogram[index] = value
        }
    }

    fun setKeystrokeLatency(latency: Double) {
        keystrokeLatency = latency
    }

    fun setDistributionOfActionsOnTheScreenHistogramData(list: List<Double>) {
        for ((index, value) in list.withIndex()) {
            distributionOfActionsOnTheScreenHistogram[index] = value
        }
    }

    fun setMovementDirectionHistogramData(list: List<Double>) {
        for ((index, value) in list.withIndex()) {
            movementDirectionHistogram[index] = value
        }
    }

    fun setAverageSpeedPerMovementDirectionHistogramData(list: List<Double>) {
        for ((index, value) in list.withIndex()) {
            averageSpeedPerMovementDirectionHistogram[index] = value
        }
    }

    fun setTravelDistanceHistogramData(list: List<Double>) {
        for ((index, value) in list.withIndex()) {
            travelDistanceHistogram[index] = value
        }
    }

    fun setExtremeMovementSpeedRelativeToTravelDistanceHistogramData(list: List<Double>) {
        for ((index, value) in list.withIndex()) {
            extremeMovementSpeedRelativeToTravelDistanceHistogram[index] = value
        }
    }

    fun setMovementElapsedTimeHistogramData(list: List<Double>) {
        for ((index, value) in list.withIndex()) {
            movementElapsedTimeHistogram[index] = value
        }
    }

    fun setAverageMovementSpeedRelativeToTravelDistanceData(list: List<Double>) {
        for ((index, value) in list.withIndex()) {
            averageMovementSpeedRelativeToTravelDistance[index] = value
        }
    }

    fun authenticate(userId: String, myCallback: (data: JSONObject) -> Unit) {
        val sliceData: MutableList<Double> = MutableList<Double>(0) { 0.0 }

        for (value in orientationHistogram) {
            sliceData.add(value)
        }

        for (value in orientationTimeHistogram) {
            sliceData.add(value)
        }

        for (value in keystrokesDurationHistogram) {
            sliceData.add(value)
        }

        sliceData.add(keystrokeLatency)

        for (value in distributionOfActionsOnTheScreenHistogram) {
            sliceData.add(value)
        }

        for (value in movementDirectionHistogram) {
            sliceData.add(value)
        }

        for (value in averageSpeedPerMovementDirectionHistogram) {
            sliceData.add(value)
        }

        for (value in travelDistanceHistogram) {
            sliceData.add(value)
        }

        for (value in extremeMovementSpeedRelativeToTravelDistanceHistogram) {
            sliceData.add(value)
        }

        for (value in movementElapsedTimeHistogram) {
            sliceData.add(value)
        }

        for (value in averageMovementSpeedRelativeToTravelDistance) {
            sliceData.add(value)
        }

        val json = JSONObject()
        json.put("user_id", userId)
        json.put("slice_data", sliceData.joinToString(separator = ","))

        Fuel.post("http://192.168.1.11:8000/authenticate")
            .body(json.toString())
            .responseString() { _, _, result ->
                val data = JSONObject(result.get())
                myCallback.invoke(data)
            }
    }
}