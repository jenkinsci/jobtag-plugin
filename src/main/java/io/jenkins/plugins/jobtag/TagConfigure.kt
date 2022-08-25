package io.jenkins.plugins.jobtag

import hudson.BulkChange
import hudson.Extension
import hudson.model.PersistentDescriptor
import jenkins.model.GlobalConfiguration
import net.sf.json.JSONObject
import org.kohsuke.accmod.Restricted
import org.kohsuke.accmod.restrictions.NoExternalUse
import org.kohsuke.stapler.DataBoundSetter
import org.kohsuke.stapler.StaplerRequest

import java.io.IOException
import java.util.logging.Level
import java.util.logging.Logger

@Extension(ordinal = TagConfigure.ORDINAL.toDouble())
class TagConfigure : GlobalConfiguration(), PersistentDescriptor {

    @get:Synchronized
    @set:DataBoundSetter
    @set:Synchronized
    var isCaseSenstive = false

    @get:Synchronized
    @set:DataBoundSetter
    @set:Synchronized
    var disableColor = false

    override fun configure(req: StaplerRequest, json: JSONObject?): Boolean {
        try {
            BulkChange(this).use { bc ->
                req.bindJSON(this, json)
                bc.commit()
            }
        } catch (exception: IOException) {
            LOGGER.log(
                    Level.WARNING, "Exception occurred while committing bulkchange operation.", exception)
            return false
        }

        save()
        return true
    }

    @Synchronized
    override fun load() {
        val file = configFile
        if (!file.exists()) {
            isCaseSenstive = false
            disableColor = false
        } else {
            super.load()
        }
    }

    @Synchronized
    override fun save() {
        if (BulkChange.contains(this)) {
            return
        }

        try {
            configFile.write(this)
            if (disableColor) {
                JobTagPublisher.clearColorStack()
            } else {
                JobTagPublisher.loadColorStack()
            }
        } catch (e: IOException) {
            LOGGER.log(Level.WARNING, "Failed to save $configFile", e)
        }

    }

    companion object {

        private val LOGGER = Logger.getLogger(TagConfigure::class.java.name)

        private val serialVersionUID = 3285310269140845583L

        @Restricted(NoExternalUse::class)
        const val ORDINAL = 200

        fun get(): TagConfigure {
            return GlobalConfiguration.all().getInstance(TagConfigure::class.java)
        }
    }

}
