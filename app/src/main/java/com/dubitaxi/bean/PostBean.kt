package com.dubitaxi.bean
data class ErrorBean(
    var message: String
)

/*
class PostBean(
    var likeData: LikeData? = LikeData(),
    var commentData: CommentData? = CommentData()
) {
    data class LikeData(
        var postId: String? = null,
        val userId: ArrayList<String>? = arrayListOf()
    )

    data class CommentData(
        var commentId: String? = "",
        var comment: String? = "",
        var userprofile: String? = "",
        var userName: String? = "",
        var timestamp: String? = "",
        var userId: String? = "",
        var postId: String? = ""
    )
}*/
