package com.nandra.covid19id.utils

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import io.reactivex.Maybe


fun observeSingleValueEvent(query: Query): Maybe<DataSnapshot> {
    return Maybe.create { emitter ->
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    emitter.onSuccess(dataSnapshot)
                } else {
                    emitter.onComplete()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                if (!emitter.isDisposed) emitter.onError(Exception(error.message))
            }
        })
    }
}