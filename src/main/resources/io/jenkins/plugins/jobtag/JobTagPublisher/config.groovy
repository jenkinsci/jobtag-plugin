package io.jenkins.plugins.jobtag.JobTagPublisher

import static io.jenkins.plugins.jobtag.JobTagPublisher.DescriptorImpl.JOB_TAG_BLOCK_NAME

def f = namespace(lib.FormTagLib)

f.optionalBlock(name: JOB_TAG_BLOCK_NAME, title: _('Edit Job Tags'), checked: instance != null) {

    f.entry(title:_("Tags:"),  field:"tags") {
        f.repeatableHeteroProperty(field:"tags",  addCaption:_("Add New Tag"), deleteCaption:_("Delete Tag"), honorOrder: true)
    }


}