package com.example.abstractapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.abstractapp.databinding.ActivityHomeBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val modalBottomSheet = ModalBottomSheet()

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.topAppBar.setNavigationOnClickListener {
            binding.drawerLayout.open()
        }

        binding.navDrawer.setNavigationItemSelectedListener { menuItem ->
            // Handle menu item selected TODO: properly figure out this routing eventually
            menuItem.isChecked = true
            binding.drawerLayout.close()
            true
        }

        binding.newButton.setOnClickListener {
            val modalBottomSheetViews = R.layout.modal_bottom_sheet_content
            modalBottomSheet.show(supportFragmentManager, ModalBottomSheet.TAG)
        }
    }
}

// TODO: figure out a better home for this code
// credit: https://material.io/components/sheets-bottom/android#modal-bottom-sheet
class ModalBottomSheet : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.modal_bottom_sheet_content, container, false)

        //TODO: add the actions for new folder and new rule
        val noteButton = view.findViewById<Button>(R.id.note_button)
        noteButton.setOnClickListener {
            val intent = Intent(getActivity(), NoteActivity::class.java)
            startActivity(intent)
        }

        return view
    }

    companion object {
        const val TAG = "ModalBottomSheet"
    }
}