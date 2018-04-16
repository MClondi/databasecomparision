package com.mjanotta.databasecomparison.home

import android.app.Fragment
import android.app.FragmentManager
import com.mjanotta.databasecomparison.R
import com.mjanotta.databasecomparison.objectbox.ObjectBoxPerformanceFragment
import com.mjanotta.databasecomparison.realm.RealmPerformanceFragment
import com.mjanotta.databasecomparison.room.RoomPerformanceFragment

class HomeRouterImpl(private val fragmentManager: FragmentManager) : HomeRouter {

    companion object {
        const val TAG_REALM_PERFORMANCE_FRAGMENT = "RealmPerformanceFragment"
        const val TAG_OBJECT_BOX_PERFORMANCE_FRAGMENT = "ObjectBoxPerformanceFragment"
        const val TAG_ROOM_PERFORMANCE_FRAGMENT = "RoomPerformanceFragment"
    }

    override fun openRealmPerformance() {
        replaceFragment(TAG_REALM_PERFORMANCE_FRAGMENT) { RealmPerformanceFragment() }
    }

    override fun openObjectBoxPerformance() {
        replaceFragment(TAG_OBJECT_BOX_PERFORMANCE_FRAGMENT) { ObjectBoxPerformanceFragment() }
    }

    override fun openRoomPerformance() {
        replaceFragment(TAG_ROOM_PERFORMANCE_FRAGMENT) { RoomPerformanceFragment() }
    }

    private fun replaceFragment(tag: String, fragment: () -> Fragment) {
        if (fragmentManager.findFragmentByTag(tag) == null) {
            fragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, fragment(), tag)
                    .commit()
        }
    }
}