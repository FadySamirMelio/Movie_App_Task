package com.movie.network.model

import com.google.gson.annotations.SerializedName

class ErrorModel(
    @SerializedName("Message") override val message: String?,
    @SerializedName("name") var name: String? = null,
    var code: Int = 0,
) : Exception() {

    override fun toString(): String {
        var error = ""
        if (code != 0)
            error += "$code "
        else if (message != null && message.isNotEmpty())
            error += message
        else if (name != null)
            error += name
        return error.trim()
    }
}