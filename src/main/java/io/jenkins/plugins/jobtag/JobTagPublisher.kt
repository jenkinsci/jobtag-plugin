package io.jenkins.plugins.jobtag

import hudson.Extension
import hudson.model.AbstractDescribableImpl
import hudson.model.Job
import hudson.model.JobProperty
import hudson.model.JobPropertyDescriptor
import jenkins.model.GlobalConfiguration
import jenkins.model.Jenkins
import net.sf.json.JSONObject
import org.jetbrains.annotations.NotNull
import org.kohsuke.stapler.DataBoundConstructor
import org.kohsuke.stapler.DataBoundSetter
import org.kohsuke.stapler.StaplerRequest
import java.awt.Color
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.logging.Logger

class JobTagPublisher @DataBoundConstructor
constructor() : JobProperty<Job<*, *>>() {

    @set:DataBoundSetter
    var tags: Set<JobTag> = HashSet()

    @Extension
    class DescriptorImpl : JobPropertyDescriptor() {


        override fun isApplicable(jobType: Class<out Job<*, *>>): Boolean {
            return true
        }

        override fun getDisplayName(): String {
            return Messages.JobTagPublisher_DescriptorImpl_DisplayName()
        }

        @Throws(hudson.model.Descriptor.FormException::class)
        override fun newInstance(req: StaplerRequest?, formData: JSONObject): JobProperty<*> {
            val tpp = req?.bindJSON(
                    JobTagPublisher::class.java,
                    formData.getJSONObject(JOB_TAG_BLOCK_NAME)
            )

            if (tpp == null) {
                LOGGER.fine("Couldn't bind JSON")
                return JobTagPublisher()
            }

            val configure = TagConfigure.get()
            for (tag in tpp.tags) {
                tag.value = if (configure.isCaseSenstive) tag.value else tag.value!!.toUpperCase()
                colorStack.putIfAbsent(tag.value!!, "#" + (Random().nextInt(8388607) + 8388607).toString(16))
                tag.color = colorStack.getOrDefault(tag.value!!, "#FFFFFF")
            }

            return tpp
        }

        companion object {

            val JOB_TAG_BLOCK_NAME = "JobTag"
        }

    }

    companion object {

        private val LOGGER = Logger.getLogger(JobTagPublisher::class.java.name)

        val colorStack = ConcurrentHashMap<String, String>()
    }


}

