package com.inaki.yugiohapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.inaki.yugiohapp.R
import com.inaki.yugiohapp.model.Data
import com.inaki.yugiohapp.utils.CardDetailsClick

class CardsAdapter(
    private val detailsCardDetailsClick: CardDetailsClick,
    private val listOfCards: MutableList<Data> = mutableListOf()
) : RecyclerView.Adapter<CardViewHolder>() {

    fun setCards(cards: List<Data>) {
        listOfCards.clear()
        listOfCards.addAll(cards)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        LayoutInflater.from(parent.context).inflate(R.layout.yugioh_cards_item, parent, false).apply {
            return CardViewHolder(this)
        }
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val card = listOfCards[position]
        holder.setInformationToTheViewHolder(card)

        holder.itemView.setOnClickListener {
            // here i can move to next fragment
            detailsCardDetailsClick.moveToDetailsFragment(card.name)
        }
    }

    override fun getItemCount(): Int = listOfCards.size
}

class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val cardName: TextView = itemView.findViewById(R.id.card_name)
    val cardType: TextView = itemView.findViewById(R.id.card_type)
    val cardDescription: TextView = itemView.findViewById(R.id.description_card)
    val cardImage: ImageView = itemView.findViewById(R.id.card_image)

    fun setInformationToTheViewHolder(cardItem: Data) {
        cardName.text = cardItem.name
        cardDescription.text = cardItem.desc
        cardType.text = cardItem.type

        Glide.with(itemView)
            .load(cardItem.cardImages[0].imageUrl)
            .placeholder(R.drawable.load_image)
            .error(R.drawable.image_error)
            .fallback(R.drawable.no_image_24)
            .into(cardImage)
    }
}