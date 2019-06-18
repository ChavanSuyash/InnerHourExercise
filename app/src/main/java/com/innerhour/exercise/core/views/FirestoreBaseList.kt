package com.innerhour.exercise.core.views

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.*
import kotlinx.android.extensions.LayoutContainer


/**
 * Created by Radoslav Yankov on 29.06.2018
 * radoslavyankov@gmail.com
 * Added features by Suyash Chavan.
 */

/**
 * Simple list bind function.
 * @param items - Generic list of the items to be displayed in the list
 * @param singleLayout - The layout that will be used in the list
 * @param singleBind - The "binding" function between the item and the layout. This is the standard "bind" function in traditional ViewHolder classes. It uses Kotlin Extensions
 * so you can just use the XML names of the views inside your layout to address them.
 *
 * NOTICE: The bind only supports single type of DocumentSnapshot only
 */
fun RecyclerView.bind(query: Query, @LayoutRes singleLayout: Int = 0, singleBind: (View.(item: DocumentSnapshot, position: Int) -> Unit)): FastListAdapter {
    layoutManager = LinearLayoutManager(context)
    return FastListAdapter(query, this).map(singleLayout, { true }, singleBind)
}

open class FastListAdapter(private val query: Query, private var list: RecyclerView) : RecyclerView.Adapter<FastListViewHolder>(),
    EventListener<QuerySnapshot> {

    private var registration: ListenerRegistration? = null
    private val snapshots = mutableListOf<DocumentSnapshot>()

    private inner class BindMap(val layout: Int, var type: Int = 0, val bind: View.(item: DocumentSnapshot, position: Int) -> Unit, val predicate: (item: DocumentSnapshot) -> Boolean)

    private var bindMap = mutableListOf<BindMap>()
    private var typeCounter = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FastListViewHolder{
        return bindMap.first { it.type == viewType }.let {

            FastListViewHolder(
                LayoutInflater.from(parent.context).inflate(it.layout,
                    parent, false), viewType)
        }
    }

    override fun onBindViewHolder(holder: FastListViewHolder, position: Int) {
        holder.bind(snapshots[position], bindMap.first { it.type == holder.holderType }.bind)
    }

    override fun getItemCount() = snapshots.size

    override fun getItemViewType(position: Int) = try {
        bindMap.first { it.predicate(snapshots[position]) }.type
    } catch (e: Exception) {
        0
    }

    /**
     * The function used for mapping types to layouts
     * @param layout - The ID of the XML layout of the given type
     * @param predicate - Function used to sort the items. For example, a Type field inside your items class with different values for different types.
     * @param bind - The "binding" function between the item and the layout. This is the standard "bind" function in traditional ViewHolder classes. It uses Kotlin Extensions
     * so you can just use the XML names of the views inside your layout to address them.
     */
    fun map(@LayoutRes layout: Int, predicate: (item: DocumentSnapshot) -> Boolean, bind: View.(item: DocumentSnapshot, position: Int) -> Unit): FastListAdapter {

        bindMap.add(BindMap(layout, typeCounter++, bind, predicate))
        list.adapter = this
        return this
    }

    /**
     * Sets up a layout manager for the recycler view.
     */
    fun layoutManager(manager: RecyclerView.LayoutManager): FastListAdapter {
        list.layoutManager = manager
        return this
    }

    fun startListening() {
        registration = query.addSnapshotListener(this)
    }

    fun stopListening() {
        registration?. let {
            registration!!.remove()
        }
        snapshots.clear()
        notifyDataSetChanged()
    }


    /**
     * Updates the list using onEvent from {@link #EventListener<QuerySnapshot>}.
     * @param documentSnapshots the newly added/modified/remove list item from firestore realtime database.
     * @param e FirebaseFirestoreException for handling related exceptions
     */
    override fun onEvent(documentSnapshots: QuerySnapshot?, e: FirebaseFirestoreException?) {
        if (e != null) {
            Log.w("onEvent:error", e)
            return
        }

        for (change in documentSnapshots!!.documentChanges) {
            when (change.type) {
                DocumentChange.Type.ADDED -> onDocumentAdded(change)
                DocumentChange.Type.MODIFIED -> onDocumentModified(change)
                DocumentChange.Type.REMOVED -> onDocumentRemoved(change)
            }
        }
    }

    private fun onDocumentAdded(change: DocumentChange) {
        snapshots.add(change.newIndex, change.document)
        notifyItemInserted(change.newIndex)
    }

    private fun onDocumentModified(change: DocumentChange) {
        if (change.oldIndex == change.newIndex) {
            snapshots[change.oldIndex]= change.document
            notifyItemChanged(change.oldIndex)
        } else {
            snapshots.removeAt(change.oldIndex)
            snapshots.add(change.newIndex, change.document)
            notifyItemMoved(change.oldIndex, change.newIndex)
        }
    }

    private fun onDocumentRemoved(change: DocumentChange) {
        snapshots.removeAt(change.oldIndex)
        notifyItemRemoved(change.oldIndex)
    }
}

class FastListViewHolder(override val containerView: View, val holderType: Int) : RecyclerView.ViewHolder(containerView),
    LayoutContainer {
    fun bind(entry: DocumentSnapshot, func: View.(item: DocumentSnapshot, position: Int) -> Unit) {
        containerView.apply {
            func(entry, adapterPosition)
        }
    }
}