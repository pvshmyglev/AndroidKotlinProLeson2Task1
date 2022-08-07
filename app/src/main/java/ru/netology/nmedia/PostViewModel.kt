package ru.netology.nmedia

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.io.IOException
import kotlin.concurrent.thread

class PostViewModel (application: Application) : AndroidViewModel(application), PostInteractionCommands{

    private val repository : PostRepository = PostRepositoryHTTPImpl()
    //val data by repository::data

    private val _data = MutableLiveData(FeedModel())
    val data : LiveData<FeedModel>
        get() = _data

    private val emptyPost = Post(
        0,
        "",
        "",
        "",
        "",
        false,
        0,
        0,
        0
    )

    val editedPost = MutableLiveData(emptyPost)
    val openedPost = MutableLiveData(emptyPost)

    private val _postUpdated = SingleLiveEvent<Post>()

    val postUpdated: LiveData<Post>
        get() = _postUpdated

    fun updatedPost(post: Post) {

        if (post.id == 0) {

            loadPosts()

        } else {

            thread {

                val updatedPost = repository.getById(post.id)

                val dataPosts = data.value?.posts?.map { thisPost ->
                    if (thisPost.id == updatedPost.id)
                    {
                        updatedPost
                    } else {
                        thisPost
                    }
                }
                dataPosts?.let{
                    _data.postValue(FeedModel(posts = it, empty = it.isEmpty()))
                }

            }
        }

    }


    fun loadPosts() {

        thread {

            _data.postValue(FeedModel(loading = true))

            val result = try {

                val posts = repository.getAll()
                FeedModel(posts = posts, empty = posts.isEmpty())

            } catch (e: IOException) {

                FeedModel(error = true)

            }

            _data.postValue(result)

        }

    }

    private fun setObserveEditOpenPost(id: Int) {

        if (editedPost.value?.id != 0 && editedPost.value?.id == id) {

            data.value?.posts?.map { post ->
                if (post.id == editedPost.value?.id) { editedPost.value = post }
            }

        }

        if (openedPost.value?.id != 0 && openedPost.value?.id == id) {

            data.value?.posts?.map { post ->
                if (post.id == openedPost.value?.id) { openedPost.value = post }
            }

        }

    }

    override fun onLike(post: Post) {

        thread {
            repository.likeById(post.id)
            _postUpdated.postValue(post)
        }
        setObserveEditOpenPost(post.id)
    }

    override fun onShare(post: Post) {
        thread {
            repository.shareById(post.id)
            _postUpdated.postValue(post)
        }

        setObserveEditOpenPost(post.id)
    }

    override fun onRemove(post: Post) {

        thread {
            repository.removeById(post.id)
            _postUpdated.postValue(emptyPost)
        }

        onCancelEdit()
        onCancelOpen()

    }

    override fun onEditPost(post: Post) {

        editedPost.value = post

    }

    override fun onSaveContent(newContent: String) {

        val text = newContent.trim()

        editedPost.value?.let { thisEditedPost ->

            if (thisEditedPost.content != text) {

                if (thisEditedPost.id == 0) {
                    thread {

                        repository.saveNewPost(thisEditedPost.copy(content = text))
                        _postUpdated.postValue(emptyPost)

                    }
                } else {
                    thread {

                        val savedPost = thisEditedPost.copy(content = text)

                        repository.editPost(savedPost)
                        _postUpdated.postValue(savedPost)

                    }
                }

            }

            editedPost.value = emptyPost

            setObserveEditOpenPost(thisEditedPost.id)

        }


    }

    override fun onCancelEdit() {

        editedPost.value = emptyPost

    }

    override fun onOpenPost(post: Post) {

        openedPost.value = post

    }

    override fun onCancelOpen() {

        openedPost.value = emptyPost

    }

}
