package com.innovativetools.ai.myaiassistant.data.model

enum class GPTModel(val model: String, val maxTokens: Int) {
    gpt4("gpt-4-0613", 3000),
    gpt35Turbo("gpt-3.5-turbo-0301", 4000),
    davinci("text-davinci-003", 4000),
}