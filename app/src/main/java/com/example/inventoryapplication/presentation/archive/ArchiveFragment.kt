package com.example.inventoryapplication.presentation.archive

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.inventoryapplication.data.Goods
import com.example.inventoryapplication.databinding.BottomSheetDialogBinding
import com.example.inventoryapplication.databinding.FragmentArchiveBinding
import com.example.inventoryapplication.presentation.IGoods
import com.example.inventoryapplication.presentation.RecyclerAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog

class ArchiveFragment : Fragment(), IGoods, SearchView.OnQueryTextListener {

    private lateinit var binding: FragmentArchiveBinding
    private lateinit var mArchiveViewModel: ArchiveViewModel
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
        mArchiveViewModel = ViewModelProvider(this)[ArchiveViewModel::class.java]
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
        mArchiveViewModel.readArchivedData.observe(viewLifecycleOwner){
            adapter.goodsList = it
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
        }

        binding.deletion.text = "Удалить"
        binding.deletion.setOnClickListener {
            deleteGoods(currentGoods)
        }

        dialog.show()
    }

    fun deleteGoods(currentGoods: Goods) {
        mArchiveViewModel.deleteGoods(currentGoods)
    }

    private fun unArchiveGoods(
        currentGoods: Goods
    ) {
        if (currentGoods.archived){
            val archivedFalse = false

            val unArchivedGoods = Goods(
                currentGoods.id,
                currentGoods.name,
                currentGoods.cost,
                currentGoods.brand,
                currentGoods.amount,
                currentGoods.photo,
                archivedFalse)

            mArchiveViewModel.updateGoods(unArchivedGoods)


            Toast.makeText(context, "'${currentGoods.name}' восставновлен!", Toast.LENGTH_LONG).show()

        } else {
            Toast.makeText(context, "'${currentGoods.name}' уже восставновлен", Toast.LENGTH_LONG).show()
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {

        return true
    }

    override fun onQueryTextChange(query: String): Boolean {
        if (!TextUtils.isEmpty(query)){
            searchArchivedData(query)
        } else {
            val emptyQuery = ""
            searchArchivedData(emptyQuery)
        }
        return true
    }

    private fun searchArchivedData(query: String){

        val searchQuery = "%$query%"

        mArchiveViewModel.searchArchivedGoods(searchQuery).observe(this){
            adapter.goodsList = it
        }
    }

}