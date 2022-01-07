package com.example.abstractapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.abstractapp.databinding.ActivityNoteBinding

class NoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNoteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)
    }
}