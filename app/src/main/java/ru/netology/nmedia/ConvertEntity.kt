package ru.netology.nmedia

internal fun PostEntity.toPost() = Post (

    id = id,
    author = author,
    content = content,
    video = video,
    publishedDate = publishedDate,
    likeByMe = likeByMe,
    countLikes = countLikes,
    countShare = countShare,
    countVisibility = countVisibility

)

internal fun Post.toPostEntity() = PostEntity (

    id = id,
    author = author,
    content = content,
    video = video,
    publishedDate = publishedDate,
    likeByMe = likeByMe,
    countLikes = countLikes,
    countShare = countShare,
    countVisibility = countVisibility

)