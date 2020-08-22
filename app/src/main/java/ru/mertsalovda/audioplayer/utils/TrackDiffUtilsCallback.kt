package ru.mertsalovda.audioplayer.utils

import androidx.recyclerview.widget.DiffUtil
import ru.mertsalovda.audioplayer.ui.model.Track

class TrackDiffUtilsCallback(
    private val oldList: List<Track>,
    private val newList: List<Track>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldMessage = oldList[oldItemPosition]
        val newMessage = newList[newItemPosition]
        return oldMessage.path == newMessage.path
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldMessage = oldList[oldItemPosition]
        val newMessage = newList[newItemPosition]
        return oldMessage.title == newMessage.title
                && oldMessage.duration == newMessage.duration
                && oldMessage.path == newMessage.path

    }
}