package id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.view.infopenyakit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.data.model.Penyakit
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.databinding.ItemPenyakitBinding

class PenyakitAdapter(private val onClick : PenyakitInterface) : RecyclerView.Adapter<PenyakitAdapter.ViewHolder>(){

    private val differCallback = object : DiffUtil.ItemCallback<Penyakit>(){
        override fun areItemsTheSame(oldItem: Penyakit, newItem: Penyakit): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Penyakit, newItem: Penyakit): Boolean {
            return oldItem == newItem
        }

    }

    private val differ = AsyncListDiffer(this, differCallback)

    inner class ViewHolder(private val binding : ItemPenyakitBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun bind(penyakit: Penyakit){
                binding.apply {
                    Glide.with(itemView)
                        .load(penyakit.image)
                        .into(ivPenyakit)

                    tvNamaPenyakit.text = penyakit.nama_penyakit
                }

                itemView.setOnClickListener {
                    onClick.onItemClick(penyakit)
                }
            }
    }

    interface PenyakitInterface {
        fun onItemClick(penyakit: Penyakit)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemPenyakitBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size

    fun setData(data : List<Penyakit>){
        differ.submitList(data)
    }
}