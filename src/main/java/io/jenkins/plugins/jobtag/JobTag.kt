package io.jenkins.plugins.jobtag

import hudson.Extension
import hudson.model.AbstractDescribableImpl
import hudson.model.Descriptor
import org.kohsuke.stapler.DataBoundConstructor
import org.kohsuke.stapler.DataBoundSetter

import java.io.Serializable

class JobTag @DataBoundConstructor
constructor(@set:DataBoundSetter
            var value: String?) : AbstractDescribableImpl<JobTag>(), Serializable {

    var color = "#FFFFFF"

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false

        val that = o as JobTag?

        return if (value != null) value == that!!.value else that!!.value == null

    }

    override fun hashCode(): Int {
        return value!!.hashCode()
    }

    @Extension
    class DescriptorImpl : Descriptor<JobTag>() {
        override fun getDisplayName(): String {
            return Messages.JobTag_DescriptorImpl_DisplayName()
        }
    }

    companion object {

        private const val serialVersionUID = -5667326362260252552L
    }

}

