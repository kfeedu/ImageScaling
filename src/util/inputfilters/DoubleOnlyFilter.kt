package util.inputfilters

import javax.swing.text.AttributeSet
import javax.swing.text.DocumentFilter

class DoubleOnlyFilter : DocumentFilter() {

    //"^[0-9]+[.]?[0-9]?$"
    //"^[+-]?([0-9]*[.])?[0-9]+$"
    var regEx = Regex("^[-]?([0-9]+[.])?[0-9]*$")

    override fun insertString(fb: FilterBypass, offset: Int, string: String?, attr: AttributeSet?) {
        var text = fb.document.getText(0, fb.document.length)
        text += string
        if (text.matches(regEx))
            super.insertString(fb, offset, string, attr)
    }

    override fun replace(fb: FilterBypass, offset: Int, length: Int, str: String?, attrs: AttributeSet?) {
        var text = fb.document.getText(0, fb.document.length)
        text += str
        if (text.matches(regEx))
            super.replace(fb, offset, length, str, attrs)
    }
}