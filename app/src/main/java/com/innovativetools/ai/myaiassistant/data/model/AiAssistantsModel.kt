package com.innovativetools.ai.myaiassistant.data.model

data class AiAssistantsModel(
    var title: String = "",
    var assistant: List<AiAssistantModel>,
)

//data class AiAssistantModel(
//    val category: String,
//    val name: String,
//    val imgUrl: String,
//    val description: String,
//    val role: String
//)

data class AiAssistantModel(
    var image: Int,
    var name: String = "",
    var description: String = "",
    var role: String = "",
    var instruction: String = "",
    val exampleList1: List<String>
//    val exampleLis2: List<String>
)







//    [
//  {
//    "id": "Intriguing Caption",
//    "category": "New Feature",
//    "imageLink": "img_intriguing_caption.png",
//    "title": "Intriguing Caption",
//    "description": "Engaging caption that sparks conversation",
//    "questions": [],
//    "prompt": "Write an intriguing caption that complements the image"
//  }]