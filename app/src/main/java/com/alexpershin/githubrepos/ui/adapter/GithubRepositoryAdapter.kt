package com.alexpershin.githubrepos.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alexpershin.githubrepos.R
import com.alexpershin.githubrepos.model.GithubRepositoryEntity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

/**
 * This class is PagedListAdapter for recyclerView. [GithubRepositoryAdapter] is responsible for displaying
 * basic information about [GithubRepositoryEntity] item.
 * [onItemClick] is callback which is triggered when [itemView] is clicked by user
* */

class GithubRepositoryAdapter(private val context: Context) :
    PagedListAdapter<GithubRepositoryEntity, GithubRepositoryAdapter.GithubRepositoryViewHolder>(
        DiffUtilCallBack()
    ) {

    var onItemClick: ((String) -> Unit)? = null

    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GithubRepositoryViewHolder {
        val view = layoutInflater.inflate(R.layout.item_github_repository, parent, false)
        return GithubRepositoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: GithubRepositoryViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class GithubRepositoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val githubRepositoryNameTv = itemView.findViewById<TextView>(R.id.repositoryNameTv)
        private val descriptionTv = itemView.findViewById<TextView>(R.id.descriptionTv)
        private val repositoryOwnerTv = itemView.findViewById<TextView>(R.id.repositoryOwnerTv)
        private val organizationAvatarIv = itemView.findViewById<ImageView>(R.id.organizationAvatarIv)

        fun bind(position: Int) {
            val githubRepositoryEntity = getItem(position)

            githubRepositoryNameTv.text = githubRepositoryEntity?.name?.capitalize()
            repositoryOwnerTv.text = githubRepositoryEntity?.owner?.login
            descriptionTv.text = provideRepositoryDescription(githubRepositoryEntity?.description)

            Glide.with(context)
                .applyDefaultRequestOptions(RequestOptions().placeholder(R.drawable.ic_github_icon))
                .load(githubRepositoryEntity?.owner?.avatarUrl).into(organizationAvatarIv)

            itemView.setOnClickListener {
                onItemClick?.invoke(githubRepositoryEntity?.cloneUrl!!)
            }

        }

        private fun provideRepositoryDescription(description: String?): String? {
            return if (description.isNullOrEmpty()) {
                context.getString(R.string.message_no_repository_description)
            } else {
                description
            }
        }

    }

    class DiffUtilCallBack : DiffUtil.ItemCallback<GithubRepositoryEntity>() {
        override fun areItemsTheSame(
            oldItem: GithubRepositoryEntity,
            newItem: GithubRepositoryEntity
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: GithubRepositoryEntity,
            newItem: GithubRepositoryEntity
        ): Boolean {
            return oldItem == newItem
        }
    }
}