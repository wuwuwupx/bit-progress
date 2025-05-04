package com.bitprogress.validation.text;

import com.bitprogress.basemodel.enums.text.TextType;
import com.bitprogress.validation.utils.ValidatorUtils;

public class ValidationUtilsText {

    public static void main(String[] args) {
        String text = "ñдωあ나ǈº1";
        TextType[] textTypes = {TextType.LETTER};
        System.out.println(ValidatorUtils.isAllowedText(text, textTypes, false));

    }

}
