package pl.sjmprofil.animaltinder.models

data class User(
    val firstName: String,
    val lastName: String,
    val email: String,
    val info: String,
    val imageUrl: String = "http://d3g9pb5nvr3u7.cloudfront.net/authors/539a28913f3c0fd71ed4e43d/2131300937/256.jpg"
) {
}