package com.example.train.api

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class SNCFResponse(
    @SerializedName("journeys") val journeys: List<Journey>
) : Parcelable

@Parcelize
data class Journey(
    @SerializedName("departure_date_time") val departureDateTime: String,
    @SerializedName("arrival_date_time") val arrivalDateTime: String,
    @SerializedName("duration") val duration: Int,
    @SerializedName("nb_transfers") val nbTransfers: Int,
    @SerializedName("sections") val sections: List<Section>,
    @SerializedName("fare") val fare: Fare? = null,
    @SerializedName("tickets") val tickets: List<Ticket>? = null // ✅ Ajout des liens vers les billets
) : Parcelable

@Parcelize
data class Ticket(
    @SerializedName("href") val href: String? // ✅ URL du billet
) : Parcelable


@Parcelize
data class Section(
    @SerializedName("id") val id: String?,
    @SerializedName("departure_date_time") val departureDateTime: String?,
    @SerializedName("arrival_date_time") val arrivalDateTime: String?,
    @SerializedName("from") val from: StopPoint?,
    @SerializedName("to") val to: StopPoint?,
) : Parcelable

@Parcelize
data class StopPoint(
    @SerializedName("id") val id: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("label") val label: String?
) : Parcelable

@Parcelize
data class Fare(
    @SerializedName("found") val found: Boolean?, // ✅ Ajout de `found`
    @SerializedName("total") val total: FareTotal?
) : Parcelable


@Parcelize
data class FareTotal(
    @SerializedName("value") val value: String?,
    @SerializedName("currency") val currency: String? // ✅ Ajout de la devise (EUR)
) : Parcelable
