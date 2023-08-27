package uz.gita.fooddeliveryumidjon.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import uz.gita.fooddeliveryumidjon.R


fun FragmentActivity.addFragment(fm: Fragment, isTransition: Boolean) {
    val fragmentTransition = supportFragmentManager.beginTransaction()

    if (isTransition) {
        fragmentTransition.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    fragmentTransition
        .add(R.id.fragment_container, fm)
        .commit()
}

fun FragmentActivity.replaceFragment(fm: Fragment, isTransition: Boolean) {
    val fragmentTransition = supportFragmentManager.beginTransaction()

    if (isTransition) {
        fragmentTransition.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    fragmentTransition
        .replace(R.id.fragment_container, fm)
        .commit()
}

fun FragmentActivity.replaceFragmentSaveStack(fm: Fragment, isTransition: Boolean) {
    val fragmentTransition = supportFragmentManager.beginTransaction()

    if (isTransition) {
        fragmentTransition.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    fragmentTransition
        .replace(R.id.fragment_container, fm)
        .addToBackStack(fm::class.java.name)
        .commit()
}

fun FragmentActivity.popBackStack() {
    if (supportFragmentManager.backStackEntryCount == 0) finish()
    else supportFragmentManager.popBackStack()
}


fun Fragment.addFragment(fm: Fragment, isTransition: Boolean) {
    requireActivity().addFragment(fm, isTransition)
}

fun Fragment.replaceFragmentSaveStack(fm: Fragment, isTransition: Boolean) {
    requireActivity().replaceFragmentSaveStack(fm, isTransition)
}

fun Fragment.replaceFragment(fm: Fragment, isTransition: Boolean) {
    requireActivity().replaceFragment(fm, isTransition)
}

fun Fragment.popBackStack() {
    requireActivity().popBackStack()
}