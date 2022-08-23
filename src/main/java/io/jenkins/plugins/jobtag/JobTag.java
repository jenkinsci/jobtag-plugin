package io.jenkins.plugins.jobtag;

import hudson.Extension;
import hudson.model.AbstractDescribableImpl;
import hudson.model.Descriptor;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;

import java.io.Serializable;

public class JobTag extends AbstractDescribableImpl<JobTag> implements Serializable {

    private static final long serialVersionUID = -5667326362260252552L;

    private String value;

    @DataBoundConstructor
    public JobTag(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @DataBoundSetter
    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JobTag that = (JobTag) o;

        return value != null ? value.equals(that.value) : that.value == null;

    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }

    @Extension
    public static class DescriptorImpl extends Descriptor<JobTag> {
        @Override
        public String getDisplayName() {
            return Messages.JobTag_DescriptorImpl_DisplayName();
        }
    }

}

