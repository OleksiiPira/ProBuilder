package com.example.probuilder.presentation

object Route {
    //    Bottom navigation
    const val HOME = "home"

    const val CATEGORIES_SECTION = "categories_section"
    const val CATEGORIES = "categories"
    const val CREATE_CATEGORY = "create_category"
    const val SUB_CATEGORY = "sub_categories?categoryId={categoryId}&categoryName={categoryName}"
    const val SERVICES = "prices?categoryId={categoryId}&categoryName={categoryName}&subCategoryName={subCategoryName}"
    const val CREATE_SERVICE = "create_category"
    const val SERVICE_DETAILS = "price?item={item}"


    const val INVOICES_SECTION = "invoices_section"
    const val INVOICES = "invoices"
    const val INVOICE_DETAILS = "invoice_details?invoice={invoice}"
    const val CREATE_INVOICE = "price?invoice={invoice}"

    const val PROFILE_SECTION = "profile_section"
    const val PROFILE = "profile"

    const val BACK = "back"
}