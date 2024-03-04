package com.innovativetools.ai.myaiassistant.data.model

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import com.innovativetools.ai.myaiassistant.domain.repository.PreferenceRepository

data class TextCompletionsParam(
    @SerializedName("prompt")
    val promptText: String = "",
    @SerializedName("temperature")
    val temperature: Double = 0.9,
    @SerializedName("top_p")
    val topP: Double = 1.0,
    @SerializedName("n")
    val n: Int = 1,
    @SerializedName("stream")
    var stream: Boolean = true,
    @SerializedName("maxTokens")
    val maxTokens: Int = 2000,
    @SerializedName("model")
    val model: GPTModel,
//  val model: GPTModel = GPTModel.gpt35Turbo,
    @SerializedName("messages")
    val messagesTurbo: List<MessageTurbo> = emptyList(),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as TextCompletionsParam
        if (promptText != other.promptText) return false
        if (temperature != other.temperature) return false
        if (topP != other.topP) return false
        if (n != other.n) return false
        if (stream != other.stream) return false
        if (maxTokens != other.maxTokens) return false
        if (model != other.model) return false
        if (messagesTurbo != other.messagesTurbo) return false
        return true
    }
    override fun hashCode(): Int {
        var result = promptText.hashCode()
        result = 31 * result + temperature.hashCode()
        result = 31 * result + topP.hashCode()
        result = 31 * result + n
        result = 31 * result + stream.hashCode()
        result = 31 * result + maxTokens
        result = 31 * result + model.hashCode()
        result = 31 * result + messagesTurbo.hashCode()
        return result
    }

//    val isTurbo: Boolean
//        get() = model == GPTModel.gpt4 || model == GPTModel.gpt35Turbo

    val isTurbo: Boolean
        get() = model == GPTModel.gpt35Turbo || model == null
}


fun TextCompletionsParam.toJson(): JsonObject {
    val json = JsonObject()
    json.addProperty("temperature", temperature)
    json.addProperty("stream", stream)
    json.addProperty("model", model.model)

    val jsonArray = JsonArray()
    for (message in messagesTurbo) jsonArray.add(message.toJson())
    json.add("messages", jsonArray)

    return json
}

//val isTurbo: Boolean
//    get() = model == GPTModel.gpt4


//val modelId = model?.model ?: GPTModel.gpt35Turbo.model
//json.addProperty("model", modelId)
//    if (isTurbo) {
//        val jsonArray = JsonArray()
//        for (message in messagesTurbo) jsonArray.add(message.toJson())
//        json.add("messages", jsonArray)
//    } else {
//        // When not using GPT-3.5 Turbo, the prompt needs to be a string.
//        json.addProperty("prompt", promptText)
//    }
//
//    // Only include the prompt if it’s not for GPT-3.5 Turbo, or if `model` is null.
//    if (model == null || model != GPTModel.gpt35Turbo) {
//        json.addProperty("prompt", promptText)
//    }
//
//    return json
//}


