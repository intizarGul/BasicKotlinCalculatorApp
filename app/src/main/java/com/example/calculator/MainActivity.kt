package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import androidx.core.view.OneShotPreDrawListener.add
import com.example.calculator.CalculatorModes.*
import kotlinx.android.synthetic.main.activity_main.*

enum class CalculatorModes {
    None,Addition,Subtraction,Multiplication
}

@Suppress("UNREACHABLE_CODE")
class MainActivity : AppCompatActivity() {
    //creating properties
    var lastButtonWasMode = false
    var currentMode = CalculatorModes.None
    var labelString = ""
    var savedNum = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupCalculator()
    }
    //create methods for different stages of the calculator
    // calculator setup
    private fun setupCalculator (){
        //create a constant array to hold all the buttons
        val allButtons = arrayOf(button0,button1,button2,button3,button4,button5,button6,button7,button8,button9)
        // create a loop to search all the indices of buttons to connect them to the correct button listener
        for(i in allButtons.indices){
            allButtons[i].setOnClickListener { didPressNumber(i) }
        }
        // setting up the + button to add numbers
        buttonAdd.setOnClickListener { changeMode(CalculatorModes.Addition) }
        // setting up the - button to add numbers
        buttonSubtract.setOnClickListener { changeMode(CalculatorModes.Subtraction) }
        // setting up the * button to add numbers
        buttonMultiply.setOnClickListener { changeMode(Multiplication) }
        // setting up the = button
        buttonEquals.setOnClickListener { didPressEquals() }
        //setting up the C (Clear) button
        buttonClear.setOnClickListener { didPressClear() }

    }
    //equals button pressed
    private fun didPressEquals(){
    /*First we need to check if the last button pressed was a mode + - *
    if yes then exit this method */
        if(lastButtonWasMode){
            return
        }
        //the second set of numbers entered in the calculator
        val labelInt = labelString.toInt()

        //Check the Current mode and add,subtract or multiply based on the mode
        when(currentMode) {
            // adding the numbers savedNum is our first number and labelInt is the second Number
            Addition -> savedNum += labelInt
            Subtraction -> savedNum -= labelInt
            Multiplication -> savedNum *= labelInt
            // if the = is pressed we exit this method so we don't add anything if it's not - + *
            None -> return
        }
        /*after the equal button we now reset the mode display the savedNum Update the textView
        and set the mode button to true */
        currentMode = None
        labelString = "$savedNum"
        updateText()
        lastButtonWasMode = true
    }
    //clear button pressed
    private fun didPressClear(){
        /* in this method we are going to clear the textView to do this
        set the calculator back to it's default state */
        lastButtonWasMode = false
        currentMode = CalculatorModes.None
        labelString = ""
        savedNum = 0

        //set the textView to 0
        textView.text = "0"
    }
    //to update the text
    private fun updateText(){
        //We need to convert 0 to a number so when it's pressed it not added
        val labelInt = labelString.toInt()
        /* once the 0 is converted to string now we need to convert
        this back to number so it's only added once */
        labelString=labelInt.toString()

        //if the calculator in None mode before they pressed  + - * then we store the number as Int
        if(currentMode == CalculatorModes.None){
            savedNum = labelInt
        }


        // in this method we set the textView to the labelString Var
        textView.text = labelString

    }
    //change the calculator mode this method takes one parameter the calculator mode
    private fun changeMode(modes: CalculatorModes){
        //check if the savedNum is 0 then stop running this method
        if(savedNum == 0) {
            return
        }
        /*
        changing the calculator mode to add numbers
        set the mode to what's pressed by the user
        */
        currentMode = modes
        //we enable the mode to make the last button pressed was a mode e.g (+,-,*) not =
        lastButtonWasMode = true


    }
    // when a number is pressed this method will take one parameter with type Int
    private fun didPressNumber(num:Int){
        //get the number that is pressed convert ot string and store this in a constant
        val strVal = num.toString()

        //checking if the last button pressed was mode then set the labelString to 0
        if(lastButtonWasMode){
            lastButtonWasMode = false
            labelString  = "0"
        }

        //get the labelString property and add the strVal to it
        labelString = "$labelString$strVal"
        //update the textView using the Update method
        updateText()
    }

}
