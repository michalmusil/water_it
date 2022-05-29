package cz.mendelu.xmusil5.waterit.rwadapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import cz.mendelu.xmusil5.waterit.R
import cz.mendelu.xmusil5.waterit.database.entities.DbPlant
import cz.mendelu.xmusil5.waterit.databinding.ListItemPlantBinding
import cz.mendelu.xmusil5.waterit.utils.DateUtils
import cz.mendelu.xmusil5.waterit.utils.PictureUtils

class PlantsRecyclerViewAdapter(
    private val context: Context,
    private val plantsList: MutableList<DbPlant>,
    private val eventListener: PlantsRecyclerViewEventListener): RecyclerView.Adapter<PlantsRecyclerViewAdapter.PlantViewHolder>() {

    inner class PlantViewHolder(val binding: ListItemPlantBinding) :
        RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantViewHolder {
        return PlantViewHolder(
            ListItemPlantBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PlantViewHolder, position: Int) {
        val bindedPlant = plantsList.get(position)

        holder.binding.root.setOnClickListener(View.OnClickListener {
            this.eventListener.onItemClicked(bindedPlant)
        })

        //Displaying the status bar of item
        holder.binding.wateringStatus.visibility = View.INVISIBLE
        if (bindedPlant.lastWatered != null && bindedPlant.daysBetweenWatering != null){
            val date = DateUtils.getDate(bindedPlant.lastWatered!!)
            if (DateUtils.daysBetween(date, DateUtils.getCurrentDate()) > bindedPlant.daysBetweenWatering!!)
                holder.binding.wateringStatus.visibility = View.VISIBLE
        }

        holder.binding.plantName.text = bindedPlant.name
        holder.binding.plantSpecies.text = bindedPlant.species
        if (bindedPlant.picture != null) {
            holder.binding.plantImageContainer.setImageBitmap(
                PictureUtils.fromByteArrayToBitmap(bindedPlant.picture)
            )
        } else{
            holder.binding.plantImageContainer.setImageDrawable(
                ContextCompat.getDrawable(context, R.drawable.ic_plant))
        }
    }

    override fun getItemCount(): Int {
        return plantsList.size
    }

    interface PlantsRecyclerViewEventListener{
        fun onItemClicked(plant: DbPlant)
    }

    //Class for comparing two lists - to find out which items to update in the adapter
    inner class PlantsDiffUtils(
        private val oldList: MutableList<DbPlant>,
        private val newList: MutableList<DbPlant>
    ) : DiffUtil.Callback() {
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
                    && oldList[oldItemPosition].species == newList[newItemPosition].species
                    && oldList[oldItemPosition].roomId == newList[newItemPosition].roomId
                    && oldList[oldItemPosition].picture.contentEquals(newList[newItemPosition].picture)
                    && oldList[oldItemPosition].dateOfPlanting == newList[newItemPosition].dateOfPlanting
                    && oldList[oldItemPosition].lastWatered == newList[newItemPosition].lastWatered
                    && oldList[oldItemPosition].daysBetweenWatering == newList[newItemPosition].daysBetweenWatering
                    && oldList[oldItemPosition].description == newList[newItemPosition].description
        }
    }
}