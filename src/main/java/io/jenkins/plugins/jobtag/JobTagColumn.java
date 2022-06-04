package io.jenkins.plugins.jobtag;

import hudson.Extension;
import hudson.model.Descriptor;
import hudson.views.ListViewColumn;
import hudson.views.ListViewColumnDescriptor;
import net.sf.json.JSONObject;
import org.kohsuke.stapler.StaplerRequest;


public class JobTagColumn extends ListViewColumn {

    @Extension
    public static final Descriptor<ListViewColumn> DESCRIPTOR = new JobTagColumn.JobTagColumnDescriptor();

    @Override
    public Descriptor<ListViewColumn> getDescriptor() {
        return DESCRIPTOR;
    }


    public static final class JobTagColumnDescriptor extends
            ListViewColumnDescriptor {
        @Override
        public String getDisplayName() {
            return Messages.JobTagColumn_JobTagColumnDescriptor_DisplayName();
        }

        @Override
        public ListViewColumn newInstance(final StaplerRequest request,
                                          final JSONObject formData) throws FormException {
            return new JobTagColumn();
        }

        @Override
        public boolean shownByDefault() {
            return true;
        }
    }
}
