package ru.mertsalovda.audioplayer.utils

import java.io.File

class SearchSoundsUtils() {
    companion object {
        fun getFiles(rootFile: File, fileList: MutableList<File>): MutableList<File> {
            if (rootFile.isDirectory) {
                val directoryFiles = rootFile.listFiles()
                if (directoryFiles != null) {
                    for (file in directoryFiles) {
                        if (file.isDirectory) {
                            getFiles(file, fileList)
                        } else {
                            if (file.name.toLowerCase().endsWith(".mp3") ||
                                file.name.toLowerCase().endsWith(".wav") ||
                                file.name.toLowerCase().endsWith(".aiff") ||
                                file.name.toLowerCase().endsWith(".ape") ||
                                file.name.toLowerCase().endsWith(".flac") ||
                                file.name.toLowerCase().endsWith(".ogg")
                            ) {
                                fileList.add(file)
                            }
                        }
                    }
                }
            }
            return fileList
        }
    }
}