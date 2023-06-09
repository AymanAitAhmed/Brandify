package com.example.pfeadminpannel.components

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import lr.aym.projet_fin_detudes.R
import kotlin.math.PI
import kotlin.math.atan2

@Composable
fun PieChart(
    modifier: Modifier = Modifier,
    radius:Float = 500f,
    innerRadius:Float = 250f,
    input:List<PieChartInput>
) {
    var circleCenter by remember {
        mutableStateOf(Offset.Zero)
    }

    var inputList by remember {
        mutableStateOf(input)
    }
    val totalValue = input.sumOf { 
        it.value
    }

    val systemBackgroundColor = MaterialTheme.colors.background

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ){
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(true) {
                    detectTapGestures(
                        onTap = { offset ->
                            val tapAngleInDegrees = (-atan2(
                                x = circleCenter.y - offset.y,
                                y = circleCenter.x - offset.x
                            ) * (180f / PI).toFloat() - 90f).mod(360f)

                            val anglePerValue = 360f / input.sumOf {
                                it.value
                            }
                            var currAngle = 0f
                            inputList.forEach { pieChartInput ->

                                currAngle += pieChartInput.value * anglePerValue
                                if (tapAngleInDegrees < currAngle) {
                                    val description = pieChartInput.description
                                    inputList = inputList.map {
                                        if (description == it.description) {
                                            it.copy(isTapped = !it.isTapped)
                                        } else {
                                            it.copy(isTapped = false)
                                        }
                                    }
                                    return@detectTapGestures
                                }
                            }

                        }
                    )
                }
        ){
            val width = size.width
            val height = size.height
            circleCenter = Offset(x= width/2f,y= height/2f)
            
            val anglePerValue = 360f/totalValue
            var currentStartAngle = 0f
            //scale a part of the pie chart if clicked
            inputList.forEach { pieChartInput ->
                val scale = if(pieChartInput.isTapped) 1.1f else 1.0f
                val angleToDraw = pieChartInput.value * anglePerValue
                scale(scale){
                    drawArc(
                        color = pieChartInput.color,
                        startAngle = currentStartAngle,
                        sweepAngle = angleToDraw,
                        useCenter = true,
                        size = Size(
                            width = radius*2f,
                            height = radius*2f
                        ),
                        topLeft = Offset(
                            (width-radius*2f)/2f,
                            (height - radius*2f)/2f
                        )
                    )
                    currentStartAngle += angleToDraw
                }
                var rotateAngle = currentStartAngle-angleToDraw/2f-90f
                var factor = 1f
                if(rotateAngle>90f){
                    rotateAngle = (rotateAngle+180).mod(360f)
                    factor = -0.92f
                }

                val percentage = (pieChartInput.value/totalValue.toFloat()*100).toInt()

                // the text percentage of every slice.
                drawContext.canvas.nativeCanvas.apply {
                    if(percentage>3){
                        rotate(rotateAngle){
                            drawText(
                                "$percentage %",
                                circleCenter.x,
                                circleCenter.y+(radius-(radius-innerRadius)/2f)*factor,
                                Paint().apply {
                                    textSize = 13.sp.toPx()
                                    textAlign = Paint.Align.CENTER
                                    color = Color.White.toArgb()
                                }
                            )
                        }
                    }
                }
            }

            //inner circle
            drawContext.canvas.nativeCanvas.apply {
                drawCircle(
                    circleCenter.x,
                    circleCenter.y,
                    innerRadius,
                    Paint().apply {
                        color = systemBackgroundColor.copy(alpha = 1f).toArgb()
                    }
                )
            }

        }
        if (totalValue == 0){
            Text(text = stringResource(id = R.string.no_reactions))
        }
        
        inputList.forEach {
            if (it.isTapped){
                Column {
                    Text(
                        it.description + ":",
                        modifier = Modifier
                            .width(Dp(innerRadius / 1.5f)),
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.body1
                    )
                    Text(
                        it.value.toString(),
                        modifier = Modifier
                            .width(Dp(innerRadius / 1.5f)),
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.h5
                    )
                }


            }
        }


    }
}

data class PieChartInput(
    val color:Color,
    val value:Int,
    val description:String,
    val isTapped:Boolean = false
)