package cz.mendelu.xmusil5.waterit.ui.rooms.roomslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cz.mendelu.xmusil5.waterit.architecture.BaseFragment
import cz.mendelu.xmusil5.waterit.database.entities.DbRoom
import cz.mendelu.xmusil5.waterit.databinding.FragmentRoomsListBinding
import cz.mendelu.xmusil5.waterit.databinding.ListItemRoomBinding
import cz.mendelu.xmusil5.waterit.utils.PictureUtils

class RoomsListFragment : BaseFragment<FragmentRoomsListBinding, RoomsListViewModel>(
    RoomsListViewModel::class) {

    override val bindingInflater: (LayoutInflater) -> FragmentRoomsListBinding
        get() = FragmentRoomsListBinding::inflate

    override fun initViews() {
        viewModel.layoutManager = GridLayoutManager(requireContext(), 3)
        viewModel.roomsAdapter = RoomsRecyclerViewAdapter()

        val rw = this.binding.roomsRecyclerView
        rw.layoutManager = viewModel.layoutManager
        rw.adapter = viewModel.roomsAdapter
        this.viewModel.getAll().observe(viewLifecycleOwner, object: Observer<MutableList<DbRoom>>{
            override fun onChanged(t: MutableList<DbRoom>?) {
                //updating views in recyclerview
                val callback = RoomsDiffUtils(viewModel.roomsList, t!!)
                val result = DiffUtil.calculateDiff(callback)
                result.dispatchUpdatesTo(viewModel.roomsAdapter)

                viewModel.roomsList.clear()
                viewModel.roomsList.addAll(t!!)
            }

        })

        binding.addRoomFab.setOnClickListener(View.OnClickListener {
            val directions = RoomsListFragmentDirections.actionRoomsListFragmentToAddOrEditRoomFragment()
            findNavController().navigate(directions)
        })
    }

    override fun onActivityCreated() {

    }




    inner class RoomsRecyclerViewAdapter: RecyclerView.Adapter<RoomsRecyclerViewAdapter.RoomViewHolder>(){

        inner class RoomViewHolder(val binding: ListItemRoomBinding): RecyclerView.ViewHolder(binding.root){}

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
            return RoomViewHolder(ListItemRoomBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }

        override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
            val bindedRoom = viewModel.roomsList.get(position)

            holder.binding.root.setOnClickListener(View.OnClickListener {
                val directions = RoomsListFragmentDirections.actionRoomsListFragmentToRoomDetailFragment(bindedRoom.id!!)
                findNavController().navigate(directions)
            })

            holder.binding.roomName.text = bindedRoom.name
            if(bindedRoom.picture != null) {
                holder.binding.roomImageContainer.setImageBitmap(
                    PictureUtils.fromByteArrayToBitmap(bindedRoom.picture))
            }
        }

        override fun getItemCount(): Int {
            return viewModel.roomsList.size
        }
    }

    //Class for comparing two lists - to find out which items to update in the adapter
    inner class RoomsDiffUtils(private val oldList: MutableList<DbRoom>,
                                private val newList: MutableList<DbRoom>): DiffUtil.Callback(){
        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].name == newList[newItemPosition].name
                    && oldList[oldItemPosition].latitude == newList[newItemPosition].latitude
                    && oldList[oldItemPosition].longitude == newList[newItemPosition].longitude
                    && oldList[oldItemPosition].picture.contentEquals(newList[newItemPosition].picture)
                    && oldList[oldItemPosition].description == newList[newItemPosition].description
        }
    }

}