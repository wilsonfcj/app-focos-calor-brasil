package com.ifsc.app.focos.queimadas.ui.bottonsheet

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.view.View.*
import android.widget.*
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ifsc.app.focos.queimadas.R
import com.ifsc.app.focos.queimadas.props.EEstados
import kotlinx.android.synthetic.main.fragment_estados_dialog.*
import kotlinx.android.synthetic.main.list_item_estados.view.*
import java.text.Normalizer


class EstadosDialogFragment() : DialogFragment() {
    private var mListener: Listener? = null

    private lateinit var linearLayoutConsultBanks : LinearLayout
    private lateinit var lienarLayoutListBanks : LinearLayout
    private lateinit var adapter : ItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_estados_dialog, container, false)
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog.window?.let {
                it.setLayout(width, height)
                it.setBackgroundDrawable(
                    ColorDrawable(
                        ContextCompat.getColor(
                            context!!,
                            R.color.icons
                        )
                    )
                )
                it.setWindowAnimations(R.style.DialogAnimationUpDown)
                it.setLayout(width, height)
                it.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE and WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM)
//                it.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
                it.setGravity(Gravity.BOTTOM)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        linearLayoutConsultBanks = view.findViewById(R.id.container_load_banks)
        lienarLayoutListBanks = view.findViewById(R.id.container_list_banks)

        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                println(query)
                adapter.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                println(newText)
                adapter.filter.filter(newText)
                return false
            }
        })

        search.setOnSearchClickListener {
        }
        showEstadosList(EEstados.getValues())
    }

    fun showEstadosList(estados : Array<EEstados>) {
        adapter = ItemAdapter(estados, mListener)
        recycler_view.layoutManager = LinearLayoutManager(context)
        recycler_view.adapter = adapter
        linearLayoutConsultBanks.visibility = INVISIBLE
        lienarLayoutListBanks.visibility = VISIBLE
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val parent = parentFragment
        mListener = if (parent != null) {
            parent as Listener
        } else {
            context as Listener
        }
    }

    override fun onDetach() {
        mListener = null
        super.onDetach()
    }

    interface Listener {
        fun onItemClicked(eEstados: EEstados)
    }

    private inner class ViewHolder internal constructor(
        inflater: LayoutInflater,
        parent: ViewGroup
    ) :
        RecyclerView.ViewHolder(
            inflater.inflate(
                R.layout.list_item_estados,
                parent,
                false
            )
        ) {

        internal val text: TextView = itemView.txt_institution
    }

    var isUpdating = false

    private inner class ItemAdapter internal constructor(private val mList: Array<EEstados>, var listener : Listener?) :
        RecyclerView.Adapter<ViewHolder>(), Filterable {

        private var mListFiltered = mList.toMutableList()

        override fun getFilter(): Filter {
            return object : Filter() {
                @SuppressLint("DefaultLocale")
                override fun performFiltering(charSequence: CharSequence): FilterResults {
                    val charString = charSequence.toString()
                    val filterResults = FilterResults()
                    if (!isUpdating) {
                        isUpdating = true
                        if (charString.isEmpty()) {
                            filterResults.values = mList.toMutableList()
                        } else {
                            var query = Normalizer.normalize(charString.toLowerCase(), Normalizer.Form.NFD)
                            query = query.replace("[^\\p{ASCII}]".toRegex(), "")
                            val filteredList = mList.filter {
                                val name = Normalizer.normalize(it.estadoName.toLowerCase(), Normalizer.Form.NFD)
                                    .replace("[^\\p{ASCII}]".toRegex(), "")

                                val description = Normalizer.normalize(it.estadoNameDes.toLowerCase(), Normalizer.Form.NFD)
                                    .replace("[^\\p{ASCII}]".toRegex(), "")

                                name.contains(query, true) || description.contains(query, true)
                            }.toMutableList()
                            filterResults.values = filteredList
                        }
                    }
                    return filterResults
                }

                override fun publishResults(charSequence: CharSequence,filterResults: FilterResults) {
                    if (filterResults.values != null) {
                        mListFiltered = filterResults.values as MutableList<EEstados>
                        isUpdating = false
                        notifyDataSetChanged()
                    }
                }
            }
        }

        override fun onViewRecycled(holder: ViewHolder) {
            super.onViewRecycled(holder)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(LayoutInflater.from(parent.context), parent)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val lItem = mListFiltered[position]
            holder.text.text = lItem.estadoNameDes
            with(holder.itemView) {
                tag = lItem
                setOnClickListener{
                    listener?.onItemClicked(lItem)
                }
            }
        }

        override fun getItemCount(): Int {
            return mListFiltered.size
        }
    }

    companion object {
        fun newInstance(): EstadosDialogFragment = EstadosDialogFragment()
    }
}
