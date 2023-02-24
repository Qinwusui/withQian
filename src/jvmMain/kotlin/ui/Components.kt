package ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.platform.Font
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import theme.menuIconColor

@Composable
fun menuTitle(
    text: String
) {
    textview(
        text,
        color = menuIconColor,
        fonSize = 20.sp
    )
}

@Composable
fun textview(
    text: String,
    color: Color,
    fonSize: TextUnit
) {
    Text(
        text,
        fontFamily = FontFamily(Font("font/feng.ttf")),
        color = color,
        textAlign = TextAlign.Center,
        fontSize = fonSize,
        overflow = TextOverflow.Ellipsis,

        )
}

@Composable
fun icon(
    imageVector: ImageVector,
) {
    Icon(
        imageVector = imageVector,
        contentDescription = null,
        modifier = Modifier.size(20.dp),
        tint = menuIconColor
    )
}

@Composable
fun image(
    modifier: Modifier = Modifier.size(20.dp),
    painter: Painter,
) {
    Image(painter = painter, contentDescription = null, modifier =modifier)
}