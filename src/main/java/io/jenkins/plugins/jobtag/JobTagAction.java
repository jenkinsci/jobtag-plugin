package io.jenkins.plugins.jobtag;

import com.google.common.collect.ImmutableList;
import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.Extension;
import hudson.model.Action;
import hudson.model.Api;
import hudson.model.Job;
import hudson.model.JobProperty;
import jenkins.model.TransientActionFactory;
import org.kohsuke.stapler.export.Exported;
import org.kohsuke.stapler.export.ExportedBean;

import java.util.*;
import java.util.stream.Collectors;

@ExportedBean
public class JobTagAction implements Action {


    private final Job job;

    JobTagAction(Job job) {
        this.job = job;
    }

    @Exported(visibility = 2)
    public List<String> getTags(){
        JobProperty jobProperties = job.getProperty("io.jenkins.plugins.jobtag.JobTagPublisher");
        Set<JobTag> tags = Optional.ofNullable(jobProperties)
                .filter(JobTagPublisher.class::isInstance)
                .map(JobTagPublisher.class::cast)
                .map(JobTagPublisher::getTags)
                .orElse(Collections.emptySet());
        return tags.stream()
                .filter(Objects::nonNull)
                .map(JobTag::getValue)
                .collect(Collectors.toList());
    }


    public Api getApi() {
        return new Api(this);
    }

    @Override
    public String getIconFileName() {
        return null;
    }

    @Override
    public String getDisplayName() {
        return null;
    }

    @Override
    public String getUrlName() {
        return "tags";
    }

    @Extension
    public static class TransientActionFactoryImpl extends TransientActionFactory {

        @Override
        public Class type() {
            return Job.class;
        }

        @NonNull
        @Override
        public Collection<? extends Action> createFor(@NonNull Object target) {
            return ImmutableList.of(new JobTagAction((Job) target));
        }
    }
}
