package me.ykrank.s1next.view.dialog

import android.os.Bundle
import io.reactivex.Single
import me.ykrank.s1next.App
import me.ykrank.s1next.R
import me.ykrank.s1next.data.api.model.wrapper.AccountResultWrapper
import me.ykrank.s1next.widget.track.event.AddFavoriteTrackEvent

/**
 * A dialog requests to add thread to user's favourites.
 */
class ThreadFavouritesAddRequestDialogFragment : ProgressDialogFragment<AccountResultWrapper>() {

    override fun getSourceObservable(): Single<AccountResultWrapper> {
        return flatMappedWithAuthenticityToken { s ->
            mS1Service.addThreadFavorite(s, arguments.getString(ARG_THREAD_ID),
                    arguments.getString(ARG_REMARK))
        }
    }

    override fun onNext(data: AccountResultWrapper) {
        val result = data.result
        if (result.status == STATUS_ADD_TO_FAVOURITES_SUCCESS || result.status == STATUS_ADD_TO_FAVOURITES_REPEAT) {
            (fragmentManager.findFragmentByTag(
                    ThreadFavouritesAddDialogFragment.TAG) as ThreadFavouritesAddDialogFragment).dismissAllowingStateLoss()
        }

        showToastText(result.message)
    }

    override fun getProgressMessage(): CharSequence? {
        return getText(R.string.dialog_progress_message_favourites_add)
    }

    companion object {

        val TAG = ThreadFavouritesAddRequestDialogFragment::class.java.name

        private val ARG_THREAD_ID = "thread_id"
        private val ARG_REMARK = "remark"

        private val STATUS_ADD_TO_FAVOURITES_SUCCESS = "favorite_do_success"
        private val STATUS_ADD_TO_FAVOURITES_REPEAT = "favorite_repeat"

        fun newInstance(threadId: String, remark: String): ThreadFavouritesAddRequestDialogFragment {
            App.get().trackAgent.post(AddFavoriteTrackEvent(threadId))

            val fragment = ThreadFavouritesAddRequestDialogFragment()
            val bundle = Bundle()
            bundle.putString(ARG_THREAD_ID, threadId)
            bundle.putString(ARG_REMARK, remark)
            fragment.arguments = bundle

            return fragment
        }
    }
}
