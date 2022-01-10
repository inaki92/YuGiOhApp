package com.inaki.yugiohapp.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.inaki.yugiohapp.adapters.CardsAdapter
import com.inaki.yugiohapp.databinding.FragmentCardsBinding
import com.inaki.yugiohapp.utils.CardDetailsClick
import com.inaki.yugiohapp.utils.UIState
import com.inaki.yugiohapp.viewmodel.CardsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CardsFragment : Fragment(), CardDetailsClick {

    private val binding by lazy {
        FragmentCardsBinding.inflate(layoutInflater)
    }

    private val cardsViewModel: CardsViewModel by viewModel()

    private lateinit var cardsAdapter: CardsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cardsAdapter = CardsAdapter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.cardsRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = cardsAdapter
        }

        binding.refreshItems.setOnRefreshListener {
            cardsViewModel.subscribeToCardsInfo()
        }

        cardsViewModel.cardsLivaData.observe(viewLifecycleOwner, ::handleCards)

        cardsViewModel.subscribeToCardsInfo()

        return binding.root
    }

    private fun handleCards(uiState: UIState) {
        when(uiState) {
            is UIState.LOADING -> {
                binding.cardsRecycler.visibility = View.GONE
                binding.refreshItems.isRefreshing = true
            }
            is UIState.SUCCESS -> {
                binding.refreshItems.isRefreshing = false
                binding.cardsRecycler.visibility = View.VISIBLE
                cardsAdapter.setCards(uiState.success.data)
            }
            is UIState.ERROR -> {
                binding.cardsRecycler.visibility = View.GONE
                binding.refreshItems.visibility = View.GONE
                binding.refreshItems.isRefreshing = false

                Toast.makeText(requireContext(), "Please retry again!", Toast.LENGTH_LONG).show()
            }
        }
    }

    companion object {
        fun newInstance() = CardsFragment()
    }

    override fun moveToDetailsFragment(cardName: String) {
        // right here we place the logic to move to the details fragment
    }
}