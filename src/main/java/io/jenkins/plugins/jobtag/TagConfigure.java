package io.jenkins.plugins.jobtag;

import edu.umd.cs.findbugs.annotations.NonNull;

import hudson.BulkChange;
import hudson.Extension;
import hudson.XmlFile;
import hudson.model.PersistentDescriptor;
import jenkins.model.GlobalConfiguration;
import jenkins.model.Jenkins;
import net.sf.json.JSONObject;
import org.kohsuke.accmod.Restricted;
import org.kohsuke.accmod.restrictions.NoExternalUse;
import org.kohsuke.stapler.DataBoundSetter;
import org.kohsuke.stapler.StaplerRequest;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Extension(ordinal = TagConfigure.ORDINAL)
public class TagConfigure extends GlobalConfiguration implements PersistentDescriptor {

    private static final Logger LOGGER = Logger.getLogger(TagConfigure.class.getName());

    private static final long serialVersionUID = 3285310269140845583L;

    @Restricted(NoExternalUse.class)
    public static final int ORDINAL = 200;

    private boolean caseSenstive = false;

    public synchronized boolean isCaseSenstive() {
        return caseSenstive;
    }

    @DataBoundSetter
    public synchronized void setCaseSenstive(boolean caseSenstive) {
        this.caseSenstive = caseSenstive;
    }

    public static @NonNull TagConfigure get() {
        return GlobalConfiguration.all().getInstance(TagConfigure.class);
    }

    @Override
    public boolean configure(StaplerRequest req, JSONObject json) {
        try (BulkChange bc = new BulkChange(this)) {
            req.bindJSON(this, json);
            bc.commit();
        } catch (IOException exception) {
            LOGGER.log(
                    Level.WARNING, "Exception occurred while committing bulkchange operation.", exception);
            return false;
        }

        save();
        return true;
    }

    @Override
    public synchronized void load() {
        XmlFile file = getConfigFile();
        if (!file.exists()) {
            caseSenstive = false;
        } else {
            super.load();
        }
    }

    @Override
    public synchronized void save() {
        if (BulkChange.contains(this))  {
            return;
        }

        try {
            getConfigFile().write(this);
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Failed to save " + getConfigFile(), e);
        }
    }

}
