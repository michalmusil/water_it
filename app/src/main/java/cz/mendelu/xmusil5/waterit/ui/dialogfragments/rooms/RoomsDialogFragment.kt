package cz.mendelu.xmusil5.waterit.ui.dialogfragments.rooms

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cz.mendelu.xmusil5.waterit.R
import cz.mendelu.xmusil5.waterit.database.entities.DbRoom
import cz.mendelu.xmusil5.waterit.databinding.DialogFragmentRoomsBinding
import cz.mendelu.xmusil5.waterit.databinding.ListItemRoomBinding
import cz.mendelu.xmusil5.waterit.utils.PictureUtils
import org.koin.androidx.viewmodel.ext.android.getViewModel

class RoomsDialogFragment(private val roomOnCLickListener: RoomOnClickListener): DialogFragment() {

    private lateinit var binding: DialogFragmentRoomsBinding
    val viewModel: RoomsDialogFragmentViewModel by lazy { getViewModel(null, RoomsDialogFragmentViewModel::class) }

    private val roomsList: MutableList<DbRoom> = mutableListOf()
    private lateinit var adapter: RoomsRecyclerViewAdapter
    private lateinit var layoutManager: GridLayoutManager


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogFragmentRoomsBinding.inflate(inflater, container, false)
        val view = binding.root

        val rw = binding.roomsPopupRecyclerView
        this.adapter = RoomsRecyclerViewAdapter()
        this.layoutManager = GridLayoutManager(requireContext(), 2)
        rw.adapter = this.adapter
        rw.layoutManager = this.layoutManager
        this.viewModel.getAllRooms().observe(viewLifecycleOwner, object: Observer<MutableList<DbRoom>>{
            override fun onChanged(t: MutableList<DbRoom>?) {
                //updating views in recyclerview
                val callback = RoomsDiffUtils(roomsList, t!!)
                val result = DiffUtil.calculateDiff(callback)
                result.dispatchUpdatesTo(adapter)

                roomsList.clear()
                roomsList.addAll(t!!)
            }
        })

        return view
    }



    interface RoomOnClickListener{
        fun onRoomClicked(room: DbRoom)
    }



    inner class RoomsRecyclerViewAdapter: RecyclerView.Adapter<RoomsRecyclerViewAdapter.RoomViewHolder>(){

        inner class RoomViewHolder(val binding: ListItemRoomBinding): RecyclerView.ViewHolder(binding.root){}

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
            return RoomViewHolder(ListItemRoomBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }

        override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
            val bindedRoom = roomsList.get(position)

            holder.binding.root.setOnClickListener(View.OnClickListener {
                roomOnCLickListener.onRoomClicked(bindedRoom)
                dismiss()
            })

            holder.binding.roomName.text = bindedRoom.name
            if(bindedRoom.picture != null) {
                holder.binding.roomImageContainer.setImageBitmap(
                    PictureUtils.fromByteArrayToBitmap(bindedRoom.picture))
            }
        }

        override fun getItemCount(): Int {
            return roomsList.size
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