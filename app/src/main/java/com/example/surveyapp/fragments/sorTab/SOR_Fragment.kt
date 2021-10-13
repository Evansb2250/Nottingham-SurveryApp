package com.example.surveyapp.fragments.sorTab

import android.os.Bundle
import android.util.Log
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
import com.example.surveyapp.activities.SurveyActivity.Companion.sorViewModel
import com.example.surveyapp.databinding.FragmentSORBinding
import java.text.DecimalFormat
import kotlin.math.nextDown
import kotlin.math.roundToInt


/**
 * A simple [Fragment] subclass.
 * Use the [SOR_Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SOR_Fragment : Fragment() {

    private val currency = DecimalFormat("£###,###.##")
    private var viewingaSorInView = false

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

        setUpViewListObserver()
        setUpSorInsertCheck(SurveyActivity.sorViewModel?.wasSorInsertedToSurvey)
        setUpRemoveAllButton()

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
        setUpMinuteSpinner()
        setUpAddButton()
        setUpRoomCatSpinner()

        SurveyActivity.sorViewModel?.currentSor?.observe(viewLifecycleOwner, { sor ->


            if (sor != null) {
                val uomType = sor.UOM.toLowerCase()
                if (uomType.equals("hr")) {
                    foundHrUom()

                } else {
                    foundNoUOM()
                }
            } else {
                foundNoUOM()
            }
        })

        return binding.root
    }

    private fun setUpMinuteSpinner() {

        val minutes = (0..59).toList()

        val minuteArrayAdapter = ArrayAdapter(
            requireActivity(), android.R.layout.simple_spinner_item,
             minutes
        )


        binding.spinnerMinute.adapter = minuteArrayAdapter

        //adding listener
        binding.spinnerMinute.onItemSelectedListener = object :

            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                val hour = (0..20).toList().get(binding.quantitySpinner.selectedItemPosition).toDouble()
                sorViewModel?.updateQuantityByHourAndMinutes(hour, minutes.get(position).toDouble())

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }

    private fun foundHrUom() {
        binding.textviewMinutes.visibility = View.VISIBLE
        binding.spinnerMinute.visibility = View.VISIBLE
        binding.quantityLabel2.text = "Hours"
    }

    private fun foundNoUOM() {
        binding.quantityLabel2.text = "Quantity"
        binding.textviewMinutes.visibility = View.INVISIBLE
        binding.spinnerMinute.visibility = View.INVISIBLE
    }


    private fun setUpRemoveAllButton() {
        binding.removeAllSorButton.setOnClickListener({
            SurveyActivity.sorViewModel?.removeAllSorFromList()
            updateListViewAfterDeletion()
            resetFields()
        })
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
                if (wasSuccessfuL) {
                    Toast.makeText(requireContext(), "Code Added To Survey", Toast.LENGTH_SHORT)
                        .show()
                    binding.sorNumberView.text =
                        SurveyActivity.sorViewModel?.addedSor2List?.size.toString()
                    setUpAddedSorListView(SurveyActivity.sorViewModel!!.addedSors.toList())

                } else
                    Toast.makeText(
                        requireContext(),
                        "Code is not selected or is already added",
                        Toast.LENGTH_SHORT
                    ).show()

            })

    }


    private fun setUpViewListObserver() {
        SurveyActivity.sorViewModel!!.viewList.observe(viewLifecycleOwner, Observer { newList ->
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
        removeSorButton.setOnClickListener { it ->
            SurveyActivity.sorViewModel!!.removeSorFromList()
            updateListViewAfterDeletion()
            resetFields()
            binding.searchView.setText(sorViewModel?.recentlyRemovedSorCode)
            binding.optionSelector.setSelection(0)
            sorViewModel!!.recentlyRemovedSorCode = ""

        }


    }


    private fun updateListViewAfterDeletion() {
        if (SurveyActivity.sorViewModel!!.addedSors != null) {
            setUpAddedSorListView(SurveyActivity.sorViewModel!!.addedSors.toList())
            binding.sorNumberView.text = SurveyActivity.sorViewModel?.addedSor2List?.size.toString()
        }
        unlockFields()
        binding.commentEntry.text.clear()
        binding.quantitySpinner.setSelection(0)
        updateTotal()
    }



    private fun setupQuantyObserver(sorViewModel: SurveySorViewModel?) {
        SurveyActivity.sorViewModel!!.sorCodeQuantity.observe(viewLifecycleOwner, Observer {

            SurveyActivity.sorViewModel!!.updateTotalByQuantity()
            updateTotal()

        })
    }


    private fun setUpSearchResult(searchResultBox: ListView) {
        searchResultBox.setOnItemClickListener { parent, view, position, id ->
            unlockFields()
            val sorcodeWithDetails = SurveyActivity.sorViewModel!!.listForView.get(position)

            val sorcode = sorcodeWithDetails.substring(0, sorcodeWithDetails.indexOf("-"))
            //val description = SurveyActivity.sorViewModel!!.listForView.get(position)
            SurveyActivity.sorViewModel?.get(sorcode)
            // setDescription(description)
            revealSelectedItem(sorcode)
            setNumberSpinnerToOne()

            resetFields()
        }

    }

    // Functionality that responds to a listing being selected
    //Preview sor list information that was added to the survey
    private fun setUpSurveyAttachButton(sorAttachedToSurveyBox: ListView) {

        sorAttachedToSurveyBox.setOnItemClickListener { parent, view, position, id ->
            SurveyActivity.sorViewModel?.sorcodeToDeleteIndex = position
            initializeSelectedSurveySorVariables(position)
        }

    }

    private fun initializeSelectedSurveySorVariables(position: Int) {
        binding.sorDescriptionBox.setText(SurveyActivity.sorViewModel!!.addedSor2List!!.get(position)!!.sorDescription)
        if (SurveyActivity.sorViewModel!!.addedSor2List.get(position).UOM.toLowerCase()
                .equals("hr")
        ) {
            foundHrUom()
            val survey = SurveyActivity.sorViewModel!!.addedSor2List.get(position)
            val wholeNumber = Math.floor(survey.quantity)
            val ratio = (survey.quantity - wholeNumber)
            val minutes = 60 * ratio
            Log.i("SOR", "percentage to calculate ${survey.quantity} floored ${wholeNumber} ratio ${ratio} minutes ${minutes} ")
            if(minutes > 0){
                binding.spinnerMinute.setSelection(minutes.roundToInt())
            }else
                binding.spinnerMinute.setSelection(0)


            binding.spinnerMinute.isFocusable = false
        } else
            foundNoUOM()


        val selectedQuantity = Math.floor(sorViewModel?.addedSor2List?.get(position)?.quantity?:0.0) + 1



        sorViewModel!!._rechargeAmount.value =
            sorViewModel?.addedSor2List?.get(position)?.total!!.toDouble()

        val category = sorViewModel?.addedSor2List?.get(position)?.roomCategory
        val selectedTotal = SurveyActivity.sorViewModel?.addedSor2List?.get(position)?.total!! * selectedQuantity!!
        val sorcode = SurveyActivity.sorViewModel!!.addedSors.get(position)
        val isRecharge = SurveyActivity.sorViewModel?.addedSor2List?.get(position)?.isRecharge
        val comment = SurveyActivity.sorViewModel?.addedSor2List?.get(position)?.surveyorDescription

        //Can go in a separate function


        if (isRecharge != null) {
            binding.rechargeBox.isChecked = isRecharge
        }


//can go into a separate Function

        displaySurveyInformationAttachedToSurvey(
            comment,
            selectedQuantity,
            selectedTotal,
            sorcode,
            category
        )

    }

    private fun displaySurveyInformationAttachedToSurvey(
        comment: String?,
        selectedQuantity: Double,
        selectedTotal: Double,
        sorcode: String,
        category: String?
    ) {
        binding.commentEntry.text.clear()
        binding.commentEntry.setText(comment)


        //Redisplays the amount and details submitted


        binding.quantitySpinner.setSelection(selectedQuantity!!.toInt() - 1)


        //  Toast.makeText(requireContext(),  selectedTotal.toString(), Toast.LENGTH_SHORT).show()
        binding.totalTextView.text.clear()

        binding.totalTextView.append(currency.format(selectedTotal))


        for (idCat in 0..constant.ROOMCATEGORIES.size - 1) {
            if (category.equals(constant.ROOMCATEGORIES[idCat].toString())) {
                binding.roomCatSpin.setSelection(idCat)
            }
        }


        //   Toast.makeText(requireContext(), " sexond" + selectedTotal.toString(), Toast.LENGTH_SHORT).show()
        lockFields()
        revealSelectedItem(sorcode)
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
        binding.imageButton.setOnClickListener { it ->
            SurveyActivity.sorViewModel?.searchViewEntry = binding.searchView.text.toString().trim()
            binding.viewmodel?.searchFor(SurveyActivity.sorViewModel!!.searchViewEntry)

            revealSelectedItem(SurveyActivity.sorViewModel!!.searchViewEntry)
            binding.searchView.text.clear()
            setNumberSpinnerToOne()


            //Searches using key word
            if (binding.optionSelector.selectedItem == constant.KEYWORD) {
                setUpListView(SurveyActivity.sorViewModel!!.listForView)
                SurveyActivity.sorViewModel?.listForViewSize?.value =
                    SurveyActivity.sorViewModel?.listForView?.size

            }



            updateListViewAfterDeletion()

            unlockFields()

            alertUser()


        }
    }

    private fun revealSelectedItem(tag: String) {
        var codeHeader = ""
        binding.sorIdentifier.visibility = View.VISIBLE
        if (tag.contains("-")) {
            codeHeader = tag.substring(0, tag.indexOf("-"))
        } else
            codeHeader = tag
        binding.sorIdentifier.text = codeHeader
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
        binding.roomCatSpin.adapter = catArrayAdapter

        binding.roomCatSpin.onItemSelectedListener = object :
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
        val list = mutableListOf<Double>()
        for(x in 0 .. 20){
            list.add(x.toDouble())
        }

        val numberArrayAdapter = ArrayAdapter(
            requireActivity(), android.R.layout.simple_spinner_item,
            (0..20).toList()
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

                val minutes = (0..59).toList().get(binding.spinnerMinute.selectedItemPosition).toDouble()
                Log.i("SOR", " minutes ${minutes}")
                sorViewModel?.updateQuantityByHourAndMinutes( list.get(position), minutes)

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
        binding.addSoRToSurveyButton.setOnClickListener { it ->
            if (!viewingaSorInView) {
                val surveyId: Int = SurveyActivity.SurveyID!!
                val sorCode = sorViewModel?.currentSor?.value?.sorCode
                val quantity = sorViewModel?.sorCodeQuantity?.value
                val total = sorViewModel?.total?.value
                val comments = binding.commentEntry.text.toString()
                val isRecharge = binding.rechargeBox.isChecked
                binding.viewmodel?.CheckBeforeAddint(
                    sorCode, surveyId, comments,
                    isRecharge, quantity, total
                )


                resetFields()
            } else
                Toast.makeText(
                    requireContext(),
                    "You must click on a sor code not in your list",
                    Toast.LENGTH_LONG
                ).show()

        }
    }


    private fun lockFields() {
        viewingaSorInView = true
        binding.roomCatSpin.isEnabled = false
        binding.spinnerMinute.isEnabled = false
        binding.quantitySpinner.isEnabled = false
        binding.rechargeBox.isClickable = false
        binding.commentEntry.isFocusable = false
    }

    //TODO Fixe bug that won't let me unlock
    private fun unlockFields() {
        viewingaSorInView = false
        binding.spinnerMinute.isEnabled = true
        binding.roomCatSpin.isEnabled = true
        binding.quantitySpinner.isEnabled = true
        binding.rechargeBox.isClickable = true
        binding.commentEntry.isFocusableInTouchMode = true

    }


    private fun resetFields() {
        //RESET TEXT
        binding.commentEntry.text.clear()
        //Reset recharge box
        binding.rechargeBox.isChecked = false
        binding.quantitySpinner.setSelection(0)
        binding.spinnerMinute.setSelection(0)
        binding.roomCatSpin.setSelection(0)
        updateTotal()

    }


}