package com.example.probuilder.presentation

object Route {
    //    Bottom navigation
    const val HOME = "home"

    const val CATEGORIES_SECTION = "categories_section"
    const val CATEGORIES = "categories?category={category}"
    const val CREATE_CATEGORY = "create_category?parentId={parentId}"

    const val SERVICES_SECTION = "services"
    const val SERVICES = "prices?categoryId={categoryId}&categoryName={categoryName}"
    const val CREATE_SERVICE = "create_service?category={category}&service={job}"

    const val PROJECTS_SECTION = "projects_section"
    const val PROJECTS = "projects"
    const val CREATE_PROJECTS = "create_projects"
    const val PROJECT_DETAILS = "project?projectId={projectId}"

    const val CLIENT_DETAILS = "client_details?projectId={projectId}&client={client}"
    const val UPSERT_CLIENT = "upsert_client?projectId={projectId}&client={client}"
    const val WORKER_DETAILS = "worker_details?projectId={projectId}&client={workerId}"
    const val UPSERT_WORKER = "worker_client?projectId={projectId}&client={worker}"

    const val INVOICES_SECTION = "invoices_section"
    const val INVOICES = "invoices"
    const val INVOICE_DETAILS = "invoice_details?invoice={invoice}"
    const val CREATE_INVOICE = "price?invoice={invoice}"

    const val PROFILE_SECTION = "profile_section"
    const val PROFILE = "profile"

    const val BACK = "back"
}