package io.jenkins.plugins.jobtag;

import hudson.Extension;
import hudson.model.Job;
import hudson.model.JobProperty;
import hudson.model.JobPropertyDescriptor;
import net.sf.json.JSONObject;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;
import org.kohsuke.stapler.StaplerRequest;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

public class JobTagPublisher extends JobProperty<Job<?, ?>> {

    private static final Logger LOGGER = Logger.getLogger(JobTagPublisher.class.getName());

    private Set<JobTag> tags = new HashSet();

    @DataBoundConstructor
    public JobTagPublisher() {
    }

    public Set<JobTag> getTags() {
        return tags;
    }

    @DataBoundSetter
    public void setTags(Set<JobTag> tags) {
        this.tags = tags;
    }

    @Extension
    public static final class DescriptorImpl extends JobPropertyDescriptor {


        public boolean isApplicable(Class<? extends Job> jobType) {
            return true;
        }

        public String getDisplayName() {
            return Messages.JobTagPublisher_DescriptorImpl_DisplayName();
        }

        public static final String JOB_TAG_BLOCK_NAME = "JobTag";

        @Override
        public JobProperty<?> newInstance(@Nonnull StaplerRequest req, JSONObject formData) throws FormException {
            JobTagPublisher tpp = req.bindJSON(
                    JobTagPublisher.class,
                    formData.getJSONObject(JOB_TAG_BLOCK_NAME)
            );

            if (tpp == null) {
                LOGGER.fine("Couldn't bind JSON");
                return null;
            }

            for (JobTag tag : tpp.tags) {
                tag.setValue(tag.getValue().toUpperCase());
            }

            return tpp;
        }

    }


}

