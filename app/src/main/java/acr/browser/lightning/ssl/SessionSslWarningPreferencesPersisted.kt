package acr.browser.lightning.ssl

import acr.browser.lightning.preference.PreferenceManager
import acr.browser.lightning.utils.domainForUrl
import javax.inject.Inject
import javax.inject.Singleton

/**
 * An implementation of [SslWarningPreferences] which stores user preferences in memory and
 * persist them.
 */
@Singleton
class SessionSslWarningPreferencesPersisted @Inject constructor() : SslWarningPreferences {

    private var ignoredSslWarnings : HashMap<String, SslWarningPreferences.Behavior>? = null;
    @Inject internal lateinit var preferenceManager: PreferenceManager

    override fun recallBehaviorForDomain(url: String?): SslWarningPreferences.Behavior? {
        if (ignoredSslWarnings == null) {
            ignoredSslWarnings = preferenceManager.sslWarningPersisted;
        }
        domainForUrl(url)?.let {
            return ignoredSslWarnings!![it]
        }
        return null
    }

    override fun rememberBehaviorForDomain(url: String, behavior: SslWarningPreferences.Behavior) {
        domainForUrl(url)?.let {
            ignoredSslWarnings?.put(it, behavior)
            preferenceManager.sslWarningPersisted = ignoredSslWarnings;
        }
    }
}