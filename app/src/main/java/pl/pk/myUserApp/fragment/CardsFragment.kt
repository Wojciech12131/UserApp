package pl.pk.myUserApp.fragment

import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat.getColor
import com.google.zxing.BarcodeFormat
import com.google.zxing.oned.Code128Writer
import kotlinx.android.synthetic.main.fragment_cards.*
import pl.pk.myUserApp.R
import pl.pk.myUserApp.data.AboutMe

private var ARG_ABOUT_ME = "aboutMe"

class CardsFragment : Fragment() {
    private lateinit var aboutMe: AboutMe
    var ARG_ABOUT_ME = "aboutMe"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            aboutMe = it.getSerializable(ARG_ABOUT_ME) as AboutMe
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cards, container, false)
    }

    override fun onStart() {
        super.onStart()
        displayBitmap(aboutMe.cards.idnNo[0])
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: AboutMe) =
            CardsFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_ABOUT_ME, param1)
                }
            }
    }

    private fun displayBitmap(value: String) {
        val widthPixels = resources.getDimensionPixelSize(R.dimen.width_barcode)
        val heightPixels = resources.getDimensionPixelSize(R.dimen.height_barcode)

        image_barcode.setImageBitmap(
            createBarcodeBitmap(
                barcodeValue = value,
                barcodeColor = R.color.black,
                backgroundColor = android.R.color.white,
                widthPixels = widthPixels,
                heightPixels = heightPixels
            )
        )
        text_barcode_number.text = value
    }

    private fun createBarcodeBitmap(
        barcodeValue: String,
        @ColorInt barcodeColor: Int,
        @ColorInt backgroundColor: Int,
        widthPixels: Int,
        heightPixels: Int
    ): Bitmap {
        val bitMatrix = Code128Writer().encode(
            barcodeValue,
            BarcodeFormat.CODE_128,
            widthPixels,
            heightPixels
        )

        val pixels = IntArray(bitMatrix.width * bitMatrix.height)
        for (y in 0 until bitMatrix.height) {
            val offset = y * bitMatrix.width
            for (x in 0 until bitMatrix.width) {
                pixels[offset + x] =
                    if (bitMatrix.get(x, y)) barcodeColor else backgroundColor
            }
        }

        val bitmap = Bitmap.createBitmap(
            bitMatrix.width,
            bitMatrix.height,
            Bitmap.Config.ALPHA_8
        )
        bitmap.setPixels(
            pixels,
            0,
            bitMatrix.width,
            0,
            0,
            bitMatrix.width,
            bitMatrix.height
        )
        return bitmap
    }
}