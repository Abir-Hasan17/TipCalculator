package com.learning.tipcalculator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.learning.tipcalculator.ui.theme.colorSliderActive
import com.learning.tipcalculator.ui.theme.colorSliderInactive
import com.learning.tipcalculator.ui.theme.colorSliderThumb
import com.learning.tipcalculator.ui.theme.colorTopBar
import com.learning.tipcalculator.values.defaultPadding
import com.learning.tipcalculator.values.sizeInputText
import com.learning.tipcalculator.values.sizeSmallText
import com.learning.tipcalculator.values.sizeTopBarText
import com.learning.tipcalculator.values.topBarTextPadding

@Preview(showBackground = true)
@Composable
fun HomeScreen(){

    var sliderPosition by remember { mutableStateOf(0f) }
    var tipPercent by remember { mutableStateOf(0) }
    var tipRemark by remember {
        mutableStateOf(Utils.getTipRemark(tipPercent))
    }
    var baseAmount by remember {
        mutableStateOf("")
    }
    var tipAmount by remember {
        mutableStateOf(Utils.getTipAmount(baseAmount, tipPercent))
    }
    var amountToPay by remember {
        mutableStateOf(Utils.getAmountToPay(tipAmount,baseAmount))
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {

        val topBar = createRef()
        Box(modifier = Modifier
            .constrainAs(topBar) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
            .fillMaxWidth()
            .background(color = colorTopBar)
            .height(80.dp)
        ) {
            Text(text = "Tip Calculator",
                fontSize = sizeTopBarText,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(topBarTextPadding)
                    .align(Alignment.Center)
            )
        }

        val inputOutput = createRef()
        Box(modifier = Modifier
            .constrainAs(inputOutput) {
                top.linkTo(topBar.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
            .fillMaxWidth()
            .padding(defaultPadding)
        ){
            Column {
                //Base Amount
                Text(text = "Base Amount",
                    fontSize = sizeInputText,
                    modifier = Modifier
                        .padding(top = defaultPadding)
                )
                OutlinedTextField(value = baseAmount,
                    onValueChange = {
                        baseAmount =  Utils.decimalCleanUp(it)
                        tipAmount = Utils.getTipAmount(baseAmount,tipPercent)
                        amountToPay = Utils.getAmountToPay(tipAmount,baseAmount)},
                    label = {
                        Text(text = "Amount")
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = defaultPadding)
                    )
                Spacer(modifier = Modifier.size(10.dp))

                //Tip percentage
                Box (modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = defaultPadding)
                ){
                    Text(text = "Tip Percentage",
                        fontSize = sizeInputText,

                    )
                    Text(text = "${tipPercent}%",
                        fontSize = sizeInputText,
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                    )

                }
                //Tip Slider
                Slider(
                    value = sliderPosition,
                    onValueChange = {
                        sliderPosition = it
                        tipPercent = it.toInt()
                        tipRemark = Utils.getTipRemark(tipPercent)
                        tipAmount = Utils.getTipAmount(baseAmount,tipPercent)
                        amountToPay = Utils.getAmountToPay(tipAmount,baseAmount)
                    },
                    colors = SliderDefaults.colors(
                        thumbColor = colorSliderThumb,
                        activeTrackColor = colorSliderActive,
                        inactiveTrackColor = colorSliderInactive,
                    ),
                    valueRange = 0f..35f,
                    modifier = Modifier

                )
                //Remark about the tip percentage
                Text(text = tipRemark,
                    fontSize = sizeSmallText,
                    color = Utils.getColorByRemark(tipRemark),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                    )

                Spacer(modifier = Modifier.size(50.dp))
                HorizontalDivider()

                //Total tip amount and Amount to pay
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = defaultPadding)
                ){
                    Column {
                        Text(text = "Total Tip Amount: ", fontSize = sizeInputText, modifier = Modifier.padding(top = defaultPadding))
                        Text(text = "Amount to Pay: ", fontSize = sizeInputText, modifier = Modifier.padding(top = defaultPadding))
                    }
                    Spacer(modifier = Modifier.size(10.dp))
                    Column {
                        Text(text = tipAmount, fontSize = sizeInputText, modifier = Modifier.padding(top = defaultPadding))
                        Text(text = amountToPay, fontSize = sizeInputText, modifier = Modifier.padding(top = defaultPadding))
                    }
                }

            }
        }
    }
}