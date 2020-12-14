package com.example.surveyapp.database.dataTables

import android.media.Image

data class Photo(
    var photoID: Int,
    //TODO add a foreign key
    var surveyID: Int,
    var photo: Image,
    var comment: String
)
