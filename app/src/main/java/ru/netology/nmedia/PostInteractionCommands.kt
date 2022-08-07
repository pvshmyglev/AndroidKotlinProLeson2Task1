package ru.netology.nmedia

interface PostInteractionCommands {

    fun onLike(post: Post)

    fun onShare(post: Post)

    fun onRemove(post: Post)

    fun onEditPost(post: Post)

    fun onOpenPost(post: Post)

    fun onSaveContent(newContent: String)

    fun onCancelEdit()

    fun onCancelOpen()

}
