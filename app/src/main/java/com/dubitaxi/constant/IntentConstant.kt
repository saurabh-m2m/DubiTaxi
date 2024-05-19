package com.dubitaxi.constant

interface IntentConstant {
    companion object {


        const val PREFRENCE_NAME = "myPrefs"
        // Formats

        const val Password_Validation =
            "(/^(?=.*\\d)(?=.*[A-Z])([@\$%&#])[0-9a-zA-Z]{4,}\$/)"


        const val EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
        const val DEVICE_TYPE = "1"

        //Base Activity
        const val BACK_PRESS_TIME_INTERVAL: Long = 2000

        const val NUMBER_PATTERN = "[0-9]+"


        const val IS_FROM = "isFrom"




        const val GALLERY = 1111
        const val CAMERA = 2222
        const val VIDEO = 3333

        // Media Type
        const val MEDIA_TYPE_IMAGE = 1
        const val MEDIA_TYPE_VIDEO = 2

        /*QUESTION_TYPE = (
        ('1', 'paragraph'),
        ('2', 'checkbox'),
        ('3', 'multiple_option'),
        ('4', 'dropdown'),
        ('5', 'file_upload'),
        ('6', 'date'),
        ('7', 'time'))*/
        const val Paragraph = 1
        const val CheckBox = 2
        const val MultipleOptions = 3
        const val DropDown = 4
        const val FileUpload = 5
        const val Date = 6
        const val Time = 7

        const val openList=2001
        const val imagelist=2002

        const val PRODUCT_ID = "productId"
        const val ADDRESS_ID = "addressId"
        const val ORDER_ID = "orderId"
    }
}