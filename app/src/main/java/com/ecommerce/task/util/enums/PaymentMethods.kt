package com.ecommerce.task.util.enums

import com.ecommerce.task.R

enum class PaymentMethods(val paymentType: Int, val paymentImage: Int, val numberValue: Int) {
    cod(R.string.cashOnDelivery, R.drawable.cash_on_delivery, 0), creditCard(
        R.string.creditCard, R.drawable.mobile_payment, 1
    )
}