package com.example.demokotlin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.demokotlin.R
import com.example.demokotlin.model.User
import de.hdodenhof.circleimageview.CircleImageView

class UserAdapter(private val users: ArrayList<User>) : RecyclerView.Adapter<UserAdapter.UserViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {

        return UserViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.singlerow, parent, false)
        )

    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentitem = users[position]

        // set this data to textview and imageview

        holder.Username.text = currentitem.name
        holder.Email.text = currentitem.email
        holder.Education.text = currentitem.education
        holder.Designation.text = currentitem.designation
        holder.MobileNo.text = currentitem.mobileno

        //set image in imageview below with glide library
        Glide.with(holder.ProfileImage.getContext()).load(currentitem.addprofile).into(holder.ProfileImage);
    }

    override fun getItemCount(): Int {
        return users.size
    }

    class UserViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val Username : TextView = itemView.findViewById(R.id.tvShowName)
        val Email : TextView = itemView.findViewById(R.id.tvShowEmail)
        val Education : TextView = itemView.findViewById(R.id.tvShowEducation)
        val Designation : TextView = itemView.findViewById(R.id.tvShowDesignation)
        val MobileNo : TextView = itemView.findViewById(R.id.tvShowMobile)
        val ProfileImage : CircleImageView = itemView.findViewById(R.id.img1)
    }
}