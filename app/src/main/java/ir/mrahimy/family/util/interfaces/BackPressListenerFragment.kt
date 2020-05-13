package ir.mrahimy.family.util.interfaces

interface BackPressListenerFragment {
    /**
     * If developer returns false the back press will not be taken into account, otherwise the activity will act naturally
     * @return false if your processing has priority, if not true
     */
    fun onBackPressed(): Boolean
}