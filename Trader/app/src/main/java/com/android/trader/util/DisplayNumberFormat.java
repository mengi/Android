package com.android.trader.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by menginar on 11.01.2018.
 * Rakamların Formatları İçin Kullanılam Sınıf
 */

public class DisplayNumberFormat {

    // Qty_T2
    static public String displayNumberQty(double number) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        return decimalFormat.format(number);
    }

    // LastPx
    static public String displayNumberDoubleLastPx(double number) {
        return String.format("%,.2f", number);
    }

    // Total = Qty_T2 * LastPx
    static public String displayNumberDoubleTotal(double number) {
        return String.format("%,.3f", number);
    }
}
