package com.atulit.nutrisport.home.domain

import com.atulit.nutrisport.shared.Resources
import org.jetbrains.compose.resources.DrawableResource

enum class DrawerItem(
    val title: String,
    val icon: DrawableResource
) {
    Profile(
        title = "Profile",
        icon = Resources.Icon.Person
    ),
    Blog(
        title = "Blog",
        icon = Resources.Icon.Book
    ),
    Locations(
        title = "Locations",
        icon = Resources.Icon.MapPin
    ),
    Contact(
        title = "Contact",
        icon = Resources.Icon.Edit
    ),
    SignOut(
        title = "Sign Out",
        icon = Resources.Icon.SignOut
    ),
    Admin(
        title = "Admin",
        icon = Resources.Icon.Unlock
    )

}