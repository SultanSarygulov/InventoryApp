package com.example.inventoryapplication.presentation.archive

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.inventoryapplication.domain.Goods
import com.example.inventoryapplication.databinding.BottomSheetDialogBinding
import com.example.inventoryapplication.databinding.FragmentArchiveBinding
import com.example.inventoryapplication.presentation.tools.IGoods
import com.example.inventoryapplication.presentation.RecyclerAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArchiveFragment : Fragment(), IGoods, SearchView.OnQueryTextListener {

    private lateinit var binding: FragmentArchiveBinding
    private val mArchiveViewModel: ArchiveViewModel by viewModels()
    private lateinit var adapter: RecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentArchiveBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
        setLiveDataObserver()


        binding.searchBar.isSubmitButtonEnabled = false
        binding.searchBar.setOnQueryTextListener(this)
    }

    private fun setRecyclerView(){
        adapter = RecyclerAdapter(this)
        binding.archiveRecycler.layoutManager = GridLayoutManager(this.context, 2)
        binding.archiveRecycler.adapter = adapter
    }

    private fun setLiveDataObserver(){
        mArchiveViewModel.archivedGoodsList.observe(viewLifecycleOwner){list ->
            adapter.modifyList(list)
        }
    }

    override fun onItemClick(currentGoods: Goods) {

        val action = ArchiveFragmentDirections.actionArchiveFragmentToEditFragment(currentGoods)
        findNavController().navigate(action)
    }

    override fun onPopupMenu(currentGoods: Goods) {

        val dialog = BottomSheetDialog(requireContext())
        val binding = BottomSheetDialogBinding.inflate(LayoutInflater.from(context))
        dialog.setContentView(binding.root)

        binding.archivation.text = "Восстановить"
        binding.archivation.setOnClickListener {
            unArchiveGoods(currentGoods)
            dialog.dismiss()
        }

        binding.deletion.text = "Удалить"
        binding.deletion.setOnClickListener {
            deleteGoods(currentGoods)
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun deleteGoods(currentGoods: Goods) {
        mArchiveViewModel.deleteGoods(currentGoods)
    }

    private fun unArchiveGoods(
        currentGoods: Goods
    ) {
        if (currentGoods.archived){

            mArchiveViewModel.unarchiveGoods(currentGoods)

            Toast.makeText(context, "'${currentGoods.name}' восставновлен!", Toast.LENGTH_LONG).show()

        } else {
            Toast.makeText(context, "'${currentGoods.name}' уже восставновлен", Toast.LENGTH_LONG).show()
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {

        return false
    }

    override fun onQueryTextChange(query: String): Boolean {
        adapter.filter(query)
        return false
    }
}