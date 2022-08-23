package io.jenkins.plugins.jobtag.TagConfigure


def f = namespace(lib.FormTagLib)

f.section(title:_("Job Tag Configure")) {
    f.checkbox(name: "caseSenstive", title: _('Case Sensitive'), checked: instance.caseSenstive) {
    }
}
