package com.example.surveyapp.fragments.sorTab

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import com.example.surveyapp.CONSTANTS.constant
import com.example.surveyapp.R
import com.example.surveyapp.activities.SurveyActivity
import com.example.surveyapp.databinding.FragmentSORBinding
import java.text.DecimalFormat


/**
 * A simple [Fragment] subclass.
 * Use the [SOR_Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SOR_Fragment : Fragment() {

    private val currency = DecimalFormat("£###,###.##")


    private lateinit var binding: FragmentSORBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Instantiate the binding
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_s_o_r_, container, false)

        // TEST
        binding.viewmodel = SurveyActivity.sorViewModel
        binding.lifecycleOwner = this
        binding.sorIdentifier.visibility = View.INVISIBLE

        setupQuantyObserver(SurveyActivity.sorViewModel)
        setUpTotalObserver(SurveyActivity.sorViewModel!!.total)
        setUpRechargeObserver(SurveyActivity.sorViewModel!!.rechargeAmount)
        setUpViewListObserver(SurveyActivity.sorViewModel!!.viewList)
        setUpSorInsertCheck(SurveyActivity.sorViewModel?.wasSorInsertedToSurvey)

        //Keep the search result number the same
        SurveyActivity.sorViewModel?.listForViewSize?.observe(viewLifecycleOwner, Observer { size ->
            binding.searchResultNumber.text = size.toString()
        })


        setUpSurveyAttachButton(binding.sorAttachedToSurveyBox)
        setUpSearchResult(binding.searchResultBox)
        setUpRemoveButton(binding.removeSorButton)
        setupImageButton()
        setUpSpinner()
        setUpNumberSpinner()
        setUpAddButton()
        setUpRoomCatSpinner()
        return binding.root
    }

    private fun setUpTotalObserver(total: MutableLiveData<Double>) {
        total.observe(viewLifecycleOwner, Observer { newAmount ->
            binding.totalTextView.setText(currency.format(newAmount))
        })
    }


    private fun setUpSorInsertCheck(wasSorInsertedToSurvey: LiveData<Boolean>?) {
        wasSorInsertedToSurvey?.observe(
            viewLifecycleOwner,
            { wasSuccessfuL ->
                // Toast.makeText(requireContext(), wasSuccessfuL.toString(), Toast.LENGTH_SHORT).show()
                binding.SorNumberView.text =
                    SurveyActivity.sorViewModel?.addedSorList?.size.toString()

                setUpAddedSorListView(SurveyActivity.sorViewModel!!.addedSors.toList())
            })

    }


    private fun setUpViewListObserver(viewList: LiveData<List<String>>) {
        viewList.observe(viewLifecycleOwner, Observer { newList ->
            setUpListView(newList)
        })
    }

    private fun setUpRechargeObserver(rechargeAmount: LiveData<Double>) {
        rechargeAmount.observe(
            viewLifecycleOwner,
            Observer { rechargeAmount ->
                binding.rechargeTextView.text.clear()
                binding.rechargeTextView.append(currency.format(rechargeAmount))
            })


    }


    private fun setUpRemoveButton(removeSorButton: Button) {
        removeSorButton.setOnClickListener({ it ->
            SurveyActivity.sorViewModel!!.removeSorFromList()
            if (SurveyActivity.sorViewModel!!.addedSors != null) {
                setUpAddedSorListView(SurveyActivity.sorViewModel!!.addedSors.toList())
                binding.SorNumberView.text =
                    SurveyActivity.sorViewModel?.addedSorList?.size.toString()
            }
            unlockFields()
            binding.commentEntry.text.clear()
            binding.quantitySpinner.setSelection(0)
            updateTotal()
        })


    }


    private fun setupQuantyObserver(sorViewModel: SurveySorViewModel?) {
        SurveyActivity.sorViewModel!!.quantitySelected.observe(viewLifecycleOwner, Observer {
            SurveyActivity.sorViewModel!!.updateTotalByQuantity()
            updateTotal()

        })
    }

    private fun setUpSearchResult(searchResultBox: ListView) {
        searchResultBox.setOnItemClickListener { parent, view, position, id ->

            unlockFields()

            val sorcode = SurveyActivity.sorViewModel!!.listForView.get(position)
            SurveyActivity.sorViewModel?.get(sorcode)
            revealSelectedItem(sorcode)
            setNumberSpinnerToOne()
            //TODO remove box
            resetFields()
        }

    }

    // Functionality that responds to a listing being selected
    //Preview sor list information that was added to the survey
    private fun setUpSurveyAttachButton(sorAttachedToSurveyBox: ListView) {

        sorAttachedToSurveyBox.setOnItemClickListener { parent, view, position, id ->
            SurveyActivity.sorViewModel?.sorcodeToDeleteIndex = position
            //Gets the values for this added Sor

            // Adds the SorCode to the current sorCode



            val selectedQuantity = SurveyActivity.sorViewModel?.addedSorList?.get(position)?.quantity?.toDouble()

            // calculates the original recharge cost
            val rechargeCost = SurveyActivity.sorViewModel?.addedSorList?.get(position)?.total!!.toDouble() / selectedQuantity!!.toDouble()
            SurveyActivity.sorViewModel!!._rechargeAmount.value = rechargeCost


            val category = SurveyActivity.sorViewModel?.addedSorList?.get(position)?.roomCategory
            val selectedTotal = SurveyActivity.sorViewModel?.addedSorList?.get(position)?.total
            val sorcode = SurveyActivity.sorViewModel!!.addedSors.get(position)
            val isRecharge = SurveyActivity.sorViewModel?.addedSorList?.get(position)?.isRecharge
            val comment = SurveyActivity.sorViewModel?.addedSorList?.get(position)?.surveyorDescription

            for( idCat in 0..constant.ROOMCATEGORIES.size -1){
                if( category.equals(constant.ROOMCATEGORIES[idCat].toString())){
                    binding.RoomCatSpin.setSelection(idCat)
                }
            }


            binding.commentEntry.text.clear()
            binding.commentEntry.setText(comment)

            SurveyActivity.sorViewModel?.get(sorcode)

            //Redisplays the amount and details submitted



            binding.quantitySpinner.setSelection(selectedQuantity!!.toInt() -1)




            //  Toast.makeText(requireContext(),  selectedTotal.toString(), Toast.LENGTH_SHORT).show()
            binding.totalTextView.text.clear()

            binding.totalTextView.append(currency.format(selectedTotal))

            if (isRecharge != null) {
                binding.rechargeBox.isChecked = isRecharge
            }

            //   Toast.makeText(requireContext(), " sexond" + selectedTotal.toString(), Toast.LENGTH_SHORT).show()
            lockFields()
            revealSelectedItem(sorcode)

        }

    }


    /*************************
     *
     *
     *
    Function calls
     *
     *
     * *************************/


    private fun setUpAddedSorListView(list: List<String>) {


        binding.sorAttachedToSurveyBox.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            list.toList()
        )

    }


    private fun setUpListView(list: List<String>) {
        binding.searchResultBox.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,

            list.toList()
        )


    }



    private fun setupImageButton() {
        binding.imageButton.setOnClickListener({ it ->
            SurveyActivity.sorViewModel?.searchViewEntry = binding.searchView.text.toString().trim()
            binding.viewmodel?.searchFor(SurveyActivity.sorViewModel!!.searchViewEntry)

            revealSelectedItem(SurveyActivity.sorViewModel!!.searchViewEntry)
            binding.searchView.text.clear()
            setNumberSpinnerToOne()

            //Searches using key word
            if (binding.optionSelector.selectedItem == constant.KEYWORD) {
                setUpListView(SurveyActivity.sorViewModel!!.listForView)
                SurveyActivity.sorViewModel?.listForViewSize?.value =  SurveyActivity.sorViewModel?.listForView?.size
            }

            unlockFields()

            alertUser()


        })
    }

    private fun revealSelectedItem(tag: String) {
        binding.sorIdentifier.visibility = View.VISIBLE
        binding.sorIdentifier.text = tag
    }

    private fun setNumberSpinnerToOne() {
        binding.quantitySpinner.setSelection(0)
    }

    private fun alertUser() {
        if (binding.viewmodel!!.searchWasFound) {
            Toast.makeText(requireContext(), "Search found", Toast.LENGTH_SHORT).show()
        } else
            Toast.makeText(requireContext(), "Nothing found", Toast.LENGTH_SHORT).show()

    }


    private fun setUpRoomCatSpinner() {
        //Create an adapter with the list being instantiated
        val catArrayAdapter = ArrayAdapter(
            requireActivity(), android.R.layout.simple_spinner_item,
            constant.ROOMCATEGORIES.toList()
        )
        //attach the adapter to the spinner adapter
        binding.RoomCatSpin.adapter = catArrayAdapter

        binding.RoomCatSpin.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                SurveyActivity.sorViewModel?.setCategory(
                    constant.ROOMCATEGORIES.get(position).toString()
                )
                //   Toast.makeText(requireContext(), constant.ROOMCATEGORIES.get(position).toString(), Toast.LENGTH_LONG).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }

    private fun setUpNumberSpinner() {
        val numberArrayAdapter = ArrayAdapter(
            requireActivity(), android.R.layout.simple_spinner_item,
            constant.quantityRange.toList()
        )


        binding.quantitySpinner.adapter = numberArrayAdapter

        //adding listener
        binding.quantitySpinner.onItemSelectedListener = object :

            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                binding.viewmodel!!.quantitySelected.value = constant.quantityRange[position]

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }


    private fun setUpSpinner() {
        val arrayAdapter = ArrayAdapter(
            requireActivity(),
            android.R.layout.simple_spinner_dropdown_item,
            constant.searchBy.toList()
        )
        binding.optionSelector.adapter = arrayAdapter
        binding.optionSelector.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                binding.viewmodel?.searchby?.value = constant.searchBy[position]

            }


        }

    }

    private fun updateTotal() {
        binding.totalTextView.text.clear()
        //    Toast.makeText(requireContext(), " sexond" +  currency.format(SurveyActivity.sorViewModel!!.total.value), Toast.LENGTH_SHORT).show()
        binding.totalTextView.append(currency.format(SurveyActivity.sorViewModel!!.total.value))
    }


    private fun setUpAddButton() {

        binding.addSoRToSurveyButton.setOnClickListener({ it ->
            val surveyId: Int = SurveyActivity.SurveyID!!



            val sorCode = SurveyActivity.sorViewModel?.currentSor?.sorCode
            val quantity = SurveyActivity.sorViewModel?.quantitySelected?.value
            val total = SurveyActivity.sorViewModel?.total?.value
            val comments = binding.commentEntry.text.toString()
            val isRecharge = binding.rechargeBox.isChecked


            binding.viewmodel?.CheckBeforeAddint(
                sorCode, surveyId, comments,
                isRecharge, quantity, total
            )


            resetFields()


        })
    }


    private fun lockFields() {
        binding.quantitySpinner.isFocusable = false
        binding.rechargeBox.isFocusable = false
        binding.commentEntry.isFocusable = false
    }

    //TODO Fixe bug that won't let me unlock
    private fun unlockFields() {
        binding.quantitySpinner.isFocusable = true
        binding.rechargeBox.isFocusable = true
        binding.commentEntry.isFocusableInTouchMode = true

    }


    private fun resetFields() {
        //RESET TEXT
        binding.commentEntry.text.clear()
        //Reset recharge box
        binding.rechargeBox.isChecked = false
        binding.quantitySpinner.setSelection(0)
        binding.RoomCatSpin.setSelection(0)
        updateTotal()

    }


}