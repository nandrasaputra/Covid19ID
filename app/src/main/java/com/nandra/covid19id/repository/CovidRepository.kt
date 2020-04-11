package com.nandra.covid19id.repository

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

object CovidRepository {
    private val firebaseDatabaseReference = FirebaseDatabase.getInstance().reference

    fun getInformationIntroductionDatabaseReference() : DatabaseReference {
        return firebaseDatabaseReference.child("content/covid_introduction")
    }

    fun getInformationOtherDatabaseReference() : DatabaseReference {
        return firebaseDatabaseReference.child("content/covid_other")
    }

    fun getInformationLamanDatabaseReference() : DatabaseReference {
        return firebaseDatabaseReference.child("content/covid_laman")
    }
}