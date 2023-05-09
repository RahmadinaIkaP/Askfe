package id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.view.history

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.data.model.History
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.databinding.ItemHistoryDiagnosisBinding
import java.text.SimpleDateFormat
import java.util.*

class HistoryAdapter(private val onClick : HistoryInterface) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<History>() {
        override fun areItemsTheSame(oldItem: History, newItem: History): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: History, newItem: History): Boolean {
            return oldItem == newItem
        }

    }

    private val differ = AsyncListDiffer(this, differCallback)

    inner class ViewHolder(private val binding: ItemHistoryDiagnosisBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(history: History) {
            binding.apply {
                val konsulDate = SimpleDateFormat("d MMMM y",
                    Locale.getDefault()).format(history.tglKonsultasi!!)

                tvNamaKucing.text = history.namaKucing
                tvTglKonsul.text = konsulDate
                tvHasilDiagnosis.text = history.hdPenyakit
                tvPersentasePenyakit.text = "${history.hdCfPersen}%"
            }
        }
    }

    interface HistoryInterface {
        fun onItemClick(history: History)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            ItemHistoryDiagnosisBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size

    fun setData(data: List<History>) {
        differ.submitList(data)
    }
}