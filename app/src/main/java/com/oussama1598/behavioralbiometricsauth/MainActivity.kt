package com.oussama1598.behavioralbiometricsauth

import android.app.AlertDialog
import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner

class MainActivity : AppCompatActivity() {
    private val auth = Authenticator()
    private val dataAdder = DataAdder()
    private var selectedDataType: String = "orientation"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val spinner: Spinner = findViewById(R.id.data_types_spinner)
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.data_types,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                selectedDataType = parent.getItemAtPosition(position).toString()
            }
        }
    }

    fun authenticate(view: View) {
        val progressDialog = ProgressDialog(this@MainActivity)
        progressDialog.setTitle("Authenticating")
        progressDialog.setMessage("Please wait")
        progressDialog.show()

        auth.setOrientationHistogramData(listOf<Double>(0.0, 0.0, 0.0, 0.0))
        auth.setOrientationTimeHistogramData(listOf<Double>(299983.0, 0.0, 0.0, 0.0))
        auth.setKeystrokesDurationHistogramData(
            listOf<Double>(
                -1.0,
                -1.0,
                -1.0,
                -1.0,
                -1.0,
                -1.0,
                -1.0,
                -1.0,
                -1.0,
                -1.0,
                -1.0,
                -1.0,
                -1.0,
                -1.0,
                -1.0,
                -1.0,
                -1.0,
                -1.0,
                -1.0,
                -1.0,
                -1.0,
                -1.0,
                -1.0,
                -1.0,
                -1.0,
                -1.0,
                -1.0,
                -1.0,
                -1.0,
                -1.0,
                -1.0,
                -1.0,
                -1.0,
                -1.0,
                -1.0,
                -1.0,
                -1.0,
                -1.0,
                -1.0,
                -1.0,
                -1.0,
                -1.0,
                -1.0,
                -1.0,
                -1.0,
                -1.0,
                -1.0,
                -1.0,
                -1.0,
                -1.0,
                -1.0,
                -1.0
            )
        )
        auth.setKeystrokeLatency(-1.0)
        auth.setDistributionOfActionsOnTheScreenHistogramData(
            listOf<Double>(
                11.0,
                2.0,
                34.0,
                555.0,
                4.0,
                165.0,
                508.0,
                13.0,
                12.0
            )
        )
        auth.setMovementDirectionHistogramData(
            listOf<Double>(
                1.0,
                0.0,
                0.0,
                2.0,
                2.0,
                0.0,
                2.0,
                2.0
            )
        )
        auth.setAverageSpeedPerMovementDirectionHistogramData(
            listOf<Double>(
                0.0696972,
                0.0,
                0.0,
                0.722615,
                0.033378,
                0.0,
                0.0448444,
                0.366227
            )
        )
        auth.setTravelDistanceHistogramData(
            listOf<Double>(
                7.0,
                2.0,
                0.0,
                0.0,
                0.0,
                0.0,
                0.0,
                0.0,
                0.0
            )
        )
        auth.setExtremeMovementSpeedRelativeToTravelDistanceHistogramData(
            listOf<Double>(
                0.0819443,
                1.36329,
                0.0,
                0.0,
                0.0,
                0.0,
                0.0,
                0.0,
                0.0
            )
        )
        auth.setMovementElapsedTimeHistogramData(
            listOf<Double>(
                3.0,
                6.0,
                0.0,
                0.0,
                0.0,
                0.0,
                0.0,
                0.0,
                0.0
            )
        )
        auth.setAverageMovementSpeedRelativeToTravelDistanceData(
            listOf<Double>(
                0.0522585,
                1.01901,
                0.0,
                0.0,
                0.0,
                0.0,
                0.0,
                0.0,
                0.0
            )
        )

        auth.authenticate("user_2") {
            println(it["status"])

            progressDialog.hide()

            if (it["status"] as Boolean) {
                if (it["authentication"] as Boolean) {
                    val builder = AlertDialog.Builder(this@MainActivity)
                    builder.setTitle("Success")
                    builder.setMessage("Authentication Was successful for user_2")

                    val dialog: AlertDialog = builder.create()
                    dialog.show()
                } else {
                    val builder = AlertDialog.Builder(this@MainActivity)
                    builder.setTitle("Alert")
                    builder.setMessage("Authentication Was not successful for user_2")

                    val dialog: AlertDialog = builder.create()
                    dialog.show()
                }
            } else {
                val builder = AlertDialog.Builder(this@MainActivity)
                builder.setTitle("Error")
                builder.setMessage(it["error"] as String)

                val dialog: AlertDialog = builder.create()
                dialog.show()
            }
        }
    }

    fun addData(view: View) {
        val progressDialog = ProgressDialog(this@MainActivity)
        progressDialog.setTitle("Adding Data for $selectedDataType")
        progressDialog.setMessage("Please wait")
        progressDialog.show()

        var raw_data: String = ""

        if (selectedDataType == "orientation")
            raw_data = "1594917728409 1"
        else if (selectedDataType == "touch")
            raw_data = "1594917726263 0 650 711 1"
        else if (selectedDataType == "keyboard")
            raw_data = "1594917184419 release 50"

        dataAdder.addDataToUser("user_1", selectedDataType, raw_data) {
            println(it["status"])

            progressDialog.hide()

            if (it["status"] as Boolean) {
                val builder = AlertDialog.Builder(this@MainActivity)
                builder.setTitle("Success")
                builder.setMessage("Data Was added successful for user_2")

                val dialog: AlertDialog = builder.create()
                dialog.show()
            } else {
                val builder = AlertDialog.Builder(this@MainActivity)
                builder.setTitle("Error")
                builder.setMessage(it["error"] as String)

                val dialog: AlertDialog = builder.create()
                dialog.show()
            }
        }
    }
}