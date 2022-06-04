import io.jenkins.plugins.jobtag.JobTag

def ret = new ArrayList();
def jobs = Jenkins.get().getAllItems();
for (j in jobs) {
    p = j.getProperty("io.jenkins.plugins.jobtag.JobTagPublisher");
    if (p != null) {
        tags = ((io.jenkins.plugins.jobtag.JobTagPublisher)p).getTags();
        if (tags.contains(new JobTag('TAG_A')) && tags.contains(new JobTag('TAG_B'))) {
            ret.add(j.getName())
        }
    }
}
return ret